package hpn332.cb.view.detail.fragment

import android.arch.lifecycle.Observer
import android.view.View
import hpn332.cb.model.abstrac.Detail
import hpn332.cb.model.adapter.SectionsPagerAdapter
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.view.detail.DetailActivity.Companion.detailViewModel
import hpn332.cb.view.more.project.ProjectTaskListFragment
import kotlinx.android.synthetic.main.content_main.view.*

/**
 *
 */
class DTag : Detail() {

    override fun init(view: View, id: Int) {

        val edit = view.edit_imageView
        val viewPager = view.viewpager
        val title = view.title_textView
        val desc = view.desc_textView
        val color = view.color_view
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
                color.setBackgroundColor(it.color)
                if (it.title == "")
                    with(title) {
                        visibility = View.INVISIBLE
                        text = ""
                    }
                else
                    with(title) {
                        text = it.title
                        visibility = View.VISIBLE
                    }

                if (it.desc == "")
                    with(desc) {
                        text = ""
                        visibility = View.GONE
                    }
                else
                    with(desc) {
                        text = it.desc
                        visibility = View.VISIBLE
                    }

                edit.visibility = View.VISIBLE
            })

        edit.setOnClickListener {
            Utils.goTo(context, type = Type.EDIT_TAG, id = id)
        }
    }
}