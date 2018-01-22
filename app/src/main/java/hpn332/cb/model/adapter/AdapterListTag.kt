package hpn332.cb.model.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import hpn332.cb.R
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.model.stucture.Tag
import kotlinx.android.synthetic.main.row_item_tag.view.*

class AdapterListTag(context: Context) : RecyclerView.Adapter<AdapterListTag.ItemHolder>() {

    private var list: List<Tag> = listOf()

    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(inflater.inflate(R.layout.row_item_tag, parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.onBind()

    override fun getItemCount(): Int = list.size


    fun setData(list: List<Tag>) {
        this.list = list
        notifyDataSetChanged()
    }


    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {

        private val title: TextView = view.title_textView
        private val description: TextView = view.description_textView
        private val color_view: View = view.color_view

        init {

            view.setOnLongClickListener(this)
        }

        fun onBind() {

            val list = list[adapterPosition]

            title.text = list.title
            description.text = list.desc


            color_view.setBackgroundColor(list.color)
        }

        override fun onLongClick(view: View): Boolean {

            Utils.goTo(inflater.context, Type.EDIT_TAG, adapterPosition)

            return true
        }
    }
}
