package com.vmake.app.everstarchatting.ui.tab_setting

import android.view.LayoutInflater
import com.vmake.app.base.ui.BaseFragment
import com.vmake.app.everstarchatting.databinding.FragmentTabSettingsBinding

class TabSettingsFragment : BaseFragment<FragmentTabSettingsBinding>() {
    companion object {
        fun newInstance() = TabSettingsFragment()
    }

    override fun makeBinding(inflater: LayoutInflater) =
        FragmentTabSettingsBinding.inflate(inflater)


    override fun FragmentTabSettingsBinding.setupView() {

    }

    override fun FragmentTabSettingsBinding.startFlow() {

    }
}