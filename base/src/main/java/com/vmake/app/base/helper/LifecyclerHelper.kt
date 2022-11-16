package com.vmake.app.base.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

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

inline fun <T> LiveData<T>.filter(crossinline filter: (T?) -> Boolean): LiveData<T> {
    return MediatorLiveData<T>().apply {
        addSource(this@filter) {
            if (filter(it)) {
                this.value = it
            }
        }
    }
}