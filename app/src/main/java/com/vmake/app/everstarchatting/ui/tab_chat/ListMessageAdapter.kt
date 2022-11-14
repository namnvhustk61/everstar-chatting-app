package com.vmake.app.everstarchatting.ui.tab_chat

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vmake.app.base.helper.formatHHmm
import com.vmake.app.base.helper.formatTime
import com.vmake.app.base.helper.getTimeNow
import com.vmake.app.base.helper.loadImage
import com.vmake.app.base.view_custom.BaseAdapterRecyclerView
import com.vmake.app.base.view_custom.BaseDiffCallBack
import com.vmake.app.everstarchatting.databinding.ItemTabChatMessageBinding
import com.vmake.app.everstarchatting.repository.message.model.Message

class ListMessageAdapter private constructor() : BaseAdapterRecyclerView<Message>() {

    companion object {
        fun newInstance() = ListMessageAdapter()

        private class MessageViewHolder(val binding: ItemTabChatMessageBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bindData(position: Int, model: Message) {
                binding.tvName.text = model.getName()
                binding.tvTime.text =
                    model.getTime().formatTime().formatHHmm() ?: getTimeNow().formatHHmm()

                binding.tvContent.text = model.getContent()
                binding.tvContent.setCustomFont(if (model.isSeen()) Typeface.NORMAL else Typeface.BOLD)
                binding.tvNumbNewContent.text =
                    if (model.getNumbYetSeen() > 9) "*" else "${model.getNumbYetSeen()}"
                binding.tvNumbNewContent.visibility = if (model.isSeen()) {
                    View.INVISIBLE
                } else View.VISIBLE

                binding.imgAvatar.loadImage(model.getUrlAvatar(), isCircle = true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MessageViewHolder(
            ItemTabChatMessageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MessageViewHolder) {
            holder.bindData(position, data[position])
        }
    }

    override var diffCallback: BaseDiffCallBack<Message>? = object : BaseDiffCallBack<Message>() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].getUrlAvatar()
                .equals(newList[newItemPosition].getUrlAvatar())
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }

    }
}