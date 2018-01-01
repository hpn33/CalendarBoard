package hpn332.cb.view.edit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import hpn332.cb.ButtonColor
import hpn332.cb.R
import hpn332.cb.utils.Key
import hpn332.cb.utils.Utils
import hpn332.cb.utils.helper.ProviderHelper
import hpn332.cb.view.edit.AEdit.Companion.vm
import hpn332.cb.view.fragment.DialogFragmentColorPicker
import kotlinx.android.synthetic.main.content_edit_backlog_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_backlog.view.*

class FAddBacklog : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        Log.d(TAG, "onCreateView: start")

        val view = inflater!!.inflate(R.layout.fragment_edit_backlog, container, false)

        init(view)

        Log.d(TAG, "onCreateView: end")

        return view
    }

    private fun init(view: View) {

        Log.d(TAG, "init: start")

        val title: EditText = view.title_editText
        val description: EditText = view.description_editText

        vm.color_panel = view.color_panel

        view.delete_imageView.visibility = View.GONE

        view.backArrow_imageView
            .setOnClickListener { activity.finish() }

        (view.color_picker_view_dialog_button as ButtonColor)
            .setOnShowDialogListener(object : ButtonColor.OnShowDialogListener {
                override fun onShowColorPickerDialog(initColor: Int) {
                    DialogFragmentColorPicker.newInstance(
                        Utils.NULL)
                        .show(activity.fragmentManager, "d")
                }

            })

        view.fab.setOnClickListener {
            ProviderHelper.insertNewBacklog(
                activity.intent.getIntExtra(Key.PROJECT, 0),
                title.text.toString(),
                description.text.toString(),
                vm.color_panel!!.color)

            activity.finish()
        }

        Log.d(TAG, "init: end")
    }

    companion object {

        private val TAG = "FAddBacklog"
    }

}
