package fu.com.parttimejob.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.heixiu.errand.net.RetrofitFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fu.com.parttimejob.R;
import fu.com.parttimejob.adapter.ChooseDreamJobListAdapter;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.base.baseadapter.OnItemClickListener;
import fu.com.parttimejob.bean.GetLabelsBean;
import fu.com.parttimejob.retrofitNet.RxUtils;
import fu.com.parttimejob.utils.SPUtil;
import io.reactivex.functions.Consumer;

/**
 * Description:
 * Data：2019/6/28-12:18
 * Author: fushuaige
 */
public class abelPopWindowL extends PopupWindow {
    private static final String TAG = "abelPopWindowL";
    public OnPopListClick onPopListClick;
    private ArrayList<String> localStrarr;
    private View conentView;
    private ChooseDreamJobListAdapter adapter;
    private RecyclerView dreamJobList;
    /**
     * 显示popupWindow
     *
     * @param parent
     */
    private View view;

    public abelPopWindowL(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.label_popup_dialog, null);
        dreamJobList = conentView.findViewById(R.id.dreamJobList);
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
        adapter = new ChooseDreamJobListAdapter();
        dreamJobList.setLayoutManager(new GridLayoutManager(context, 4));
        dreamJobList.setAdapter(adapter);
        final ArrayList<BaseRecyclerModel> data = new ArrayList();

        adapter.setOnItemClickListener(new OnItemClickListener<BaseRecyclerModel>() {
            @Override
            public void onClick(View view, BaseRecyclerModel baseRecyclerModel, int position) {
                adapter.chooseAloneSelectPosition(position);
                if (adapter.getSelectPositions().size() == 0) {
                    onPopListClick.onItemClick("");
                    SPUtil.putString(context, "searchLabelName", "");

                } else {
                    String labels = ((GetLabelsBean) adapter.getData().get(adapter.getSelectPositions().get(0))).getLabels();
                    SPUtil.putString(context, "searchLabelName", labels);
                    onPopListClick.onItemClick(labels);
                }
                dismiss();
            }
        });
        if (SPUtil.getString(context, "searchLabelName", "") != null && !SPUtil.getString(context, "searchLabelName", "").equals("")) {
            localStrarr = new ArrayList<>();
            localStrarr.add(SPUtil.getString(context, "searchLabelName", ""));
        } else {
            localStrarr = new ArrayList<>();
        }

        final ArrayList<String> allStrarr = new ArrayList<>();

        RxUtils.wrapRestCall(RetrofitFactory.INSTANCE.getRetrofit().getLabel()).subscribe(new Consumer<GetLabelsBean>() {
            @Override
            public void accept(GetLabelsBean getLabelsBean) {
                if (getLabelsBean.getLabels() != null && !getLabelsBean.getLabels().equals("")) {
                    List<String> serverStrarr = Arrays.asList(getLabelsBean.getLabels().split(","));

                    if (localStrarr.size() == 0) {
                        allStrarr.addAll(serverStrarr);
                    } else {
                        allStrarr.add(localStrarr.get(0));
                        for (String stra : serverStrarr) {
                            if (!allStrarr.contains(stra)) {
                                allStrarr.add(stra);
                            }
                        }
                    }

                    for (int i = 0; i < allStrarr.size(); i++) {
                        GetLabelsBean bean = new GetLabelsBean();
                        bean.setLabels(allStrarr.get(i));
                        if (i == 0) {
                            bean.setLabelssel(true);
                        }
                        data.add(bean);
                    }
                    for (BaseRecyclerModel datum : data) {
                        Log.i(TAG, "accept: " + ((GetLabelsBean) datum).getLabels());
                    }
                    adapter.addAll(data);
                    adapter.changeSelectPosition(0);
                    onPopListClick.onItemClick(((GetLabelsBean) adapter.getData().get(adapter.getSelectPositions().get(0))).getLabels());
                }
            }
        });

    }

    public void showPopupWindow(View parent, OnPopListClick onPopListClick) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width, 0);
        } else {
            this.dismiss();
        }
        this.onPopListClick = onPopListClick;
    }


    public interface OnPopListClick {
        void onItemClick(String jobName);
    }
}
