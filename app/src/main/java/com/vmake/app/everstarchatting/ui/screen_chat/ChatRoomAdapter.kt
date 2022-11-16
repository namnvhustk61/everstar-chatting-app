/**
 * @author Joyce Hong
 * @email soja0524@gmail.com
 * @created 2019-09-03
 * @desc
 */

package com.vmake.app.everstarchatting.ui.screen_chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vmake.app.base.view_custom.BaseAdapterRecyclerView
import com.vmake.app.base.view_custom.BaseDiffCallBack
import com.vmake.app.everstarchatting.databinding.RowChatPartnerBinding
import com.vmake.app.everstarchatting.databinding.RowChatUserBinding
import com.vmake.app.everstarchatting.repository.message.model.Message

class ChatRoomAdapter : BaseAdapterRecyclerView<Message>() {

    companion object {
        val CHAT_MINE = 0
        val CHAT_PARTNER = 1
        fun newInstance() = ChatRoomAdapter()

        private class ChatUserHolder(val binding: RowChatUserBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bindData(position: Int, model: Message) {
                binding.message.text = model.messageContent
            }
        }

        private class ChatPartnerHolder(val binding: RowChatPartnerBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bindData(position: Int, model: Message) {
                binding.message.text = model.messageContent
                binding.username.text = model.userName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CHAT_MINE -> ChatUserHolder(
                RowChatUserBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            CHAT_PARTNER -> ChatPartnerHolder(
                RowChatPartnerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw Exception(ChatRoomAdapter.javaClass.name)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return data[position].viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChatUserHolder) {
            holder.bindData(position, data[position])
        }

        if (holder is ChatPartnerHolder) {
            holder.bindData(position, data[position])
        }
    }

    override var diffCallback: BaseDiffCallBack<Message>? = object : BaseDiffCallBack<Message>() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].messageContent.equals(newList[newItemPosition].messageContent)
        }
    }

}