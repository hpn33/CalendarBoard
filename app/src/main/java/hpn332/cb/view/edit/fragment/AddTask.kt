package hpn332.cb.view.edit.fragment

import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import hpn332.cb.R
import hpn332.cb.model.abstrac.Frag
import hpn332.cb.model.stucture.Task
import hpn332.cb.utils.Utils.onClick
import hpn332.cb.view.edit.EditActivity.Companion.editViewModel
import kotlinx.android.synthetic.main.content_edit_task_center.view.*
import kotlinx.android.synthetic.main.content_edit_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_edit_task.view.*

class AddTask : Frag() {
    override fun setView(): Int = R.layout.fragment_edit_task

    override fun init(view: View, id: Int) {

        title = view.title_editText
        description = view.description_editText
        num1 = view.num1
        num2 = view.num2
        num3 = view.num3
        num4 = view.num4
        num5 = view.num5

        with(view) {
            delete_imageView.visibility = View.GONE

            fab.onClick(activity) {
                editViewModel.insertTask(
                    Task(
                        title = title.text.toString(),
                        desc = description.text.toString(),
                        project_id = id,
                        rank = rank))
            }

            backArrow_imageView.onClick(activity)
        }
    }

    private lateinit var num1: RadioButton
    private lateinit var num2: RadioButton
    private lateinit var num3: RadioButton
    private lateinit var num4: RadioButton
    private lateinit var num5: RadioButton
    private lateinit var title: EditText
    private lateinit var description: EditText

    private val tags: String
        get() {
            val stringBuilder = StringBuilder()

//            (0 until List.L_TAGS.size)
//                //                .filter { tagBoxes[it]!!.isChecked }
//                .forEach { stringBuilder.append(List.L_TAGS[it].id).append("`") }

            return stringBuilder.toString()
        }

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

    /*private fun setupTags() {

		List.L_TAGS.clear()
		List.L_CHECK.clear()

		//		ProviderHelper.queryListTag(List.L_TAGS)

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