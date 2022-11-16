package com.vmake.app.everstarchatting.repository.message.model

enum class SocketStatus {
    OnMessage, OnConnect, OnDisconnect, OnConnectError
}
data class SocketModel(val status: SocketStatus, val data: Message? = null)
