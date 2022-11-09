package com.vmake.app.base.helper

import android.app.Activity
import com.vmake.app.base.utils.ScreenUtil

object StatusBarHelper {
    fun overlayStatusBar(activity: Activity, isWhiteContent: Boolean = false) {
        ScreenUtil.setOverlayStatusBarWithTransparentColor(
            activity,
            isWhiteContent
        )
    }

    fun overlayNavigatorBar(activity: Activity, isWhiteContent: Boolean = false) {
        ScreenUtil.setOverlayNavigatorBarWithTransparentColor(
            activity,
            isWhiteContent
        )
    }

    fun setClearOverlay(activity: Activity, isWhiteContent: Boolean = false) {
        ScreenUtil.setClearOverlay(
            activity,
            isWhiteContent
        )
    }
}