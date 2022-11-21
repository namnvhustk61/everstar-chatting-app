package com.vmake.app.base.helper.preview_url

import com.vmake.app.base.helper.ioLaunch
import com.vmake.app.base.helper.mainLaunch
import com.vmake.app.base.helper.runInBackground
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.launch
import org.jsoup.Jsoup


object PreviewUrlHelper {

    private val AGENT = "Mozilla"
    private val REFERRER = "http://www.google.com"
    private val TIMEOUT = 10000
    private val DOC_SELECT_QUERY = "meta[property^=og:]"
    private val OPEN_GRAPH_KEY = "content"
    private val PROPERTY = "property"
    private val OG_IMAGE = "og:image"
    private val OG_DESCRIPTION = "og:description"
    private val OG_URL = "og:url"
    private val OG_TITLE = "og:title"
    private val OG_SITE_NAME = "og:site_name"
    private val OG_TYPE = "og:type"

      fun fetchUrl(link: String, listener: PreviewUrlCallback) {
        var url = link
        if (!link.contains("http")) {
            url = "http://$link"
        }

        val openGraphResult = PreviewUrlResult()

        ioLaunch {
            try {
                val response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent(AGENT)
                    .referrer(REFERRER)
                    .timeout(TIMEOUT)
                    .followRedirects(true)
                    .execute()

                val doc = response.parse()

                val ogTags = doc.select(DOC_SELECT_QUERY)
                when {
                    ogTags.size > 0 ->
                        ogTags.forEachIndexed { index, _ ->
                            val tag = ogTags[index]
                            val text = tag.attr(AnnotationTarget.PROPERTY.toString())

                            when (text) {
                                OG_IMAGE -> {
                                    openGraphResult.image = (tag.attr(OPEN_GRAPH_KEY))
                                }
                                OG_DESCRIPTION -> {
                                    openGraphResult.description = (tag.attr(OPEN_GRAPH_KEY))
                                }
                                OG_URL -> {
                                    openGraphResult.url = (tag.attr(OPEN_GRAPH_KEY))
                                }
                                OG_TITLE -> {
                                    openGraphResult.title = (tag.attr(OPEN_GRAPH_KEY))
                                }
                                OG_SITE_NAME -> {
                                    openGraphResult.siteName = (tag.attr(OPEN_GRAPH_KEY))
                                }
                                OG_TYPE -> {
                                    openGraphResult.type = (tag.attr(OPEN_GRAPH_KEY))
                                }
                            }
                        }
                }
                listener.onPostResponse(openGraphResult)
            } catch (e: Exception) {
                listener.onError(e.localizedMessage)
            }
        }

    }


}