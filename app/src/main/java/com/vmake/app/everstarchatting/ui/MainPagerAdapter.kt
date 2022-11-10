package com.vmake.app.everstarchatting.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle,
    private val listChildFragment: List<Fragment>
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = listChildFragment.size

    override fun createFragment(position: Int): Fragment = listChildFragment[position]
}