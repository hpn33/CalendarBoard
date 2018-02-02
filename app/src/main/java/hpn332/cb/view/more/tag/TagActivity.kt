package hpn332.cb.view.more.tag

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import hpn332.cb.R
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.utils.Utils.onClick
import kotlinx.android.synthetic.main.activity_tag_list.*
import kotlinx.android.synthetic.main.content_list_toolbar.*
import kotlinx.android.synthetic.main.include_recycler_view.*

class TagActivity : AppCompatActivity() {

    private lateinit var tagListAdapter: TagListAdapter
    private lateinit var tagViewModel: TagViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_list)

        tagViewModel = ViewModelProviders.of(this).get(TagViewModel::class.java)
        tagListAdapter = TagListAdapter(this)


        tagViewModel.getAllTag().observe(this,
            Observer { tagListAdapter.setData(it!!) })

        with(recycler_view) {
            adapter = tagListAdapter
            layoutManager = LinearLayoutManager(this@TagActivity)
        }

        backArrow_imageView.onClick(this)

        fab.onClick {
            Utils.goTo(this, type = Type.ADD_TAG)
        }
    }
}
