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
package hpn332.cb.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import hpn332.cb.R;
import hpn332.library.view.ColorPanelView;
import hpn332.library.view.ColorPickerView;

public class DialogFragmentColorPicker extends DialogFragment {

	public interface ColorPickerDialogListener {
		void onColorSelected(int color);

		void onDialogDismissed(int color);
	}

	private ColorPickerView colorPicker;
	private ColorPanelView  oldColorPanel;
	private ColorPanelView  newColorPanel;
	private Button          okButton;

	private ColorPickerDialogListener mListener;

	public static DialogFragmentColorPicker newInstance(int initialColor) {

		DialogFragmentColorPicker frag = new DialogFragmentColorPicker();
		Bundle                    args = new Bundle();

		args.putInt("init_color", initialColor);

		frag.setArguments(args);

		return frag;
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

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		Log.d("color-picker-view", "onAttach()");

		// Check for listener in parent activity
		try {
			mListener = (ColorPickerDialogListener) context;
		} catch (ClassCastException e) {
			e.printStackTrace();
			throw new ClassCastException("Parent activity must implement "
					                             + "ColorPickerDialogListener to receive result.");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
		                             ViewGroup.LayoutParams.WRAP_CONTENT);

		return dialog;
	}

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.content_color_picker, container);

		colorPicker = view.findViewById(R.id.color_picker_view);
		oldColorPanel = view.findViewById(R.id.color_panel_old);
		newColorPanel = view.findViewById(R.id.color_panel_new);
		okButton = view.findViewById(R.id.done_button);

		colorPicker.setOnColorChangedListener(newColor -> newColorPanel.setColor(newColor));

		okButton.setOnClickListener(view1 -> {
			mListener.onColorSelected(colorPicker.getColor());
			getDialog().dismiss();
		});

		okButton.setText("DONE");

		int initColor = getArguments().getInt("init_color");

		if (initColor == 0) initColor = -16777216;

		oldColorPanel.setColor(initColor);
		colorPicker.setColor(initColor, true);

		return view;
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		mListener.onDialogDismissed(colorPicker.getColor());
	}
}
