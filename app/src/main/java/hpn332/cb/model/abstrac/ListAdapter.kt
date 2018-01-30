package hpn332.cb.model.abstrac

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hpn332.cb.model.stucture.Project

/**
 * Created by hpn332 on 28/01/2018.
 */
abstract class ListAdapter(context: Context) : RecyclerView.Adapter<ListAdapter.ItemHolder>() {

    private var list: List<Project> = listOf()

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(inflater.inflate(getResViewHolder(), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.onBind()

    override fun getItemCount(): Int = list.size

    fun setData(list: List<Project>) {
        this.list = list
        notifyDataSetChanged()
    }

    abstract fun getResViewHolder(): Int

    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(){}
    }
}