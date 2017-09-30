package hpn332.cb.ui.activity;

import android.support.v4.app.Fragment;
import android.util.Log;

import hpn332.cb.ui.fragment.AddFragmentBacklog;
import hpn332.cb.ui.fragment.AddFragmentProject;
import hpn332.cb.ui.fragment.AddFragmentTag;
import hpn332.cb.ui.fragment.AddFragmentTask;
import hpn332.cb.model.database.Abstract.Extends;
import hpn332.cb.utils.Type;

public class AddActivity extends Extends {

	@Override
	protected Fragment checkTypeAndGetFragment(int type) {

		Log.d(TAG, "checkTypeAndGetFragment: start type is :: " + type);
		switch (type) {
			case Type.PROJECT:
				return new AddFragmentProject();
			case Type.BACKLOG:
				return new AddFragmentBacklog();
			case Type.TASK:
				return new AddFragmentTask();
			case Type.TAGs:
				return new AddFragmentTag();
			default:
				throw new IllegalArgumentException("Unknown type");
		}
	}
}
