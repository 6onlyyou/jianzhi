package fu.com.parttimejob.bean;

import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;

public class KuaiDiBean extends BaseRecyclerModel {

    /**
     * thirdAccount : 123456789
     * name : 啊冲
     * goodsId : 1
     * receiveTime : 2019-07-26 17:04:06.0
     * phoneNumber : 110
     * address : 杭州市西湖区10号地下车库
     * id : 3
     */

    private String thirdAccount;
    private String name;
    private int goodsId;
    private String receiveTime;
    private String phoneNumber;
    private String address;
    private int id;

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

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
