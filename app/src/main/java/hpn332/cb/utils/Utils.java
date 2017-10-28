package hpn332.cb.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import hpn332.cb.ui.activity.EditActivity;
import hpn332.library.view.ColorPanelView;
import hpn332.library.view.ColorPickerView;

public class Utils {

	private static final String TAG = "Utils";

	public static final int NULL = -1;

	public static void setupColorPicker(
			ColorPickerView colorPicker, final ColorPanelView newColorPanel,
			ColorPanelView oldColorPanel, int initColor) {

		if (initColor == NULL) initColor = -16777216;

		colorPicker.setAlphaSliderVisible(false);
		colorPicker.setColor(initColor, true);
		oldColorPanel.setColor(initColor);
		newColorPanel.setColor(initColor);

		colorPicker.setOnColorChangedListener(newColor -> {
			newColorPanel.setColor(newColor);
			Log.d(TAG, "onColorChanged: color :: " + newColor);
		});
	}

	public static void goTo(Context context, int type) {
		goTo(context, type, NULL);
	}

	public static void goToP(Context context, int type, int project) {
		goTo(context, type, NULL, NULL, project);
	}

	public static void goTo(Context context, int type, int position) {
		goTo(context, type, position, NULL, NULL);
	}

	public static void goTo(Context context, int type, int position, int step) {
		goTo(context, type, position, step, NULL);
	}

	private static void goTo(
			Context context, int type, int position, int step, int project) {

		Intent intent = new Intent(context.getApplicationContext(), EditActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
				.putExtra(Key.TYPE, type);

		if (position != NULL) intent.putExtra(Key.POSITION, position);
		if (project != NULL) intent.putExtra(Key.PROJECT, project);
		if (step != NULL) intent.putExtra(Key.STEP, step);

		context.startActivity(intent);
	}
}
