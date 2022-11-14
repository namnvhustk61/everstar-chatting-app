package com.vmake.app.everstarchatting.repository.friend.model

data class Friend(
    private val urlAvatar: String?,
    private val name: String?,
) {
    fun getName() = name
    fun getUrlAvatar() = urlAvatar
}
