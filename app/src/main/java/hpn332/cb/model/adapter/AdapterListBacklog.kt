package hpn332.cb.model.adapter

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import hpn332.cb.R
import hpn332.cb.view.task.AListTask
import hpn332.cb.utils.Type
import hpn332.cb.utils.Utils
import hpn332.cb.model.stucture.BackLog
import hpn332.cb.view.edit.AEdit.Companion.vm

class AdapterListBacklog : RecyclerView.Adapter<AdapterListBacklog.ItemHolder> {

    private var inflater: LayoutInflater? = null
    private var arrayList: ArrayList<BackLog>? = null

    constructor(
        context: Context, arrayList: ArrayList<BackLog>) {

        this.arrayList = arrayList
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = inflater!!.inflate(R.layout.row_item_backlog, parent, false)

        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: position :: " + position)
        holder.onBind(position)
    }

    override fun getItemCount(): Int = arrayList!!.size

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener, View.OnClickListener {

        private val title: TextView = view.findViewById(R.id.title_textView)
        private val description: TextView = view.findViewById(R.id.description_textView)
        private val colorView: View = view.findViewById(R.id.color_view)

        init {

            view.setOnLongClickListener(this)
            view.setOnClickListener(this)
        }

        fun onBind(position: Int) {

            title.text = arrayList!![position].title
            description.text = arrayList!![position].desc

            colorView.setBackgroundColor(arrayList!![position].color)
        }

        override fun onLongClick(view: View): Boolean {

            Log.d(TAG, "onLongClick: position : " + adapterPosition)

            if (arrayList!![adapterPosition].title != "BASE"
                && arrayList!![adapterPosition].desc != "BASE Backlog"
                && arrayList!![adapterPosition].color != -1)
                Utils.goTo(inflater!!.context, Type.EDIT_BACKLOG, adapterPosition)
            else
                Snackbar.make(view, "this not provider for Edit", Snackbar.LENGTH_SHORT).show()

            return true
        }

        override fun onClick(view: View) {
            Log.d(TAG, "onClick: position : " + adapterPosition)

            // todo
//            if (backlog == null)
//                AListTask.onClickBacklog(arrayList!![adapterPosition].id)
//            else
                vm.backlogId = arrayList!![adapterPosition].id
        }
    }

    companion object {

        private val TAG = "AdapterListBacklog"
    }
}
