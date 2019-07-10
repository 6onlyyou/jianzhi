package fu.com.parttimejob.bean;

/**
 * Description:
 * Data：2019/7/10-16:21
 * Author: fushuaige
 */
public class UserInfoBean {
    private String thirdAccount;
    private int identyType;
    private int loginType;
    private String registrationDate;
    private String city;
    private String longitude;
    private String latitude;

    public String getThirdAccount() {
        return thirdAccount;
    }

    public void setThirdAccount(String thirdAccount) {
        this.thirdAccount = thirdAccount;
    }

    public int getIdentyType() {
        return identyType;
    }

    public void setIdentyType(int identyType) {
        this.identyType = identyType;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
