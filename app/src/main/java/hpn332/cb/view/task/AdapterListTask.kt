package hpn332.cb.view.task

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import java.util.ArrayList

import hpn332.cb.R
import hpn332.cb.utils.List
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.model.stucture.Task
import hpn332.cb.view.task.AListTask.Companion.vm
import kotlinx.android.synthetic.main.row_item_task.view.*

class AdapterListTask(
    context: Context,
    private val arrayList: ArrayList<Task>,
    private val step: Int) : RecyclerView.Adapter<AdapterListTask.ItemHolder>() {

	private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val TAG = "AdapterListTask"

    init {
        Log.d(TAG, "AdapterListTask: " + step)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
		val view = inflater.inflate(R.layout.row_item_task, parent, false)

		return ItemHolder(view)
	}

	override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.onBind(position)


	override fun getItemCount(): Int = arrayList.size

	inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {

		private val title: TextView = view.title_textView
        private val description: TextView = view.description_textView
        private val rank: TextView = view.rank_textView
        private val tagLayout: LinearLayout = view.show_tag_layout
        private val nextStep: ImageView = view.next_step_imageView

        init {
            view.setOnLongClickListener(this)
		}

		fun onBind(position: Int) {

			title.text = arrayList[position].title
			description.text = arrayList[position].desc
			rank.text = arrayList[position].rank.toString()

			if (arrayList[position].step < 3) {

				nextStep.visibility = View.VISIBLE

				nextStep.setOnClickListener {
//                    ProviderHelper.updateOneTaskToNextStep(
//							arrayList[position].id,
//							arrayList[position].step + 1)

					vm.onClickNext()
				}
			}

			if (!arrayList[position].tag_id.isEmpty()) addTagColor()
		}

		private fun addTagColor() {

			tagLayout.visibility = View.VISIBLE

//			ProviderHelper.queryListTag(List.L_TAGS)

			val tags = arrayList[adapterPosition].tag_id.split("`".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

			val view = arrayOfNulls<View>(tags.size)

            for ((j, s) in tags.withIndex()) {

				for (i in 0 until List.L_TAGS.size) {

					if (s == List.L_TAGS[i].id.toString()) {
						view[j] = inflater.inflate(R.layout.view_tag_color, tagLayout, false)

						view[j]?.findViewById<View>(R.id.view_tag_color)?.setBackgroundColor(
								List.L_TAGS[i].color)

						tagLayout.addView(view[j])
					}
				}
            }

//            var j = 0
//            for (s in tags) {
//
//                for (i in 0 until List.L_TAGS.size) {
//
//                    if (s == List.L_TAGS[i].id.toString()) {
//                        view[j] = inflater.inflate(R.layout.view_tag_color, tagLayout, false)
//
//                        view[j]?.findViewById<View>(R.id.view_tag_color)?.setBackgroundColor(
//                            List.L_TAGS[i].color)
//
//                        tagLayout.addView(view[j])
//                    }
//                }
//                j++
//            }

		}

		override fun onLongClick(view: View): Boolean {

			Log.d(TAG, "onLongClick: position : " + adapterPosition)

			Utils.goTo(inflater.context, Type.EDIT_TASK, adapterPosition, step)

			return true
		}
	}

}
