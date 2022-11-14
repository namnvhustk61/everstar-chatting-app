package com.vmake.app.base.data

import android.content.Context

import com.vmake.app.base.R
import com.vmake.app.base.utils.NetworkUtil
import java.io.IOException
import java.net.SocketException

enum class ViewStatus {
    Success, Error, UnAuthorized, Loading, Disconnect, None, UpdateData
}

open class ViewState<T>(
    open val status: ViewStatus,
    open val data: T? = null,
    open val message: String? = null
) {
    companion object {
        fun <T> empty() = ViewState<T>(ViewStatus.None, null, null)
    }

    fun clone(data: T? = null) = ViewState(status, data, message)
}

fun <T> getErrorState(exception: Exception, context: Context): ViewState<T> {
    val messageUnknown = exception.message ?: context.getString(R.string.message_unknown_error)

    fun getErrorMessage(data: Any?) = data as String? ?: messageUnknown

    fun <T> unknownError() =
        ViewState<T>(ViewStatus.Error, null, getErrorMessage(null))

    fun lostConnectionMessage() = if (NetworkUtil.isConnected(context))
        context.getString(R.string.message_lost_connect_server)
    else context.getString(R.string.message_lost_connect_internet)

    if (exception is IOException || exception is SocketException) {
        return ViewState(ViewStatus.Disconnect, null, lostConnectionMessage())
    }

    if (exception is MyDataException) {
        if (exception.errorResult == null) return unknownError()
        when (exception.errorResult.status) {
            MyData.Status.Error -> ViewState(
                ViewStatus.Error,
                null,
                getErrorMessage(exception.errorResult.data)
            )
            MyData.Status.UnAuthorized -> ViewState(
                ViewStatus.UnAuthorized,
                null,
                getErrorMessage(context.getString(R.string.message_unauthorized))
            )
        }
    }
    return unknownError()
}