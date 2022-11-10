package com.vmake.app.base.view_custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.vmake.app.base.R

class MyEditText : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    companion object {
        final val ARR_KEY_STYLEABLE = intArrayOf(
            android.R.attr.textStyle
        )
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        attrs?.let { obtain(context, it) }
    }

    private fun obtain(context: Context, attrs: AttributeSet) {

        val ta: TypedArray = context.obtainStyledAttributes(attrs, ARR_KEY_STYLEABLE)

        val fontStyle = ta.getInt(ARR_KEY_STYLEABLE.indexOf(android.R.attr.textStyle), 0)
        setCustomFont(fontStyle)
        ta.recycle()
    }

    private fun setCustomFont(fontStyle: Int) {
        when (fontStyle) {
            Typeface.BOLD -> typeface = ResourcesCompat.getFont(context, R.font.seguisb)
            Typeface.ITALIC -> typeface = ResourcesCompat.getFont(context, R.font.segoeuil)
            Typeface.NORMAL -> typeface = ResourcesCompat.getFont(context, R.font.segoeui)
        }
    }
}