package com.vmake.app.everstarchatting.ui.tab_friends

import android.view.LayoutInflater
import com.vmake.app.base.ui.BaseFragment
import com.vmake.app.everstarchatting.databinding.FragmentTabFriendsBinding

class TabFriendsFragment : BaseFragment<FragmentTabFriendsBinding>() {
    companion object {
        fun newInstance() = TabFriendsFragment()
    }

    override fun makeBinding(inflater: LayoutInflater) = FragmentTabFriendsBinding.inflate(inflater)

    override fun FragmentTabFriendsBinding.setupView() {

    }

    override fun FragmentTabFriendsBinding.startFlow() {

    }
}