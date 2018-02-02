package hpn332.cb.view.edit.fragment

import android.arch.lifecycle.Observer
import android.view.View
import hpn332.cb.R
import hpn332.cb.model.abstrac.Frag
import hpn332.cb.model.stucture.Tag
import hpn332.cb.utils.Utils
import hpn332.cb.utils.Utils.onClick
import hpn332.cb.view.edit.EditActivity.Companion.editViewModel
import kotlinx.android.synthetic.main.content_color_picker.view.*
import kotlinx.android.synthetic.main.content_edit_tag_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_tag.view.*

class EditTag : Frag() {

    override fun setView(): Int = R.layout.fragment_edit_tag

    override fun init(view: View, id: Int) {

        val title = view.title_editText
        val description = view.description_editText
        val colorPicker = view.color_picker_view

        editViewModel.getTagById(id).observe(this,
            Observer {
                it!!

                title.setText(it.title)
                description.setText(it.desc)

                Utils.setupColorPicker(colorPicker!!,
                    view.color_panel_new,
                    view.color_panel_old,
                    it.color)
            })

        with(view) {
            fab.onClick(activity) {
                editViewModel.updateTag(
                    Tag(
                        id,
                        title.text.toString(),
                        description.text.toString(),
                        colorPicker.color
                    ))
            }

            backArrow_imageView.onClick(activity)

            delete_imageView.onClick(activity) { editViewModel.deleteTagById(id) }
        }
    }
}
