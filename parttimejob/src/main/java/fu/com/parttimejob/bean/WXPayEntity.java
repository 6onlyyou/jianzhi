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
     * prepay_id : wx201712110943237209bf96000439181872
     * nonce_str : 16740f6d-721c-433d-8741-07e917
     * timestamp : 1512956603
     * sign : 5547F6BAAD1D89EF8FCF61C04ECBB46E
     * package : Sign=WXPay
     */

    private String appid;
    private String mch_id;
    private String prepay_id;
    private String nonce_str;
    private int timestamp;
    private String sign;
    @SerializedName("package")
    private String mPackage;

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

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
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
