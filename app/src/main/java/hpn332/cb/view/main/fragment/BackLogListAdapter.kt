package hpn332.cb.view.main.fragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hpn332.cb.R
import hpn332.cb.model.stucture.BackLog
import kotlinx.android.synthetic.main.row_item_backlog.view.*

class BackLogListAdapter(context: Context) : RecyclerView.Adapter<BackLogListAdapter.ItemHolder>() {

    private var list: List<BackLog> = listOf()

    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(inflater.inflate(R.layout.row_item_backlog, parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.onBind()

    override fun getItemCount(): Int = list.size


    fun setData(list: List<BackLog>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.title_textView
        private val projectId = view.project_id

        fun onBind() {

            val item = list[adapterPosition]

            title.text = item.title
            projectId.text = "${item.project_id}"

            view.setOnClickListener {
                Toast.makeText(inflater.context, "Not yet", Toast.LENGTH_SHORT).show()
            }

            view.setOnLongClickListener {
                Toast.makeText(inflater.context, "Not long yet", Toast.LENGTH_SHORT).show()

                true
            }
        }
    }
}
