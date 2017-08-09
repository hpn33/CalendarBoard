package hpn332.cb.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import hpn332.cb.utils.model.FragmentStructure;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

	private final List<FragmentStructure> fragmentList = new ArrayList<>();

	public SectionsPagerAdapter(FragmentManager fm) {super(fm);}

	@Override
	public Fragment getItem(int position) {
		return fragmentList.get(position).getFragment();
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

	public void addFragment(Fragment fragment, String title) {
		fragmentList.add(new FragmentStructure(fragment, title));
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return fragmentList.get(position).getTitle();
	}
}