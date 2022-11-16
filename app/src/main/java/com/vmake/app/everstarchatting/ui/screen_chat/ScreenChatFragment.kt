package com.vmake.app.everstarchatting.ui.screen_chat

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vmake.app.base.helper.getMyAdapter
import com.vmake.app.base.helper.loadImage
import com.vmake.app.base.helper.notNullObserve
import com.vmake.app.base.ui.BaseFragment
import com.vmake.app.everstarchatting.databinding.FragmentScreenChatBinding
import com.vmake.app.everstarchatting.finish
import com.vmake.app.everstarchatting.repository.message.model.Message
import com.vmake.app.everstarchatting.repository.message.model.MessageType
import com.vmake.app.everstarchatting.ui.MainViewModel

class ScreenChatFragment : BaseFragment<FragmentScreenChatBinding>() {

    override fun makeBinding(inflater: LayoutInflater) = FragmentScreenChatBinding.inflate(inflater)

    //For setting the recyclerView.
    private val chatRoomAdapter: ChatRoomAdapter? by lazy { binding?.recyclerView?.getMyAdapter<ChatRoomAdapter>() }
    private var user: String? = null
    private var urlAvatar: String? = null

    private val socketViewModel: MainViewModel by activityViewModels()

    override fun FragmentScreenChatBinding.setupView() {
        getArguments()
        leave.setOnClickListener { finish() }
        send.setOnClickListener { sendMessage() }
        setupRecycle()
    }

    override fun FragmentScreenChatBinding.startFlow() {
        user?.let { user ->
            socketViewModel.getListerSocketOnMessageForConversation(user)
                .notNullObserve(this@ScreenChatFragment) {
                    it.data?.let { message ->
                        addItemToRecyclerView(message.apply {
                            viewType = MessageType.CHAT_PARTNER.index
                        })
                    }
                }
        }

    }

    override fun onBackPress() {
        finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        user = null
        urlAvatar = null
        socketViewModel.listerSocketStatus.removeObservers(this)
    }

    private fun FragmentScreenChatBinding.getArguments() {
        user = arguments?.getString("user")
        urlAvatar = arguments?.getString("urlAvatar")

        partnerName.text = user
        imgAvatar.loadImage(urlAvatar, isCircle = true)
    }

    private fun FragmentScreenChatBinding.setupRecycle() {
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ChatRoomAdapter.newInstance()
    }

    private fun sendMessage() {
        val content = binding?.editText?.text.toString()
        val message = Message(user ?: "", content, user ?: "", MessageType.CHAT_MINE.index)

        socketViewModel.sendSocket(message.toString())

        addItemToRecyclerView(message)
    }

    private fun addItemToRecyclerView(message: Message) {
        chatRoomAdapter?.add(message)
        binding?.recyclerView?.scrollToPosition(
            (chatRoomAdapter?.itemCount ?: 0) - 1
        ) //move focus on last message
    }

}