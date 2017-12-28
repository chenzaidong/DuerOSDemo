package cn.chenzd.duerosdemo.retrofit;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by chenzaidong on 2017/12/28.
 */

public interface ApiService {
    String BASE_URL ="https://openapi.baidu.com/oauth/2.0/";

    @GET("token")
    Observable<TokenEntity> getToken(@QueryMap Map<String, Object> request);
}
