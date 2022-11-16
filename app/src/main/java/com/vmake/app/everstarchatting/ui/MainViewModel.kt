package com.vmake.app.everstarchatting.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.vmake.app.base.data.SingleLiveEvent
import com.vmake.app.base.helper.filter
import com.vmake.app.base.helper.launch
import com.vmake.app.base.ui.BaseViewModel
import com.vmake.app.everstarchatting.helper.ClientCallback
import com.vmake.app.everstarchatting.helper.SocketClientHelper
import com.vmake.app.everstarchatting.repository.message.model.Message
import com.vmake.app.everstarchatting.repository.message.model.SocketModel
import com.vmake.app.everstarchatting.repository.message.model.SocketStatus
import java.net.Socket

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val socketClientHelper = SocketClientHelper.newInstance()

    private val _listerSocketStatus = SingleLiveEvent<SocketModel>()
    val listerSocketStatus: LiveData<SocketModel> = _listerSocketStatus

    fun getListerSocketOnMessageForConversation(user: String): LiveData<SocketModel>{
        return listerSocketStatus.filter { socketModel ->
            socketModel?.data?.userName == user
        }
    }

    fun setupSocketClient() {
        launch {
            socketClientHelper.builder("192.168.1.31", 1235)
                .setCallbackLister(object : ClientCallback {
                    override fun onMessage(message: String?) {
//                        {"messageContent":"hi","roomName":"pc", "userName":"pc"}
                        _listerSocketStatus.postValue(
                            SocketModel(
                                SocketStatus.OnMessage,
                                Message.fromJson(message)
                            )
                        )
                    }

                    override fun onConnect(socket: Socket?) {
                        _listerSocketStatus.postValue(SocketModel(SocketStatus.OnConnect))
                    }

                    override fun onDisconnect(socket: Socket?, message: String?) {
                        _listerSocketStatus.postValue(SocketModel(SocketStatus.OnDisconnect))

                    }

                    override fun onConnectError(socket: Socket?, message: String?) {
                        _listerSocketStatus.postValue(SocketModel(SocketStatus.OnConnectError))
                    }
                })
                .connect()
        }
    }

    fun sendSocket(str: String) {
        launch {
            socketClientHelper.send(str)
        }
    }

    fun disConnect() {
        launch {
            socketClientHelper.disconnect()
        }
    }
}