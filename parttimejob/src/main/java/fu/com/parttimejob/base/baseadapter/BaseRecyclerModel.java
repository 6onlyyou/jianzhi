package fu.com.parttimejob.base.baseadapter;

/**
 *
 * 刘宇飞创建 on 2017/5/18.
 * 描述：
 */

public class BaseRecyclerModel {
    public int viewType;
    public String viewTypeSt;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getViewTypeSt() {
        return viewTypeSt;
    }

    public void setViewTypeSt(String viewTypeSt) {
        this.viewTypeSt = viewTypeSt;
    }
}
