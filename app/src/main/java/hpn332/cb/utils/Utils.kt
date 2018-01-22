package hpn332.cb.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View

import hpn332.cb.view.edit.AEdit
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
        var minitColor = initColor

        if (minitColor == NULL) minitColor = -16777216

        colorPicker.setAlphaSliderVisible(false)
        colorPicker.setColor(minitColor, true)
        oldColorPanel.color = minitColor
        newColorPanel.color = minitColor


        colorPicker.setOnClickListener { }
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
     * @param step Int
     * @param project Int
     */
    fun goTo(
        context: Context, type: Int = NULL, position: Int = NULL, step: Int = NULL, project: Int = NULL, id: Int = NULL) {

        val intent = Intent(context.applicationContext, AEdit::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .putExtra(Key.TYPE, type)

        if (position != NULL) intent.putExtra(Key.POSITION, position)
        if (project != NULL) intent.putExtra(Key.PROJECT, project)
        if (step != NULL) intent.putExtra(Key.STEP, step)
        if (id != NULL) intent.putExtra(Key.ID, id)

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
