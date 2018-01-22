package hpn332.cb.view.project

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import hpn332.cb.R
import hpn332.cb.view.task.AListTask
import hpn332.cb.utils.Key
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.model.stucture.Project

class AdapterListProject(context: Context) : RecyclerView.Adapter<AdapterListProject.ItemHolder>() {

    private var list: List<Project> = listOf()

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(inflater.inflate(R.layout.row_item_project, parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.onBind()

    override fun getItemCount(): Int = list.size

    fun setData(list: List<Project>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.title_textView)
        //        private val count_task: TextView = view.findViewById(R.id.count_task_textView)

        fun onBind() {

            val item = list[adapterPosition]

            title.text = item.title
            //            count_task.text = list.desc

            view.setOnClickListener {
                inflater.context.startActivity(
                    Intent(inflater.context, AListTask::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(Key.PROJECT, item.id))
            }

            view.setOnLongClickListener {
                Utils.goTo(inflater.context, Type.EDIT_PROJECT, id = item.id)

                true
            }

        }
    }
}
