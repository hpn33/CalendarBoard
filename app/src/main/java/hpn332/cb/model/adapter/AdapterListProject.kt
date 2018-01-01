package hpn332.cb.model.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import hpn332.cb.R
import hpn332.cb.view.task.AListTask
import hpn332.cb.utils.Key
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.model.stucture.Project

class AdapterListProject(context: Context/*, private val arrayList: ArrayList<Project>*/) : RecyclerView.Adapter<AdapterListProject.ItemHolder>() {

    var list: List<Project> = listOf()

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(inflater.inflate(R.layout.row_item_project, parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.onBind()

    override fun getItemCount(): Int = list.size

    fun setData(list: List<Project>) {
        this.list = list
    }

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        private val title: TextView = view.findViewById(R.id.title_textView)
        private val count_task: TextView = view.findViewById(R.id.count_task_textView)

        init {

            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }

        fun onBind() {

            val list = list[adapterPosition]

            title.text = list.title
            count_task.text = list.desc
        }


        override fun onLongClick(view: View): Boolean {

            Utils.goTo(inflater.context, Type.EDIT_PROJECT, adapterPosition)

            return true
        }

        override fun onClick(view: View) {
            inflater.context.startActivity(
                Intent(inflater.context, AListTask::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra(Key.PROJECT, list[adapterPosition].id))
        }
    }
}
