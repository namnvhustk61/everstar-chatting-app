package com.vmake.app.everstarchatting.repository.message.model

import com.google.gson.Gson


enum class MessageType(val index: Int) {
    CHAT_MINE(0), CHAT_PARTNER(1);
}

data class Message(
    val userName: String,
    val messageContent: String,
    val roomName: String,
    var viewType: Int,
    var urlAvatar: String? = null,
) {
    companion object {
        fun fromJson(json: String?): Message? {
            return try {
                Gson().fromJson(json, Message::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun toString(): String = Gson().toJson(this)
}
