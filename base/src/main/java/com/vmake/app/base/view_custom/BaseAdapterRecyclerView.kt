package com.vmake.app.base.view_custom

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapterRecyclerView<T> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_NULL = 0
        const val VIEW_DATA = 1
    }

    protected open var isShowNull: Boolean = false
    protected val data: MutableList<T> = arrayListOf()

    abstract var diffCallback: BaseDiffCallBack<T>?

    override fun getItemCount(): Int {
        return if (isShowNull && data.isEmpty()) {
            1
        } else {
            data.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.isEmpty()) {
            VIEW_NULL
        } else {
            VIEW_DATA
        }
    }

    open fun onDataUpdate() {

    }

    fun getListData() = data

    fun getDataItem(position: Int) = data[position]

    fun submit(list: List<T>?, callBack: Runnable? = null) {
        if (list == null) return
        diffSet(data.toMutableList(), data.apply {
            clear()
            addAll(list)
            onDataUpdate()
        })
        notifyDataSetChanged()
        callBack?.run()
    }

    fun add(list: List<T>?, callBack: Runnable? = null) {
        if (list.isNullOrEmpty()) return
        val startIndex = data.size

        diffSet(data, data.apply {
            addAll(list)
            onDataUpdate()
        })
        notifyItemRangeInserted(startIndex, list.size)
        callBack?.run()
    }

    fun add(list: T, callBack: Runnable? = null) {
        if (list == null) return

        diffSet(data.toMutableList(), data.apply {
            add(list)
            onDataUpdate()
        })

        notifyItemInserted(data.size - 1)
        callBack?.run()
    }

    fun removeItem(position: Int) {
        if (position >= data.size) {
            return
        }
        diffSet(data.toMutableList(), data.apply {
            removeAt(position)
            onDataUpdate()
        })
        notifyItemRemoved(position)
    }

    fun submitDiffSet(list: List<T>?, callBack: Runnable? = null) {
        if (list == null) return
        if (data.isEmpty()) {
            submit(list, callBack)
            return
        }
        diffSet(oldList = data, newList = list)
        data.clear()
        data.addAll(list)
        onDataUpdate()
        notifyDataSetChanged()

        callBack?.run()
    }

    private fun clearData() {
        data.clear()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear(callBack: Runnable? = null) {
        clearData()
        notifyDataSetChanged()
        callBack?.run()
        onDataUpdate()
    }

    private fun diffSet(oldList: List<T>, newList: List<T>) {
        diffCallback?.let {
            it.setListData(oldList, newList)
            DiffUtil.calculateDiff(it).dispatchUpdatesTo(this)
        }
    }
}

class NullViewHolder(view: View) : RecyclerView.ViewHolder(view)