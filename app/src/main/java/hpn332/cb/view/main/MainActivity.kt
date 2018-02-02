package hpn332.cb.view.main

import android.arch.lifecycle.ViewModelProviders
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import hpn332.cb.R
import hpn332.cb.model.adapter.SectionsPagerAdapter
import hpn332.cb.view.main.fragment.BackLogListFragment
import hpn332.cb.view.main.fragment.ProjectListFragment
import hpn332.cb.view.main.fragment.TagListFragment
import kotlinx.android.synthetic.main.activity_project_list.*

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var mainViewModel: MainViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        init()
    }

    /**
     * Init The view
     */
    private fun init() {

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        with(sectionsPagerAdapter) {
            addFragment(ProjectListFragment(), "Project") //index 0
            addFragment(BackLogListFragment(), "BackLog") //index 1
            addFragment(TagListFragment(), "Tag") //index 2

            //            setCustomTab(tabs)
        }

        //        mainViewModel.getAllProject().observe(this,
        //            Observer {
        //                sectionsPagerAdapter.setCount(0, it!!.size)
        //            })


        viewpager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewpager)
        tabs.tabTextColors = ColorStateList.valueOf(0xffffffff.toInt())

        info_imageView.setOnClickListener {
            Toast.makeText(this, "you don`t know about me?", Toast.LENGTH_SHORT).show()
        }

        logo_textView.setOnClickListener {
            Toast.makeText(this, "This logo is don`t do anything.", Toast.LENGTH_SHORT).show()
        }

        menu_imageView.setOnClickListener {
            Toast.makeText(this, "i don`t know maybe delete this", Toast.LENGTH_SHORT).show()
        }
    }

    ///
    val POSITION = "POSITION"

    public override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt(POSITION, tabs.selectedTabPosition)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewpager.currentItem = savedInstanceState.getInt(POSITION)
    }
}
