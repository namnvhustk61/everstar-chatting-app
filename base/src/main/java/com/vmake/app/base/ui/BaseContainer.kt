package com.vmake.app.base.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

abstract class BaseContainer<B : ViewBinding>(val parent: Lifecycle) : DefaultLifecycleObserver {

    protected abstract fun makeBinding(inflater: LayoutInflater): B
    protected abstract fun B.startFlow(context: Context)
    protected abstract fun B.onRelease()
    protected var binding: B? = null

    private var implListener: Any? = null
    protected open fun getImplListener() = implListener

    private var data: Any? = null
    fun <D> getData(): D? = data as D?
    fun setData(data: Any?) {
        this.data = data
    }

    fun setupLayout(
        viewParentContainer: ViewGroup?,
        context: Context,
        layoutInflater: LayoutInflater,
        data: Any? = null,
        implListener: Any? = null,
    ): B? {
        parent.addObserver(this)
        if (this.implListener == null) this.implListener = implListener
        if (this.data == null) this.data = data

        if (binding == null) {
            binding = makeBinding(layoutInflater)
            binding?.run {
                startFlow(context)
            }
        }
        viewParentContainer?.addView(binding?.root)
        return binding
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        parent.removeObserver(this)
        binding?.onRelease()
        binding = null
        implListener = null
    }
}