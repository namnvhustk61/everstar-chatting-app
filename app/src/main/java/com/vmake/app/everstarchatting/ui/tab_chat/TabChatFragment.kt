package com.vmake.app.everstarchatting.ui.tab_chat

import android.content.Intent
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vmake.app.base.data.ViewStatus
import com.vmake.app.base.helper.notNullObserve
import com.vmake.app.base.ui.BaseFragment
import com.vmake.app.everstarchatting.databinding.FragmentTabChatBinding
import com.vmake.app.everstarchatting.gotoScreenChatFragment

class TabChatFragment : BaseFragment<FragmentTabChatBinding>() {

    companion object {
        fun newInstance() = TabChatFragment()
    }

    override fun makeBinding(inflater: LayoutInflater) = FragmentTabChatBinding.inflate(inflater)

    private lateinit var adapterListMessage: ListMessageAdapter
    private lateinit var adapterListFriend: ListFriendAdapter

    private val tabChatViewModel: TabChatViewModel by viewModels()

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

        tabChatViewModel.fetchListMessageViewState.notNullObserve(this@TabChatFragment) {
            when (it.status) {
                ViewStatus.Success -> {
                    adapterListMessage.submit(it.data)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        tabChatViewModel.fetchListFriend()
        tabChatViewModel.fetchListMessage()
    }

    private fun FragmentTabChatBinding.setupRecycleListMessage() {
        adapterListFriend = ListFriendAdapter.newInstance(
            action = object : ImplListFriendAdapter {
                override fun onClickItem(position: Int) {
                    gotoScreenChatFragment()
                }
            }
        )

        adapterListMessage = ListMessageAdapter.newInstance(
            adapterListFriend,
            implActionMessage = object : ImplListMessageAdapter {
                override fun onClickItem(position: Int) {
                    gotoScreenChatFragment()
                }
            }
        )
        rcvChatList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rcvChatList.adapter = adapterListMessage
    }
}