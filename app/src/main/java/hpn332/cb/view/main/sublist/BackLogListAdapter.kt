package hpn332.cb.view.main.sublist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hpn332.cb.R
import hpn332.cb.model.stucture.BackLog

class BackLogListAdapter(context: Context) : RecyclerView.Adapter<BackLogListAdapter.ItemHolder>() {

    private var list: List<BackLog> = listOf()

    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(inflater.inflate(R.layout.row_item_project, parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.onBind()

    override fun getItemCount(): Int = list.size


    fun setData(list: List<BackLog>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind() {

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
