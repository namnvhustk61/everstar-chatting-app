package com.vmake.app.base

object Config {
    const val DIALOG_ZONE = android.R.id.content

    const val RESIZE_IMAGE_SD = 480 //in pixel
    const val VIDEO_RATIO_3_2 = 3f / 2f // width/height in landscape
    const val VIDEO_RATIO_15_9 = 15f / 9f // width/height in landscape
    const val VIDEO_RATIO_16_9 = 16f / 9f // width/height in landscape
    const val MIN_SCREEN_RATIO = VIDEO_RATIO_16_9 // width/height in landscape
    const val VISIBLE_ALPHA = 1f
    const val INVISIBLE_ALPHA = 0f
    const val TIME_ANIMATE = 180L // in millisecond
    const val NEED_SHOW_VIDEO_SEEKBAR_DURATION_S = 1 // in second
}