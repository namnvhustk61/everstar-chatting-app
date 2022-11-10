package com.vmake.app.everstarchatting.ui

import android.view.LayoutInflater
import androidx.viewpager2.widget.ViewPager2
import com.vmake.app.base.ui.BaseFragment
import com.vmake.app.everstarchatting.databinding.FragmentMainBinding
import com.vmake.app.everstarchatting.ui.tab_chat.TabChatFragment
import com.vmake.app.everstarchatting.ui.tab_friends.TabFriendsFragment
import com.vmake.app.everstarchatting.ui.tab_setting.TabSettingsFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override var isModeChangeStatusBar: Boolean = true
    override var isSetNoStatusBar: Boolean = true

    override fun makeBinding(inflater: LayoutInflater) = FragmentMainBinding.inflate(inflater)


    override fun FragmentMainBinding.setupView() {
        setPagerAdapter()
        mergeTabAndPager()
    }

    override fun FragmentMainBinding.startFlow() {

    }

    private fun FragmentMainBinding.setPagerAdapter() {
        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        viewPager.adapter = MainPagerAdapter(
            childFragmentManager, lifecycle, listOf(
                TabChatFragment.newInstance(),
                TabFriendsFragment.newInstance(),
                TabSettingsFragment.newInstance()
            )
        )
    }

    private fun FragmentMainBinding.mergeTabAndPager() {
        val listTab = listOf(
            tabHome,
            tabFile,
            tabSettings
        ).also { ls ->
            ls.forEachIndexed { index, tabBarItem ->
                tabBarItem.setOnClickListener {
                    if (index != viewPager.currentItem) {
                        viewPager.setCurrentItem(index, false)
                    }
                }
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                listTab.forEachIndexed { index, tabBarItem ->
                    tabBarItem.setActive(index == position)
                }
            }
        })
        // default first tab
        listTab[0].setActive(true)
    }
}