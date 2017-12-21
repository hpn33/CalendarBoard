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
 *
 * 
 * 

 * Change Log:
 * 
 * 1.1
 * - Fixed buggy measure and layout code. You can now make the view any size you want.
 * - Optimization of the drawing using a bitmap cache, a lot faster!
 * - Support for hardware acceleration for all but the problematic
 *	 part of the view will still be software rendered but much faster!
 *   See comment in drawSatValPanel() for more info.
 * - Support for declaring some variables in xml.
 * 
 * 1.2 - 2015-05-08
 * - More bugs in onMeasure() have been fixed, should handle all cases properly now.
 * - View automatically saves its state now.
 * - Automatic border color depending on current theme.
 * - Code cleanup, trackball support removed since they do not exist anymore.
 * 
 * 1.3 - 2015-05-10
 * - Fixed hue bar selection did not align with what was shown in the sat/val panel.
 *   Fixed by replacing the linear gardient used before. Now drawing individual lines
 *   of different colors. This was expensive so we now use a bitmap cache for the hue
 *   panel too.
 * - Replaced all RectF used in the layout process with Rect since the
 *   floating point values was causing layout issues (perfect alignment).
 */
package hpn332.library.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ComposeShader
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.Paint.Style
import android.graphics.Point
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Shader.TileMode
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

import hpn332.library.R
import hpn332.library.drawable.AlphaPatternDrawable

/**
 * Displays a color picker to the user and allow them
 * to select a color. A slider for the alpha channel is
 * also available. Enable it by setting
 * setAlphaSliderVisible(boolean) to true.
 *
 * @author Daniel Nilsson
 */
class ColorPickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle) {

    /**
     * The width in px of the hue panel.
     */
    private var mHuePanelWidthPx: Int = 0
    /**
     * The height in px of the alpha panel
     */
    private var mAlphaPanelHeightPx: Int = 0
    /**
     * The distance in px between the different
     * color panels.
     */
    private var mPanelSpacingPx: Int = 0
    /**
     * The radius in px of the color palette tracker circle.
     */
    private var mCircleTrackerRadiusPx: Int = 0
    /**
     * The px which the tracker of the hue or alpha panel
     * will extend outside of its bounds.
     */
    private var mSliderTrackerOffsetPx: Int = 0
    /**
     * Height of slider tracker on hue panel,
     * width of slider on alpha panel.
     */
    private var mSliderTrackerSizePx: Int = 0

    private var mSatValPaint: Paint? = null
    private var mSatValTrackerPaint: Paint? = null

    private var mAlphaPaint: Paint? = null
    private var mAlphaTextPaint: Paint? = null
    private var mHueAlphaTrackerPaint: Paint? = null

    private var mBorderPaint: Paint? = null

    private var mValShader: Shader? = null
    private var mSatShader: Shader? = null
    private var mAlphaShader: Shader? = null

    /*
	 * We cache a bitmap of the sat/val panel which is expensive to draw each time.
	 * We can reuse it when the user is sliding the circle picker as long as the hue isn't changed.
	 */
    private var mSatValBackgroundCache: BitmapCache? = null
    /* We cache the hue background to since its also very expensive now. */
    private var mHueBackgroundCache: BitmapCache? = null

    /* Current values */
    private var mAlpha = 0xff
    private var mHue = 360f
    private var mSat = 0f
    private var mVal = 0f

    private var mShowAlphaPanel = false
    private var mAlphaSliderText: String? = null
    private var mSliderTrackerColor = DEFAULT_SLIDER_COLOR
    private var mBorderColor = DEFAULT_BORDER_COLOR

    /**
     * Minimum required padding. The offset from the
     * edge we must have or else the finger tracker will
     * get clipped when it's drawn outside of the view.
     */
    private var mRequiredPadding: Int = 0

    /**
     * The Rect in which we are allowed to draw.
     * Trackers can extend outside slightly,
     * due to the required padding we have set.
     */
    private var mDrawingRect: Rect? = null

    private var mSatValRect: Rect? = null
    private var mHueRect: Rect? = null
    private var mAlphaRect: Rect? = null

