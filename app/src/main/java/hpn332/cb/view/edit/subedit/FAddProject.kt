package hpn332.cb.view.edit.subedit

import android.view.View
import hpn332.cb.R
import hpn332.cb.model.stucture.Project
import hpn332.cb.utils.Utils.onClick
import hpn332.cb.view.edit.AEdit
import hpn332.cb.view.edit.EditActivity.Companion.editViewModel
import kotlinx.android.synthetic.main.content_edit_project_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_project.view.*

class FAddProject : AEdit() {

    override fun setView(): Int = R.layout.fragment_edit_project

    override fun init(view: View, id: Int) {

        val title = view.title_editText
        val description = view.description_editText

        with(view) {
            delete_imageView.visibility = View.GONE

            fab.onClick(activity) {
                editViewModel.insertProject(
                    Project(
                        title = title.text.toString(),
                        desc = description.text.toString()
                    ))
            }

            backArrow_imageView.onClick(activity)
        }
    }
}
