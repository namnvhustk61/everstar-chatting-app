package com.vmake.app.everstarchatting.repository.conversation.model

data class Conversation(
    private val urlAvatar: String?,
    private val name: String?,
    private val content: String?,
    private val time: String? = null,
    private val numbYetSeen: Int = 0,
) {
    fun getUrlAvatar() = urlAvatar
    fun getName() = name
    fun getContent() = content
    fun getTime() = time
    fun isSeen() = numbYetSeen == 0
    fun getNumbYetSeen() = numbYetSeen
}


