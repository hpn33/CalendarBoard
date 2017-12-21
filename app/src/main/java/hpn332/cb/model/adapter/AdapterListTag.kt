package hpn332.cb.model.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import hpn332.cb.R
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.model.stucture.Tag
import kotlinx.android.synthetic.main.row_item_tag.view.*

class AdapterListTag(
    context: Context, private val arrayList: ArrayList<Tag>) : RecyclerView.Adapter<AdapterListTag.ItemHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = inflater.inflate(R.layout.row_item_tag, parent, false)

        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = arrayList.size

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {

        private val title: TextView = view.title_textView
        private val description: TextView = view.description_textView
        private val color_view: View = view.color_view

        init {

            view.setOnLongClickListener(this)
        }

        fun onBind(position: Int) {

            title.text = arrayList[position].title
            description.text = arrayList[position].desc


            color_view.setBackgroundColor(arrayList[position].color)
        }

        override fun onLongClick(view: View): Boolean {

            Utils.goTo(inflater.context, Type.EDIT_TAG, position)

            return true
        }
    }
}
