package com.vmake.app.base.helper


import java.util.regex.Pattern

fun String.detectUrls(): List<String> {
    val urlPattern: Pattern = Pattern.compile(
        "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
        Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
    )
    val urlList = mutableListOf<String>()
    val urlMatcher = urlPattern.matcher(this)
    var matchStart: Int
    var matchEnd: Int
    while (urlMatcher.find()) {
        matchStart = urlMatcher.start(1)
        matchEnd = urlMatcher.end()
        urlList.add(this.substring(matchStart, matchEnd))
    }
    return urlList
}
