package com.vmake.app.everstarchatting.repository.conversation.model

data class Conversation(
    private val urlAvatar: String?,
    private val name: String?,
    private var content: String? = null,
    private val time: String? = null,
    private var numbYetSeen: Int = 0,
) {
    fun getUrlAvatar() = urlAvatar
    fun getName() = name
    fun getContent() = content
    fun setContent(content: String) {
        this.content = content
    }

    fun updateNumbYetSeenIncrease() {
        this.numbYetSeen++
    }

    fun refreshNumbYetSeen() {
        this.numbYetSeen = 0
    }

    fun getTime() = time
    fun isSeen() = numbYetSeen == 0
    fun getNumbYetSeen() = numbYetSeen

}


