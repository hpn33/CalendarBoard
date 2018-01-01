package hpn332.cb.view.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton

import hpn332.cb.R
import hpn332.cb.model.adapter.AdapterListBacklog
import hpn332.cb.utils.helper.ProviderHelper
import hpn332.cb.model.stucture.CheckTagStructure
import hpn332.cb.utils.Key
import hpn332.cb.utils.List
import hpn332.cb.view.edit.AEdit.Companion.vm
import kotlinx.android.synthetic.main.content_edit_task_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_task.view.*

class FAddTask : Fragment() {

	private var num1: RadioButton? = null
	private var num2: RadioButton? = null
	private var num3: RadioButton? = null
	private var num4: RadioButton? = null
	private var num5: RadioButton? = null
	private var tagLayout: LinearLayout? = null
	private lateinit var tagBoxes: Array<CheckBox?>

	private var recyclerView: RecyclerView? = null
	private var title: EditText? = null
	private var description: EditText? = null

	private var backlog: Backlog? = null

	private val tags: String
		get() {
			Log.d(TAG, "getTags: start")

			val stringBuilder = StringBuilder()

            (0 until List.L_TAGS.size)
                .filter { tagBoxes[it]!!.isChecked }
                .forEach { stringBuilder.append(List.L_TAGS[it].id).append("`") }

			Log.d(TAG, "getTags: end")
			return stringBuilder.toString()
		}

	private val rank: Int
		get() {
			Log.d(TAG, "getRank: ")
			if (num1!!.isChecked) return 1
			if (num2!!.isChecked) return 2
			if (num3!!.isChecked) return 3
			if (num4!!.isChecked) return 4
			return if (num5!!.isChecked) 5 else 1
		}

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		backlog = context as AEdit?
	}

	override fun onCreateView(
			inflater: LayoutInflater?, container: ViewGroup?,
			savedInstanceState: Bundle?): View? {

		Log.d(TAG, "onCreateView: start")

		val view = inflater!!.inflate(R.layout.fragment_edit_task, container, false)

		init(view)

		Log.d(TAG, "onCreateView: end")

		return view
	}

	private fun init(view: View) {

		Log.d(TAG, "init: start")

		title = view.findViewById(R.id.title_editText)
		description = view.findViewById(R.id.description_editText)

		tagLayout = view.findViewById(R.id.layout_for_tags)

		num1 = view.findViewById(R.id.num1)
		num2 = view.findViewById(R.id.num2)
		num3 = view.findViewById(R.id.num3)
		num4 = view.findViewById(R.id.num4)
		num5 = view.findViewById(R.id.num5)

		view.delete_imageView.visibility = View.GONE

		view.backArrow_imageView.setOnClickListener { activity.finish() }

		view.addTag.setOnClickListener { startActivity(Intent(context, FAddTag::class.java)) }

		view.fab.setOnClickListener { v ->

			if (vm.backlogId != 0) {
				ProviderHelper.insertNewTask(
						activity.intent.getIntExtra(Key.PROJECT, 0),
						vm.backlogId,
						title!!.text.toString(),
						description!!.text.toString(),
						"",
						rank)
				activity.finish()

			} else
				Snackbar.make(v, "select the backlog", Snackbar.LENGTH_SHORT).show()
		}


		recyclerView = view.findViewById(R.id.recycler_view)


		setupTags()
		setupBacklogFragment()

		Log.d(TAG, "init: end")
	}

	private fun setupBacklogFragment() {
		Log.d(TAG, "setupBacklogFragment: start")


		recyclerView!!.layoutManager = LinearLayoutManager(
				context, LinearLayoutManager.HORIZONTAL, false)

		recyclerView!!.adapter = AdapterListBacklog(context, List.L_BACKLOG, backlog!!)

		Log.d(TAG, "setupBacklogFragment: end")
	}

	private fun setupTags() {
		Log.d(TAG, "setupTags: start")

		List.L_TAGS.clear()
		List.L_CHECK.clear()

		ProviderHelper.queryListTag(List.L_TAGS)

		for (arrayList in List.L_TAGS) {
			List.L_CHECK.add(
					CheckTagStructure(arrayList.title,
							arrayList.color,
							false))
			Log.d(TAG, "setupTags: color::" + arrayList.color)
		}

		tagBoxes = arrayOfNulls(List.L_CHECK.size)

		for (i in 0 until List.L_CHECK.size) {
			tagBoxes[i] = CheckBox(context)
			tagBoxes[i]!!.setPadding(8, 12, 8, 12)

			tagBoxes[i]!!.text = List.L_CHECK[i].title
			//tagBoxes[i].setBackgroundResource(Utils.getColor(List.L_CHECK.get(i).getColor()));

			tagLayout!!.addView(tagBoxes[i])
		}

		Log.d(TAG, "setupTags: end")
	}

	companion object {

		private val TAG = "FAddTask"
	}
}
