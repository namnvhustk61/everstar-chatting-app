package com.vmake.app.base.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<T>.notNullObserveOnce(
    owner: LifecycleOwner,
    block: (value: T) -> Unit
) {
    if (!this.hasActiveObservers())
        this.observe(owner, androidx.lifecycle.Observer { it?.let { block(it) } })
}

fun <T> LiveData<T>.notNullObserve(owner: LifecycleOwner, block: (value: T) -> Unit) {
    this.observe(owner, androidx.lifecycle.Observer { it?.let { block(it) } })
}