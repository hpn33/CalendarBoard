package hpn332.cb.view.task

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log

import hpn332.cb.model.adapter.AdapterListBacklog
import hpn332.cb.utils.helper.ProviderHelper
import hpn332.cb.view.fragment.FListTask
import hpn332.cb.R
import hpn332.cb.utils.Key
import hpn332.cb.utils.List
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.model.adapter.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.include_backlog_list.*

class AListTask : AppCompatActivity(), FListTask.OnStepFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        Log.d(TAG, "onCreate: start")

        init()

        Log.d(TAG, "onCreate: end")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: start")

        query()

        viewPager!!.adapter = adapter

        Log.d(TAG, "onResume: end")
    }

    private fun init() {
        Log.d(TAG, "init: start")

        project_id = intent.getIntExtra(Key.PROJECT, 0)

        fab_backlog_plus
            .setOnClickListener({
                Utils.goToP(applicationContext,
                    Type.ADD_BACKLOG, project_id)
            })

        fab_task_plus
            .setOnClickListener({
                Utils.goToP(applicationContext,
                    Type.ADD_TASK, project_id)
            })

        setupBacklogFragment()
        setupViewPager()

        Log.d(TAG, "init: end")
    }

    private fun setupBacklogFragment() {
        Log.d(TAG, "setupBacklogFragment: start")

        recycler_view.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false)

        recycler_view.adapter = AdapterListBacklog(this, List.L_BACKLOG)


        Log.d(TAG, "setupBacklogFragment: end")
    }

    private fun setupViewPager() {
        Log.d(TAG, "setupViewPager: start")

        adapter = SectionsPagerAdapter(supportFragmentManager)

        with(adapter!!) {
            addFragment(FListTask.newInstance(0), "TODO") //index 0
            addFragment(FListTask.newInstance(1), "Doing") //index 1
            addFragment(FListTask.newInstance(2), "Done") //index 2
        }
        viewPager = findViewById(R.id.viewpager)

        tabs.setupWithViewPager(viewPager)

        Log.d(TAG, "setupViewPager: end")
    }

    override fun onClickNext() {
        refreshAdapter()
    }

    private fun refreshAdapter() {
        Log.d(TAG, "refreshAdapter: start")

        query()

        viewPager!!.adapter = adapter

        tabs.getTabAt(tabs.selectedTabPosition)!!.select()

        Log.d(TAG, "refreshAdapter: end")
    }

    private fun query() {
        Log.d(TAG, "query: start")

        ProviderHelper.queryListBacklogByProject(project_id, List.L_BACKLOG)

        queryOfTask(1)

        Log.d(TAG, "query: end")
    }

    companion object {

        private val TAG = "AListTask"

        @SuppressLint("StaticFieldLeak")
        private var viewPager: ViewPager? = null
        private var adapter: SectionsPagerAdapter? = null
        private var project_id: Int = 0

        private fun queryOfTask(backlogId: Int) {
            Log.d(TAG, "queryOfTask: start")

            ProviderHelper.queryListTaskByBacklogAndStep(
                1, project_id, backlogId, List.L_TODO)
            ProviderHelper.queryListTaskByBacklogAndStep(
                2, project_id, backlogId, List.L_DOING)
            ProviderHelper.queryListTaskByBacklogAndStep(
                3, project_id, backlogId, List.L_DONE)

            Log.d(TAG, "queryOfTask: end")
        }

        fun onClickBacklog(backlogId: Int) {
            Log.d(TAG, "onClickBacklog: backlog id :: " + backlogId)

            queryOfTask(backlogId)

            viewPager!!.adapter = adapter
        }
    }
}
