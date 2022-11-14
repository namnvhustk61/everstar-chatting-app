package com.vmake.app.base.helper

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import com.vmake.app.base.Config
import com.vmake.app.base.utils.NetworkUtil


fun View.show() {
    if (!isVisible)
        visibility = View.VISIBLE
}

fun View.hide(isGone: Boolean = false) {
    if (isVisible)
        visibility = if (isGone) View.GONE else View.INVISIBLE
}

fun View?.setOnClickInternetListener(onClick: (View?) -> Unit) {
    this?.setOnClickListener(OnInternetClickListener(onClick))
}

class OnInternetClickListener(
    private val onClickAction: (View?) -> Unit
) : View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.context?.let { NetworkUtil.isConnected(it) } == true)
            onClickAction(v)
    }
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun View.setBackgroundWithColor(color: Int) {
    setBackgroundColor(ContextCompat.getColor(context, color))
}

fun View.postDelayedSkipException(delay: Long = 0, task: () -> Unit): Runnable {
    return postDelayed(delay) {
        try {
            task()
        } catch (e: Exception) {
        }
    }
}

fun View.showWithFadeAnimation(duration: Long = Config.TIME_ANIMATE): View {
    alpha = Config.INVISIBLE_ALPHA
    if (!isVisible) show()
    post {
        animate()
            .alpha(Config.VISIBLE_ALPHA)
            .setDuration(duration)
            .start()
    }
    return this
}

fun View.hideWithFadeAnimation(
    isGone: Boolean = false,
    duration: Long = Config.TIME_ANIMATE,
    onEnd: Runnable? = null
): View {
    if (!isVisible) return this
    post {
        animate()
            .alpha(Config.INVISIBLE_ALPHA)
            .setDuration(duration)
            .withEndAction {
                hide(isGone)
                onEnd?.run()
            }
            .start()
    }
    return this
}

fun View.dimWithFade() {
    post {
        animate()
            .alpha(Config.VISIBLE_ALPHA / 3f)
            .setDuration(Config.TIME_ANIMATE)
            .start()
    }
}

fun View.unDimWithFade() {
    post {
        animate()
            .alpha(Config.VISIBLE_ALPHA)
            .setDuration(Config.TIME_ANIMATE)
            .start()
    }
}
