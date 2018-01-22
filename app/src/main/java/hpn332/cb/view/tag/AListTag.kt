package hpn332.cb.view.tag

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log

import hpn332.cb.R
import hpn332.cb.view.edit.FAddTag
import hpn332.cb.model.adapter.AdapterListTag
import hpn332.cb.model.stucture.Tag
import hpn332.cb.utils.helper.ProviderHelper
import hpn332.cb.utils.List
import kotlinx.android.synthetic.main.activity_tag_list.*
import kotlinx.android.synthetic.main.content_list_toolbar.*
import kotlinx.android.synthetic.main.include_recycler_view.*

class AListTag : AppCompatActivity() {

    private val TAG = "AListTag"
    private lateinit var adapter: AdapterListTag
    private lateinit var vm: VMTag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_list)

        Log.d(TAG, "onCreate: start")


        vm = ViewModelProviders.of(this).get(VMTag::class.java)
        adapter = AdapterListTag(this)


        vm.getAllTag().observe(this,
            Observer { adapter.setData(it!!) })


        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)


        backArrow_imageView.setOnClickListener({ finish() })

        fab.setOnClickListener {
            startActivity(Intent(this, FAddTag::class.java))
        }


        Log.d(TAG, "onCreate: end")
    }
}
