package fu.com.parttimejob.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lljjcoder.citylist.Toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;
import fu.com.parttimejob.R;
import fu.com.parttimejob.base.baseadapter.BaseRecyclerModel;
import fu.com.parttimejob.bean.GetLabelsBean;
import fu.com.parttimejob.bean.ResumeInfoBean;
import fu.com.parttimejob.utils.SPUtil;

public class TalentFragmentAdapter extends PagerAdapter {

    private List<Fragment> mViews;
    private List<ResumeInfoBean> data;

    public TalentFragmentAdapter() {
        mViews = new ArrayList<>();
        data = new ArrayList<>();
    }

    public List<ResumeInfoBean> getData() {
        return data;
    }

    public void setData(List<ResumeInfoBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    ViewGroup contvi = null;

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        contvi = container;
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_jianli, container, false);
        container.addView(view);
        String[] strarr = null;
        ArrayList<String> listimg = new ArrayList();
        List<BaseRecyclerModel> datas = new ArrayList();
        TextView myLocation = view.findViewById(R.id.myLocation);

        LinearLayout myPhotos = view.findViewById(R.id.myPhotos);
        JZVideoPlayerStandard videoplayer = view.findViewById(R.id.videoplayer);
        ImageView myPhoto = view.findViewById(R.id.myPhoto);

        TextView phone = view.findViewById(R.id.phone);
        myLocation.setText(data.get(position).getCity());
        TextView name = view.findViewById(R.id.name);
        name.setText(data.get(position).getName());
        phone.setText("联系电话：" + data.get(position).getContactInformation());

        ImageView playphone = view.findViewById(R.id.playphone);
        playphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        if (SPUtil.getInt(container.getContext(), "vipLevel", 0) <1) {
                            ToastUtils.showShortToast(container.getContext(), "充值后才可以拨打电话噢");
                        } else {
                            callPhone(data.get(position).getContactInformation());
                        }

            }
        });
        TextView sex = view.findViewById(R.id.sex);
        RecyclerView biaoQian = view.findViewById(R.id.biaoQianList);
        biaoQian.setLayoutManager(new GridLayoutManager(view.getContext(),3));
        ChooseDreamJobListAdapter adapter = new ChooseDreamJobListAdapter();
        biaoQian.setAdapter(adapter);
        String sexs = "";
        if (data.get(position).getSex() == 1) {
            sexs = "男";
        } else {
            sexs = "女";
        }
        sex.setText(sexs + "  " + data.get(position).getAge() + "岁");
        TextView myInfo = view.findViewById(R.id.myInfo);
//        myInfo.setText(data.get(position).getPersonalProfile());
        if (data.get(position).getPicOrVedioSource() != null && !data.get(position).getPicOrVedioSource().equals("")) {
            strarr = data.get(position).getPicOrVedioSource().split(";");
            int index = 0;
            while (index < strarr.length) {
                listimg.add(strarr[index]);
                index++;
            }

            if (listimg.size() < 1) {

                Glide.with(container.getContext())
                        .load("")
                        .placeholder(R.mipmap.defind)
                        .into(myPhoto);
                myPhoto.setVisibility(View.VISIBLE);
                videoplayer.setVisibility(View.GONE);
            } else if (listimg.size() == 1) {
                Glide.with(container.getContext())
                        .load(listimg.get(0))
                        .placeholder(R.mipmap.defind)
                        .into(myPhoto);
                myPhoto.setVisibility(View.VISIBLE);
                videoplayer.setVisibility(View.GONE);
            } else {
                videoplayer.setUp(listimg.get(0),
                        JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                        "视频简历");
                Glide.with(container.getContext())
                        .load(listimg.get(1))
                        .placeholder(R.mipmap.defind)
                        .into(videoplayer.thumbImageView);
                myPhoto.setVisibility(View.GONE);
                videoplayer.setVisibility(View.VISIBLE);
            }
        }

        if (!TextUtils.isEmpty(data.get(position).getLabelName())) {
            if (data.get(position).getLabelName().contains(",")) {
                String[] split = data.get(position).getLabelName().split(",");
                for (String s : split) {
                    GetLabelsBean bean = new GetLabelsBean();
                    bean.setLabels(s);
                    datas.add(bean);
                }
            } else {
                GetLabelsBean bean = new GetLabelsBean();
                bean.setLabels(data.get(position).getLabelName());
                datas.add(bean);
            }
            adapter.addAll(datas);
        } else {
            biaoQian.setVisibility(View.GONE);
        }
        return view;
    }

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        contvi.getContext().startActivity(intent);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
