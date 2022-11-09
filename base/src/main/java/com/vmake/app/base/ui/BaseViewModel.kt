package com.vmake.app.base.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    protected fun getContext(): Context = getApplication<Application>().applicationContext
}