package cn.chenzd.duerosdemo.retrofit;

import com.baidu.dcs.okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static cn.chenzd.duerosdemo.retrofit.ApiService.BASE_URL;


/**
 * Created by chenzaidong on 2017/12/28.
 */

public class RetrofitFactory {

    private static final int TIME_OUT = 4;

    private ApiService mApiService;
    private static class SingleInstance {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();
    }

    public static RetrofitFactory getInstance() {
        return SingleInstance.INSTANCE;
    }



    private RetrofitFactory() {
        //配置网络请求
        Retrofit  mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mApiService = mRetrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return mApiService;
    }
}
