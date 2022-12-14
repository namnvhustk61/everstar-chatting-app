package com.vmake.app.everstarchatting

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.vmake.app.base.ui.BaseFragment
import com.vmake.app.everstarchatting.ui.MainFragmentDirections


fun Fragment.finish() {
    findNavController().navigateUp()
}

fun <B : ViewBinding> BaseFragment<B>.gotoScreenChatFragment(
    user: String? = null,
    urlAvatar: String? = null
) {
    navigateTo(MainFragmentDirections.actionMainFragmentToScreenChatFragment().apply {
        arguments.putString("user", user)
        arguments.putString("urlAvatar", urlAvatar)
    })
}

fun <B : ViewBinding> BaseFragment<B>.navigateTo(direction: NavDirections) {
    findNavController().navigate(direction)
}