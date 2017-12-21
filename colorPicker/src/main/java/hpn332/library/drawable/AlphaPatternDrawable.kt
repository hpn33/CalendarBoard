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

package hpn332.library.drawable

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Bitmap.Config
import android.graphics.drawable.Drawable

/**
 * This drawable will draw a simple white and gray chessboard pattern.
 * It's pattern you will often see as a background behind a
 * partly transparent image in many applications.
 * @author Daniel Nilsson
 */
class AlphaPatternDrawable(rectangleSize: Int) : Drawable() {

	private var mRectangleSize = 10

	private val mPaint = Paint()
	private val mPaintWhite = Paint()
	private val mPaintGray = Paint()

	private var numRectanglesHorizontal: Int = 0
	private var numRectanglesVertical: Int = 0

	/**
	 * Bitmap in which the pattern will be cached.
	 * This is so the pattern will not have to be recreated each time draw() gets called.
	 * Because recreating the pattern i rather expensive. I will only be recreated if the
	 * size changes.
	 */
	private var mBitmap: Bitmap? = null

	init {
		mRectangleSize = rectangleSize
		mPaintWhite.color = -0x1
		mPaintGray.color = -0x343435
	}

	override fun draw(canvas: Canvas) {
		if (mBitmap != null && !mBitmap!!.isRecycled)
			canvas.drawBitmap(mBitmap!!, null, bounds, mPaint)
	}

	override fun getOpacity(): Int = 0

	override fun setAlpha(alpha: Int) {
		throw UnsupportedOperationException("Alpha is not supported by this drawable.")
	}

	override fun setColorFilter(cf: ColorFilter?) {
		throw UnsupportedOperationException("ColorFilter is not supported by this drawable.")
	}

	override fun onBoundsChange(bounds: Rect) {
		super.onBoundsChange(bounds)

		val height = bounds.height()
		val width = bounds.width()

		numRectanglesHorizontal = Math.ceil((width / mRectangleSize).toDouble()).toInt()
		numRectanglesVertical = Math.ceil((height / mRectangleSize).toDouble()).toInt()

		generatePatternBitmap()

	}

	/**
	 * This will generate a bitmap with the pattern
	 * as big as the rectangle we were allow to draw on.
	 * We do this to chache the bitmap so we don't need to
	 * recreate it each time draw() is called since it
	 * takes a few milliseconds.
	 */
	private fun generatePatternBitmap() {

		if (bounds.width() <= 0 || bounds.height() <= 0) return

		mBitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Config.ARGB_8888)
		val canvas = Canvas(mBitmap!!)

		val r = Rect()
		var verticalStartWhite = true
		for (i in 0..numRectanglesVertical) {

			var isWhite = verticalStartWhite
			for (j in 0..numRectanglesHorizontal) {

                with(r){
                    top = i * mRectangleSize
                    left = j * mRectangleSize
                    bottom = top + mRectangleSize
                    right = left + mRectangleSize
                }
//                r.bottom = r.top + mRectangleSize
//                r.right = r.left + mRectangleSize

				canvas.drawRect(r, if (isWhite) mPaintWhite else mPaintGray)

				isWhite = !isWhite
			}

			verticalStartWhite = !verticalStartWhite

		}

	}

}
