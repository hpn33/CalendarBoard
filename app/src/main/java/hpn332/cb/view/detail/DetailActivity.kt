package hpn332.cb.view.project

import android.arch.lifecycle.Observer
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
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.android.synthetic.main.content_main.*

class DetailActivity : AppCompatActivity() {

    companion object {
        lateinit var detailViewModel: DetailViewModel
    }

    private lateinit var viewPager: ViewPager
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        edit_imageView.visibility = View.INVISIBLE

        init()
    }

    private fun init() {


        detailViewModel.project.id = intent.getIntExtra(Key.ID, 0)
        viewPager = findViewById(R.id.viewpager)
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        with(sectionsPagerAdapter) {
            addFragment(ProjectTaskListFragment.newInstance(1), "TODO") //index 0
            addFragment(ProjectTaskListFragment.newInstance(2), "Doing") //index 1
            addFragment(ProjectTaskListFragment.newInstance(3), "Done") //index 2
        }

        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)

        detailViewModel.getProject().observe(this,
            Observer {
                detailViewModel.project = it!!

                if (detailViewModel.project.title == "")
                    with(project_title) {
                        visibility = View.INVISIBLE
                        text = ""
                    }
                else
                    with(project_title) {
                        text = detailViewModel.project.title
                        visibility = View.VISIBLE
                    }

                if (detailViewModel.project.desc == "")
                    with(project_desc) {
                        text = ""
                        visibility = View.INVISIBLE
                    }
                else
                    with(project_desc) {
                        text = detailViewModel.project.desc
                        visibility = View.VISIBLE
                    }

                edit_imageView.visibility = View.VISIBLE
            })

        edit_imageView.setOnClickListener {
            Utils.goToEdit(this, Type.EDIT_PROJECT, id = detailViewModel.project.id)
        }

        fab_task_plus.setOnClickListener {
            Utils.goToEdit(this, Type.ADD_TASK, id = detailViewModel.project.id)
        }
    }
}
