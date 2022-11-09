package com.vmake.app.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.vmake.app.base.R
import com.vmake.app.base.helper.EventHelper
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.vmake.app.base.helper.hideKeyboard

abstract class BaseDialogFragment<B : ViewBinding> : DialogFragment() {

    abstract fun makeBinding(inflater: LayoutInflater): B
    abstract fun B.setupView()
    abstract fun B.startFlow()

    protected open fun onReceivedEvent(event: Any?) {}

    private var binding: B? = null

    private var needSetupView = true

    override fun getTheme() = R.style.MyDialogFullScreen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventHelper.registerEventBus(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            needSetupView = true
            binding = makeBinding(inflater)
        } else {
            clearViewFromParent(binding?.root)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.root?.setOnClickListener { hideKeyboard(it) }
        if (needSetupView) {
            needSetupView = false
            binding?.run {
                setupView()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.run {
            startFlow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventHelper.unRegisterEventBus(this)
        binding = null
        needSetupView = true
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onMessageEvent(message: Any?) {
        onReceivedEvent(message)
    }

    private fun clearViewFromParent(view: View?) {
        view?.also {
            val parentView = it.parent as ViewGroup?
            parentView?.endViewTransition(it)
            parentView?.removeView(it)
        }
    }

    fun hideKeyboard() = hideKeyboard(binding?.root)
}