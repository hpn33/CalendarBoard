package hpn332.cb.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import hpn332.cb.R;
import hpn332.cb.ui.fragment.EditBacklogFragment;
import hpn332.cb.ui.fragment.EditProjectFragment;
import hpn332.cb.ui.fragment.EditTagFragment;
import hpn332.cb.ui.fragment.EditTaskFragment;
import hpn332.cb.utils.Key;

public class EditActivity extends AppCompatActivity {

	private static final String TAG = "EditActivity";

	public static final int PROJECT = 0;
	public static final int BACKLOG = 1;
	public static final int TASK    = 2;
	public static final int TAGs    = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		Log.d(TAG, "onCreate: start");

		init();

		Log.d(TAG, "onCreate: end");
	}

	private void init() {
		Log.d(TAG, "init: start");


		setFragment(
				checkTypeAndGetFragment(
						getIntent().getIntExtra(Key.KEY_TYPE, 0)));

		Log.d(TAG, "init: end");
	}

	private void setFragment(Fragment fragment) {
		Log.d(TAG, "setFragment: start");
		getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.frameLayout, fragment)
				.commit();
		Log.d(TAG, "setFragment: end");
	}

	private Fragment checkTypeAndGetFragment(int type) {
		Log.d(TAG, "checkTypeAndGetFragment: start type is :: " + type);
		switch (type) {
			case PROJECT:
				return new EditProjectFragment();
			case BACKLOG:
				return new EditBacklogFragment();
			case TASK:
				return new EditTaskFragment();
			case TAGs:
				return new EditTagFragment();
			default:
				throw new IllegalArgumentException("Unknown type");
		}
	}


}
