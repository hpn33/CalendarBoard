package hpn332.cb.view.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

import hpn332.cb.R
import hpn332.cb.utils.presenter.Backlog
import hpn332.cb.view.fragment.AddFragmentBacklog
import hpn332.cb.view.fragment.AddFragmentProject
import hpn332.cb.view.fragment.AddFragmentTag
import hpn332.cb.view.fragment.AddFragmentTask
import hpn332.cb.view.fragment.DialogFragmentColorPicker
import hpn332.cb.view.fragment.EditFragmentBacklog
import hpn332.cb.view.fragment.EditFragmentProject
import hpn332.cb.view.fragment.EditFragmentTag
import hpn332.cb.view.fragment.EditFragmentTask
import hpn332.cb.utils.Key
import hpn332.cb.utils.Type
import hpn332.library.view.ColorPanelView

class EditActivity : AppCompatActivity(), DialogFragmentColorPicker.ColorPickerDialogListener, Backlog {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_edit)
		Log.d(TAG, "onCreate: start")

		init()

		Log.d(TAG, "onCreate: end")
	}

	private fun init() {
		Log.d(TAG, "init: start")

		setFragment(checkTypeAndGetFragment())

		Log.d(TAG, "init: end")
	}

	private fun setFragment(fragment: Fragment) {
		Log.d(TAG, "setFragment: start")

		supportFragmentManager
				.beginTransaction()
				.add(R.id.frameLayout, fragment)
				.commit()

		Log.d(TAG, "setFragment: end")
	}

	override fun onColorSelected(color: Int) {
		color_panel!!.color = color
		Log.d(TAG, "onColorSelected: color " + color)
	}

	override fun onDialogDismissed(color: Int) {
		color_panel!!.color = color
		Log.d(TAG, "onDialogDismissed: color " + color)
	}

	private fun checkTypeAndGetFragment(): Fragment {
		Log.d(TAG, "checkTypeAndGetFragment: start type is :: ")

		when (intent.getIntExtra(Key.TYPE, 0)) {
			Type.ADD_PROJECT  -> return AddFragmentProject()
			Type.ADD_BACKLOG  -> return AddFragmentBacklog()
			Type.ADD_TASK     -> return AddFragmentTask()
			Type.ADD_TAG      -> return AddFragmentTag()
			Type.EDIT_PROJECT -> return EditFragmentProject()
			Type.EDIT_BACKLOG -> return EditFragmentBacklog()
			Type.EDIT_TASK    -> return EditFragmentTask()
			Type.EDIT_TAG     -> return EditFragmentTag()
		}

		throw IllegalArgumentException("Unknown type")
	}

	override fun onClickBacklog(backlogId: Int) {
		Log.d(TAG, "onClickBacklog: backlog id :: " + backlogId)

		EditActivity.backlogId = backlogId
	}

	companion object {

		private val TAG = "EditActivity"

		var color_panel: ColorPanelView? = null

		var backlogId = 0
	}
}
