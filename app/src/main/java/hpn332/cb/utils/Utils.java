package hpn332.cb.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import hpn332.cb.ui.activity.AddActivity;
import hpn332.cb.ui.activity.EditActivity;
import hpn332.library.view.ColorPanelView;
import hpn332.library.view.ColorPickerView;

public class Utils {

	private static final String TAG = "Utils";

	public static final int NULL = 0;

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

	public static void goToEdit(Context context, int type, int position, int step) {
		goTo(context, EditActivity.class, type, position, step, NULL);
	}

	public static void goToAdd(Context context, int type, int project) {
		goTo(context, AddActivity.class, type, NULL, NULL, project);
	}

	public static void goTo(
			Context context, Class aClass, int type, int position, int step, int project) {
		context.startActivity(new Intent(context.getApplicationContext(), aClass)
				                      .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
				                      .putExtra(Key.TYPE, type)
				                      .putExtra(Key.POSITION, position)
				                      .putExtra(Key.PROJECT, project)
				                      .putExtra(Key.STEP, step));
	}
}
