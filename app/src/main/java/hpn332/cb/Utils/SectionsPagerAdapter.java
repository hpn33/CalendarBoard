package hpn332.cb.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "SectionsPagerAdapter";

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String>   fragmentName  = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {super(fm);}

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String name) {
        mFragmentList.add(fragment);
        fragmentName.add(name);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentName.get(position);
    }
}