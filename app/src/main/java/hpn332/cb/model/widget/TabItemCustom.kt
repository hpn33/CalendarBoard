package hpn332.cb.model.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.design.R
import android.support.v7.widget.TintTypedArray
import android.util.AttributeSet
import android.view.View

/**
 * Created by hpn332 on 28/01/2018.
 */
class TabItemCustom(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    constructor(context: Context?) : this(context!!)

    var mText: CharSequence? = null
    var mIcon: Drawable? = null
    var mCustomLayout: Int? = null

    init {
        val a = TintTypedArray.obtainStyledAttributes(context, attrs,
            R.styleable.TabItem)
        mText = a.getText(R.styleable.TabItem_android_text)
        mIcon = a.getDrawable(R.styleable.TabItem_android_icon)
        mCustomLayout = a.getResourceId(R.styleable.TabItem_android_layout, 0)
        a.recycle()
    }


}