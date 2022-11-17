/**
 * @author Joyce Hong
 * @email soja0524@gmail.com
 * @created 2019-09-03
 * @desc
 */

package com.vmake.app.everstarchatting.ui.screen_chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vmake.app.base.helper.loadImage
import com.vmake.app.base.view_custom.BaseAdapterRecyclerView
import com.vmake.app.base.view_custom.BaseDiffCallBack
import com.vmake.app.everstarchatting.databinding.RowChatPartnerBinding
import com.vmake.app.everstarchatting.databinding.RowChatUserBinding
import com.vmake.app.everstarchatting.repository.message.model.Message

class ChatRoomAdapter : BaseAdapterRecyclerView<Message>() {

    companion object {
        val CHAT_MINE = 0
        val CHAT_PARTNER = 1

        val NO_HAS_SAME_ITEM = 0
        val TOP_HAS_SAME_ITEM = 1
        val BOTTOM_HAS_SAME_ITEM = 2
        val TOP_BOTTOM_HAS_SAME_ITEM = 3

        val KEY_RELATIVE_POSITION = "KEY_RELATIVE_POSITION"
        private var radius: Float = -1F
        private var radiusDiv10: Float = -1F

        fun newInstance() = ChatRoomAdapter()

        private class ChatUserHolder(val binding: RowChatUserBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bindData(position: Int, model: Message, relativePosition: Int) {
                binding.message.text = model.messageContent
                renderBackground(relativePosition)
            }

            fun renderBackground(relativePosition: Int) {
                if (radius == -1F) {
                    radius = binding.message.radius
                    radiusDiv10 = radius / 4
                }
                when (relativePosition) {
                    NO_HAS_SAME_ITEM -> {
                        binding.message.setRadii(radius, radius, radius, radius)
                    }
                    TOP_HAS_SAME_ITEM -> {
                        binding.message.setRadii(radius, radiusDiv10, radius, radius)
                    }
                    BOTTOM_HAS_SAME_ITEM -> {
                        binding.message.setRadii(radius, radius, radius, radiusDiv10)
                    }
                    TOP_BOTTOM_HAS_SAME_ITEM -> {
                        binding.message.setRadii(radius, radiusDiv10, radius, radiusDiv10)

                    }
                }
            }
        }

        private class ChatPartnerHolder(val binding: RowChatPartnerBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bindData(position: Int, model: Message, relativePosition: Int) {
                binding.message.text = model.messageContent
                binding.imgAvatar.loadImage(model.urlAvatar)
                renderBackground(relativePosition)
            }

            fun renderBackground(relativePosition: Int) {
                if (radius == -1F) {
                    radius = binding.message.radius
                    radiusDiv10 = radius / 4
                }
                when (relativePosition) {
                    NO_HAS_SAME_ITEM -> {
                        binding.message.setRadii(radius, radius, radius, radius)
                        binding.imgAvatar.visibility = View.VISIBLE
                    }
                    TOP_HAS_SAME_ITEM -> {
                        binding.message.setRadii(radiusDiv10, radius, radius, radius)
                        binding.imgAvatar.visibility = View.VISIBLE
                    }
                    BOTTOM_HAS_SAME_ITEM -> {
                        binding.message.setRadii(radius, radius, radiusDiv10, radius)
                        binding.imgAvatar.visibility = View.INVISIBLE
                    }
                    TOP_BOTTOM_HAS_SAME_ITEM -> {
                        binding.message.setRadii(radiusDiv10, radius, radiusDiv10, radius)
                        binding.imgAvatar.visibility = View.INVISIBLE

                    }
                }
            }
        }
    }

    var listRelativePosition: ArrayList<Int> = arrayListOf()
    var listRelativePositionOld: ArrayList<Int> = arrayListOf()

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
            holder.bindData(position, data[position], listRelativePosition[position])
        }
        if (holder is ChatPartnerHolder) {
            holder.bindData(position, data[position], listRelativePosition[position])
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val bundle = payloads[0] as Bundle
            if (holder is ChatUserHolder) {
                holder.renderBackground(bundle.getInt(KEY_RELATIVE_POSITION))
            }

            if (holder is ChatPartnerHolder) {
                holder.renderBackground(bundle.getInt(KEY_RELATIVE_POSITION))
            }
        }
    }

    override fun onDataUpdate() {
        var relativePosition = NO_HAS_SAME_ITEM
        listRelativePositionOld.clear()
        listRelativePositionOld.addAll(listRelativePosition.toMutableList())

        listRelativePosition.clear()

        data.forEachIndexed { index, message ->
            relativePosition = NO_HAS_SAME_ITEM
            data.getOrNull(index - 1)?.let {
                if (it.viewType == message.viewType) {
                    relativePosition += 1
                }
            }
            data.getOrNull(index + 1)?.let {
                if (it.viewType == message.viewType) {
                    relativePosition += 2
                }
            }
            listRelativePosition.add(relativePosition)

            diffCallback?.getChangePayload(index, index)
        }
    }

    override var diffCallback: BaseDiffCallBack<Message>? = object : BaseDiffCallBack<Message>() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].messageContent === newList[newItemPosition].messageContent
                    && listRelativePositionOld.getOrNull(oldItemPosition) == listRelativePosition.getOrNull(
                newItemPosition
            )
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

            val diff = Bundle()
            if (listRelativePosition.getOrNull(newItemPosition) != listRelativePositionOld.getOrNull(
                    oldItemPosition
                )
            ) {
                diff.putInt(
                    KEY_RELATIVE_POSITION,
                    listRelativePosition.getOrNull(newItemPosition) ?: 0
                )
            }
            return if (diff.size() == 0) {
                return super.getChangePayload(oldItemPosition, newItemPosition)
            } else diff
        }
    }

}