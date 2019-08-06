package fu.com.parttimejob.bean;

import java.io.Serializable;

import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;

/**
 * Description:
 * Data：2019/7/14-23:11
 * Author: fushuaige
 */
public class SameCityBean extends BaseRecyclerModel  {
    private int id;
    private String companyName;
    private String label;
    private int numberOfVirtualCoins;
    private int recruitingNumbers;
    private int redEnvelopeNumber;
    private String salaryAndWelfare;
    private String contactAddress;
    private String businessLicenseImg;
    private String city;
    private String workContent;

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public int getRedEnvelopeNumber() {
        return redEnvelopeNumber;
    }

    public void setRedEnvelopeNumber(int redEnvelopeNumber) {
        this.redEnvelopeNumber = redEnvelopeNumber;
    }

    public String getSalaryAndWelfare() {
        return salaryAndWelfare;
    }

    public void setSalaryAndWelfare(String salaryAndWelfare) {
        this.salaryAndWelfare = salaryAndWelfare;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getBusinessLicenseImg() {
        return businessLicenseImg;
    }

    public void setBusinessLicenseImg(String businessLicenseImg) {
        this.businessLicenseImg = businessLicenseImg;
    }
}
