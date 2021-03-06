package cn.chenzd.duerosdemo.retrofit;

/**
 * Created by chenzaidong on 2017/12/28.
 */

public class TokenEntity {
    /**
     * access_token : 1.a6b7dbd428f731035f771b8d15063f61.86400.1292922000-2346678-124328
     * expires_in : 86400
     * refresh_token : 2.385d55f8615fdfd9edb7c4b5ebdc3e39.604800.1293440400-2346678-124328
     * scope : public
     * session_key : ANXxSNjwQDugf8615OnqeikRMu2bKaXCdlLxn
     * session_secret : 248APxvxjCZ0VEC43EYrvxqaK4oZExMB
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String scope;
    private String session_key;
    private String session_secret;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getSession_secret() {
        return session_secret;
    }

    public void setSession_secret(String session_secret) {
        this.session_secret = session_secret;
    }

    @Override
    public String toString() {
        return "TokenEntity{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", scope='" + scope + '\'' +
                ", session_key='" + session_key + '\'' +
                ", session_secret='" + session_secret + '\'' +
                '}';
    }
}
