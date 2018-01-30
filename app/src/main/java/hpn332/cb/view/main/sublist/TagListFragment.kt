package hpn332.cb.view.main.sublist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
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
class TagListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.list_fragment, container, false)

        init(view)

        return view
    }

    private fun init(view: View) {

        val tagListAdapter = TagListAdapter(context)

        view.fab.setOnClickListener {Utils.goToEdit(context, Type.ADD_TAG) }

        mainViewModel.getAllTag().observe(this,
            Observer {
                tagListAdapter.setData(it!!)
            })

        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

        with(view.recycler_view) {
            adapter = tagListAdapter
            layoutManager = staggeredGridLayoutManager

        }
    }
}