package com.vmake.app.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.vmake.app.base.helper.EventHelper
import com.vmake.app.base.helper.StatusBarHelper
import com.vmake.app.base.helper.hideKeyboard
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    abstract fun makeBinding(inflater: LayoutInflater): B
    abstract fun B.setupView()
    abstract fun B.startFlow()

    protected open var colorWhiteTextOnStatusBar: Boolean = false
    protected open var isSetNoStatusBar: Boolean = false

    protected open var isModeChangeStatusBar: Boolean = false

    protected open fun isScreen(): Boolean = true

    protected open fun onBackPress() {}
    protected open fun onReceivedEvent(event: Any?) {}

    protected var binding: B? = null

    private var needSetupView = true

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
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            backPressCallback
        )
    }

    override fun onResume() {
        super.onResume()
        if (isSetNoStatusBar) {
            setNoStatusBar()
        }

        binding?.run {
            startFlow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventHelper.unRegisterEventBus(this)
        binding = null
        needSetupView = true
        clearOverlayBar()
    }

    private val backPressCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPress()
        }
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

    protected open fun setNoStatusBar() {
        if(!isModeChangeStatusBar) return
        StatusBarHelper.overlayStatusBar(requireActivity(), colorWhiteTextOnStatusBar)
        StatusBarHelper.overlayNavigatorBar(requireActivity(), colorWhiteTextOnStatusBar)
    }

    protected open fun clearOverlayBar() {
        if(!isModeChangeStatusBar) return
        StatusBarHelper.setClearOverlay(requireActivity(), !colorWhiteTextOnStatusBar)
    }
}