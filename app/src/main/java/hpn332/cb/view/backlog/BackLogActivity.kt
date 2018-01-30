package hpn332.cb.view.backlog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import hpn332.cb.R
import kotlinx.android.synthetic.main.content_list_toolbar.*

/**
 * Created by hpn332 on 27/01/2018.
 */
class BackLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backlog_list)

        backArrow_imageView.setOnClickListener { finish() }
    }
}