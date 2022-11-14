package com.vmake.app.everstarchatting.ui.tab_chat

import android.app.Application


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vmake.app.base.data.ViewState
import com.vmake.app.base.data.ViewStatus
import com.vmake.app.base.helper.launch
import com.vmake.app.base.ui.BaseViewModel
import com.vmake.app.everstarchatting.repository.friend.GetListFriendRepository
import com.vmake.app.everstarchatting.repository.friend.model.Friend
import com.vmake.app.everstarchatting.repository.message.GetListMessageRepository
import com.vmake.app.everstarchatting.repository.message.model.Message

class TabChatViewModel(application: Application) : BaseViewModel(application) {

    private val getListFriendRepository by lazy { GetListFriendRepository.newInstance() }
    private val getListMessageRepository by lazy { GetListMessageRepository.newInstance() }

    private val _fetchListFriendViewState = MutableLiveData<ViewState<List<Friend>>>()
    val fetchListFriendViewState: LiveData<ViewState<List<Friend>>> = _fetchListFriendViewState

    private val _fetchListMessageViewState = MutableLiveData<ViewState<List<Message>>>()
    val fetchListMessageViewState: LiveData<ViewState<List<Message>>> = _fetchListMessageViewState

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

    fun fetchListMessage() {
        launch {
            try {
                _fetchListMessageViewState.value =
                    ViewState(ViewStatus.Success, getListMessageRepository.getListFriend())
            } catch (e: Exception) {
                _fetchListMessageViewState.value = ViewState(ViewStatus.Error)
            }
        }
    }


}