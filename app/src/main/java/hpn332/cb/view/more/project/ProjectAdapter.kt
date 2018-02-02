package hpn332.cb.view.more.project

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hpn332.cb.R
import hpn332.cb.model.stucture.Task
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.view.detail.DetailActivity.Companion.detailViewModel
import kotlinx.android.synthetic.main.row_item_task.view.*

class ProjectAdapter(context: Context) : RecyclerView.Adapter<ProjectAdapter.ItemHolder>() {

    private var list: List<Task> = listOf()

    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder = ItemHolder(inflater.inflate(R.layout.row_item_task, parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.onBind(position)

    override fun getItemCount(): Int = list.size


    fun setData(list: List<Task>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.title_textView
        private val description = view.description_textView
        private val rank = view.rank_textView
        private val tagLayout = view.show_tag_layout
        private val nextStep = view.next_step_imageView

        private lateinit var item: Task

        fun onBind(position: Int) {

            item = list[position]

            title.text = item.title
            description.text = item.desc
            rank.text = item.rank.toString()

            if (item.step < 3) {

                nextStep.visibility = View.VISIBLE

                nextStep.setOnClickListener {

                    item.step = item.step + 1
                    detailViewModel.updateTask(item)
                }
            }

            //            if (!item.tag_id.isEmpty()) addTagColor()

            view.setOnLongClickListener {
                Utils.goTo(inflater.context,type =  Type.EDIT_TASK, id = item.id)
                true
            }
        }

        private fun addTagColor() {

            tagLayout.visibility = View.VISIBLE

            //			ProviderHelper.queryListTag(List.L_TAGS)

//            val tags = item.tag_id.split("`".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

//            val view = arrayOfNulls<View>(tags.size)

            //            for ((j, s) in tags.withIndex()) {
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
            //            }

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
    }

}
