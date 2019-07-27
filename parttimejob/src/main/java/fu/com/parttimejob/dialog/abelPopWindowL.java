package fu.com.parttimejob.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.heixiu.errand.net.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import fu.com.parttimejob.R;
import fu.com.parttimejob.adapter.ChooseDreamJobListAdapter;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.bean.GetLabelsBean;
import fu.com.parttimejob.retrofitNet.RxUtils;
import io.reactivex.functions.Consumer;

/**
 * Description:
 * Data：2019/6/28-12:18
 * Author: fushuaige
 */
public class abelPopWindowL extends PopupWindow {
    private View conentView;
    private ChooseDreamJobListAdapter adapter;
    private RecyclerView dreamJobList;
    public abelPopWindowL(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.label_popup_dialog, null);
        dreamJobList =conentView.findViewById(R.id.dreamJobList);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        @SuppressLint("ResourceAsColor") ColorDrawable dw = new ColorDrawable(R.color.white);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);
        adapter =new ChooseDreamJobListAdapter();
        dreamJobList.setLayoutManager(new GridLayoutManager(context, 4)) ;
        dreamJobList.setAdapter(adapter);
        final ArrayList<BaseRecyclerModel>  list =new ArrayList();
        adapter.addAll(list);
        final ArrayList<BaseRecyclerModel>  list2 = new ArrayList();
            RxUtils.wrapRestCall(RetrofitFactory.INSTANCE.getRetrofit().getLabel()).subscribe(new Consumer<GetLabelsBean>() {
                @Override
                public void accept(GetLabelsBean getLabelsBean) throws Exception {
                    if (getLabelsBean.getLabels() != null && !getLabelsBean.getLabels().equals("")) {
                        String[] strarr1 =getLabelsBean.getLabels().substring(0, getLabelsBean.getLabels().length()).split(",");
                        int index =0;
                        for ( index = 0; index< strarr1.length-1;index++) {
                            GetLabelsBean baseRecyclerModel = new GetLabelsBean();
                            baseRecyclerModel.setLabels(strarr1[index]);
                            if(index == 0){
                                baseRecyclerModel.setLabelssel(true);
                            }
                            index++;//自增
                            list2.add(baseRecyclerModel);
                        }
                        adapter.addAll(list2);
                    }
                }
            });

    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    private View view;
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width , 0);
        } else {
            this.dismiss();
        }
    }
}
