package fu.com.parttimejob.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by YuanGang on 2018/4/27.
 */

public class GetTokenEntity implements Serializable {

    @SerializedName("refresh_token")
    private String mRefreshToken;
    @SerializedName("openid")
    private String mOpenid;
    @SerializedName("scope")
    private String mScope;
    @SerializedName("access_token")
    private String mAccessToken;
    @SerializedName("expires_in")
    private String mExpiresIn;

    public String getmAccessToken() {
        return mAccessToken;
    }

    public void setmAccessToken(String mAccessToken) {
        this.mAccessToken = mAccessToken;
    }

    public String getmExpiresIn() {
        return mExpiresIn;
    }

    public void setmExpiresIn(String mExpiresIn) {
        this.mExpiresIn = mExpiresIn;
    }

    public String getmRefreshToken() {
        return mRefreshToken;
    }

    public void setmRefreshToken(String mRefreshToken) {
        this.mRefreshToken = mRefreshToken;
    }

    public String getmOpenid() {
        return mOpenid;
    }

    public void setmOpenid(String mOpenid) {
        this.mOpenid = mOpenid;
    }

    public String getmScope() {
        return mScope;
    }

    public void setmScope(String mScope) {
        this.mScope = mScope;
    }

}
