package hpn332.cb.view.detail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import hpn332.cb.R
import hpn332.cb.utils.Key
import hpn332.cb.utils.Type
import hpn332.cb.view.detail.fragment.DBacklog
import hpn332.cb.view.detail.fragment.DProject
import hpn332.cb.view.detail.fragment.DTag
import hpn332.cb.view.detail.fragment.DTask

class DetailActivity : AppCompatActivity() {

    companion object {
        lateinit var detailViewModel: DetailViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        savedInstanceState ?: setFragment(checkTypeAndGetFragment())
    }

    private fun setFragment(fragment: Fragment) =
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameLayout, fragment)
            .commit()

    private fun checkTypeAndGetFragment(): Fragment =
        when (intent.getIntExtra(Key.TYPE, 0)) {
            Type.PROJECT -> DProject()
            Type.BACKLOG -> DBacklog()
            Type.TASK    -> DTask()
            Type.TAG     -> DTag()
            else         ->
                throw IllegalArgumentException("Unknown type")
        }
}
