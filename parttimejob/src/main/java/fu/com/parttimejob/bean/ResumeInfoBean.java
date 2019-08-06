package fu.com.parttimejob.bean;

/**
 * Description:
 * Data：2019/7/14-1:03
 * Author: fushuaige
 */
public class ResumeInfoBean {
    private String thirdAccount;
    private String name;
    private int sex;
    private String age;
    private String personalProfile;
    private String picOrVedioSource;
    private String city;
    private String phoneNum;
    private String labelName;
    public String headImg;
    public String contactInformation;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPersonalProfile() {
        return personalProfile;
    }

    public void setPersonalProfile(String personalProfile) {
        this.personalProfile = personalProfile;
    }

    public String getPicOrVedioSource() {
        return picOrVedioSource;
    }

    public void setPicOrVedioSource(String picOrVedioSource) {
        this.picOrVedioSource = picOrVedioSource;
    }
}
