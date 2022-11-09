package com.vmake.app.base.helper

import android.annotation.SuppressLint
import androidx.annotation.*
import androidx.annotation.IntRange

object AnnotationHelper {

    @Nullable
    @NonNull
    private fun isNull() {
    }

    @SuppressLint("SupportAnnotationUsage")
    @AnimatorRes
    @AnimRes
    @AnyRes
    @ArrayRes
    @AttrRes
    @BoolRes
    @ColorRes
    @DimenRes
    @DrawableRes
    @FractionRes
    @IdRes
    @IntegerRes
    @InterpolatorRes
    @LayoutRes
    @MenuRes
    @PluralsRes
    @RawRes
    @StringRes
    @StyleableRes
    @StyleRes
    @XmlRes
    private fun resource() {
    }

    @SuppressLint("SupportAnnotationUsage")
    @ColorInt
    @FloatRange
    @IntRange
    @Size
    private fun number(){}


    @UiThread @MainThread @WorkerThread @BinderThread
    private fun thread(){}

    @CallSuper
    @VisibleForTesting
    @CheckResult
    private fun bit(): Boolean{return true}
}