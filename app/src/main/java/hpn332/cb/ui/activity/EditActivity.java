package hpn332.cb.ui.activity;

import android.support.v4.app.Fragment;
import android.util.Log;

import hpn332.cb.ui.fragment.EditFragmentBacklog;
import hpn332.cb.ui.fragment.EditFragmentProject;
import hpn332.cb.ui.fragment.EditFragmentTag;
import hpn332.cb.ui.fragment.EditFragmentTask;
import hpn332.cb.model.database.Abstract.Extends;
import hpn332.cb.utils.Type;

public class EditActivity extends Extends {

	@Override
	protected Fragment checkTypeAndGetFragment(int type) {
		Log.d(TAG, "checkTypeAndGetFragment: start type is :: " + type);
		switch (type) {
			case Type.PROJECT:
				return new EditFragmentProject();
			case Type.BACKLOG:
				return new EditFragmentBacklog();
			case Type.TASK:
				return new EditFragmentTask();
			case Type.TAGs:
				return new EditFragmentTag();
			default:
				throw new IllegalArgumentException("Unknown type");
		}
	}
}
