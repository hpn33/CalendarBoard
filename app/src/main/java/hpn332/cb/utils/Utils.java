package hpn332.cb.utils;

import android.util.Log;

import hpn332.library.view.ColorPanelView;
import hpn332.library.view.ColorPickerView;

public class Utils {

	private static final String TAG = "Utils";

	public static final int NO_COLOR = 0;

	public static void setupColorPicker(
			ColorPickerView colorPicker, final ColorPanelView newColorPanel,
			ColorPanelView oldColorPanel, int initColor) {

		if (initColor == NO_COLOR) initColor = -16777216;

		colorPicker.setAlphaSliderVisible(false);
		colorPicker.setColor(initColor, true);
		oldColorPanel.setColor(initColor);
		newColorPanel.setColor(initColor);

		colorPicker.setOnColorChangedListener(newColor -> {
			newColorPanel.setColor(newColor);
			Log.d(TAG, "onColorChanged: color :: " + newColor);
		});

	}
}
