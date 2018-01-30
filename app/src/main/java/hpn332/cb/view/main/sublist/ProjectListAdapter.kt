package hpn332.cb.view.main.sublist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import hpn332.cb.R
import hpn332.cb.model.stucture.Project
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.view.project.DetailActivity
import kotlinx.android.synthetic.main.row_item_project.view.*

class ProjectListAdapter(context: Context) : RecyclerView.Adapter<ProjectListAdapter.ItemHolder>() {

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

        private val title: TextView = view.title_textView
        private val count: TextView = view.count_textView
        private val percent: TextView = view.percent_textView


        fun onBind() {

            val item = list[adapterPosition]

            title.text = item.title
            count.text = "${item.count_task}"
            percent.text = percent(item)

            view.setOnClickListener {
                Utils.goTo(inflater.context, DetailActivity::class.java, id = item.id)
            }

            view.setOnLongClickListener {
                Utils.goToEdit(inflater.context, Type.EDIT_PROJECT, id = item.id)

                true
            }
        }

        private fun percent(project: Project) =
            if (project.count_task != 0)
                "%${(100 / project.count_task) * project.count_done_task}"
            else "%0"

        //        fun getRandomIntInRange(max: Int, min: Int): Int = Random().nextInt(max - min + min) + min

    }
}
