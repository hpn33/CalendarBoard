package hpn332.cb.view.main.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hpn332.cb.R
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.view.main.MainActivity.Companion.mainViewModel
import kotlinx.android.synthetic.main.list_fragment.view.*

/**
 *
 */
class ProjectListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.list_fragment, container, false)

        init(view)

        return view
    }

    private fun init(view: View) {

        val mainProjectListAdapter = ProjectListAdapter(context)

        view.fab.setOnClickListener {
            Utils.goTo(context, type = Type.ADD_PROJECT)
        }

        mainViewModel.getAllProject().observe(this,
            Observer {
                mainProjectListAdapter.setData(it!!)
            })

        with(view.recycler_view) {
            adapter = mainProjectListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}