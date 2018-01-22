package hpn332.cb.view.project

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View

import hpn332.cb.R
import hpn332.cb.utils.Type as t
import hpn332.cb.utils.Utils as u
import kotlinx.android.synthetic.main.activity_project_list.*
import kotlinx.android.synthetic.main.include_recycler_view.*

class AListProject : AppCompatActivity() {

    private val tag = "Project"
    private lateinit var vm: VMProject
    private lateinit var adapter: AdapterListProject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)
        Log.d(tag, "onCreate: start")

        vm = ViewModelProviders.of(this).get(VMProject::class.java)

        init()

        Log.d(tag, "onCreate: end")
    }

    /**
     * Init The view
     */
    private fun init() {

        toolbar.title = tag

        adapter = AdapterListProject(this)

        vm.getAllProject().observe(this,
            Observer {
                it!!
                if (it.isNotEmpty()) {
                    adapter.setData(it)
                    progress.visibility = View.GONE
                    recycler_view.visibility = View.VISIBLE
                } else {
                    progress.visibility = View.VISIBLE
                    recycler_view.visibility = View.GONE
                }
            })


        recycler_view.layoutManager = GridLayoutManager(this, 3)
        recycler_view.adapter = adapter


        fab.setOnClickListener { u.goTo(this, t.ADD_PROJECT) }
    }
}
