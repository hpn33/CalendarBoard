package hpn332.cb.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log

import hpn332.cb.R
import hpn332.cb.utils.List
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.model.adapter.AdapterListProject
import hpn332.cb.utils.helper.ProviderHelper
import kotlinx.android.synthetic.main.activity_project_list.*
import kotlinx.android.synthetic.main.include_recycler_view.*

class ListProjectActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_project_list)
		Log.d(TAG, "onCreate: start")

		init()
		using()

		Log.d(TAG, "onCreate: end")
	}

	private fun init() {
		Log.d(TAG, "init: start")

		toolbar.title = TAG

		Log.d(TAG, "init: end")
	}

	private fun using() {
		Log.d(TAG, "using: start")

		fab.setOnClickListener({ Utils.goTo(applicationContext, Type.ADD_PROJECT) })


        recycler_view.layoutManager = GridLayoutManager(applicationContext, 2)

		Log.d(TAG, "using: end")
	}

	override fun onResume() {
		super.onResume()
		Log.d(TAG, "onResume: start")

		ProviderHelper.queryListProject(List.L_PROJECT)
        recycler_view.adapter = AdapterListProject(applicationContext, List.L_PROJECT)

		Log.d(TAG, "onResume: end")
	}

	companion object {

		private val TAG = "ListProjectActivity"
	}


}
