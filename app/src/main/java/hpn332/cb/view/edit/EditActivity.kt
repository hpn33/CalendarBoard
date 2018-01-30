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
import hpn332.cb.view.edit.subedit.*

class EditActivity : AppCompatActivity(), DialogFragmentColorPicker.ColorPickerDialogListener {

    companion object {

        private val TAG = "EditActivity"

        lateinit var editViewModel: EditViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        editViewModel = ViewModelProviders.of(this).get(EditViewModel::class.java)

        savedInstanceState ?: setFragment(checkTypeAndGetFragment())
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameLayout, fragment)
            .commit()
    }

    override fun onColorSelected(color: Int) {
        editViewModel.colorPanelView?.color = color
        Log.d(TAG, "onColorSelected: color " + color)
    }

    override fun onDialogDismissed(color: Int) {
        editViewModel.colorPanelView?.color = color
        Log.d(TAG, "onDialogDismissed: color " + color)
    }

    private fun checkTypeAndGetFragment(): Fragment {

        return when (intent.getIntExtra(Key.TYPE, 0)) {
            Type.ADD_PROJECT  -> FAddProject()
            Type.ADD_BACKLOG  -> FAddBacklog()
            Type.ADD_TASK     -> FAddTask()
            Type.ADD_TAG      -> FAddTag()
            Type.EDIT_PROJECT -> FEditProject()
            Type.EDIT_BACKLOG -> FEditBacklog()
            Type.EDIT_TASK    -> FEditTask()
            Type.EDIT_TAG     -> FEditTag()
            else              ->
                throw IllegalArgumentException("Unknown type")
        }
    }
}
