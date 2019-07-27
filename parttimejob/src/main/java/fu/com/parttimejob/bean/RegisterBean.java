package fu.com.parttimejob.bean;

/**
 * Description:
 * Data：2019/7/5-17:50
 * Author: fushuaige
 */
public class RegisterBean {
    private String tip;
    private Boolean isRegister;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Boolean getRegister() {
        return isRegister;
    }

    public void setRegister(Boolean register) {
        isRegister = register;
    }
}
