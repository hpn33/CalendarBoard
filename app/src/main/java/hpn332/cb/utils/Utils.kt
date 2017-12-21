package hpn332.cb.utils

import android.content.Context
import android.content.Intent
import android.util.Log

import hpn332.cb.view.activity.EditActivity
import hpn332.library.view.ColorPanelView
import hpn332.library.view.ColorPickerView

object Utils {

    private val TAG = "Utils"

    val NULL = -1

    fun setupColorPicker(
        colorPicker: ColorPickerView, newColorPanel: ColorPanelView,
        oldColorPanel: ColorPanelView, initColor: Int) {
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

    fun goToP(context: Context, type: Int, project: Int) =
        goTo(context, type, NULL, NULL, project)

    @JvmOverloads
    fun goTo(context: Context, type: Int, position: Int = NULL) =
        goTo(context, type, position, NULL, NULL)

    fun goTo(context: Context, type: Int, position: Int, step: Int) =
        goTo(context, type, position, step, NULL)

    private fun goTo(
        context: Context, type: Int, position: Int, step: Int, project: Int) {

        val intent = Intent(context.applicationContext, EditActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .putExtra(Key.TYPE, type)

        if (position != NULL) intent.putExtra(Key.POSITION, position)
        if (project != NULL) intent.putExtra(Key.PROJECT, project)
        if (step != NULL) intent.putExtra(Key.STEP, step)

        context.startActivity(intent)
    }
}
