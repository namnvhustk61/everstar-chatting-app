package com.vmake.app.base.helper.preview_url

interface PreviewUrlCallback {
    fun onPostResponse(result: PreviewUrlResult)
    fun onError(error: String?)
}