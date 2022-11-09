package com.vmake.app.base.helper

import org.greenrobot.eventbus.EventBus

object EventHelper {
    fun registerEventBus(scene: Any?) {
        try {
            EventBus.getDefault().register(scene)
        } catch (e: Exception) {
        }
    }

    fun unRegisterEventBus(scene: Any?) {
        try {
            EventBus.getDefault().unregister(scene)
        } catch (e: Exception) {
        }
    }

    fun removeStickyEvent(event: Any?) {
        try {
            EventBus.getDefault().removeStickyEvent(event)
        } catch (e: Exception) {
        }
    }

    fun postEvent(event: Any?, isSticky: Boolean = false) {
        if (isSticky)
            EventBus.getDefault().postSticky(event)
        else
            EventBus.getDefault().post(event)
    }
}