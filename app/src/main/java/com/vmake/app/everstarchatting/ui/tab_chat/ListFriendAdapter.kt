package com.vmake.app.everstarchatting.ui.tab_chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vmake.app.base.helper.loadImage
import com.vmake.app.base.view_custom.BaseAdapterRecyclerView
import com.vmake.app.base.view_custom.BaseDiffCallBack
import com.vmake.app.everstarchatting.databinding.ItemTabChatFriendBinding
import com.vmake.app.everstarchatting.repository.friend.model.Friend

interface ImplListFriendAdapter {
    fun onClickItem(position: Int)
}

class ListFriendAdapter private constructor() : BaseAdapterRecyclerView<Friend>() {

    private var action: ImplListFriendAdapter? = null

    companion object {
        fun newInstance(action: ImplListFriendAdapter? = null) = ListFriendAdapter().apply {
            this.action = action
        }

        private class FriendViewHolder(val binding: ItemTabChatFriendBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bindData(position: Int, model: Friend, action: ImplListFriendAdapter?) {
                action?.let {
                    binding.root.setOnClickListener {
                        action.onClickItem(position)
                    }
                }
                binding.tvName.text = model.getName()
                binding.imgAvatar.loadImage(model.getUrlAvatar(), isCircle = true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FriendViewHolder(
            ItemTabChatFriendBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FriendViewHolder) {
            holder.bindData(position, data[position], action)
        }
    }

    override var diffCallback: BaseDiffCallBack<Friend>? = object : BaseDiffCallBack<Friend>() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].getUrlAvatar()
                .equals(newList[newItemPosition].getUrlAvatar())
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }

    }
}