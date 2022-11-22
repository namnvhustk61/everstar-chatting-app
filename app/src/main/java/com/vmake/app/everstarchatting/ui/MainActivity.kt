package com.vmake.app.everstarchatting.ui

import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
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
        followConnectInternet()
        mainViewModel.followSpeedInternet()
    }

    override fun ActivityMainBinding.startFlow() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.disConnect()
    }

    private fun followConnectInternet() {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    mainViewModel.reConnect()

                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    mainViewModel.disConnect()
                }
            })
    }


}