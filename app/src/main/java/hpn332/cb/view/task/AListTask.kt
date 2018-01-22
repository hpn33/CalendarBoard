package hpn332.cb.view.task

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log

import hpn332.cb.model.adapter.AdapterListBacklog
import hpn332.cb.R
import hpn332.cb.utils.Key
import hpn332.cb.utils.List
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.include_backlog_list.*

class AListTask : AppCompatActivity() {


    //    private lateinit var adapter: AdapterListTask
    private val tag = "AListTask"


    @SuppressLint("StaticFieldLeak")
    private lateinit var viewPager: ViewPager
    private lateinit var adapterA: SectionsPagerAdapter
    private var project_id: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        Log.d(tag, "onCreate: start")

        vm = ViewModelProviders.of(this).get(VMTask::class.java)

        init()

        Log.d(tag, "onCreate: end")
    }

    private fun init() {

        Log.d(tag, "init: start")

        project_id = intent.getIntExtra(Key.PROJECT, 0)

        adapterA = SectionsPagerAdapter(supportFragmentManager)

        viewPager = findViewById(R.id.viewpager)
        viewPager.adapter = adapterA

        tabs.setupWithViewPager(viewPager)


        vm.getTodo().observe(this,
            Observer {

            })

        vm.getDoing().observe(this,
            Observer {

            })

        vm.getDone().observe(this,
            Observer {

            })



        fab_backlog_plus
            .setOnClickListener({
                Utils.goTo(applicationContext,
                    Type.ADD_BACKLOG, project = project_id)
            })

        fab_task_plus
            .setOnClickListener({
                Utils.goTo(applicationContext,
                    Type.ADD_TASK, project = project_id)
            })



//        setupBacklogFragment()
        setupViewPager()

        Log.d(tag, "init: end")
    }

    private fun setupBacklogFragment() {
        Log.d(tag, "setupBacklogFragment: start")

        recycler_view.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false)

        recycler_view.adapter = AdapterListBacklog(this, List.L_BACKLOG)


        Log.d(tag, "setupBacklogFragment: end")
    }

    private fun setupViewPager() {
        Log.d(tag, "setupViewPager: start")


        with(adapterA) {
            addFragment(FListTask.newInstance(0), "TODO") //index 0
            addFragment(FListTask.newInstance(1), "Doing") //index 1
            addFragment(FListTask.newInstance(2), "Done") //index 2
        }

        Log.d(tag, "setupViewPager: end")
    }

//    override fun onClickNext() {
//        refreshAdapter()
//    }
//
//    fun refreshAdapter() {
//        Log.d(tag, "refreshAdapter: start")
//
//        query()
//
//        viewPager.adapter = adapterA
//
//        tabs.getTabAt(tabs.selectedTabPosition)!!.select()
//
//        Log.d(tag, "refreshAdapter: end")
//    }


    private fun query() {
        Log.d(tag, "query: start")

//        ProviderHelper.queryListBacklogByProject(project_id, List.L_BACKLOG)

        queryOfTask(1)

        Log.d(tag, "query: end")
    }



    private fun queryOfTask(backlogId: Int) {
        Log.d(tag, "queryOfTask: start")

//        ProviderHelper.queryListTaskByBacklogAndStep(
//            1, project_id, backlogId, List.L_TODO)
//        ProviderHelper.queryListTaskByBacklogAndStep(
//            2, project_id, backlogId, List.L_DOING)
//        ProviderHelper.queryListTaskByBacklogAndStep(
//            3, project_id, backlogId, List.L_DONE)

        Log.d(tag, "queryOfTask: end")
    }

    fun onClickBacklog(backlogId: Int) {
        Log.d(tag, "onClickBacklog: backlog id :: " + backlogId)

        queryOfTask(backlogId)

        viewPager.adapter.notifyDataSetChanged()
    }

    companion object {

        lateinit var vm: VMTask

    }
}
