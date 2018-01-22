package hpn332.cb.view.edit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import hpn332.cb.R
import hpn332.cb.ButtonColor
import hpn332.cb.utils.helper.ProviderHelper
import hpn332.cb.utils.Key
import hpn332.cb.utils.List
import hpn332.cb.view.edit.AEdit.Companion.vm
import hpn332.cb.view.fragment.DialogFragmentColorPicker
import kotlinx.android.synthetic.main.content_edit_backlog_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_backlog.view.*

class FEditBacklog : Fragment() {

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

        val arrayList = List.L_BACKLOG

        val position = activity.intent.getIntExtra(Key.POSITION, 0)
        val id = arrayList[position].id
        val initColor = arrayList[position].color

        val title: EditText = view.title_editText
        val description: EditText = view.description_editText

        vm.colorPanelView = view.color_panel
        vm.colorPanelView!!.color = initColor

        title.setText(arrayList[position].title)
        description.setText(arrayList[position].desc)

        view.fab.setOnClickListener {
            ProviderHelper.updateOneBacklog(
                id,
                title.text.toString(),
                description.text.toString(),
                vm.colorPanelView!!.color)

            activity.finish()
            Log.d(TAG, "init: update id ::" + id)
        }

        view.delete_imageView.setOnClickListener {
            ProviderHelper.deleteOneBacklog(id)
            activity.finish()
            Log.d(TAG, "init: delete :: " + id)
        }

        view.backArrow_imageView
            .setOnClickListener { activity.finish() }

        (view.color_picker_view_dialog_button as ButtonColor)
            .setOnShowDialogListener(object : ButtonColor.OnShowDialogListener {
                override fun onShowColorPickerDialog(initColor: Int) {
                    DialogFragmentColorPicker.newInstance(initColor)
                        .show(activity.fragmentManager, "d")
                }

            })

        Log.d(TAG, "init: end")
    }

    companion object {

        private val TAG = "FEditBacklog"
    }
}
