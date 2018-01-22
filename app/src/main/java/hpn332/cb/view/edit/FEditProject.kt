package hpn332.cb.view.edit

import android.arch.lifecycle.Observer
import android.content.ContentValues.TAG
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import hpn332.cb.R
import hpn332.cb.model.stucture.Project
import hpn332.cb.utils.helper.ProviderHelper
import hpn332.cb.utils.Key
import hpn332.cb.utils.List
import hpn332.cb.view.edit.AEdit.Companion.vm
import kotlinx.android.synthetic.main.content_edit_project_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_project.view.*
import hpn332.cb.utils.Utils.onClick

class FEditProject : Fragment() {


    private val TAG = "FEditProject"

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: start")

        val view = inflater!!.inflate(R.layout.fragment_edit_project, container, false)

        init(view)

        Log.d(TAG, "onCreateView: end")
        return view
    }

    private fun init(view: View) {

        val title = view.title_editText
        val description = view.description_editText

        val id = activity.intent.getIntExtra(Key.ID, 0)

//        vm.getProjectById(id).observe(this,
//            Observer {
//                it!!
//                title.setText(it.title)
//                description.setText(it.desc)
//            })



        with(view) {

            fab.onClick(activity) {
                vm.updateProjects(listOf(Project(
                    id,
                    title.text.toString(),
                    description.text.toString())
                ))
            }

            delete_imageView.onClick(activity) {
                vm.deleteProjects(listOf(Project()))
            }

            backArrow_imageView.onClick(activity)
        }
    }
}
