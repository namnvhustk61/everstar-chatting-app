package com.vmake.app.everstarchatting.ui.tab_chat

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vmake.app.base.helper.formatHHmm
import com.vmake.app.base.helper.formatTime
import com.vmake.app.base.helper.getTimeNow
import com.vmake.app.base.helper.loadImage
import com.vmake.app.base.view_custom.BaseAdapterRecyclerView
import com.vmake.app.base.view_custom.BaseDiffCallBack
import com.vmake.app.everstarchatting.R
import com.vmake.app.everstarchatting.databinding.ItemTabChatMessageBinding
import com.vmake.app.everstarchatting.databinding.ItemTabChatViewTypeRecycleFriendBinding
import com.vmake.app.everstarchatting.repository.message.model.Message

interface ImplListMessageAdapter {
    fun onClickItem(position: Int)
}

class ListMessageAdapter private constructor(private val adapterListFriend: ListFriendAdapter) :
    BaseAdapterRecyclerView<Message>() {

    private var implActionMessage: ImplListMessageAdapter? = null

    companion object {
        val VIEW_TYPE_RECYCLE_FRIEND = 0
        val VIEW_TYPE_ITEM_MESSAGE = 1

        fun newInstance(
            adapterListFriend: ListFriendAdapter,
            implActionMessage: ImplListMessageAdapter? = null,
            ) = ListMessageAdapter(adapterListFriend).apply {
            this.implActionMessage = implActionMessage
        }

        private class MessageViewHolder(val binding: ItemTabChatMessageBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bindData(position: Int, model: Message, action: ImplListMessageAdapter?) {
                action?.let {
                    binding.root.setOnClickListener { action.onClickItem(position) }
                }
                binding.tvName.text = model.getName()
                binding.tvTime.text =
                    model.getTime().formatTime().formatHHmm() ?: getTimeNow().formatHHmm()

                binding.tvContent.text = model.getContent()
                binding.tvContent.setTextColor(
                    if (model.isSeen())
                        ContextCompat.getColor(binding.root.context, R.color.text_gray_light)
                    else
                        ContextCompat.getColor(binding.root.context, R.color.text_gray)
                )
                binding.tvNumbNewContent.text =
                    if (model.getNumbYetSeen() > 9) "*" else "${model.getNumbYetSeen()}"
                binding.tvNumbNewContent.visibility = if (model.isSeen()) {
                    View.INVISIBLE
                } else View.VISIBLE

                binding.imgAvatar.loadImage(model.getUrlAvatar(), isCircle = true)
            }
        }

        private class RecycleFriendViewHolder(val binding: ItemTabChatViewTypeRecycleFriendBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun setupRecycleListFriend(adapterListFriend: ListFriendAdapter) {
                binding.rcvHortFriends.layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                binding.rcvHortFriends.adapter = adapterListFriend
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            VIEW_TYPE_RECYCLE_FRIEND -> RecycleFriendViewHolder(
                ItemTabChatViewTypeRecycleFriendBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            VIEW_TYPE_ITEM_MESSAGE -> MessageViewHolder(
                ItemTabChatMessageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw Exception(ListFriendAdapter.javaClass.name)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_RECYCLE_FRIEND
        else VIEW_TYPE_ITEM_MESSAGE
    }

    override fun getItemCount() = data.size + 1

    override var diffCallback: BaseDiffCallBack<Message>? = object : BaseDiffCallBack<Message>() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].getUrlAvatar()
                .equals(newList[newItemPosition].getUrlAvatar())
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MessageViewHolder) {
            holder.bindData(position, data[position - 1], implActionMessage)
        }
        if (holder is RecycleFriendViewHolder) {
            holder.setupRecycleListFriend(adapterListFriend)
            // TODO Save State for Recycle List Friend
            val key = holder.getLayoutPosition()
            val state = scrollStates[key]
            if (state != null) {
                holder.binding.rcvHortFriends.layoutManager?.onRestoreInstanceState(state)
            }
        }
    }

    // TODO Save State for Recycle List Friend
    private var scrollStates: MutableMap<Int, Parcelable?> = HashMap()

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is RecycleFriendViewHolder) {
            val key: Int = holder.getLayoutPosition()
            scrollStates[key] = holder.binding.rcvHortFriends.layoutManager?.onSaveInstanceState()
        }

    }
}