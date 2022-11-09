package com.vmake.app.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.vmake.app.base.helper.EventHelper

abstract class BaseApplication : Application(), Application.ActivityLifecycleCallbacks {

    companion object {
        var isAppInBackground: Boolean = true
    }

    override fun onCreate() {
        super.onCreate()
        EventHelper.registerEventBus(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        EventHelper.unRegisterEventBus(this)
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {

    }

    override fun onActivityStarted(p0: Activity) {
        isAppInBackground = false
    }

    override fun onActivityResumed(p0: Activity) {

    }

    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStopped(p0: Activity) {
        isAppInBackground = true
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(p0: Activity) {

    }
}