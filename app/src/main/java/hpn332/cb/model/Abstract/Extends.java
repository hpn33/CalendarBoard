package hpn332.cb.model.database.Abstract;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import hpn332.cb.R;
import hpn332.cb.ui.fragment.DialogFragmentColorPicker;
import hpn332.cb.utils.Key;
import hpn332.library.view.ColorPanelView;

public abstract class Extends extends AppCompatActivity
		implements DialogFragmentColorPicker.ColorPickerDialogListener {

	protected String TAG = getClass().getSimpleName();

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
				checkTypeAndGetFragment(
						getIntent().getIntExtra(Key.TYPE, 0)));

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

	/**
	 * sample  private Fragment checkTypeAndGetFragment(int type) {
	 * Log.d(TAG, "checkTypeAndGetFragment: start type is :: " + type);
	 * switch (type) {
	 * case Type.PROJECT:
	 * return new AddFragmentProject();
	 * case Type.BACKLOG:
	 * return new AddFragmentBacklog();
	 * case Type.TASK:
	 * return new AddFragmentTask();
	 * case Type.TAGs:
	 * return new AddFragmentTag();
	 * default:
	 * throw new IllegalArgumentException("Unknown type");
	 * }
	 * }
	 *
	 * @param type
	 * @return
	 */
	protected abstract Fragment checkTypeAndGetFragment(int type);
}
