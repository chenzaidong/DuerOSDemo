/*
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.chenzd.duerosdemo.devicemodule.screen;

import cn.chenzd.duerosdemo.devicemodule.screen.message.HtmlPayload;
import cn.chenzd.duerosdemo.devicemodule.screen.message.LinkClickedPayload;
import cn.chenzd.duerosdemo.devicemodule.screen.message.RenderVoiceInputTextPayload;
import cn.chenzd.duerosdemo.devicemodule.system.HandleDirectiveException;
import cn.chenzd.duerosdemo.framework.BaseDeviceModule;
import cn.chenzd.duerosdemo.framework.IMessageSender;
import cn.chenzd.duerosdemo.framework.message.ClientContext;
import cn.chenzd.duerosdemo.framework.message.Directive;
import cn.chenzd.duerosdemo.framework.message.Event;
import cn.chenzd.duerosdemo.framework.message.Header;
import cn.chenzd.duerosdemo.framework.message.MessageIdHeader;
import cn.chenzd.duerosdemo.framework.message.Payload;
import cn.chenzd.duerosdemo.systeminterface.IWebView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Screen模块处理并执行服务下发的指令，如HtmlView指令，以及发送事件，如LinkClicked事件
 * <p>
 * Created by wuruisheng on 2017/5/31.
 */
public class ScreenDeviceModule extends BaseDeviceModule {
    private final IWebView webView;
    private List<IRenderVoiceInputTextListener> listeners;
    private final List<IRenderListener> renderListeners;

    public ScreenDeviceModule(IWebView webView, IMessageSender messageSender) {
        super(ApiConstants.NAMESPACE, messageSender);
        this.webView = webView;
        webView.addWebViewListener(url -> sendLinkClickedEvent(url));
        this.listeners = Collections.synchronizedList(new ArrayList<IRenderVoiceInputTextListener>());
        renderListeners = new ArrayList<>();
    }

    @Override
    public ClientContext clientContext() {
        return null;
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String name = directive.header.getName();
        if (name.equals(ApiConstants.Directives.HtmlView.NAME)) {
            handleHtmlPayload(directive.getPayload());
        } else if (name.equals(ApiConstants.Directives.RenderVoiceInputText.NAME)) {
            handleRenderVoiceInputTextPayload(directive.getPayload());
            handleScreenDirective(directive);
        } else if (name.equals(ApiConstants.Directives.RenderCard.NAME)) {
            handleScreenDirective(directive);
        } else if (name.equals(ApiConstants.Directives.RenderHint.NAME)) {
            handleScreenDirective(directive);
        } else {
            String message = "VoiceOutput cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }
    }

    @Override
    public void release() {
        listeners.clear();
    }

    private void handleHtmlPayload(Payload payload) {
        if (payload instanceof HtmlPayload) {
            HtmlPayload htmlPayload = (HtmlPayload) payload;
            webView.loadUrl(htmlPayload.getUrl());
        }
    }

    private void handleRenderVoiceInputTextPayload(Payload payload) {
        RenderVoiceInputTextPayload textPayload = (RenderVoiceInputTextPayload) payload;
        fireRenderVoiceInputText(textPayload);
    }

    private void handleScreenDirective(Directive directive) {
        for (IRenderListener listener : renderListeners) {
            listener.onRenderDirective(directive);
        }
    }

    private void sendLinkClickedEvent(String url) {
        String name = ApiConstants.Events.LinkClicked.NAME;
        Header header = new MessageIdHeader(getNameSpace(), name);

        LinkClickedPayload linkClickedPayload = new LinkClickedPayload(url);
        Event event = new Event(header, linkClickedPayload);
        if (messageSender != null) {
            messageSender.sendEvent(event);
        }
    }

    private void fireRenderVoiceInputText(RenderVoiceInputTextPayload payload) {
        for (IRenderVoiceInputTextListener listener : listeners) {
            listener.onRenderVoiceInputText(payload);
        }
    }

    public void addRenderVoiceInputTextListener(IRenderVoiceInputTextListener listener) {
        listeners.add(listener);
    }

    public void addRenderListener(IRenderListener listener) {
        renderListeners.add(listener);
    }

    public void removeRenderListener(IRenderListener listener) {
        renderListeners.remove(listener);
    }

    public interface IRenderVoiceInputTextListener {
        /**
         * 接收到RenderVoiceInputText指令时回调
         *
         * @param payload 内容
         */
        void onRenderVoiceInputText(RenderVoiceInputTextPayload payload);

    }

    public interface IRenderListener {
        void onRenderDirective(Directive directive);
    }
}
