package com.vmake.app.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.vmake.app.base.helper.EventHelper
import com.vmake.app.base.helper.mainLaunch
import kotlinx.coroutines.delay
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    public var binding: B? = null
    abstract fun makeBinding(inflater: LayoutInflater): B

    abstract fun B.setupView()
    abstract fun B.startFlow()

    protected open fun setTimeDelayBeforeShowMillis(): Long = 0
    protected open fun onReceivedEvent(event: Any?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventHelper.registerEventBus(this)
        mainLaunch {
            if (binding == null) {
                binding = makeBinding(layoutInflater)
                delay(setTimeDelayBeforeShowMillis())
                binding?.setupView()
                setContentView(binding?.root)
                binding?.startFlow()
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onMessageEvent(message: Any?) {
        onReceivedEvent(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventHelper.unRegisterEventBus(this)
        binding = null
    }

}