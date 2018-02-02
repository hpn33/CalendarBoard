package hpn332.cb.view.detail.fragment

import android.arch.lifecycle.Observer
import android.view.View
import hpn332.cb.R
import hpn332.cb.model.abstrac.Frag
import hpn332.cb.model.adapter.SectionsPagerAdapter
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.view.detail.DetailActivity.Companion.detailViewModel
import hpn332.cb.view.more.project.ProjectTaskListFragment
import kotlinx.android.synthetic.main.content_main.view.*
import kotlinx.android.synthetic.main.fragment_detail.view.*

/**
 *
 */
class DetailFragment : Frag() {

    override fun setView(): Int = R.layout.fragment_detail

    override fun init(view: View, id: Int) {

        val edit = view.edit_imageView
        val viewPager = view.viewpager
        val title = view.title_textView
        val desc = view.desc_textView
        val tabs = view.tabs
        val sectionsPagerAdapter = SectionsPagerAdapter(context, activity.supportFragmentManager)

        edit.visibility = View.INVISIBLE

        with(sectionsPagerAdapter) {
            addFragment(ProjectTaskListFragment.newInstance(1), "TODO") //index 0
            addFragment(ProjectTaskListFragment.newInstance(2), "Doing") //index 1
            addFragment(ProjectTaskListFragment.newInstance(3), "Done") //index 2
        }

        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)

        detailViewModel.getTagById(id).observe(this,
            Observer {
                detailViewModel.tag = it!!

                title.text = it.title
                desc.text = it.desc
                //                if (detailViewModel.project.title == "")
                //                    with(title) {
                //                        visibility = View.INVISIBLE
                //                        text = ""
                //                    }
                //                else
                //                    with(title) {
                //                        text = detailViewModel.project.title
                //                        visibility = View.VISIBLE
                //                    }
                //
                //                if (detailViewModel.project.desc == "")
                //                    with(desc) {
                //                        text = ""
                //                        visibility = View.INVISIBLE
                //                    }
                //                else
                //                    with(desc) {
                //                        text = detailViewModel.project.desc
                //                        visibility = View.VISIBLE
                //                    }

                edit.visibility = View.VISIBLE
            })

        edit.setOnClickListener {
            Utils.goTo(context, type = Type.EDIT_PROJECT, id = id)
        }

        view.fab_task_plus.setOnClickListener {
            Utils.goTo(context, type = Type.ADD_TASK, id = id)
        }
    }
}