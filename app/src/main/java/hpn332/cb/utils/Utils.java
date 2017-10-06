package hpn332.cb.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import hpn332.cb.ui.activity.EditActivity;
import hpn332.library.view.ColorPanelView;
import hpn332.library.view.ColorPickerView;

public class Utils {

	private static final String TAG = "Utils";

	public static final int ZERO = 0;

	public static void setupColorPicker(
			ColorPickerView colorPicker, final ColorPanelView newColorPanel,
			ColorPanelView oldColorPanel, int initColor) {

		if (initColor == ZERO) initColor = -16777216;

		colorPicker.setAlphaSliderVisible(false);
		colorPicker.setColor(initColor, true);
		oldColorPanel.setColor(initColor);
		newColorPanel.setColor(initColor);

		colorPicker.setOnColorChangedListener(newColor -> {
			newColorPanel.setColor(newColor);
			Log.d(TAG, "onColorChanged: color :: " + newColor);
		});
	}

	public static void goTo(Context context, int type, int position, int step) {
		goTo(context, type, position, step, ZERO);
	}

	public static void goTo(Context context, int type, int position) {
		goTo(context, type, position, ZERO);
	}

	private static void goTo(
			Context context, int type, int position, int step, int project) {
		context.startActivity(new Intent(context.getApplicationContext(), EditActivity.class)
				                      .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
				                      .putExtra(U.Key.TYPE, type)
				                      .putExtra(U.Key.POSITION, position)
				                      .putExtra(U.Key.PROJECT, project)
				                      .putExtra(U.Key.STEP, step));
	}
}