    private var mStartTouchPoint: Point? = null

    private var mAlphaPattern: AlphaPatternDrawable? = null
    private var mListener: OnColorChangedListener? = null

    private //Our preferred width and height is 200dp for the square sat / val rectangle.
    val preferredWidth: Int
        get() {
            val width = DrawingUtils.dpToPx(context, 200f)

            return (width + mHuePanelWidthPx + mPanelSpacingPx).toInt()
        }

    private val preferredHeight: Int
        get() {
            var height = DrawingUtils.dpToPx(context, 200f)

            if (mShowAlphaPanel) {
                height += mPanelSpacingPx + mAlphaPanelHeightPx
            }
            return height
        }

    /**
     * Get the current color this view is showing.
     *
     * @return the current color.
     */
    /**
     * Set the color the view should show.
     *
     * @param color The color that should be selected. #argb
     */
    var color: Int
        get() = Color.HSVToColor(mAlpha, floatArrayOf(mHue, mSat, mVal))
        set(color) = setColor(color, false)

    /**
     * Get color of the tracker slider on the hue and alpha panel.
     *
     * @return
     */
    /**
     * Set the color of the tracker slider on the hue and alpha panel.
     *
     * @param color
     */
    var sliderTrackerColor: Int
        get() = mSliderTrackerColor
        set(color) {
            mSliderTrackerColor = color
            mHueAlphaTrackerPaint!!.color = mSliderTrackerColor
            invalidate()
        }

    /**
     * Get the color of the border surrounding all panels.
     */
    /**
     * Set the color of the border surrounding all panels.
     *
     * @param color
     */
    var borderColor: Int
        get() = mBorderColor
        set(color) {
            mBorderColor = color
            invalidate()
        }

    interface OnColorChangedListener {
        fun onColorChanged(newColor: Int)
    }

    init {
        init(context, attrs)
    }

    public override fun onSaveInstanceState(): Parcelable? {

        val state = Bundle()
        state.putParcelable("instanceState", super.onSaveInstanceState())
        state.putInt("alpha", mAlpha)
        state.putFloat("hue", mHue)
        state.putFloat("sat", mSat)
        state.putFloat("val", mVal)
        state.putBoolean("show_alpha", mShowAlphaPanel)
        state.putString("alpha_text", mAlphaSliderText)

        return state
    }

    public override fun onRestoreInstanceState(state: Parcelable?) {
        var state = state

        if (state is Bundle) {
            val bundle = state as Bundle?

            mAlpha = bundle!!.getInt("alpha")
            mHue = bundle.getFloat("hue")
            mSat = bundle.getFloat("sat")
            mVal = bundle.getFloat("val")
            mShowAlphaPanel = bundle.getBoolean("show_alpha")
            mAlphaSliderText = bundle.getString("alpha_text")


            state = bundle.getParcelable("instanceState")
        }
        super.onRestoreInstanceState(state)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        //Load those if set in xml resource file.
        val a = getContext()
            .obtainStyledAttributes(attrs, R.styleable.colorpickerview__ColorPickerView)
        mShowAlphaPanel = a.getBoolean(R.styleable.colorpickerview__ColorPickerView_alphaChannelVisible,
            false)
        mAlphaSliderText = a.getString(R.styleable.colorpickerview__ColorPickerView_alphaChannelText)
        mSliderTrackerColor = a.getColor(R.styleable.colorpickerview__ColorPickerView_sliderColor, -0x424243)
        mBorderColor = a.getColor(R.styleable.colorpickerview__ColorPickerView_borderColor, -0x919192)
        a.recycle()

        applyThemeColors(context)


        mHuePanelWidthPx = DrawingUtils.dpToPx(getContext(), HUE_PANEL_WIDTH_DP.toFloat())
        mAlphaPanelHeightPx = DrawingUtils.dpToPx(getContext(), ALPHA_PANEL_HEIGHT_DP.toFloat())
        mPanelSpacingPx = DrawingUtils.dpToPx(getContext(), PANEL_SPACING_DP.toFloat())
        mCircleTrackerRadiusPx = DrawingUtils.dpToPx(getContext(), CIRCLE_TRACKER_RADIUS_DP.toFloat())
        mSliderTrackerSizePx = DrawingUtils.dpToPx(getContext(), SLIDER_TRACKER_SIZE_DP.toFloat())
        mSliderTrackerOffsetPx = DrawingUtils.dpToPx(getContext(), SLIDER_TRACKER_OFFSET_DP.toFloat())

        mRequiredPadding = resources.getDimensionPixelSize(R.dimen.colorpickerview__required_padding)

        initPaintTools()

        //Needed for receiving trackball motion events.
        isFocusable = true
        isFocusableInTouchMode = true
    }

