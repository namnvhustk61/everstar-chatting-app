package com.vmake.app.everstarchatting.ui

import android.view.LayoutInflater
import com.vmake.app.base.ui.BaseActivity
import com.vmake.app.everstarchatting.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun setTimeDelayBeforeShowMillis() = 3000L
    override fun makeBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)

    override fun ActivityMainBinding.setupView() {
    }

    override fun ActivityMainBinding.startFlow() {
    }
}