package fu.com.parttimejob.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
        TextView myLocation  = view.findViewById(R.id.myLocation);
        myLocation.setText(data.get(position).getCity());
        TextView name  = view.findViewById(R.id.name);
        name.setText(data.get(position).getName());
        TextView sex  = view.findViewById(R.id.sex);
        sex.setText(data.get(position).getSex()+"  "+data.get(position).getAge()+"岁");
        TextView myInfo  = view.findViewById(R.id.myInfo);
        myInfo.setText(data.get(position).getPersonalProfile());
        TextView phone  = view.findViewById(R.id.phone);
        phone.setText("联系电话："+data.get(position).getPhoneNum());

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
