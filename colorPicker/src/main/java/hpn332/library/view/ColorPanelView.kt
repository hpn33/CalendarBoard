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

package hpn332.library.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

import hpn332.library.R
import hpn332.library.drawable.AlphaPatternDrawable

/**
 * This class draws a panel which which will be filled with a color which can be set.
 * It can be used to show the currently selected color which you will get from
 * the [ColorPickerView].
 * @author Daniel Nilsson
 */
class ColorPanelView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {

	private var mBorderColor = DEFAULT_BORDER_COLOR
	private var mColor = -0x1000000

	private var mBorderPaint: Paint? = null
	private var mColorPaint: Paint? = null

	private var mDrawingRect: Rect? = null
	private var mColorRect: Rect? = null

	private var mAlphaPattern: AlphaPatternDrawable? = null

	/**
	 * Get the color currently show by this view.
	 * @return color
	 */
	/**
	 * Set the color that should be shown by this view.
	 * @param color
	 */
	var color: Int
		get() = mColor
		set(color) {
			mColor = color
			invalidate()
		}

	init {
		init(context, attrs)
	}

	public override fun onSaveInstanceState(): Parcelable? {

		val state = Bundle()
		state.putParcelable("instanceState", super.onSaveInstanceState())
		state.putInt("color", mColor)

		return state
	}

	public override fun onRestoreInstanceState(state: Parcelable?) {
		var state = state

		if (state is Bundle) {
			val bundle = state as Bundle?
			mColor = bundle!!.getInt("color")
			state = bundle.getParcelable("instanceState")
		}
		super.onRestoreInstanceState(state)
	}


	private fun init(context: Context, attrs: AttributeSet?) {

		val a = getContext().obtainStyledAttributes(attrs, R.styleable.colorpickerview__ColorPickerView)
		mBorderColor = a.getColor(R.styleable.colorpickerview__ColorPickerView_borderColor, -0x919192)
		a.recycle()

		applyThemeColors(context)

		mBorderPaint = Paint()
		mColorPaint = Paint()
	}

	private fun applyThemeColors(c: Context) {
		// If no specific border color has been
		// set we take the default secondary text color
		// as border/slider color. Thus it will adopt
		// to theme changes automatically.

		val value = TypedValue()
		val a = c.obtainStyledAttributes(value.data, intArrayOf(android.R.attr.textColorSecondary))

		if (mBorderColor == DEFAULT_BORDER_COLOR)
			mBorderColor = a.getColor(0, DEFAULT_BORDER_COLOR)

		a.recycle()
	}


	override fun onDraw(canvas: Canvas) {
		val rect = mColorRect

		//if(BORDER_WIDTH_PX > 0){
		mBorderPaint!!.color = mBorderColor
		canvas.drawRect(mDrawingRect!!, mBorderPaint!!)
		//}

		if (mAlphaPattern != null) mAlphaPattern!!.draw(canvas)

		mColorPaint!!.color = mColor

		canvas.drawRect(rect!!, mColorPaint!!)
	}

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

		val width = View.MeasureSpec.getSize(widthMeasureSpec)
		val height = View.MeasureSpec.getSize(heightMeasureSpec)

		setMeasuredDimension(width, height)
	}

	override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
		super.onSizeChanged(w, h, oldw, oldh)

		mDrawingRect = Rect()

        with(mDrawingRect!!){
            left = paddingLeft
            right = w - paddingRight
            top = paddingTop
            bottom = h - paddingBottom
        }

		setUpColorRect()
	}

	private fun setUpColorRect() {
		val dRect = mDrawingRect

		val left = dRect!!.left + BORDER_WIDTH_PX
		val top = dRect.top + BORDER_WIDTH_PX
		val bottom = dRect.bottom - BORDER_WIDTH_PX
		val right = dRect.right - BORDER_WIDTH_PX

		mColorRect = Rect(left, top, right, bottom)

		mAlphaPattern = AlphaPatternDrawable(DrawingUtils.dpToPx(context, 2f))

		mAlphaPattern!!.setBounds(Math.round(mColorRect!!.left.toFloat()),
				Math.round(mColorRect!!.top.toFloat()),
				Math.round(mColorRect!!.right.toFloat()),
				Math.round(mColorRect!!.bottom.toFloat()))

	}

	companion object {

		/**
		 * The width in pixels of the border
		 * surrounding the color panel.
		 */
		private val BORDER_WIDTH_PX = 1

		private val DEFAULT_BORDER_COLOR = -0x919192
	}

}
