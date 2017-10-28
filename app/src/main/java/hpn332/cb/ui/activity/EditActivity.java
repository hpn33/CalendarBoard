package hpn332.cb.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import hpn332.cb.R;
import hpn332.cb.ui.fragment.AddFragmentBacklog;
import hpn332.cb.ui.fragment.AddFragmentProject;
import hpn332.cb.ui.fragment.AddFragmentTag;
import hpn332.cb.ui.fragment.AddFragmentTask;
import hpn332.cb.ui.fragment.DialogFragmentColorPicker;
import hpn332.cb.ui.fragment.EditFragmentBacklog;
import hpn332.cb.ui.fragment.EditFragmentProject;
import hpn332.cb.ui.fragment.EditFragmentTag;
import hpn332.cb.ui.fragment.EditFragmentTask;
import hpn332.cb.ui.fragment.ListFragmentBacklog;
import hpn332.cb.utils.Key;
import hpn332.cb.utils.Type;
import hpn332.library.view.ColorPanelView;

public class EditActivity extends AppCompatActivity
		implements DialogFragmentColorPicker.ColorPickerDialogListener,
		ListFragmentBacklog.OnBacklogFragment {

	private static final String TAG = "EditActivity";

	public static ColorPanelView color_panel;

	public static int backlogId = 0;

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

		setFragment(checkTypeAndGetFragment());

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

	@Override
	public void onColorSelected(int color) {
		color_panel.setColor(color);
		Log.d(TAG, "onColorSelected: color " + color);
	}

	@Override
	public void onDialogDismissed(int color) {
		color_panel.setColor(color);
		Log.d(TAG, "onDialogDismissed: color " + color);
	}

	protected Fragment checkTypeAndGetFragment() {
		Log.d(TAG, "checkTypeAndGetFragment: start type is :: ");

		switch (getIntent().getIntExtra(Key.TYPE, 0)) {
			case Type.ADD_PROJECT:
				return new AddFragmentProject();
			case Type.ADD_BACKLOG:
				return new AddFragmentBacklog();
			case Type.ADD_TASK:
				return new AddFragmentTask();
			case Type.ADD_TAG:
				return new AddFragmentTag();
			case Type.EDIT_PROJECT:
				return new EditFragmentProject();
			case Type.EDIT_BACKLOG:
				return new EditFragmentBacklog();
			case Type.EDIT_TASK:
				return new EditFragmentTask();
			case Type.EDIT_TAG:
				return new EditFragmentTag();
		}

		throw new IllegalArgumentException("Unknown type");
	}

	@Override
	public void onClickBacklog(int backlogId) {
		Log.d(TAG, "onClickBacklog: backlog id :: " + backlogId);

		EditActivity.backlogId = backlogId;
	}
}
