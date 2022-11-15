package com.vmake.app.everstarchatting.ui.screen_chat

import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.vmake.app.base.helper.launch
import com.vmake.app.base.helper.loadImage
import com.vmake.app.base.helper.runInMain
import com.vmake.app.base.ui.BaseFragment
import com.vmake.app.everstarchatting.databinding.FragmentScreenChatBinding
import com.vmake.app.everstarchatting.finish
import com.vmake.app.everstarchatting.repository.message.Message
import com.vmake.app.everstarchatting.repository.message.MessageType

class ScreenChatFragment : BaseFragment<FragmentScreenChatBinding>() {

    override fun makeBinding(inflater: LayoutInflater) = FragmentScreenChatBinding.inflate(inflater)

    //For setting the recyclerView.
    lateinit var chatRoomAdapter: ChatRoomAdapter

    private var user: String? = null
    private var urlAvatar: String? = null

    override fun FragmentScreenChatBinding.setupView() {
        user = arguments?.getString("user")
        urlAvatar = arguments?.getString("urlAvatar")

        partnerName.text = user
        imgAvatar.loadImage(urlAvatar, isCircle = true)
        leave.setOnClickListener { finish() }
        send.setOnClickListener { sendMessage() }

        setupRecycle()
        setupSocket()
    }

    override fun FragmentScreenChatBinding.startFlow() {

    }

    override fun onBackPress() {
        finish()
    }

    private fun FragmentScreenChatBinding.setupRecycle() {
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        chatRoomAdapter = ChatRoomAdapter.newInstance()
        recyclerView.adapter = chatRoomAdapter
    }

    private fun setupSocket() {

    }

    private fun sendMessage() {
        val content = binding?.editText?.text.toString()
        val message = Message(user ?: "", content, user ?: "", MessageType.CHAT_MINE.index)

        launch {
            addItemToRecyclerView(message)
        }
    }

    private suspend fun addItemToRecyclerView(message: Message) {
        runInMain {
            chatRoomAdapter.add(message)
            binding?.recyclerView?.scrollToPosition(chatRoomAdapter.itemCount - 1) //move focus on last message
        }
    }

}