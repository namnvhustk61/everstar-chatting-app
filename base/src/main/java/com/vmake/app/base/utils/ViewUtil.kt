package com.vmake.app.base.utils

import android.content.res.Resources
import android.view.View
import kotlin.math.roundToInt

object ViewUtil {
    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (dp * density).roundToInt()
    }

    fun getLocationOnScreen(view: View): Triple<Int, Int, Int> {
        val originalPos = IntArray(2)
        view.getLocationOnScreen(originalPos)
        val x = originalPos[0]
        val y = originalPos[1]
        return Triple(x, y, view.height)
    }
}