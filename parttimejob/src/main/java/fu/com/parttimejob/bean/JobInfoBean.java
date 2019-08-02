package fu.com.parttimejob.bean;

import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;

/**
 * Description:
 * Data：2019/7/14-0:52
 * Author: fushuaige
 */
public class JobInfoBean extends BaseRecyclerModel {
    private String thirdAccount;
    private String companyName;
    private String label;
    private int numberOfVirtualCoins;
    private int recruitingNumbers;
    private String salaryAndWelfare;
    private String businessLicenseImg;
    private String publichDate;
    private String city;
    private int id;

    private int redEnvelopeNumber;
    private String contactAddress;
    private String workContent;
    private int forwordCount;
    private int viewCount;


    public int getRedEnvelopeNumber() {
        return redEnvelopeNumber;
    }

    public void setRedEnvelopeNumber(int redEnvelopeNumber) {
        this.redEnvelopeNumber = redEnvelopeNumber;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublichDate() {
        return publichDate;
    }

    public void setPublichDate(String publichDate) {
        this.publichDate = publichDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getThirdAccount() {
        return thirdAccount;
    }

    public void setThirdAccount(String thirdAccount) {
        this.thirdAccount = thirdAccount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getNumberOfVirtualCoins() {
        return numberOfVirtualCoins;
    }

    public void setNumberOfVirtualCoins(int numberOfVirtualCoins) {
        this.numberOfVirtualCoins = numberOfVirtualCoins;
    }

    public int getRecruitingNumbers() {
        return recruitingNumbers;
    }

    public void setRecruitingNumbers(int recruitingNumbers) {
        this.recruitingNumbers = recruitingNumbers;
    }

    public String getSalaryAndWelfare() {
        return salaryAndWelfare;
    }

    public void setSalaryAndWelfare(String salaryAndWelfare) {
        this.salaryAndWelfare = salaryAndWelfare;
    }

    public String getBusinessLicenseImg() {
        return businessLicenseImg;
    }

    public void setBusinessLicenseImg(String businessLicenseImg) {
        this.businessLicenseImg = businessLicenseImg;
    }
}
