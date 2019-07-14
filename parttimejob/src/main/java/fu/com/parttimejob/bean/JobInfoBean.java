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
    private int salaryAndWelfare;
    private String businessLicenseImg;

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

    public int getSalaryAndWelfare() {
        return salaryAndWelfare;
    }

    public void setSalaryAndWelfare(int salaryAndWelfare) {
        this.salaryAndWelfare = salaryAndWelfare;
    }

    public String getBusinessLicenseImg() {
        return businessLicenseImg;
    }

    public void setBusinessLicenseImg(String businessLicenseImg) {
        this.businessLicenseImg = businessLicenseImg;
    }
}
