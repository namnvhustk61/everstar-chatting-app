package com.vmake.app.everstarchatting.ui.tab_chat

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vmake.app.base.data.ViewStatus
import com.vmake.app.base.helper.notNullObserve
import com.vmake.app.base.ui.BaseFragment
import com.vmake.app.everstarchatting.databinding.FragmentTabChatBinding
import com.vmake.app.everstarchatting.gotoScreenChatFragment
import com.vmake.app.everstarchatting.repository.message.model.SocketStatus
import com.vmake.app.everstarchatting.ui.MainViewModel

class TabChatFragment : BaseFragment<FragmentTabChatBinding>() {

    companion object {
        fun newInstance() = TabChatFragment()
    }

    override fun makeBinding(inflater: LayoutInflater) = FragmentTabChatBinding.inflate(inflater)

    private lateinit var adapterListConversation: ListConversationAdapter
    private lateinit var adapterListFriend: ListFriendAdapter

    private val tabChatViewModel: TabChatViewModel by viewModels()
    private val socketViewModel: MainViewModel by activityViewModels()


    override fun FragmentTabChatBinding.setupView() {
        setupRecycleListMessage()
    }

    override fun FragmentTabChatBinding.startFlow() {
        tabChatViewModel.fetchListFriendViewState.notNullObserve(this@TabChatFragment) {
            when (it.status) {
                ViewStatus.Success -> {
                    adapterListFriend.submit(it.data)
                }
            }
        }

        tabChatViewModel.fetchListConversationViewState.notNullObserve(this@TabChatFragment) {
            when (it.status) {
                ViewStatus.Success -> {
                    adapterListConversation.submit(it.data)
                }
            }
        }

        socketViewModel.listerSocketStatus.notNullObserve(this@TabChatFragment) {
            when (it.status) {
                SocketStatus.OnMessage -> {
                    tabChatViewModel.updateMessage(it.data)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        tabChatViewModel.fetchListFriend()
        tabChatViewModel.fetchListConversation()
    }

    private fun FragmentTabChatBinding.setupRecycleListMessage() {
        adapterListFriend = ListFriendAdapter.newInstance(
            action = object : ImplListFriendAdapter {
                override fun onClickItem(position: Int) {
                    tabChatViewModel.getFriendByPos(position)?.let {
                        gotoScreenChatFragment(user = it.getName(), urlAvatar = it.getUrlAvatar())
                    }
                }
            }
        )

        adapterListConversation = ListConversationAdapter.newInstance(
            adapterListFriend,
            implActionMessage = object : ImplListMessageAdapter {
                override fun onClickItem(position: Int) {
                    tabChatViewModel.getConversationByPos(position)?.let {
                        gotoScreenChatFragment(user = it.getName(), urlAvatar = it.getUrlAvatar())
                    }
                    tabChatViewModel.refreshNumbYetSeenConversationByPos(position)
                }
            }
        )
        rcvChatList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rcvChatList.adapter = adapterListConversation
    }
}