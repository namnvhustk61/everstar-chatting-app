package com.vmake.app.everstarchatting.repository.message


enum class MessageType(val index : Int){
    CHAT_MINE(0),CHAT_PARTNER(1);
}
data class Message (val userName : String, val messageContent : String, val roomName: String,var viewType : Int)
