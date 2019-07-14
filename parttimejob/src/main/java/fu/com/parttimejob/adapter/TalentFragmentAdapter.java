package fu.com.parttimejob.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fu.com.parttimejob.R;

public class TalentFragmentAdapter extends PagerAdapter {

    private List<Fragment> mViews;
    private List<Object> data;

    public TalentFragmentAdapter() {
        mViews = new ArrayList<>();
        data = new ArrayList<>();
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
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

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
