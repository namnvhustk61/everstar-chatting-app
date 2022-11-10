package com.vmake.app.base.view_custom

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffCallBack<M> : DiffUtil.Callback() {
    val oldList = arrayListOf<M>()
    val newList = arrayListOf<M>()
    fun setListData(old: List<M>, new: List<M>) {
        oldList.clear()
        newList.clear()
        oldList.addAll(old)
        newList.addAll(new)
    }

    override fun getNewListSize(): Int = newList.size
    override fun getOldListSize(): Int = oldList.size
}