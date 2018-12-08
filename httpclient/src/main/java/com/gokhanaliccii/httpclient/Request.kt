package com.gokhanaliccii.httpclient

import com.gokhanaliccii.httpclient.HttpRequestQueue.HttpRequestInfo

class Request<T>(val requestQueue: HttpRequestQueue, val requestInfo: HttpRequestInfo<T>) {
    private var tempData: T? = null
    private var latency: Long = 1000

    fun enqueue(httpResult: HttpRequestQueue.HttpResult<T>, shouldCache: Boolean, shouldRetry: Boolean = true) {
        if (tempData != null) {
            send(httpResult)
            return
        }

        requestQueue.add(getHttpRequest(httpResult), shouldCache, shouldRetry)
    }

    private fun getHttpRequest(httpResult: HttpRequestQueue.HttpResult<T>) =
        HttpRequestQueue.HttpRequest(requestInfo, httpResult)

    private fun send(httpResult: HttpRequestQueue.HttpResult<T>) {
        httpResult.onResponse(tempData!!)
    }
}