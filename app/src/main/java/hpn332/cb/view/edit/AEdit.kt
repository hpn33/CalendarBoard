package hpn332.cb.view.edit

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

import hpn332.cb.R
import hpn332.cb.view.fragment.DialogFragmentColorPicker
import hpn332.cb.utils.Key
import hpn332.cb.utils.Type

class AEdit : AppCompatActivity(), DialogFragmentColorPicker.ColorPickerDialogListener, Backlog {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        Log.d(TAG, "onCreate: start")

        vm = ViewModelProviders.of(this).get(VMEdit::class.java)

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
        vm.color_panel?.color = color
        Log.d(TAG, "onColorSelected: color " + color)
    }

    override fun onDialogDismissed(color: Int) {
        vm.color_panel?.color = color
        Log.d(TAG, "onDialogDismissed: color " + color)
    }

    private fun checkTypeAndGetFragment(): Fragment {
        Log.d(TAG, "checkTypeAndGetFragment: start type is :: ")

        when (intent.getIntExtra(Key.TYPE, 0)) {
            Type.ADD_PROJECT  -> return FAddProject()
            Type.ADD_BACKLOG  -> return FAddBacklog()
            Type.ADD_TASK     -> return FAddTask()
            Type.ADD_TAG      -> return FAddTag()
            Type.EDIT_PROJECT -> return FEditProject()
            Type.EDIT_BACKLOG -> return FEditBacklog()
            Type.EDIT_TASK    -> return FEditTask()
            Type.EDIT_TAG     -> return FEditTag()
        }

        throw IllegalArgumentException("Unknown type")
    }

    override fun onClickBacklog(backlogId: Int) {
        Log.d(TAG, "onClickBacklog: backlog id :: " + backlogId)

        vm.backlogId = backlogId
    }

    companion object {

        private val TAG = "AEdit"

        lateinit var vm: VMEdit

    }
}
