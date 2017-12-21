package hpn332.cb.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View

import hpn332.cb.R
import hpn332.cb.view.fragment.AddFragmentTag
import hpn332.cb.model.adapter.AdapterListTag
import hpn332.cb.utils.helper.ProviderHelper
import hpn332.cb.utils.List
import kotlinx.android.synthetic.main.activity_tag_list.*
import kotlinx.android.synthetic.main.content_list_toolbar.*
import kotlinx.android.synthetic.main.include_recycler_view.*

class ListTagActivity : AppCompatActivity() {

    private var adapter: AdapterListTag? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_list)

        Log.d(TAG, "onCreate: start")

        init()
        using()

        Log.d(TAG, "onCreate: end")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume: start")

        ProviderHelper.queryListTag(List.L_TAGS)
        recycler_view.adapter = adapter

        Log.d(TAG, "onResume: end")
    }

    private fun init() {

        Log.d(TAG, "init: start")

        backArrow_imageView.setOnClickListener({ finish() })

        adapter()

        Log.d(TAG, "init: end")
    }

    private fun using() {

        Log.d(TAG, "using: start")

        fab.setOnClickListener(
            {
                startActivity(Intent(applicationContext, AddFragmentTag::class.java))
            })

        Log.d(TAG, "using: end")
    }

    private fun adapter() {

        Log.d(TAG, "adapter: start")

        adapter = AdapterListTag(applicationContext, List.L_TAGS)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)

        Log.d(TAG, "adapter: end")
    }

    companion object {

        private val TAG = "ListTagActivity"
    }
}
