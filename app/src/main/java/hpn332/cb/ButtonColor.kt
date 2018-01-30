package hpn332.cb

import android.content.Context
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet

class ButtonColor : AppCompatButton {

    private var mListener: OnShowDialogListener? = null

    private val mColor = -0x1000000

    interface OnShowDialogListener {
        fun onShowColorPickerDialog(initColor: Int)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

        setOnClickListener {
            if (mListener != null)
                mListener!!.onShowColorPickerDialog(mColor)
            else
                throw IllegalArgumentException(
                    "You must first call setOnShowDialogListener() and " + "handle showing the DialogFragmentColorPicker yourself.")

        }
    }

    /**
     * Since the color picker dialog is now a DialogFragment
     * this preference cannot take care of showing it without
     * access to the fragment manager. Therefore I leave it up to
     * you to actually show the dialog once the preference is clicked.
     *
     *
     * Call saveValue() once you have a color to save.
     *
     * @param listener
     */
    fun setOnShowDialogListener(listener: OnShowDialogListener) {
        mListener = listener
    }

}
