package fu.com.parttimejob.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by YuanGang on
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    public ArrayList<String> titles = new ArrayList<>();
    public ArrayList<Fragment> fragments = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addTitle(String title) {
        titles.add(title);
    }

    public void addFragments(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles.size() > 0)
            return titles.get(position);
        return null;
    }
}
