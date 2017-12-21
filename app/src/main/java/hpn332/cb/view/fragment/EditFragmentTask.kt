package hpn332.cb.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton

import java.util.ArrayList

import hpn332.cb.R
import hpn332.cb.utils.helper.ProviderHelper
import hpn332.cb.model.stucture.CheckTagStructure
import hpn332.cb.model.stucture.TaskStructure
import hpn332.cb.utils.Key
import hpn332.cb.utils.List
import kotlinx.android.synthetic.main.content_edit_task_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_task.view.*

class EditFragmentTask : Fragment() {

    private var num1: RadioButton? = null
    private var num2: RadioButton? = null
    private var num3: RadioButton? = null
    private var num4: RadioButton? = null
    private var num5: RadioButton? = null
    private var title: EditText? = null
    private var description: EditText? = null
    private var tagLayout: LinearLayout? = null
    private lateinit var tagBoxes: Array<CheckBox?>

    /**
     * Get Array of List
     *
     * @return arrayList
     */
    private val arrayOfStep: ArrayList<TaskStructure>
        get() {

            when (activity.intent.getIntExtra(Key.STEP, 0)) {
                0 -> return List.L_TODO
                1 -> return List.L_DOING
                2 -> return List.L_DONE
            }

            throw IllegalArgumentException("Cant find array")
        }

    private val tags: String
        get() {
            Log.d(TAG, "getTags: ")

            val stringBuilder = StringBuilder()

            (0 until List.L_TAGS.size)
                .filter { tagBoxes[it]!!.isChecked }
                .forEach { stringBuilder.append(List.L_TAGS[it].id).append("`") }

            return stringBuilder.toString()
        }

    private val rank: Int
        get() {
            if (num1!!.isChecked) return 1
            if (num2!!.isChecked) return 2
            if (num3!!.isChecked) return 3
            if (num4!!.isChecked) return 4
            return if (num5!!.isChecked) 5 else 1
        }

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: start")

        val view = inflater!!.inflate(R.layout.fragment_edit_task, container, false)

        init(view)

        Log.d(TAG, "onCreateView: end")
        return view
    }

    private fun init(view: View) {

        Log.d(TAG, "init: start")

        tagLayout = view.layout_for_tags

        title = view.title_editText
        description = view.description_editText

        num1 = view.num1
        num2 = view.num2
        num3 = view.num3
        num4 = view.num4
        num5 = view.num5

        val arrayList = arrayOfStep

        val position = activity.intent.getIntExtra(Key.POSITION, 0)
        val id = arrayList[position].id
        val step = arrayList[position].step
        Log.d(TAG, "makeReadyStep: id :: " + id)

        title!!.setText(arrayList[position].title)
        description!!.setText(arrayList[position].desc)
        setCheckTag(arrayList, position)
        setCheckRank(arrayList[position].rank)

        view.backArrow_imageView.setOnClickListener { activity.finish() }

        view.addTag.setOnClickListener { startActivity(Intent(context, AddFragmentTag::class.java)) }

        view.fab.setOnClickListener {
            ProviderHelper.updateOneTask(
                id,
                title!!.text.toString(),
                description!!.text.toString(),
                tags,
                step,
                rank)
            Log.d(TAG, "onClick: update id ::" + id)

            activity.finish()
        }

        view.delete_imageView.setOnClickListener {
            ProviderHelper.deleteOneTask(id)
            activity.finish()
        }

        Log.d(TAG, "init: end")
    }

    private fun setCheckTag(arrayList: ArrayList<TaskStructure>, position: Int) {

        setupTags()

        val s = arrayList[position].tag.split("`".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (i in 0 until List.L_TAGS.size) {
            for (value in s) {
                if (value != "" && List.L_TAGS[i].id == Integer.valueOf(value)) {
                    tagBoxes[i]!!.toggle()
                    List.L_CHECK[i].isBool = true
                }
            }
        }
    }

    /**
     * Set the check box range
     *
     * @param rank
     */
    private fun setCheckRank(rank: Int) {
        when (rank) {
            1 -> num1!!.toggle()
            2 -> num2!!.toggle()
            3 -> num3!!.toggle()
            4 -> num4!!.toggle()
            5 -> num5!!.toggle()
        }
    }

    private fun setupTags() {
        Log.d(TAG, "setupTags: ")

        List.L_TAGS.clear()
        List.L_CHECK.clear()

        ProviderHelper.queryListTag(List.L_TAGS)

        for (arrayList in List.L_TAGS) {
            List.L_CHECK.add(
                CheckTagStructure(arrayList.title,
                    arrayList.color,
                    false))
            Log.d(TAG, "setupTags: color::" + arrayList.color)
        }

        tagBoxes = arrayOfNulls(List.L_CHECK.size)

        for (i in 0 until List.L_CHECK.size) {
            tagBoxes[i] = CheckBox(context)
            tagBoxes[i]!!.setPadding(8, 12, 8, 12)

            tagBoxes[i]!!.text = List.L_CHECK[i].title
            //tagBoxes[i].setBackgroundResource(Utils.getColor(List.L_CHECK.get(i).getColor()));

            tagLayout!!.addView(tagBoxes[i])
        }
    }

    companion object {

        private val TAG = "EditFragmentTask"
    }

}
