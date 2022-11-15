package com.vmake.app.everstarchatting.ui.screen_chat

import android.view.LayoutInflater
import com.vmake.app.base.ui.BaseFragment
import com.vmake.app.everstarchatting.databinding.FragmentScreenChatBinding
import com.vmake.app.everstarchatting.finish

class ScreenChatFragment : BaseFragment<FragmentScreenChatBinding>() {

    override fun makeBinding(inflater: LayoutInflater) = FragmentScreenChatBinding.inflate(inflater)


    override fun FragmentScreenChatBinding.setupView() {
    }

    override fun FragmentScreenChatBinding.startFlow() {

    }

    override fun onBackPress() {
        finish()
    }
}