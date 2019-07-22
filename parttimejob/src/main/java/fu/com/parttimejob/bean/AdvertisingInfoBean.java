package fu.com.parttimejob.bean;

import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;

/**
 * Description:
 * Data：2019/7/21-23:59
 * Author: fushuaige
 */
public class AdvertisingInfoBean extends BaseRecyclerModel {
    private String longitude;
    private String latitude;
    private int id;
    private String thirdAccount;
    private String companyName;
    private int numberOfVirtualCoins;
    private int redEnvelopeNumber;
    private String city;
    private String advertisementImg;
    private String publichDate;
    private int forwordCount;
    private int viewCount;
    private int  unclaimedVirtualCoins;
    private String advertisementContent;
    private String headImg;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getThirdAccount() {
        return thirdAccount;
    }

    public void setThirdAccount(String thirdAccount) {
        this.thirdAccount = thirdAccount;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getUnclaimedVirtualCoins() {
        return unclaimedVirtualCoins;
    }

    public void setUnclaimedVirtualCoins(int unclaimedVirtualCoins) {
        this.unclaimedVirtualCoins = unclaimedVirtualCoins;
    }

    public String getAdvertisementContent() {
        return advertisementContent;
    }

    public void setAdvertisementContent(String advertisementContent) {
        this.advertisementContent = advertisementContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getNumberOfVirtualCoins() {
        return numberOfVirtualCoins;
    }

    public void setNumberOfVirtualCoins(int numberOfVirtualCoins) {
        this.numberOfVirtualCoins = numberOfVirtualCoins;
    }

    public int getRedEnvelopeNumber() {
        return redEnvelopeNumber;
    }

    public void setRedEnvelopeNumber(int redEnvelopeNumber) {
        this.redEnvelopeNumber = redEnvelopeNumber;
    }

    public String getAdvertisementImg() {
        return advertisementImg;
    }

    public void setAdvertisementImg(String advertisementImg) {
        this.advertisementImg = advertisementImg;
    }

    public String getPublichDate() {
        return publichDate;
    }

    public void setPublichDate(String publichDate) {
        this.publichDate = publichDate;
    }

    public int getForwordCount() {
        return forwordCount;
    }

    public void setForwordCount(int forwordCount) {
        this.forwordCount = forwordCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
