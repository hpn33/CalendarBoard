package hpn332.cb.view.main.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hpn332.cb.R
import hpn332.cb.view.main.MainActivity.Companion.mainViewModel
import kotlinx.android.synthetic.main.list_fragment.view.*

/**
 * Created by hpn332 on 28/01/2018.
 */
class BackLogListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.list_fragment, container, false)

        init(view)

        return view
    }

    private fun init(view: View) {

        val backLogListAdapter = BackLogListAdapter(context)

        view.fab.visibility = View.INVISIBLE

        mainViewModel.getAllBackLog().observe(this,
            Observer {
                backLogListAdapter.setData(it!!)
            })

        with(view.recycler_view) {
            adapter = backLogListAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }
}