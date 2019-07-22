package fu.com.parttimejob.bean;

/**
 * Description:
 * Data：2019/7/22-17:00
 * Author: fushuaige
 */
public class AdInfoBean {
    private int id;
    private String companyName;
    private int numberOfVirtualCoins;
    private int redEnvelopeNumber;
    private String advertisementImg;
    private String city;
    private String publichDate;
    private String state;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPublichDate() {
        return publichDate;
    }

    public void setPublichDate(String publichDate) {
        this.publichDate = publichDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
