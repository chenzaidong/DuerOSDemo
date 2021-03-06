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
package cn.chenzd.duerosdemo.devicemodule.voiceoutput;

/**
 * 定义了表示Voice Output模块的namespace、name，以及其事件、指令的name
 * <p>
 * Created by guxiuzhong@baidu.com on 2017/6/1.
 */
public class ApiConstants {
    public static final String NAMESPACE = "ai.dueros.device_interface.voice_output";
    public static final String NAME = "VoiceOutputInterface";

    public static final class Events {
        public static final class SpeechStarted {
            public static final String NAME = SpeechStarted.class.getSimpleName();
        }

        public static final class SpeechFinished {
            public static final String NAME = SpeechFinished.class.getSimpleName();
        }

        public static final class SpeechState {
            public static final String NAME = SpeechState.class.getSimpleName();
        }
    }

    public static final class Directives {
        public static final class Speak {
            public static final String NAME = Speak.class.getSimpleName();
        }
    }
}
