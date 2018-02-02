package hpn332.cb.view.more.project

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import hpn332.cb.R
import hpn332.cb.model.adapter.SectionsPagerAdapter
import hpn332.cb.utils.Key
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.utils.Utils.onClick
import hpn332.cb.view.detail.DetailViewModel
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.android.synthetic.main.content_main.*

class ProjectActivity : AppCompatActivity() {

    companion object {
        lateinit var projectViewModel: DetailViewModel
    }

    private lateinit var viewPager: ViewPager
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        projectViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        edit_imageView.visibility = View.INVISIBLE

        init()
    }

    private fun init() {


        projectViewModel.project.id = intent.getIntExtra(Key.ID, 0)
        viewPager = findViewById(R.id.viewpager)
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        with(sectionsPagerAdapter) {
            addFragment(ProjectTaskListFragment.newInstance(1), "TODO") //index 0
            addFragment(ProjectTaskListFragment.newInstance(2), "Doing") //index 1
            addFragment(ProjectTaskListFragment.newInstance(3), "Done") //index 2
        }

        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)

        /*projectViewModel.getProjectById().observe(this,
            Observer {
                projectViewModel.project = it!!

                if (projectViewModel.project.title == "")
                    with(title_textView) {
                        visibility = View.GONE
                        text = ""
                    }
                else
                    with(title_textView) {
                        text = projectViewModel.project.title
                        visibility = View.VISIBLE
                    }

                if (projectViewModel.project.desc == "")
                    with(desc_textView) {
                        text = ""
                        visibility = View.GONE
                    }
                else
                    with(desc_textView) {
                        text = projectViewModel.project.desc
                        visibility = View.VISIBLE
                    }

                edit_imageView.visibility = View.VISIBLE
            })*/

        edit_imageView.setOnClickListener {
            Utils.goTo(this,type =  Type.EDIT_PROJECT, id = projectViewModel.project.id)
        }

        //        backlog_button.setOnClickListener {
        //            Utils.goTo(this, BackLogActivity::class.java)
        //        }

        fab_task_plus.onClick {
            Utils.goTo(this,type =  Type.ADD_TASK, id = projectViewModel.project.id)
        }
    }
}
