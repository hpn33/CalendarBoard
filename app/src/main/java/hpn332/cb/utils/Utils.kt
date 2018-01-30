package hpn332.cb.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View

import hpn332.cb.view.edit.EditActivity
import hpn332.library.view.ColorPanelView
import hpn332.library.view.ColorPickerView

object Utils {

    private const val TAG = "Utils"

    const val NULL = -1

    /**
     * This method init color picker
     *
     * @param colorPicker
     * @param newColorPanel
     * @param oldColorPanel
     * @param initColor
     */
    fun setupColorPicker(
        colorPicker: ColorPickerView, newColorPanel: ColorPanelView,
        oldColorPanel: ColorPanelView, initColor: Int = NULL) {
        var initColor = initColor


        // black -16777216
        // white -2697514
//        if (initColor == NULL) initColor = -16777216
        if (initColor == NULL) initColor = -2697514

        colorPicker.setAlphaSliderVisible(false)
        colorPicker.setColor(initColor, true)
        oldColorPanel.color = initColor
        newColorPanel.color = initColor

        colorPicker.setOnColorChangedListener(object : ColorPickerView.OnColorChangedListener {
            override fun onColorChanged(newColor: Int) {
                newColorPanel.color = newColor
                Log.d(TAG, "onColorChanged: color :: " + newColor)
            }
        })
    }

    /**
     * With this method can going to another activity
     * with intent and any info that maybe need
     *
     * @param context Context
     * @param type Int
     * @param position Int
     * @param project_id Int
     * @param task_id Int
     * @param id
     */
    fun goToEdit(
        context: Context, type: Int = NULL, position: Int = NULL,
        project_id: Int = NULL, task_id: Int = NULL, id: Int = NULL) {

        goTo(context, EditActivity::class.java, type, position, project_id, task_id, id)
    }

    /**
     * With this method can going to another activity
     * with intent and any info that maybe need
     *
     * @param context Context
     * @param clazz Class
     * @param type Int
     * @param position Int
     * @param project_id Int
     * @param task_id Int
     * @param id
     */
    fun goTo(
        context: Context, clazz: Class<*>, type: Int = NULL, position: Int = NULL,
        project_id: Int = NULL, task_id: Int = NULL, id: Int = NULL) {

        val intent = Intent(context.applicationContext, clazz)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        if (type != NULL) intent.putExtra(Key.TYPE, type)
        if (id != NULL) intent.putExtra(Key.ID, id)
        if (position != NULL) intent.putExtra(Key.POSITION, position)
        if (project_id != NULL) intent.putExtra(Key.PROJECT_ID, project_id)
        if (task_id != NULL) intent.putExtra(Key.TASK_ID, task_id)

        context.startActivity(intent)
    }

    /**
     * setOnClick
     *
     * @param activity
     * @param f this a function
     */
    fun View.onClick(activity: Activity? = null, f: () -> Unit = {}) {
        this.setOnClickListener {
            f()
            activity?.finish()
        }
    }
}