    private fun applyThemeColors(c: Context) {
        // If no specific border/slider color has been
        // set we take the default secondary text color
        // as border/slider color. Thus it will adopt
        // to theme changes automatically.

        val value = TypedValue()
        val a = c.obtainStyledAttributes(value.data, intArrayOf(android.R.attr.textColorSecondary))

        if (mBorderColor == DEFAULT_BORDER_COLOR)
            mBorderColor = a.getColor(0, DEFAULT_BORDER_COLOR)

        if (mSliderTrackerColor == DEFAULT_SLIDER_COLOR)
            mSliderTrackerColor = a.getColor(0, DEFAULT_SLIDER_COLOR)

        a.recycle()
    }

    private fun initPaintTools() {

        mSatValPaint = Paint()
        mSatValTrackerPaint = Paint()
        mHueAlphaTrackerPaint = Paint()
        mAlphaPaint = Paint()
        mAlphaTextPaint = Paint()
        mBorderPaint = Paint()

        with(mSatValTrackerPaint!!) {
            style = Style.STROKE
            strokeWidth = DrawingUtils.dpToPx(context, 2f).toFloat()
            isAntiAlias = true
        }

        with(mHueAlphaTrackerPaint!!) {
            color = mSliderTrackerColor
            style = Style.STROKE
            strokeWidth = DrawingUtils.dpToPx(context, 2f).toFloat()
            isAntiAlias = true
        }

        with(mAlphaTextPaint!!) {
            color = -0xe3e3e4
            textSize = DrawingUtils.dpToPx(context, 14f).toFloat()
            isAntiAlias = true
            textAlign = Align.CENTER
            isFakeBoldText = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (mDrawingRect!!.width() <= 0 || mDrawingRect!!.height() <= 0) {
            return
        }

        drawSatValPanel(canvas)
        drawHuePanel(canvas)
        drawAlphaPanel(canvas)
    }

    private fun drawSatValPanel(canvas: Canvas) {
        val rect = mSatValRect

        if (BORDER_WIDTH_PX > 0) {
            mBorderPaint!!.color = mBorderColor
            canvas.drawRect(mDrawingRect!!.left.toFloat(), mDrawingRect!!.top.toFloat(),
                (rect!!.right + BORDER_WIDTH_PX).toFloat(),
                (rect.bottom + BORDER_WIDTH_PX).toFloat(), mBorderPaint!!)
        }

        if (mValShader == null) {
            //Black gradient has either not been created or the view has been resized.
            mValShader = LinearGradient(
                rect!!.left.toFloat(), rect.top.toFloat(), rect.left.toFloat(), rect.bottom.toFloat(),
                -0x1, -0x1000000, TileMode.CLAMP)
        }

        //If the hue has changed we need to recreate the cache.
        if (mSatValBackgroundCache == null || mSatValBackgroundCache!!.value != mHue) {

            if (mSatValBackgroundCache == null) {
                mSatValBackgroundCache = BitmapCache()
            }

            //We create our bitmap in the cache if it doesn't exist.
            if (mSatValBackgroundCache!!.bitmap == null) {
                mSatValBackgroundCache!!.bitmap = Bitmap
                    .createBitmap(rect!!.width(), rect.height(), Config.ARGB_8888)
            }

            //We create the canvas once so we can draw on our bitmap and the hold on to it.
            if (mSatValBackgroundCache!!.canvas == null) {
                mSatValBackgroundCache!!.canvas = Canvas(mSatValBackgroundCache!!.bitmap!!)
            }

            val rgb = Color.HSVToColor(floatArrayOf(mHue, 1f, 1f))

            mSatShader = LinearGradient(
                rect!!.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.top.toFloat(),
                -0x1, rgb, TileMode.CLAMP)

            val mShader = ComposeShader(
                mValShader!!, mSatShader!!, PorterDuff.Mode.MULTIPLY)
            mSatValPaint!!.shader = mShader

            // Finally we draw on our canvas, the result will be
            // stored in our bitmap which is already in the cache.
            // Since this is drawn on a canvas not rendered on
            // screen it will automatically not be using the
            // hardware acceleration. And this was the code that
            // wasn't supported by hardware acceleration which mean
            // there is no need to turn it of anymore. The rest of
            // the view will still be hw accelerated.
            mSatValBackgroundCache!!.canvas!!.drawRect(0f, 0f,
                mSatValBackgroundCache!!.bitmap!!.width.toFloat(),
                mSatValBackgroundCache!!.bitmap!!.height.toFloat(),
                mSatValPaint!!)

            //We set the hue value in our cache to which hue it was drawn with,
            //then we know that if it hasn't changed we can reuse our cached bitmap.
            mSatValBackgroundCache!!.value = mHue
        }

        // We draw our bitmap from the cached, if the hue has changed
        // then it was just recreated otherwise the old one will be used.
        canvas.drawBitmap(mSatValBackgroundCache!!.bitmap!!, null, rect!!, null)

        val p = satValToPoint(mSat, mVal)

        mSatValTrackerPaint!!.color = -0x1000000
        canvas.drawCircle(p.x.toFloat(), p.y.toFloat(),
            (mCircleTrackerRadiusPx - DrawingUtils.dpToPx(context, 1f)).toFloat(),
            mSatValTrackerPaint!!)

        mSatValTrackerPaint!!.color = -0x222223
        canvas.drawCircle(p.x.toFloat(), p.y.toFloat(),
            mCircleTrackerRadiusPx.toFloat(), mSatValTrackerPaint!!)
    }

    private fun drawHuePanel(canvas: Canvas) {
        val rect = mHueRect

        if (BORDER_WIDTH_PX > 0) {
            mBorderPaint!!.color = mBorderColor

            canvas.drawRect((rect!!.left - BORDER_WIDTH_PX).toFloat(),
                (rect.top - BORDER_WIDTH_PX).toFloat(),
                (rect.right + BORDER_WIDTH_PX).toFloat(),
                (rect.bottom + BORDER_WIDTH_PX).toFloat(),
                mBorderPaint!!)
        }

        if (mHueBackgroundCache == null) {
            mHueBackgroundCache = BitmapCache()
            mHueBackgroundCache!!.bitmap = Bitmap.createBitmap(rect!!.width(), rect.height(), Config.ARGB_8888)
            mHueBackgroundCache!!.canvas = Canvas(mHueBackgroundCache!!.bitmap!!)


            val hueColors = IntArray((rect.height() + 0.5f).toInt())

            // Generate array of all colors, will be drawn as individual lines.
            var h = 360f
            for (i in hueColors.indices) {
                hueColors[i] = Color.HSVToColor(floatArrayOf(h, 1f, 1f))
                h -= 360f / hueColors.size
            }

            // Time to draw the hue color gradient,
            // its drawn as individual lines which
            // will be quite many when the resolution is high
            // and/or the panel is large.
            val linePaint = Paint()
            linePaint.strokeWidth = 0f
            for (i in hueColors.indices) {
                linePaint.color = hueColors[i]
                mHueBackgroundCache!!.canvas!!
                    .drawLine(0f, i.toFloat(), mHueBackgroundCache!!.bitmap!!.width.toFloat(), i.toFloat(), linePaint)
            }
        }

        canvas.drawBitmap(mHueBackgroundCache!!.bitmap!!, null, rect!!, null)

        val p = hueToPoint(mHue)

        val r = RectF()
        r.left = (rect.left - mSliderTrackerOffsetPx).toFloat()
        r.right = (rect.right + mSliderTrackerOffsetPx).toFloat()
        r.top = (p.y - mSliderTrackerSizePx / 2).toFloat()
        r.bottom = (p.y + mSliderTrackerSizePx / 2).toFloat()

        canvas.drawRoundRect(r, 2f, 2f, mHueAlphaTrackerPaint!!)
    }

    private fun drawAlphaPanel(canvas: Canvas) {
        /*
		 * Will be drawn with hw acceleration, very fast.
		 * Also the AlphaPatternDrawable is backed by a bitmap
		 * generated only once if the size does not change.
		 */

        if (!mShowAlphaPanel || mAlphaRect == null || mAlphaPattern == null) return

        val rect = mAlphaRect

        if (BORDER_WIDTH_PX > 0) {
            mBorderPaint!!.color = mBorderColor
            canvas.drawRect((rect!!.left - BORDER_WIDTH_PX).toFloat(),
                (rect.top - BORDER_WIDTH_PX).toFloat(),
                (rect.right + BORDER_WIDTH_PX).toFloat(),
                (rect.bottom + BORDER_WIDTH_PX).toFloat(),
                mBorderPaint!!)
        }

        mAlphaPattern!!.draw(canvas)

        val hsv = floatArrayOf(mHue, mSat, mVal)
        val color = Color.HSVToColor(hsv)
        val acolor = Color.HSVToColor(0, hsv)

        mAlphaShader = LinearGradient(rect!!.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.top.toFloat(),
            color, acolor, TileMode.CLAMP)


        mAlphaPaint!!.shader = mAlphaShader

        canvas.drawRect(rect, mAlphaPaint!!)

        if (mAlphaSliderText != null && mAlphaSliderText != "") {
            canvas.drawText(mAlphaSliderText!!, rect.centerX().toFloat(),
                (rect.centerY() + DrawingUtils.dpToPx(context, 4f)).toFloat(),
                mAlphaTextPaint!!)
        }


        val p = alphaToPoint(mAlpha)

        val r = RectF()
        r.left = (p.x - mSliderTrackerSizePx / 2).toFloat()
        r.right = (p.x + mSliderTrackerSizePx / 2).toFloat()
        r.top = (rect.top - mSliderTrackerOffsetPx).toFloat()
        r.bottom = (rect.bottom + mSliderTrackerOffsetPx).toFloat()

        canvas.drawRoundRect(r, 2f, 2f, mHueAlphaTrackerPaint!!)
    }

    private fun hueToPoint(hue: Float): Point {

        val rect = mHueRect
        val height = rect!!.height().toFloat()

        val p = Point()

        p.y = (height - hue * height / 360f + rect.top).toInt()
        p.x = rect.left

        return p
    }

    private fun satValToPoint(sat: Float, value: Float): Point {

        val rect = mSatValRect
        val height = rect!!.height().toFloat()
        val width = rect.width().toFloat()

        val p = Point()

        p.x = (sat * width + rect.left).toInt()
        p.y = ((1f - value) * height + rect.top).toInt()

        return p
    }

    private fun alphaToPoint(alpha: Int): Point {

        val rect = mAlphaRect
        val width = rect!!.width().toFloat()

        val p = Point()

        p.x = (width - alpha * width / 0xff + rect.left).toInt()
        p.y = rect.top

        return p
    }

    private fun pointToSatVal(x: Float, y: Float): FloatArray {
        var x = x
        var y = y

        val rect = mSatValRect
        val result = FloatArray(2)

        val width = rect!!.width().toFloat()
        val height = rect.height().toFloat()

        x = when {
            x < rect.left  -> 0f
            x > rect.right -> width
            else           -> x - rect.left
        }

        y = when {
            y < rect.top    -> 0f
            y > rect.bottom -> height
            else            -> y - rect.top
        }

        result[0] = 1f / width * x
        result[1] = 1f - 1f / height * y

        return result
    }

    private fun pointToHue(y: Float): Float {
        var y = y

        val rect = mHueRect

        val height = rect!!.height().toFloat()

        y = when {
            y < rect.top    -> 0f
            y > rect.bottom -> height
            else            -> y - rect.top
        }


        val hue = 360f - y * 360f / height
        Log.d("color-picker-view", "Hue: " + hue)

        return hue
    }

    private fun pointToAlpha(x: Int): Int {
        var x = x

        val rect = mAlphaRect
        val width = rect!!.width()

        x = when {
            x < rect.left  -> 0
            x > rect.right -> width
            else           -> x - rect.left
        }

        return 0xff - x * 0xff / width
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var update = false

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                mStartTouchPoint = Point(event.x.toInt(), event.y.toInt())
                update = moveTrackersIfNeeded(event)
            }
            MotionEvent.ACTION_MOVE -> update = moveTrackersIfNeeded(event)
            MotionEvent.ACTION_UP   -> {
                mStartTouchPoint = null
                update = moveTrackersIfNeeded(event)
            }
        }

