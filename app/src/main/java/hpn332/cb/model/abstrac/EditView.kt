package hpn332.cb.model.abstrac

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import hpn332.cb.R
import hpn332.cb.utils.Key
import hpn332.cb.view.DialogFragmentColorPicker
import hpn332.library.view.ColorPanelView

abstract class EditView : AppCompatActivity(), DialogFragmentColorPicker.ColorPickerDialogListener {

	protected var tag = javaClass.simpleName

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_fragment)

		init()
	}

	private fun init() =
		setFragment(
				checkTypeAndGetFragment(
						intent.getIntExtra(Key.TYPE, 0)))


	private fun setFragment(fragment: Fragment) =
		supportFragmentManager
				.beginTransaction()
				.add(R.id.frameLayout, fragment)
				.commit()

	override fun onColorSelected(color: Int) {
		color_panel!!.color = color
		Log.d(tag, "onColorSelected: color " + color)
	}

	override fun onDialogDismissed(color: Int) {
		color_panel!!.color = color
		Log.d(tag, "onDialogDismissed: color " + color)
	}

	/**
	 * sample  private Fragment checkTypeAndGetFragment(int type) {
	 * Log.d(tag_id, "checkTypeAndGetFragment: start type is :: " + type);
	 * switch (type) {
	 * case Type.ADD_PROJECT:
	 * return new AddFragmentProject();
	 * case Type.ADD_BACKLOG:
	 * return new AddFragmentBacklog();
	 * case Type.ADD_TASK:
	 * return new AddFTask();
	 * case Type.ADD_TAG:
	 * return new AddFragmentTag();
	 * default:
	 * throw new IllegalArgumentException("Unknown type");
	 * }
	 * }
	 *
	 * @param type
	 * @return
	 */
	protected abstract fun checkTypeAndGetFragment(type: Int): Fragment

	companion object {

		var color_panel: ColorPanelView? = null
	}
}
