package com.vmake.app.everstarchatting.ui

import android.app.Application
import com.vmake.app.base.helper.launch
import com.vmake.app.base.ui.BaseViewModel
import com.vmake.app.everstarchatting.helper.ClientCallback
import com.vmake.app.everstarchatting.helper.SocketClientHelper
import java.net.Socket

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val socketClientHelper = SocketClientHelper.newInstance()

    fun setupSocketClient() {
        launch {
            socketClientHelper.builder("192.168.1.31", 1235)
                .setCallbackLister(object : ClientCallback {
                    override fun onMessage(message: String?) {

                    }

                    override fun onConnect(socket: Socket?) {
                        socketClientHelper.send("mobile Hello World!");
                    }

                    override fun onDisconnect(socket: Socket?, message: String?) {

                    }

                    override fun onConnectError(socket: Socket?, message: String?) {

                    }
                })
                .connect()
        }

    }
}