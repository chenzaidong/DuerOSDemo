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
package cn.chenzd.duerosdemo.framework.dispatcher;

import cn.chenzd.duerosdemo.util.ObjectMapperUtil;

import org.codehaus.jackson.JsonProcessingException;

import java.io.IOException;

/**
 * 字节数据转换成对象
 * <p>
 * Created by wuruisheng on 2017/5/12.
 */
public class Parser {
    protected <T> T parse(byte[] bytes, Class<T> clazz) throws IOException {
        try {
            return ObjectMapperUtil.instance().getObjectReader().withType(clazz).readValue(bytes);
        } catch (JsonProcessingException e) {
            String unparsedContent = new String(bytes, "UTF-8");
            String message = String.format("failed to parse %1$s", clazz.getSimpleName());
            throw new DcsJsonProcessingException(message, e, unparsedContent);
        }
    }
}