package com.gokhanaliccii.httpclient

import com.gokhanaliccii.httpclient.HttpRequestQueue.HttpRequestInfo
import java.lang.reflect.Type

class Request<T>(val requestQueue: HttpRequestQueue<T>, val requestInfo: HttpRequestInfo, val type: Type) {
    private var tempData: T? = null
    private var latency: Long = 1000

    fun enqueue(httpResult: HttpRequestQueue.HttpResult<T>, shouldCache: Boolean, shouldRetry: Boolean = true) {
        if (tempData != null) {
            send(httpResult)
            return
        }

        requestQueue.add(getHttpRequest(httpResult),shouldCache,shouldRetry)
    }

    private fun getHttpRequest(httpResult: HttpRequestQueue.HttpResult<T>) =
        HttpRequestQueue.HttpRequest(requestInfo, type, httpResult)

    private fun send(httpResult: HttpRequestQueue.HttpResult<T>) {
        httpResult.onResponse(tempData!!)
    }
}