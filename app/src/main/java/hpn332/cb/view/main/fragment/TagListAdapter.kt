package hpn332.cb.view.main.fragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hpn332.cb.R
import hpn332.cb.model.stucture.Tag
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.view.detail.DetailActivity
import kotlinx.android.synthetic.main.item_tag.view.*

class TagListAdapter(context: Context) : RecyclerView.Adapter<TagListAdapter.ItemHolder>() {

    private var list: List<Tag> = listOf()

    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(inflater.inflate(R.layout.item_tag, parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.onBind()

    override fun getItemCount(): Int = list.size


    fun setData(list: List<Tag>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.title_textView

        fun onBind() {

            val item = list[adapterPosition]

            title.text = item.title
            view.setBackgroundColor(item.color)

            view.setOnClickListener {
                Utils.goTo(inflater.context, DetailActivity::class.java, Type.TAG, item.id)
            }

            view.setOnLongClickListener {
                Utils.goTo(inflater.context, type = Type.EDIT_TAG, id = item.id)

                true
            }
        }
    }
}
