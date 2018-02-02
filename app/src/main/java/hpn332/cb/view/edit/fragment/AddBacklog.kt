package hpn332.cb.view.edit.fragment

import android.view.View
import hpn332.cb.ButtonColor
import hpn332.cb.R
import hpn332.cb.model.stucture.BackLog
import hpn332.cb.utils.Utils
import hpn332.cb.utils.Utils.onClick
import hpn332.cb.view.DialogFragmentColorPicker
import hpn332.cb.model.abstrac.Frag
import hpn332.cb.view.edit.EditActivity.Companion.editViewModel
import kotlinx.android.synthetic.main.content_edit_backlog_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_backlog.view.*

class AddBacklog : Frag() {

    override fun setView(): Int = R.layout.fragment_edit_backlog

    override fun init(view: View, id: Int) {
        val title = view.title_editText
        val description = view.description_editText
        editViewModel.colorPanelView = view.color_panel

        with(view) {
            delete_imageView.visibility = View.GONE

            backArrow_imageView.onClick(activity)

            fab.onClick(activity) {
                editViewModel.insertBackLog(
                    BackLog(
                        title = title.text.toString(),
                        desc = description.text.toString(),
                        project_id = id,
                        color = editViewModel.colorPanelView!!.color
                    )
                )
            }
        }
        (view.color_picker_view_dialog_button as ButtonColor)
            .setOnShowDialogListener(object : ButtonColor.OnShowDialogListener {
                override fun onShowColorPickerDialog(initColor: Int) {
                    DialogFragmentColorPicker.newInstance(Utils.NULL)
                        .show(activity.fragmentManager, "d")
                }

            })
    }
}
