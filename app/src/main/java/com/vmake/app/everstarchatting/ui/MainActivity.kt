package com.vmake.app.everstarchatting.ui

import android.graphics.Color
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.vmake.app.base.ui.BaseActivity
import com.vmake.app.everstarchatting.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun setTimeDelayBeforeShowMillis() = 3000L
    override fun makeBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)

    private val mainViewModel: MainViewModel by viewModels()
    override fun ActivityMainBinding.setupView() {
        window.statusBarColor = Color.WHITE
        mainViewModel.setupSocketClient()
    }

    override fun ActivityMainBinding.startFlow() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.disConnect()
    }
}