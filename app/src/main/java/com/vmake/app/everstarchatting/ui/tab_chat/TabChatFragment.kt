package com.vmake.app.everstarchatting.ui.tab_chat

import android.view.LayoutInflater
import com.vmake.app.base.ui.BaseFragment
import com.vmake.app.everstarchatting.databinding.FragmentTabChatBinding

class TabChatFragment : BaseFragment<FragmentTabChatBinding>() {

    companion object {
        fun newInstance() = TabChatFragment()
    }

    override fun makeBinding(inflater: LayoutInflater) = FragmentTabChatBinding.inflate(inflater)

    override fun FragmentTabChatBinding.setupView() {

    }

    override fun FragmentTabChatBinding.startFlow() {

    }
}