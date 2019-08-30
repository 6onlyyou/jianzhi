package fu.com.parttimejob.bean;

import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;

public class BillBean extends BaseRecyclerModel {

    /**
     * count : +10
     * name : 微信充值
     * time : 2019-08-03 22:54:09
     */

    private String count;
    private String name;
    private String time;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
