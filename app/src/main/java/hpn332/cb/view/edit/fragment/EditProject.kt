package hpn332.cb.view.edit.fragment

import android.arch.lifecycle.Observer
import android.view.View
import hpn332.cb.R
import hpn332.cb.model.stucture.Project
import hpn332.cb.utils.Utils.onClick
import hpn332.cb.model.abstrac.Frag
import hpn332.cb.view.edit.EditActivity.Companion.editViewModel
import kotlinx.android.synthetic.main.content_edit_project_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_project.view.*

class EditProject : Frag() {

    override fun setView(): Int = R.layout.fragment_edit_project

    override fun init(view: View, id: Int) {

        val title = view.title_editText
        val description = view.description_editText

        editViewModel.getProjectById(id).observe(this,
            Observer {
                it!!
                title.setText(it.title)
                description.setText(it.desc)
            })


        with(view) {
            fab.onClick(activity) {
                editViewModel.updateProject(
                    Project(
                        id,
                        title.text.toString(),
                        description.text.toString())
                )
            }

            delete_imageView.onClick(activity) { editViewModel.deleteProjectById(id) }

            backArrow_imageView.onClick(activity)
        }
    }
}
