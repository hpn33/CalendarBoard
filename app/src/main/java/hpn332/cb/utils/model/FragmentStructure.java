package hpn332.cb.utils.model;

import android.support.v4.app.Fragment;

public class FragmentStructure {

	private Fragment fragment;
	private String   title;

	public FragmentStructure(Fragment fragment, String title) {
		this.fragment = fragment;
		this.title = title;
	}

	public Fragment getFragment() {return fragment;}

	public String getTitle()      {return title;}
}
