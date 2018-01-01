package hpn332.cb.view.project

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log

import hpn332.cb.R
import hpn332.cb.utils.List
import hpn332.cb.utils.Type as t
import hpn332.cb.utils.Utils as u
import hpn332.cb.model.adapter.AdapterListProject
import hpn332.cb.model.stucture.Project
import hpn332.cb.utils.helper.ProviderHelper
import kotlinx.android.synthetic.main.activity_project_list.*
import kotlinx.android.synthetic.main.include_recycler_view.*

class AListProject : AppCompatActivity() {

    private lateinit var vm: VMProject
    private lateinit var adapter: AdapterListProject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)
        Log.d(TAG, "onCreate: start")

        vm = ViewModelProviders.of(this).get(VMProject::class.java)
        adapter = AdapterListProject(this)


        vm.getAllProjects().observe(this,
            Observer {
                println("$it")
                adapter.setData(it!!)
            })

        init()

        Log.d(TAG, "onCreate: end")
    }

    private fun init() {
        Log.d(TAG, "init: start")

        toolbar.title = TAG

        fab.setOnClickListener { u.goTo(this, t.ADD_PROJECT) }

        recycler_view.layoutManager = GridLayoutManager(this, 3)
        recycler_view.adapter = adapter

        Log.d(TAG, "init: end")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: start")

        //        ProviderHelper.queryListProject(List.L_PROJECT)


        Log.d(TAG, "onResume: end")
    }

    companion object {

        private val TAG = "AListProject"
    }


}
