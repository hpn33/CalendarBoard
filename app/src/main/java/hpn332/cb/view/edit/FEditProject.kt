package hpn332.cb.view.edit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import hpn332.cb.R
import hpn332.cb.utils.helper.ProviderHelper
import hpn332.cb.utils.Key
import hpn332.cb.utils.List
import kotlinx.android.synthetic.main.content_edit_project_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_project.view.*

class FEditProject : Fragment() {



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

        val title = view.title_editText
        val description = view.description_editText

        val position = activity.intent.getIntExtra(Key.POSITION, 0)
        val id = List.L_PROJECT[position].id

        Log.d(TAG, "makeReadyStep: id :: " + id)

        title.setText(List.L_PROJECT[position].title)
        description.setText(List.L_PROJECT[position].desc)

        view.fab.setOnClickListener {
            ProviderHelper.updateOneProject(
                id,
                title.text.toString(),
                description.text.toString())

            Log.d(TAG, "onClick: update id ::" + id)

            activity.finish()
        }

        view.delete_imageView.setOnClickListener {
            ProviderHelper.deleteOneProject(id)
            activity.finish()
        }

        view.backArrow_imageView.setOnClickListener { activity.finish() }
        Log.d(TAG, "init: end")
    }

    companion object {

        private val TAG = "FEditProject"
    }
}
