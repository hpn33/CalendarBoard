package hpn332.cb.view.task

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import hpn332.cb.R
import hpn332.cb.utils.Key
import hpn332.cb.utils.List
import kotlinx.android.synthetic.main.include_recycler_view.view.*

class FListTask : Fragment() {

	private lateinit var recyclerView: RecyclerView

	override fun onCreateView(
			inflater: LayoutInflater?, container: ViewGroup?,
			savedInstanceState: Bundle?): View? {

		Log.d(TAG, "onCreateView: start")

		val view = inflater!!.inflate(R.layout.include_recycler_view, container, false)

        recyclerView = view.recycler_view

		adapter(arguments.getInt(Key.STEP))

		Log.d(TAG, "onCreateView: end")

		return view
	}

	private fun adapter(index: Int) {
		Log.d(TAG, "adapter: start")

        recyclerView.adapter = AdapterListTask(context, List.L_STEP[index], index)

        recyclerView.layoutManager = LinearLayoutManager(context)

		Log.d(TAG, "adapter: end")
	}

	companion object {

		private val TAG = "FListTask"

		fun newInstance(index: Int): FListTask {

			Log.d(TAG, "newInstance: start")

			val args = Bundle()

			args.putInt(Key.STEP, index)
			val fragment = FListTask()
			fragment.arguments = args

			Log.d(TAG, "newInstance: end")
			return fragment
		}
	}
}
