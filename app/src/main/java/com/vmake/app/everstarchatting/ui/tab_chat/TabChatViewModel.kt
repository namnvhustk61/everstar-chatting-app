package com.vmake.app.everstarchatting.ui.tab_chat


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vmake.app.base.data.ViewState
import com.vmake.app.base.data.ViewStatus
import com.vmake.app.base.helper.launch
import com.vmake.app.base.ui.BaseViewModel
import com.vmake.app.everstarchatting.repository.conversation.GetListConversationRepository
import com.vmake.app.everstarchatting.repository.conversation.model.Conversation
import com.vmake.app.everstarchatting.repository.friend.GetListFriendRepository
import com.vmake.app.everstarchatting.repository.friend.model.Friend

class TabChatViewModel(application: Application) : BaseViewModel(application) {

    private val getListFriendRepository by lazy { GetListFriendRepository.newInstance() }
    private val getListConversationRepository by lazy { GetListConversationRepository.newInstance() }

    private val _fetchListFriendViewState = MutableLiveData<ViewState<List<Friend>>>()
    val fetchListFriendViewState: LiveData<ViewState<List<Friend>>> = _fetchListFriendViewState

    private val _fetchListConversationViewState = MutableLiveData<ViewState<List<Conversation>>>()
    val fetchListConversationViewState: LiveData<ViewState<List<Conversation>>> =
        _fetchListConversationViewState

    fun fetchListFriend() {
        launch {
            try {
                _fetchListFriendViewState.value =
                    ViewState(ViewStatus.Success, getListFriendRepository.getListFriend())
            } catch (e: Exception) {
                _fetchListFriendViewState.value = ViewState(ViewStatus.Error)
            }
        }
    }

    fun fetchListConversation() {
        launch {
            try {
                _fetchListConversationViewState.value =
                    ViewState(ViewStatus.Success, getListConversationRepository.getListFriend())
            } catch (e: Exception) {
                _fetchListConversationViewState.value = ViewState(ViewStatus.Error)
            }
        }
    }

    private fun getDataListFriend(): List<Friend> = _fetchListFriendViewState.value?.data ?: listOf()
    private fun getDataListConversation(): List<Conversation> =
        _fetchListConversationViewState.value?.data ?: listOf()

    fun getFriendByPos(idx: Int): Friend? {
        if (idx < 0 || idx >= getDataListFriend().size) {
            return null
        }
        return getDataListFriend()[idx]
    }

    fun getConversationByPos(idx: Int): Conversation? {
        if (idx < 0 || idx >= getDataListConversation().size) {
            return null
        }
        return getDataListConversation()[idx]
    }
}