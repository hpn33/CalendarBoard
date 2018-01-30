package hpn332.cb.view.project

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hpn332.cb.R
import hpn332.cb.utils.Key
import hpn332.cb.view.project.DetailActivity.Companion.detailViewModel
import kotlinx.android.synthetic.main.include_recycler_view.view.*

class ProjectTaskListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.include_recycler_view, container, false)

        init(view)

        return view
    }

    private fun init(view: View) {

        val taskAdapterList = ProjectAdapter(context)

        when (arguments.getInt(Key.STEP)) {
            1 -> detailViewModel.getTodo().observe(this,
                Observer {
                    taskAdapterList.setData(it!!)
                })
            2 -> detailViewModel.getDoing().observe(this,
                Observer {
                    taskAdapterList.setData(it!!)
                })
            3 -> detailViewModel.getDone().observe(this,
                Observer {
                    taskAdapterList.setData(it!!)
                })
        }
        with(view.recycler_view) {
            adapter = taskAdapterList
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {

        fun newInstance(index: Int): ProjectTaskListFragment {

            val args = Bundle()

            args.putInt(Key.STEP, index)
            val fragment = ProjectTaskListFragment()
            fragment.arguments = args

            return fragment
        }
    }
}
