package com.vmake.app.everstarchatting.helper

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket

interface ClientCallback {
    fun onMessage(message: String?)
    fun onConnect(socket: Socket?)
    fun onDisconnect(socket: Socket?, message: String?)
    fun onConnectError(socket: Socket?, message: String?)
}

class SocketClientHelper private constructor() {
    companion object {
        fun newInstance() = SocketClientHelper()
    }

    private var ip: String? = null
    private var port: Int? = null
    private var socket: Socket? = null
    private val TIME_OUT = 60000

    private var listener: ClientCallback? = null

    private var socketOutput: OutputStream? = null
    private var socketInput: BufferedReader? = null

    fun builder(ip: String, port: Int): SocketClientHelper {
        this.ip = ip
        this.port = port
        return this
    }

    fun setCallbackLister(listener: ClientCallback): SocketClientHelper {
        this.listener = listener
        return this
    }

    suspend fun connect() {
        if (socket == null) {
            socket = Socket()
        }
        if (port == null) {
            return
        }
        val socketAddress = InetSocketAddress(ip, port!!)
        try {
            socket?.connect(socketAddress, TIME_OUT)
            socketOutput = socket?.getOutputStream()
            socketInput = BufferedReader(InputStreamReader(socket?.getInputStream()))

            if (listener != null) listener?.onConnect(socket)
            receiveThread()
        } catch (e: IOException) {
            if (listener != null) listener?.onConnectError(socket, e.message)
        }
    }

    suspend fun disconnect() {
        try {
            socket!!.close()
        } catch (e: IOException) {
            if (listener != null) listener!!.onDisconnect(socket, e.message)
        }
    }

    fun send(message: String) {
        try {
            socketOutput!!.write(message.toByteArray())
        } catch (e: IOException) {
            if (listener != null) listener!!.onDisconnect(socket, e.message)
        }
    }


    private suspend fun receiveThread() {
        var message: String?
        try {
            while (socketInput!!.readLine()
                    .also { message = it } != null
            ) {
                // each line must end with a \n to be received
                if (listener != null) listener!!.onMessage(message)
            }
        } catch (e: IOException) {
            if (listener != null) listener!!.onDisconnect(socket, e.message)
        }
    }
}

