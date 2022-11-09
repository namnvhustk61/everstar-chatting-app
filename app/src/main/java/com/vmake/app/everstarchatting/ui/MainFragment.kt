package com.vmake.app.everstarchatting.ui

import android.view.LayoutInflater
import com.vmake.app.base.ui.BaseFragment
import com.vmake.app.everstarchatting.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override var isModeChangeStatusBar: Boolean = true
    override var isSetNoStatusBar: Boolean = true

    override fun makeBinding(inflater: LayoutInflater) = FragmentMainBinding.inflate(inflater)


    override fun FragmentMainBinding.setupView() {

    }

    override fun FragmentMainBinding.startFlow() {

    }

}