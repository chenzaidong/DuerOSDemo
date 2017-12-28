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
package cn.chenzd.duerosdemo.http.builder;

import java.util.Map;

/**
 * 请求是否包含参数
 * <p>
 * Created by guxiuzhong@baidu.com on 2017/5/15.
 */
public interface HasParamInterface {
    /**
     * 构建请求参数
     *
     * @param params 请求参数
     * @return OkHttpRequestBuilder
     */
    OkHttpRequestBuilder params(Map<String, String> params);

    /**
     * @param key 请求参数的key
     * @param val 请求参数的值
     * @return OkHttpRequestBuilder
     */
    OkHttpRequestBuilder addParams(String key, String val);
}