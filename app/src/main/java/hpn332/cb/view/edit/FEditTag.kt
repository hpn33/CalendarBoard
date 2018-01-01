package hpn332.cb.view.edit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import hpn332.cb.R
import hpn332.cb.utils.Key
import hpn332.cb.utils.List
import hpn332.cb.utils.Utils
import hpn332.cb.utils.helper.ProviderHelper
import hpn332.library.view.ColorPickerView
import kotlinx.android.synthetic.main.content_color_picker.view.*
import kotlinx.android.synthetic.main.content_edit_tag_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_tag.view.*

class FEditTag : Fragment() {

    private var colorPicker: ColorPickerView? = null

    private var title: EditText? = null
    private var description: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: start")

        val view = inflater!!.inflate(R.layout.fragment_edit_tag, container, false)

        init(view)

        Log.d(TAG, "onCreateView: end")
        return view
    }

    private fun init(view: View) {

        Log.d(TAG, "init: start")

        title = view.title_editText
        description = view.description_editText

        colorPicker = view.color_picker_view

        val position = activity.intent.getIntExtra(Key.POSITION, 0)
        val arrayList = List.L_TAGS

        title!!.setText(arrayList[position].title)
        description!!.setText(arrayList[position].desc)

        view.backArrow_imageView
            .setOnClickListener { activity.finish() }

        view.delete_imageView.setOnClickListener {
            ProviderHelper.deleteOneTag(arrayList[position].id)

            activity.finish()
        }

        view.fab.setOnClickListener {
            ProviderHelper.updateOneTag(
                    arrayList[position].id,
                    title!!.text.toString(),
                    description!!.text.toString(),
                    colorPicker!!.color)
            activity.finish()
        }

        Utils.setupColorPicker(colorPicker!!,
            view.color_panel_new,
            view.color_panel_old,
            arrayList[position].color)

        Log.d(TAG, "init: end")
    }

    companion object {

        private val TAG = "FEditTag"
    }

}
