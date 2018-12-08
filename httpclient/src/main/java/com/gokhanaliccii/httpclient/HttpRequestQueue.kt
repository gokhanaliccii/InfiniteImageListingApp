package com.gokhanaliccii.httpclient

import java.lang.reflect.Type

interface HttpRequestQueue {

    fun <T> add(info: HttpRequest<T>, needCache: Boolean)

    fun <T> add(info: HttpRequest<T>, needCache: Boolean, shouldUseRetry: Boolean)

    fun remove(tag: String)

    fun removeAll()

    fun start()

    fun stop()

    class HttpRequest<T>(val requestInfo: HttpRequestInfo, val type: Type, val resultListener: HttpResult<T>)

    class HttpRequestInfo(val url: String, val method: Method) {
        var body: Any? = null
        lateinit var headers: Map<String, String>

        enum class Method {
            GET, POST
        }
    }

    interface HttpResult<T> {
        fun onResponse(response: T) {}

        fun onResponse(response: List<T>) {}
    }
}