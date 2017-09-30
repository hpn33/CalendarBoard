package hpn332.cb;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class ButtonColor extends AppCompatButton {

	public interface OnShowDialogListener {
		void onShowColorPickerDialog(int initColor);
	}

	private OnShowDialogListener mListener;

	private int mColor = 0xFF000000;

	public ButtonColor(Context context) {
		super(context);
		init();
	}

	public ButtonColor(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ButtonColor(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {

		setOnClickListener(view -> {
			if (mListener != null) mListener.onShowColorPickerDialog(mColor);
			else throw new IllegalArgumentException(
					"You must first call setOnShowDialogListener() and "
							+ "handle showing the DialogFragmentColorPicker yourself.");

		});
	}

	/**
	 * Since the color picker dialog is now a DialogFragment
	 * this preference cannot take care of showing it without
	 * access to the fragment manager. Therefore I leave it up to
	 * you to actually show the dialog once the preference is clicked.
	 * <p>
	 * Call saveValue() once you have a color to save.
	 *
	 * @param listener
	 */
	public void setOnShowDialogListener(OnShowDialogListener listener) {
		mListener = listener;
	}

}
