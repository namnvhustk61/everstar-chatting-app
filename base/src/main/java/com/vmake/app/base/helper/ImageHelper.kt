package com.vmake.app.base.helper

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.transition.ViewPropertyTransition

import com.vmake.app.base.R
import com.vmake.app.base.utils.ViewUtil
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AnimateObject {
    companion object {
        const val INVISIBLE_ALPHA = 0f
        const val VISIBLE_ALPHA = 1f

        val FADE_IN = ViewPropertyTransition.Animator() {
            it.alpha = INVISIBLE_ALPHA
            val fadeAnim =
                ObjectAnimator.ofFloat(it, "alpha", INVISIBLE_ALPHA, VISIBLE_ALPHA)
            fadeAnim.duration = 100
            fadeAnim.start()
        }
    }
}

fun ImageView.setTint(color: Int) {
    setColorFilter(ContextCompat.getColor(context, color))
}

fun ImageView.loadGif(source: Any?) {
    source?.let {
        Glide.with(this).asGif().load(it).into(this)
    }
}

fun ImageView.loadImage(
    source: Any?,
    isCircle: Boolean = false,
    isCenterCrop: Boolean = true,
    roundDp: Float? = null,
    empty: Int? = null,
    isAnimate: Boolean = true
) {
    val emptySource = empty
        ?: if (roundDp != null) R.drawable.empty_image_round else if (isCircle) R.drawable.empty_image_circle else R.drawable.empty_image
    val loadSource = source ?: emptySource
    loadSource.let {
        var request = Glide.with(this)
            .load(it)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        request = if (isAnimate) {
            request.transition(GenericTransitionOptions.with(AnimateObject.FADE_IN))
        } else
            request.dontAnimate()

        request = if (isCenterCrop) {
            request.centerCrop()
        } else request.fitCenter()

        if (isCircle) {
            request = request.transform(MultiTransformation(CenterCrop(), CircleCrop()))
        }
        if (roundDp != null) {
            request = request.transform(
                if (isCenterCrop)
                    MultiTransformation(
                        CenterCrop(), RoundedCorners(ViewUtil.dpToPx(roundDp))
                    )
                else RoundedCorners(
                    ViewUtil.dpToPx(roundDp)
                )
            )
        }
        request = request.placeholder(emptySource)
        request = request.error(emptySource)
        request.into(this)
    }
}

const val RESIZE_IMAGE_SD = 480 //in pixel

suspend fun Context.resizeImage(path: String): String {
    val bitmap: Bitmap = BitmapFactory.decodeFile(path)
    var ratio = 1.0f
    val size = if (bitmap.width > bitmap.height) bitmap.height else bitmap.width
    if (size > RESIZE_IMAGE_SD) ratio = RESIZE_IMAGE_SD.toFloat() / size
    val resized = Bitmap.createScaledBitmap(
        bitmap,
        (bitmap.width * ratio).toInt(),
        (bitmap.height * ratio).toInt(),
        true
    ).rotateImage(path)
    val saveFile = File(
        cacheDir,
        "resize_${System.currentTimeMillis()}.jpg"
    )
    try {
        FileOutputStream(
            saveFile
        ).use { out ->
            resized.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        throw IOException()
    }
    return saveFile.path
}

private fun Bitmap.rotateImage(filePath: String): Bitmap {
    var exifInterface: ExifInterface? = null
    try {
        exifInterface = ExifInterface(filePath)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    val orientation: Int? = exifInterface?.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_UNDEFINED
    )
    val matrix = Matrix()
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
        else -> {
        }
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}