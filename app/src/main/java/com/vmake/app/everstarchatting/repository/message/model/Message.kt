package com.vmake.app.everstarchatting.repository.message.model

import com.google.gson.Gson


enum class MessageType(val index: Int) {
    CHAT_MINE(10), CHAT_PARTNER(20);
}

data class Message(
    val userName: String,
    val messageContent: String,
    val roomName: String,
    var viewType: Int,
    var urlAvatar: String? = null,
    var contentType: Int = 0 // 0: Text, 1: Video horizontal, 2: Video vertical 3: Image
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
