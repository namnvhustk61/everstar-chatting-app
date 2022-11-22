package com.vmake.app.everstarchatting.helper

import com.vmake.app.base.helper.backgroundLaunch
import com.vmake.app.base.helper.ioLaunch
import com.vmake.app.base.helper.runInBackground
import kotlinx.coroutines.delay
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
    private var socketAddress: InetSocketAddress? = null
    private var socket: Socket? = null
    private val TIME_OUT = 3000

    private var listener: ClientCallback? = null

    private var socketOutput: OutputStream? = null
    private var socketInput: BufferedReader? = null

    fun builder(ip: String, port: Int): SocketClientHelper {
        this.ip = ip
        this.port = port
        this.socketAddress = InetSocketAddress(ip, port)
        return this
    }

    fun setCallbackLister(listener: ClientCallback): SocketClientHelper {
        this.listener = listener
        return this
    }

    suspend fun connect() {
        runInBackground {
            if (socket == null) {
                socket = Socket()
            }
            if (port == null) {
                return@runInBackground
            }
            try {
                socket?.connect(socketAddress, TIME_OUT)
                socketOutput = socket?.getOutputStream()
                socketInput = BufferedReader(InputStreamReader(socket?.getInputStream()))
                receiveThread()

                listener?.onConnect(socket)
            } catch (e: IOException) {
                listener?.onConnectError(socket, e.message)
            }
        }
        checkConnect()
    }

    suspend fun disconnect() {
        runInBackground {
            try {
                socket?.close()
                socketInput?.close()
                socketOutput?.close()
                socket = null
                listener?.onDisconnect(socket, null)
            } catch (e: IOException) {
                listener?.onDisconnect(socket, e.message)
            }
        }
    }

    suspend fun send(message: String) {
        runInBackground {
            try {
                socketOutput?.write(message.toByteArray())
            } catch (e: IOException) {
                listener?.onDisconnect(socket, e.message)
            }
        }
    }

    suspend fun reConnect() {
        try {
            if ( socket == null || socket?.isClosed == true) {
                connect()
            }
        } catch (e: Exception) {
        }
    }

    private fun receiveThread() {
        ioLaunch {
            var message: String?
            try {
                while (socketInput?.readLine()
                        .also { message = it } != null
                ) {
                    // each line must end with a \n to be received
                    listener?.onMessage(message)
                }
            } catch (e: IOException) {
                listener?.onDisconnect(socket, e.message)
            }
        }
    }

    private fun checkConnect() {
        backgroundLaunch {
            while (true) {
                delay(timeMillis = 3_000)
                try {
                    if (socket?.isClosed == true) {
                        reConnect()
                        return@backgroundLaunch
                    }
                } catch (e: Exception) {
                }
            }
        }
    }
}

