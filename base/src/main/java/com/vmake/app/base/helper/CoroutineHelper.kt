package com.vmake.app.base.helper

import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

fun getMainScope(): CoroutineScope {
    return CoroutineScope(Dispatchers.Main)
}

fun getBackgroundScope(): CoroutineScope {
    return CoroutineScope(Dispatchers.Default)
}

fun getIOScope(): CoroutineScope {
    return CoroutineScope(Dispatchers.IO)
}

fun mainLaunch(block: suspend CoroutineScope.() -> Unit) = getMainScope().launch { block() }
fun backgroundLaunch(block: suspend CoroutineScope.() -> Unit) =
    getBackgroundScope().launch { block() }

fun ioLaunch(block: suspend CoroutineScope.() -> Unit) = getIOScope().launch { block() }

fun AndroidViewModel.launch(block: suspend CoroutineScope.() -> Unit) =
    viewModelScope.launch { block() }

fun Fragment.launch(block: suspend CoroutineScope.() -> Unit) =
    viewLifecycleOwner.lifecycleScope.launch { block() }

suspend fun <T> runInBackground(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Default) { block() }

suspend fun <T> runInIO(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.IO) { block() }

suspend fun <T> runInMain(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.Main) { block() }

suspend inline fun loop(
    timeMillis: Long,
    crossinline block: suspend CoroutineScope.() -> Boolean
) =
    withContext(Dispatchers.Default) {
        while (block()) {
            delay(timeMillis)
        }
    }
