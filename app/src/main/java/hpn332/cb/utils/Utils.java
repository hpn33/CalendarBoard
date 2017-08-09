package hpn332.cb.utils;

import hpn332.cb.R;

public class Utils {

	public static int getColor(int color) {

		switch (color) {
			case 1:
				return R.color.green;
			case 2:
				return R.color.orange;
			case 3:
				return R.color.blue;
			case 4:
				return R.color.gold;

			default:
				return R.color.green;
		}
	}
}
