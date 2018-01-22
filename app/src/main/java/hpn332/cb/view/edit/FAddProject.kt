package hpn332.cb.view.edit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import hpn332.cb.R
import hpn332.cb.model.stucture.Project
import hpn332.cb.view.edit.AEdit.Companion.vm
import kotlinx.android.synthetic.main.content_edit_project_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_project.view.*

class FAddProject : Fragment() {

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

        Log.d(TAG, "init: start")

        val title: EditText = view.title_editText
        val description: EditText = view.description_editText

        view.delete_imageView.visibility = View.GONE

        view.fab.setOnClickListener {
            vm.insertProjects(
                listOf(
                    Project(
                        title = title.text.toString(),
                        desc = description.text.toString())))
            activity.finish()
        }

        view.backArrow_imageView.setOnClickListener { activity.finish() }

        Log.d(TAG, "init: end")
    }

    companion object {

        private val TAG = "FAddProject"
    }

}
