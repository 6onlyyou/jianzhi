package fu.com.parttimejob.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 */

public class WXPayEntity implements Serializable {

    private static final long serialVersionUID = 1144209869581148930L;
    /**
     * appid : wx1d3b4994f7a135da
     * mch_id : 1493995262
     * prepayid : wx201712110943237209bf96000439181872
     * noncestr : 16740f6d-721c-433d-8741-07e917
     * timestamp : 1512956603
     * sign : 5547F6BAAD1D89EF8FCF61C04ECBB46E
     * package : Sign=WXPay
     */

    private String appid;
    private String mch_id;
    private String prepayid;
    private String noncestr;
    private String timestamp;
    private String sign;
    @SerializedName("package")
    private String mPackage;
    private String partnerid;

    public String getmPackage() {
        return mPackage;
    }

    public void setmPackage(String mPackage) {
        this.mPackage = mPackage;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPackageX() {
        return mPackage;
    }

    public void setPackageX(String packageX) {
        this.mPackage = packageX;
    }
}
