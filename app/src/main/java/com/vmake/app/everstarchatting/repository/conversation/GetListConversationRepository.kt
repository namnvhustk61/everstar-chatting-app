package com.vmake.app.everstarchatting.repository.conversation

import com.vmake.app.base.helper.runInBackground
import com.vmake.app.everstarchatting.repository.conversation.model.Conversation

class GetListConversationRepository {
    companion object {
        fun newInstance() = GetListConversationRepository()
    }

    suspend fun getListFriend() = runInBackground {

        listOf<Conversation>()
    }
}