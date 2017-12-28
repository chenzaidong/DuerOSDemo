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
package cn.chenzd.duerosdemo.app;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.HashMap;
import java.util.Map;

import cn.chenzd.duerosdemo.BuildConfig;
import cn.chenzd.duerosdemo.http.HttpConfig;
import cn.chenzd.duerosdemo.retrofit.RetrofitFactory;
import cn.chenzd.duerosdemo.retrofit.TokenEntity;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * DcsSample application
 * <p>
 * Created by zhangyan42@baidu.com on 2017/5/11.
 */
public class DcsSampleApplication extends Application {
    private static volatile DcsSampleApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initLogger();
        initToken();
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .tag("DuerOSDemo")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private void initToken() {
//        grant_type=client_credentials&
//                client_id=Va5yQRHlA4Fq4eR3LT0vuXV4&
//                client_secret= 0rDSjzQ20XUj5itV7WRtznPQSzr5pVw2&
        Map<String,Object> map = new HashMap<>();
        map.put("grant_type","client_credentials");
        map.put("client_id","lEL9bjDQkYwndmhljsZgudSEMKSRGuol");
        map.put("client_secret","P0ptcFMGgu7Mql05Ly4kGvOYvemVnFrH");
        RetrofitFactory.getInstance().getApiService().getToken(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tokenEntity -> {
                    Logger.i("tokenEntity="+tokenEntity);
                    HttpConfig.setAccessToken(tokenEntity.getAccess_token());
                }, throwable -> Logger.e(throwable,throwable.getMessage()));
    }

    public static DcsSampleApplication getInstance() {
        return instance;
    }
}