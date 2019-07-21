package fu.com.parttimejob.bean;

import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;

/**
 * Description:
 * Data：2019/7/3-22:29
 * Author: fushuaige
 */
public class GetLabelsBean extends BaseRecyclerModel {
    private String labels;
    private Boolean labelssel;

    public Boolean getLabelssel() {
        return labelssel;
    }

    public void setLabelssel(Boolean labelssel) {
        this.labelssel = labelssel;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }
}
