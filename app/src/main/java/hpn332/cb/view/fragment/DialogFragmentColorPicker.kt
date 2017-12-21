/*
 * Copyright (C) 2015 Daniel Nilsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hpn332.cb.view.fragment

import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button

import hpn332.cb.R
import hpn332.library.view.ColorPanelView
import hpn332.library.view.ColorPickerView
import kotlinx.android.synthetic.main.content_color_picker.view.*

class DialogFragmentColorPicker : DialogFragment() {

	private var colorPicker: ColorPickerView? = null
	private var oldColorPanel: ColorPanelView? = null
	private var newColorPanel: ColorPanelView? = null
	private var okButton: Button? = null

	private var mListener: ColorPickerDialogListener? = null

	interface ColorPickerDialogListener {
		fun onColorSelected(color: Int)

		fun onDialogDismissed(color: Int)
	}

	/*@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		Log.d("color-picker-view", "onAttach()");

		// Check for listener in parent activity
		try {
			mListener = (ColorPickerDialogListener) activity;
		} catch (ClassCastException e) {
			e.printStackTrace();
			throw new ClassCastException("Parent activity must implement "
					                             + "ColorPickerDialogListener to receive result.");
		}
	}*/

	override fun onAttach(context: Context) {
		super.onAttach(context)

		Log.d("color-picker-view", "onAttach()")

		// Check for listener in parent activity
		try {
			mListener = context as ColorPickerDialogListener
		} catch (e: ClassCastException) {
			e.printStackTrace()
			throw ClassCastException("Parent activity must implement " + "ColorPickerDialogListener to receive result.")
		}

	}

	override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
		val dialog = super.onCreateDialog(savedInstanceState)

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
		dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT)

		return dialog
	}

	override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle): View? {
		val view = inflater.inflate(R.layout.content_color_picker, container)

		colorPicker = view.color_picker_view
		oldColorPanel = view.color_panel_old
		newColorPanel = view.color_panel_new
		okButton = view.done_button

		colorPicker!!.setOnColorChangedListener(object : ColorPickerView.OnColorChangedListener {
            override fun onColorChanged(newColor: Int) {
                newColorPanel!!.color = newColor
            }

        })

		okButton!!.setOnClickListener {
            mListener!!.onColorSelected(colorPicker!!.color)
			dialog.dismiss()
		}

		okButton!!.text = "DONE"

		var initColor = arguments.getInt("init_color")

		if (initColor == 0) initColor = -16777216

		oldColorPanel!!.color = initColor
		colorPicker!!.setColor(initColor, true)

		return view
	}

	override fun onDismiss(dialog: DialogInterface) {
		super.onDismiss(dialog)
		mListener!!.onDialogDismissed(colorPicker!!.color)
	}

	companion object {

		fun newInstance(initialColor: Int): DialogFragmentColorPicker {

			val frag = DialogFragmentColorPicker()
			val args = Bundle()

			args.putInt("init_color", initialColor)

			frag.arguments = args

			return frag
		}
	}
}
