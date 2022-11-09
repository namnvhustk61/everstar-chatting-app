package com.vmake.app.base.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

import androidx.window.layout.WindowMetrics

import androidx.window.layout.WindowMetricsCalculator

object ScreenUtil {
    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    fun getNavigationBarHeight(context: Context): Int {
        val resourceId =
            context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    fun getRealScreenWidth(activity: Activity): Int {
        val windowMetrics: WindowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
        return windowMetrics.bounds.width()
    }

    fun getRealScreenHeight(activity: Activity): Int {
        val windowMetrics: WindowMetrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
        return windowMetrics.bounds.height()
    }

    fun getScreenWidth(context: Context): Int {
        val windowManager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        val windowManager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }

    fun setOverlayStatusBarWithTransparentColor(
        activity: Activity,
        isWhiteContent: Boolean = true
    ) {
        activity.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            // TODO color
            statusBarColor = Color.TRANSPARENT
            // TODO fullscreen
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            // TODO color text
            val lFlags: Int = decorView.systemUiVisibility
            decorView.systemUiVisibility =
                if (isWhiteContent) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun setOverlayNavigatorBarWithTransparentColor(
        activity: Activity,
        isWhiteContent: Boolean = true
    ) {
        activity.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            // TODO color
            navigationBarColor = Color.TRANSPARENT

            // TODO fullscreen
            decorView.systemUiVisibility = decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            // TODO color text
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val lFlags: Int = decorView.systemUiVisibility
                decorView.systemUiVisibility =
                    if (isWhiteContent) lFlags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    fun setClearOverlay(
        activity: Activity,
        isWhiteContent: Boolean = true
    ) {
        activity.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            // TODO default
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            var lFlags: Int = decorView.systemUiVisibility

            // TODO color text
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                decorView.systemUiVisibility =
                    lFlags or if (isWhiteContent) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                decorView.systemUiVisibility =
                    lFlags or if (isWhiteContent) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            }
        }
    }


}