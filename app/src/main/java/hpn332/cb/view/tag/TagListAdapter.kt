package hpn332.cb.view.tag

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hpn332.cb.R
import hpn332.cb.model.stucture.Tag
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import kotlinx.android.synthetic.main.row_item_tag.view.*

class TagListAdapter(context: Context) : RecyclerView.Adapter<TagListAdapter.ItemHolder>() {

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

    inner class ItemHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.title_textView
        private val description = view.description_textView
        private val colorView = view.color_view

        fun onBind() {

            val item = list[adapterPosition]

            title.text = item.title
            description.text = item.desc

            colorView.setBackgroundColor(item.color)

            view.setOnLongClickListener {
                Utils.goToEdit(inflater.context, Type.EDIT_TAG, adapterPosition)

                true
            }
        }
    }
}
