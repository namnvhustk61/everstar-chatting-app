package com.vmake.app.base.data


import com.google.gson.Gson
import retrofit2.Response

data class MyData<T>(val status: Status, val data: T?) {
    enum class Status {
        Error, UnAuthorized
    }
}

class MyDataException(val errorResult: MyData<*>?) : Exception()

const val RESPONSE_CODE_UNAUTHORIZED = 401

fun <T> Response<T>?.response(): T {
    if (this == null) throw MyDataException(MyData(MyData.Status.Error, null))
    if (this.isSuccessful && this.body() != null) {
        return this.body()!!
    } else {
        val errorData: String? = this.errorBody()?.string()
        val status =
            if (this.code() == RESPONSE_CODE_UNAUTHORIZED)
                MyData.Status.UnAuthorized else MyData.Status.Error
        val errorResult = MyData(status, errorData)
        throw MyDataException(errorResult)
    }
}

fun <M> String?.toModel(type: Class<M>): M? {
    if (this == null) return null
    return try {
        Gson().fromJson(this, type)
    } catch (e: Exception) {
        null
    }
}