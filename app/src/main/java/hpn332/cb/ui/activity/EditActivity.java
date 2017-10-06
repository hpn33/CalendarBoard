package hpn332.cb.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import hpn332.cb.R;
import hpn332.cb.ui.fragment.AddFragmentProject;
import hpn332.cb.ui.fragment.AddFragmentTag;
import hpn332.cb.ui.fragment.AddFragmentTask;
import hpn332.cb.ui.fragment.DialogFragmentColorPicker;
import hpn332.cb.ui.fragment.EditFragmentProject;
import hpn332.cb.ui.fragment.EditFragmentTag;
import hpn332.cb.ui.fragment.EditFragmentTask;
import hpn332.cb.utils.U;
import hpn332.cb.utils.U.Type;
import hpn332.library.view.ColorPanelView;

public class EditActivity extends AppCompatActivity
		implements DialogFragmentColorPicker.ColorPickerDialogListener {

	private static final String TAG = "EditActivity";

	public static ColorPanelView color_panel;

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
				checkTypeAndGetFragment());

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

		switch (getIntent().getIntExtra(U.Key.TYPE, 0)) {
			case Type.ADD_PROJECT:
				return new AddFragmentProject();
			case Type.ADD_BACKLOG:
				return new AddFragmentProject();
			case Type.ADD_TASK:
				return new AddFragmentTask();
			case Type.ADD_TAG:
				return new AddFragmentTag();
			case Type.EDIT_PROJECT:
				return new EditFragmentProject();
			case Type.EDIT_BACKLOG:
				return new EditFragmentProject();
			case Type.EDIT_TASK:
				return new EditFragmentTask();
			case Type.EDIT_TAG:
				return new EditFragmentTag();
		}

		throw new IllegalArgumentException("Unknown type");
	}
}
