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
package cn.chenzd.duerosdemo.framework.message;

import cn.chenzd.duerosdemo.devicemodule.alerts.message.DeleteAlertPayload;
import cn.chenzd.duerosdemo.devicemodule.alerts.message.SetAlertPayload;
import cn.chenzd.duerosdemo.devicemodule.audioplayer.message.ClearQueuePayload;
import cn.chenzd.duerosdemo.devicemodule.audioplayer.message.PlayPayload;
import cn.chenzd.duerosdemo.devicemodule.audioplayer.message.StopPayload;
import cn.chenzd.duerosdemo.devicemodule.screen.message.HtmlPayload;
import cn.chenzd.duerosdemo.devicemodule.screen.message.RenderVoiceInputTextPayload;
import cn.chenzd.duerosdemo.devicemodule.speakcontroller.message.AdjustVolumePayload;
import cn.chenzd.duerosdemo.devicemodule.speakcontroller.message.SetMutePayload;
import cn.chenzd.duerosdemo.devicemodule.speakcontroller.message.SetVolumePayload;
import cn.chenzd.duerosdemo.devicemodule.system.message.SetEndPointPayload;
import cn.chenzd.duerosdemo.devicemodule.system.message.ThrowExceptionPayload;
import cn.chenzd.duerosdemo.devicemodule.voiceinput.message.ListenPayload;
import cn.chenzd.duerosdemo.devicemodule.voiceoutput.message.SpeakPayload;

import java.util.HashMap;

/**
 * 利用namespace和name做为key存储不同payload子类class并且find
 * <p>
 * Created by wuruisheng on 2017/6/1.
 */
public class PayloadConfig {
    private final HashMap<String, Class<?>> payloadClass;

    private static class PayloadConfigHolder {
        private static final PayloadConfig instance = new PayloadConfig();
    }

    public static PayloadConfig getInstance() {
        return PayloadConfigHolder.instance;
    }

    private PayloadConfig() {
        payloadClass = new HashMap<>();

        // AudioInputImpl
        String namespace = cn.chenzd.duerosdemo.devicemodule.voiceinput.ApiConstants.NAMESPACE;
        String name = cn.chenzd.duerosdemo.devicemodule.voiceinput.ApiConstants.Directives.Listen.NAME;
        insertPayload(namespace, name, ListenPayload.class);

        // VoiceOutput
        namespace = cn.chenzd.duerosdemo.devicemodule.voiceoutput.ApiConstants.NAMESPACE;
        name = cn.chenzd.duerosdemo.devicemodule.voiceoutput.ApiConstants.Directives.Speak.NAME;
        insertPayload(namespace, name, SpeakPayload.class);

        // audio
        namespace = cn.chenzd.duerosdemo.devicemodule.audioplayer.ApiConstants.NAMESPACE;
        name = cn.chenzd.duerosdemo.devicemodule.audioplayer.ApiConstants.Directives.Play.NAME;
        insertPayload(namespace, name, PlayPayload.class);

        name = cn.chenzd.duerosdemo.devicemodule.audioplayer.ApiConstants.Directives.Stop.NAME;
        insertPayload(namespace, name, StopPayload.class);

        name = cn.chenzd.duerosdemo.devicemodule.audioplayer.ApiConstants.Directives.ClearQueue.NAME;
        insertPayload(namespace, name, ClearQueuePayload.class);

        //  alert
        namespace = cn.chenzd.duerosdemo.devicemodule.alerts.ApiConstants.NAMESPACE;
        name = cn.chenzd.duerosdemo.devicemodule.alerts.ApiConstants.Directives.SetAlert.NAME;
        insertPayload(namespace, name, SetAlertPayload.class);

        name = cn.chenzd.duerosdemo.devicemodule.alerts.ApiConstants.Directives.DeleteAlert.NAME;
        insertPayload(namespace, name, DeleteAlertPayload.class);

        // SpeakController
        namespace = cn.chenzd.duerosdemo.devicemodule.speakcontroller.ApiConstants.NAMESPACE;
        name = cn.chenzd.duerosdemo.devicemodule.speakcontroller.ApiConstants.Directives.SetVolume.NAME;
        insertPayload(namespace, name, SetVolumePayload.class);

        name = cn.chenzd.duerosdemo.devicemodule.speakcontroller.ApiConstants.Directives.AdjustVolume.NAME;
        insertPayload(namespace, name, AdjustVolumePayload.class);

        name = cn.chenzd.duerosdemo.devicemodule.speakcontroller.ApiConstants.Directives.SetMute.NAME;
        insertPayload(namespace, name, SetMutePayload.class);

        // System
        namespace = cn.chenzd.duerosdemo.devicemodule.system.ApiConstants.NAMESPACE;
        name = cn.chenzd.duerosdemo.devicemodule.system.ApiConstants.Directives.SetEndpoint.NAME;
        insertPayload(namespace, name, SetEndPointPayload.class);

        name = cn.chenzd.duerosdemo.devicemodule.system.ApiConstants.Directives.ThrowException.NAME;
        insertPayload(namespace, name, ThrowExceptionPayload.class);

        // Screen
        namespace = cn.chenzd.duerosdemo.devicemodule.screen.ApiConstants.NAMESPACE;
        name = cn.chenzd.duerosdemo.devicemodule.screen.ApiConstants.Directives.HtmlView.NAME;
        insertPayload(namespace, name, HtmlPayload.class);
        name = cn.chenzd.duerosdemo.devicemodule.screen.ApiConstants.Directives.RenderVoiceInputText.NAME;
        insertPayload(namespace, name, RenderVoiceInputTextPayload.class);
    }

    void insertPayload(String namespace, String name, Class<?> type) {
        final String key = namespace + name;
        payloadClass.put(key, type);
    }

    Class<?> findPayloadClass(String namespace, String name) {
        final String key = namespace + name;
        return payloadClass.get(key);
    }
}