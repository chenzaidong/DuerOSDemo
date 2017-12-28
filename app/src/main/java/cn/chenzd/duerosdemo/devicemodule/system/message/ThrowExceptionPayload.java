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
package cn.chenzd.duerosdemo.devicemodule.system.message;

import cn.chenzd.duerosdemo.framework.message.Payload;

/**
 * ThrowException指令对应的payload结构
 * <p>
 * Created by wuruisheng on 2017/6/8.
 */
public class ThrowExceptionPayload extends Payload {
    private String code;
    private String description;

    public ThrowExceptionPayload() {
    }

    public ThrowExceptionPayload(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "code=" + code + "  description=" + description;
    }
}