package hpn332.cb.view.edit

import android.arch.lifecycle.ViewModelProviders
import android.content.ContentValues.TAG
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

import hpn332.cb.R
import hpn332.cb.view.fragment.DialogFragmentColorPicker
import hpn332.cb.utils.Key
import hpn332.cb.utils.Type
import hpn332.cb.view.task.AListTask.Companion.vm

class AEdit : AppCompatActivity(), DialogFragmentColorPicker.ColorPickerDialogListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        Log.d(TAG, "onCreate: start")

        vm = ViewModelProviders.of(this).get(VMEdit::class.java)

        savedInstanceState ?: setFragment(checkTypeAndGetFragment())

        Log.d(TAG, "onCreate: end")
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameLayout, fragment)
            .commit()
    }


    override fun onColorSelected(color: Int) {
        vm.colorPanelView?.color = color
        Log.d(TAG, "onColorSelected: color " + color)
    }

    override fun onDialogDismissed(color: Int) {
        vm.colorPanelView?.color = color
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

    companion object {

        private val TAG = "AEdit"

        lateinit var vm: VMEdit

    }
}
