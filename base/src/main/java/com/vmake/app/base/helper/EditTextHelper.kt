package com.vmake.app.base.helper

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class CustomPasswordTransformationMethod : PasswordTransformationMethod() {
    override fun getTransformation(source: CharSequence?, view: View?): CharSequence {
        return CustomCharSequence(source!!)
    }

    private class CustomCharSequence(private val source: CharSequence) : CharSequence {

        override val length: Int
            get() = source.length

        override fun get(index: Int): Char = '●'

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
            source.subSequence(startIndex, endIndex)
    }
}

fun EditText.trimText(): String = text.toString().trim()
fun Editable?.trimText(): String = toString().trim()

fun hideKeyboard(view: View?): Boolean {
    view?.let {
       hideKeyboard(view.context, view)
    }
    return false
}

fun showKeyboard(edit: EditText?): Boolean {
    edit?.let {
        showKeyboard(edit, edit.context)
    }
    return false
}

fun EditText.passwordText(): String {
    return this.text.toString()
}


private fun hideKeyboard(activity: Context?, view: View?) {
    if (view == null || activity == null) return
    val imm = activity
        .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

private fun showKeyboard(edit: EditText, context: Context?, isRequestFocus: Boolean = true) {
    if (context == null) return
    edit.isFocusable = true
    edit.isFocusableInTouchMode = true
    if (isRequestFocus)
        edit.requestFocus()
    try {
        edit.setSelection(edit.text.toString().trim { it <= ' ' }.length)
    } catch (ignored: Exception) {

    }

    val imm = context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(edit, 0)
}