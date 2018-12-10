package com.gokhanaliccii.httpclient

import java.lang.Exception

interface HttpRequestQueue {

    fun <T> add(info: HttpRequest<T>, needCache: Boolean)

    fun <T> add(info: HttpRequest<T>, needCache: Boolean, shouldUseRetry: Boolean)

    fun removeAll()

    fun start()

    fun stop()

    class HttpRequest<T>(val requestInfo: HttpRequestInfo<T>, val resultListener: HttpResult<T>)

    class HttpRequestInfo<T>(val url: String, val method: Method) {
        var body: Any? = null
        var returnType: Class<T>? = null
        var isArray: Boolean? = false

        lateinit var headers: Map<String, String>

        enum class Method {
            GET, POST
        }
    }

    interface HttpResult<T> {
        fun onResponse(response: T) {}

        fun onResponse(response: List<T>) {}

        fun onRequestFailed(exception: Exception) {}
    }
}