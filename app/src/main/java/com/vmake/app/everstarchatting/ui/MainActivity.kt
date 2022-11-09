package com.vmake.app.everstarchatting.ui

import com.vmake.app.base.ui.BaseActivity
import com.vmake.app.everstarchatting.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun setTimeDelayBeforeShowMillis() = 3000L
    override fun makeBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun ActivityMainBinding.setupView() {
    }

    override fun ActivityMainBinding.startFlow() {
    }
}