package hpn332.cb.view.edit

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import hpn332.cb.R
import hpn332.cb.utils.Key
import hpn332.cb.utils.Type
import hpn332.cb.view.DialogFragmentColorPicker
import hpn332.cb.view.edit.fragment.*

class EditActivity : AppCompatActivity(), DialogFragmentColorPicker.ColorPickerDialogListener {

    companion object {

        private val TAG = "EditActivity"

        lateinit var editViewModel: EditViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        editViewModel = ViewModelProviders.of(this).get(EditViewModel::class.java)

        savedInstanceState ?: setFragment(checkTypeAndGetFragment())
    }

    private fun setFragment(fragment: Fragment) =
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameLayout, fragment)
            .commit()

    override fun onColorSelected(color: Int) {
        editViewModel.colorPanelView?.color = color
        Log.d(TAG, "onColorSelected: color " + color)
    }

    override fun onDialogDismissed(color: Int) {
        editViewModel.colorPanelView?.color = color
        Log.d(TAG, "onDialogDismissed: color " + color)
    }

    private fun checkTypeAndGetFragment(): Fragment =
        when (intent.getIntExtra(Key.TYPE, 0)) {
            Type.ADD_PROJECT  -> AddProject()
            Type.ADD_BACKLOG  -> AddBacklog()
            Type.ADD_TASK     -> AddTask()
            Type.ADD_TAG      -> AddTag()
            Type.EDIT_PROJECT -> EditProject()
            Type.EDIT_BACKLOG -> EditBacklog()
            Type.EDIT_TASK    -> EditTask()
            Type.EDIT_TAG     -> EditTag()
            else              ->
                throw IllegalArgumentException("Unknown type")
        }
}
