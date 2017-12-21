package hpn332.library.view

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

internal object DrawingUtils {

	fun dpToPx(c: Context, dipValue: Float): Int {
		val metrics = c.resources.displayMetrics

		val value = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)

		// Round
		var res = (value + 0.5).toInt()

		// Ensure at least 1 pixel if val was > 0
		if (res == 0 && value > 0) res = 1

		return res
	}

}
