package com.vmake.app.base.ui

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

abstract class BasePartner(val parent: Lifecycle) : DefaultLifecycleObserver {

    open fun builder(): BasePartner {
        parent.addObserver(this)
        return this
    }

    protected abstract fun onRelease()

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        parent.removeObserver(this)
        onRelease()
    }
}