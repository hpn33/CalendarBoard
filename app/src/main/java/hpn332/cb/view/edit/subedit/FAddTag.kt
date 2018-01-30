package hpn332.cb.view.edit.subedit

import android.view.View
import hpn332.cb.R
import hpn332.cb.model.stucture.Tag
import hpn332.cb.utils.Utils
import hpn332.cb.utils.Utils.onClick
import hpn332.cb.view.edit.AEdit
import hpn332.cb.view.edit.EditActivity.Companion.editViewModel
import kotlinx.android.synthetic.main.content_color_picker.view.*
import kotlinx.android.synthetic.main.content_edit_tag_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_tag.view.*

class FAddTag : AEdit() {

    override fun setView(): Int = R.layout.fragment_edit_tag

    override fun init(view: View, id: Int) {

        val title = view.title_editText
        val description = view.description_editText
        val colorPicker = view.color_picker_view

        Utils.setupColorPicker(colorPicker,
            view.color_panel_new,
            view.color_panel_old,
            Utils.NULL)


        with(view) {
            delete_imageView.visibility = View.GONE

            fab.onClick(activity) {
                editViewModel.insertTag(
                    Tag(
                        title = title.text.toString(),
                        desc = description.text.toString(),
                        color = colorPicker.color
                    )
                )
            }

            backArrow_imageView.onClick(activity)
        }
    }
}
