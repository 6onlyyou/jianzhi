package fu.com.parttimejob.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;
import fu.com.parttimejob.R;
import fu.com.parttimejob.bean.ResumeInfoBean;

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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_jianli, container, false);
        container.addView(view);
        String[] strarr =null;
        ArrayList<String> listimg = new ArrayList();
        TextView myLocation  = view.findViewById(R.id.myLocation);

        LinearLayout myPhotos  = view.findViewById(R.id.myPhotos);
        JZVideoPlayerStandard videoplayer  = view.findViewById(R.id.videoplayer);
        ImageView myPhoto  = view.findViewById(R.id.myPhoto);

        myLocation.setText(data.get(position).getCity());
        TextView name  = view.findViewById(R.id.name);
        name.setText(data.get(position).getName());
        TextView sex  = view.findViewById(R.id.sex);
        String sexs = "";
        if (data.get(position).getSex() == 1) {
            sexs = "男";
        } else {
            sexs = "女";
        }
        sex.setText(sexs+"  "+data.get(position).getAge()+"岁");
        TextView myInfo  = view.findViewById(R.id.myInfo);
        myInfo.setText(data.get(position).getPersonalProfile());
        TextView phone  = view.findViewById(R.id.phone);
        phone.setText("联系电话："+data.get(position).getPhoneNum());
        if (data.get(position).getPicOrVedioSource() != null && !data.get(position).getPicOrVedioSource().equals("")) {
            strarr = data.get(position).getPicOrVedioSource().substring(0, data.get(position).getPicOrVedioSource().length()).split(";");
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
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
