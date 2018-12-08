package com.gokhanaliccii.httpclient.util

import java.io.Closeable
import java.net.HttpURLConnection

fun closeStreams(vararg streams: Closeable) {
    streams.forEach(Closeable::close)
}

fun Int.isResponseCodeSucceed() = this in HttpURLConnection.HTTP_OK..HttpURLConnection.HTTP_ACCEPTED