        if (update) {
            if (mListener != null)
                mListener!!.onColorChanged(Color.HSVToColor(mAlpha, floatArrayOf(mHue, mSat, mVal)))

            invalidate()
            return true
        }

        return super.onTouchEvent(event)
    }

    private fun moveTrackersIfNeeded(event: MotionEvent): Boolean {
        if (mStartTouchPoint == null) return false


        var update = false

        val startX = mStartTouchPoint!!.x
        val startY = mStartTouchPoint!!.y

        if (mHueRect!!.contains(startX, startY)) {
            mHue = pointToHue(event.y)

            update = true
        } else if (mSatValRect!!.contains(startX, startY)) {
            val result = pointToSatVal(event.x, event.y)

            mSat = result[0]
            mVal = result[1]

            update = true
        } else if (mAlphaRect != null && mAlphaRect!!.contains(startX, startY)) {
            mAlpha = pointToAlpha(event.x.toInt())

            update = true
        }

        return update
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var finalWidth = 0
        var finalHeight = 0

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        val widthAllowed = View.MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        val heightAllowed = View.MeasureSpec.getSize(heightMeasureSpec) - paddingBottom - paddingTop


        //Log.d("color-picker-view", "widthMode: " + modeToString(widthMode) + " heightMode: " + modeToString(heightMode) + " widthAllowed: " + widthAllowed + " heightAllowed: " + heightAllowed);

        if (widthMode == View.MeasureSpec.EXACTLY || heightMode == View.MeasureSpec.EXACTLY) {
            //A exact value has been set in either direction, we need to stay within this size.

            if (widthMode == View.MeasureSpec.EXACTLY && heightMode != View.MeasureSpec.EXACTLY) {
                //The with has been specified exactly, we need to adopt the height to fit.
                var h = (widthAllowed - mPanelSpacingPx - mHuePanelWidthPx).toInt()

                if (mShowAlphaPanel) h += mPanelSpacingPx + mAlphaPanelHeightPx

                if (h > heightAllowed) {
                    //We can't fit the view in this container, set the size to whatever was allowed.
                    finalHeight = heightAllowed
                } else finalHeight = h


                finalWidth = widthAllowed

            } else if (heightMode == View.MeasureSpec.EXACTLY && widthMode != View.MeasureSpec.EXACTLY) {
                //The height has been specified exactly, we need to stay within this height and adopt the width.

                var w = (heightAllowed + mPanelSpacingPx + mHuePanelWidthPx).toInt()

                if (mShowAlphaPanel)
                    w -= mPanelSpacingPx + mAlphaPanelHeightPx

                finalWidth = when {
                    w > widthAllowed -> {
                        //we can't fit within this container, set the size to whatever was allowed.
                        widthAllowed
                    }
                    else             -> w
                }

                finalHeight = heightAllowed

            } else {
                //If we get here the dev has set the width and height to exact sizes. For example match_parent or 300dp.
                //This will mean that the sat/val panel will not be square but it doesn't matter. It will work anyway.
                //In all other senarios our goal is to make that panel square.

                //We set the sizes to exactly what we were told.
                finalWidth = widthAllowed
                finalHeight = heightAllowed
            }

        } else {
            //If no exact size has been set we try to make our view as big as possible
            //within the allowed space.

            //Calculate the needed width to layout using max allowed height.
            var widthNeeded = (heightAllowed + mPanelSpacingPx + mHuePanelWidthPx).toInt()

            //Calculate the needed height to layout using max allowed width.
            var heightNeeded = (widthAllowed - mPanelSpacingPx - mHuePanelWidthPx).toInt()

            if (mShowAlphaPanel) {
                widthNeeded -= mPanelSpacingPx + mAlphaPanelHeightPx
                heightNeeded += mPanelSpacingPx + mAlphaPanelHeightPx
            }

            var widthOk = false
            var heightOk = false

            if (widthNeeded <= widthAllowed) widthOk = true


            if (heightNeeded <= heightAllowed) heightOk = true


            //Log.d("color-picker-view", "Size - Allowed w: " + widthAllowed + " h: " + heightAllowed + " Needed w:" + widthNeeded + " h: " + heightNeeded);


            when {
                widthOk && heightOk  -> {
                    finalWidth = widthAllowed
                    finalHeight = heightNeeded
                }
                !heightOk && widthOk -> {
                    finalHeight = heightAllowed
                    finalWidth = widthNeeded
                }
                !widthOk && heightOk -> {
                    finalHeight = heightNeeded
                    finalWidth = widthAllowed
                }
                else                 -> {
                    finalHeight = heightAllowed
                    finalWidth = widthAllowed
                }
            }
            //
            //            if (widthOk && heightOk) {
            //            } else if (!heightOk && widthOk) {
            //                finalHeight = heightAllowed
            //                finalWidth = widthNeeded
            //            } else if (!widthOk && heightOk) {
            //                finalHeight = heightNeeded
            //                finalWidth = widthAllowed
            //            } else {
            //                finalHeight = heightAllowed
            //                finalWidth = widthAllowed
            //            }
        }

        //Log.d("color-picker-view", "Final Size: " + finalWidth + "x" + finalHeight);

        setMeasuredDimension(finalWidth + paddingLeft + paddingRight,
            finalHeight + paddingTop + paddingBottom)
    }

    override fun getPaddingTop(): Int = Math.max(super.getPaddingTop(), mRequiredPadding)

    override fun getPaddingBottom(): Int = Math.max(super.getPaddingBottom(), mRequiredPadding)

    override fun getPaddingLeft(): Int = Math.max(super.getPaddingLeft(), mRequiredPadding)

    override fun getPaddingRight(): Int = Math.max(super.getPaddingRight(), mRequiredPadding)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mDrawingRect = Rect()

        with(mDrawingRect!!) {
            left = paddingLeft
            right = w - paddingRight
            top = paddingTop
            bottom = h - paddingBottom
        }

        //The need to be recreated because they depend on the size of the view.
        mValShader = null
        mSatShader = null
        mAlphaShader = null

        // Clear those bitmap caches since the size may have changed.
        mSatValBackgroundCache = null
        mHueBackgroundCache = null

        //Log.d("color-picker-view", "Size: " + w + "x" + h);

        setUpSatValRect()
        setUpHueRect()
        setUpAlphaRect()
    }

    private fun setUpSatValRect() {
        //Calculate the size for the big color rectangle.
        val dRect = mDrawingRect

        val left = dRect!!.left + BORDER_WIDTH_PX
        val top = dRect.top + BORDER_WIDTH_PX
        var bottom = dRect.bottom - BORDER_WIDTH_PX
        val right = dRect.right - BORDER_WIDTH_PX - mPanelSpacingPx - mHuePanelWidthPx


        if (mShowAlphaPanel) bottom -= mAlphaPanelHeightPx + mPanelSpacingPx


        mSatValRect = Rect(left, top, right, bottom)
    }

    private fun setUpHueRect() {
        //Calculate the size for the hue slider on the left.
        val dRect = mDrawingRect

        val left = dRect!!.right - mHuePanelWidthPx + BORDER_WIDTH_PX
        val top = dRect.top + BORDER_WIDTH_PX
        val bottom = dRect.bottom - BORDER_WIDTH_PX -
            if (mShowAlphaPanel) mPanelSpacingPx + mAlphaPanelHeightPx else 0
        val right = dRect.right - BORDER_WIDTH_PX

        mHueRect = Rect(left, top, right, bottom)
    }

    private fun setUpAlphaRect() {

        if (!mShowAlphaPanel) return

        val dRect = mDrawingRect

        val left = dRect!!.left + BORDER_WIDTH_PX
        val top = dRect.bottom - mAlphaPanelHeightPx + BORDER_WIDTH_PX
        val bottom = dRect.bottom - BORDER_WIDTH_PX
        val right = dRect.right - BORDER_WIDTH_PX

        mAlphaRect = Rect(left, top, right, bottom)

        mAlphaPattern = AlphaPatternDrawable(DrawingUtils.dpToPx(context, 5f))
        mAlphaPattern!!.setBounds(Math.round(mAlphaRect!!.left.toFloat()), Math
            .round(mAlphaRect!!.top.toFloat()), Math.round(mAlphaRect!!.right.toFloat()), Math
            .round(mAlphaRect!!.bottom.toFloat()))
    }

    /**
     * Set a OnColorChangedListener to get notified when the color
     * selected by the user has changed.
     *
     * @param listener
     */
    fun setOnColorChangedListener(listener: OnColorChangedListener) {
        mListener = listener
    }

    /**
     * Set the color this view should show.
     *
     * @param color    The color that should be selected. #argb
     * @param callback If you want to get a callback to
     * your OnColorChangedListener.
     */
    fun setColor(color: Int, callback: Boolean) {

        val alpha = Color.alpha(color)
        val red = Color.red(color)
        val blue = Color.blue(color)
        val green = Color.green(color)

        val hsv = FloatArray(3)

        Color.RGBToHSV(red, green, blue, hsv)

        mAlpha = alpha
        mHue = hsv[0]
        mSat = hsv[1]
        mVal = hsv[2]

        if (callback && mListener != null)
            mListener!!.onColorChanged(Color.HSVToColor(mAlpha, floatArrayOf(mHue, mSat, mVal)))

        invalidate()
    }

    /**
     * Set if the user is allowed to adjust the alpha panel. Default is false.
     * If it is set to false no alpha will be set.
     *
     * @param visible
     */
    fun setAlphaSliderVisible(visible: Boolean) {
        if (mShowAlphaPanel != visible) {
            mShowAlphaPanel = visible

            /*
			 * Force recreation.
			 */
            mValShader = null
            mSatShader = null
            mAlphaShader = null
            mHueBackgroundCache = null
            mSatValBackgroundCache = null

            requestLayout()
        }
    }

    /**
     * Set the text that should be shown in the
     * alpha slider. Set to null to disable text.
     *
     * @param res string resource id.
     */
    fun setAlphaSliderText(res: Int) {
        val text = context.getString(res)
        setAlphaSliderText(text)
    }

    /**
     * Set the text that should be shown in the
     * alpha slider. Set to null to disable text.
     *
     * @param text Text that should be shown.
     */
    fun setAlphaSliderText(text: String) {
        mAlphaSliderText = text
        invalidate()
    }

    /**
     * Get the current value of the text
     * that will be shown in the alpha
     * slider.
     *
     * @return
     */
    fun getAlphaSliderText(): String? = mAlphaSliderText

    private inner class BitmapCache {
        var canvas: Canvas? = null
        var bitmap: Bitmap? = null
        var value: Float = 0.toFloat()
    }

    companion object {

        private val DEFAULT_BORDER_COLOR = -0x919192
        private val DEFAULT_SLIDER_COLOR = -0x424243

        private val HUE_PANEL_WIDTH_DP = 30
        private val ALPHA_PANEL_HEIGHT_DP = 20
        private val PANEL_SPACING_DP = 10
        private val CIRCLE_TRACKER_RADIUS_DP = 5
        private val SLIDER_TRACKER_SIZE_DP = 4
        private val SLIDER_TRACKER_OFFSET_DP = 2

        /**
         * The width in pixels of the border
         * surrounding all color panels.
         */
        private val BORDER_WIDTH_PX = 1
    }

}
