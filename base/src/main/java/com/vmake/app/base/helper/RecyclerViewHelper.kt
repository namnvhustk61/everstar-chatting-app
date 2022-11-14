package com.vmake.app.base.helper

import androidx.recyclerview.widget.RecyclerView


fun <T> RecyclerView.getMyAdapter(): T?{
    return adapter as T
}