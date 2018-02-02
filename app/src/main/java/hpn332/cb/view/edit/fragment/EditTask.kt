package hpn332.cb.view.edit.fragment

import android.arch.lifecycle.Observer
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import hpn332.cb.R
import hpn332.cb.model.abstrac.Frag
import hpn332.cb.model.stucture.Task
import hpn332.cb.utils.Utils.onClick
import hpn332.cb.view.edit.EditActivity
import hpn332.cb.view.edit.EditActivity.Companion.editViewModel
import kotlinx.android.synthetic.main.content_edit_task_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_task.view.*

class EditTask : Frag() {

    override fun setView(): Int = R.layout.fragment_edit_task

    override fun init(view: View, id: Int) {
        title = view.title_editText
        desc = view.description_editText
        num1 = view.num1
        num2 = view.num2
        num3 = view.num3
        num4 = view.num4
        num5 = view.num5

        editViewModel.getTaskById(id).observe(this,
            Observer {
                it!!
                title.setText(it.title)
                desc.setText(it.desc)
                setCheckRank(it.rank)

                item = it
            })


        with(view) {
            fab.onClick(activity) {
                EditActivity.editViewModel.updateTask(
                    Task(
                        id,
                        title.text.toString(),
                        desc.text.toString(),
                        item.backlog_id,
                        item.project_id,
                        item.tag_id,
                        item.step,
                        rank
                    )
                )
            }

            delete_imageView.onClick(activity) { editViewModel.deleteTasksById(id) }

            backArrow_imageView.onClick(activity)
        }
    }

    private lateinit var num1: RadioButton
    private lateinit var num2: RadioButton
    private lateinit var num3: RadioButton
    private lateinit var num4: RadioButton
    private lateinit var num5: RadioButton
    private lateinit var title: EditText
    private lateinit var desc: EditText

    private lateinit var item: Task

    private val rank: Int
        get() {
            return when {
                num1.isChecked -> 1
                num2.isChecked -> 2
                num3.isChecked -> 3
                num4.isChecked -> 4
                num5.isChecked -> 5
                else           -> 1
            }
        }

    private val tags: String
        get() {

            val stringBuilder = StringBuilder()

//            (0 until List.L_TAGS.size)
//                //                .filter { tagBoxes[it]!!.isChecked }
//                .forEach { stringBuilder.append(List.L_TAGS[it].id).append("`") }

            return stringBuilder.toString()
        }

    private fun setCheckTag(arrayList: ArrayList<Task>, position: Int) {

        //        setupTags()

        val s = arrayList[position].tag_id.split("`".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

//        for (i in 0 until List.L_TAGS.size) {
//            for (value in s) {
//                if (value != "" && List.L_TAGS[i].id == Integer.valueOf(value)) {
//                    //                    tagBoxes[i]!!.toggle()
//                    List.L_CHECK[i].isBool = true
//                }
//            }
//        }
    }

    /**
     * Set the check box range
     *
     * @param rank
     */
    private fun setCheckRank(rank: Int) {
        when (rank) {
            1 -> num1.toggle()
            2 -> num2.toggle()
            3 -> num3.toggle()
            4 -> num4.toggle()
            5 -> num5.toggle()
        }
    }

    /*private fun setupTags() {
        Log.d(TAG, "setupTags: ")

        List.L_TAGS.clear()
        List.L_CHECK.clear()

//        ProviderHelper.queryListTag(List.L_TAGS)

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
    }*/


}
