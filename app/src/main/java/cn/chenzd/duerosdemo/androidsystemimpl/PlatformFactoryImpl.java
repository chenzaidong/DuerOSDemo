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
package cn.chenzd.duerosdemo.androidsystemimpl;

import android.content.Context;
import android.os.Looper;


import java.util.concurrent.LinkedBlockingDeque;

import cn.chenzd.duerosdemo.androidsystemimpl.alert.AlertsFileDataStoreImpl;
import cn.chenzd.duerosdemo.androidsystemimpl.audioinput.AudioVoiceInputImpl;
import cn.chenzd.duerosdemo.androidsystemimpl.playbackcontroller.IPlaybackControllerImpl;
import cn.chenzd.duerosdemo.androidsystemimpl.player.AudioTrackPlayerImpl;
import cn.chenzd.duerosdemo.androidsystemimpl.player.MediaPlayerImpl;
import cn.chenzd.duerosdemo.androidsystemimpl.wakeup.WakeUpImpl;
import cn.chenzd.duerosdemo.systeminterface.IAlertsDataStore;
import cn.chenzd.duerosdemo.systeminterface.IAudioInput;
import cn.chenzd.duerosdemo.systeminterface.IAudioRecord;
import cn.chenzd.duerosdemo.systeminterface.IHandler;
import cn.chenzd.duerosdemo.systeminterface.IMediaPlayer;
import cn.chenzd.duerosdemo.systeminterface.IPlatformFactory;
import cn.chenzd.duerosdemo.systeminterface.IPlaybackController;
import cn.chenzd.duerosdemo.systeminterface.IWakeUp;
import cn.chenzd.duerosdemo.systeminterface.IWebView;

/**
 * Created by wuruisheng on 2017/6/7.
 */
public class PlatformFactoryImpl implements IPlatformFactory {
    private IHandler mainHandler;
    private IAudioInput voiceInput;
    private IWebView webView;
    private IPlaybackController playback;
    private Context context;
    private IAudioRecord audioRecord;
    private LinkedBlockingDeque<byte[]> linkedBlockingDeque = new LinkedBlockingDeque<>();

    public PlatformFactoryImpl(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public IHandler createHandler() {
        return new HandlerImpl();
    }

    @Override
    public IHandler getMainHandler() {
        if (mainHandler == null) {
            mainHandler = new HandlerImpl(Looper.getMainLooper());
        }

        return mainHandler;
    }

    @Override
    public IAudioRecord getAudioRecord() {
        if (audioRecord == null) {
            audioRecord = new AudioRecordThread(linkedBlockingDeque);
        }
        return audioRecord;
    }

    @Override
    public IWakeUp getWakeUp() {
        return new WakeUpImpl(context, linkedBlockingDeque);
    }

    @Override
    public IAudioInput getVoiceInput() {
        if (voiceInput == null) {
            voiceInput = new AudioVoiceInputImpl(linkedBlockingDeque);
        }

        return voiceInput;
    }

    @Override
    public IMediaPlayer createMediaPlayer() {
        return new MediaPlayerImpl();
    }

    @Override
    public IMediaPlayer createAudioTrackPlayer() {
        return new AudioTrackPlayerImpl();
    }

    @Override
    public IAlertsDataStore createAlertsDataStore() {
        return new AlertsFileDataStoreImpl();
    }

    @Override
    public IWebView getWebView() {
        return webView;
    }

    @Override
    public IPlaybackController getPlayback() {
        if (playback == null) {
            playback = new IPlaybackControllerImpl();
        }

        return playback;
    }

    @Override
    public void setWebView(IWebView webView) {
        this.webView = webView;
    }
}