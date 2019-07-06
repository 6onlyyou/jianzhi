package fu.com.parttimejob.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by YuanGang on 2018/4/27.
 */

public class WXInfoEntity implements Serializable {
    @SerializedName("city")
    private String mCity;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("headimgurl")
    private String mHeadimgurl;

    public ArrayList getmPrivilege() {
        return mPrivilege;
    }

    public void setmPrivilege(ArrayList mPrivilege) {
        this.mPrivilege = mPrivilege;
    }

    @SerializedName("privilege")
    private ArrayList mPrivilege;
    @SerializedName("unionid")
    private String mUnionid;
    @SerializedName("openid")
    private String mOpenid;
    @SerializedName("nickname")
    private String mNickname;
    @SerializedName("sex")
    private String mSex;
    @SerializedName("province")
    private String mProvince;

    public String getmOpenid() {
        return mOpenid;
    }

    public void setmOpenid(String mOpenid) {
        this.mOpenid = mOpenid;
    }

    public String getmNickname() {
        return mNickname;
    }

    public void setmNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    public String getmSex() {
        return mSex;
    }

    public void setmSex(String mSex) {
        this.mSex = mSex;
    }

    public String getmProvince() {
        return mProvince;
    }

    public void setmProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmHeadimgurl() {
        return mHeadimgurl;
    }

    public void setmHeadimgurl(String mHeadimgurl) {
        this.mHeadimgurl = mHeadimgurl;
    }


    public String getmUnionid() {
        return mUnionid;
    }

    public void setmUnionid(String mUnionid) {
        this.mUnionid = mUnionid;
    }



}