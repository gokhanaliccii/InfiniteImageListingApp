package com.gokhanaliccii.httpclient

import android.os.Looper
import android.util.Log
import com.gokhanaliccii.httpclient.util.closeStreams
import com.gokhanaliccii.httpclient.util.isResponseCodeSucceed
import com.gokhanaliccii.jsonparser.jsonTo
import com.gokhanaliccii.jsonparser.jsonToList
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.ProtocolException
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class JsonRequestQueue : HttpRequestQueue {

    private val executorService: ExecutorService by lazy { Executors.newFixedThreadPool(10) }

    override fun <T> add(request: HttpRequestQueue.HttpRequest<T>, needCache: Boolean) {
    }

    override fun <T> add(request: HttpRequestQueue.HttpRequest<T>, needCache: Boolean, shouldUseRetry: Boolean) {
        sendRequest(request)
    }

    override fun start() {
    }

    override fun stop() {
    }

    override fun removeAll() {
    }

    private fun <T> sendRequest(request: HttpRequestQueue.HttpRequest<T>) {
        val requestInfo = request.requestInfo

        executorService.submit {
            try {
                val connection = URL(requestInfo.url).openConnection() as HttpURLConnection
                putHeaders(requestInfo, connection)
                putHttpMethod(requestInfo, connection)

                if (connection.responseCode.isResponseCodeSucceed()) {
                    val outputStream = ByteArrayOutputStream()
                    val inputStream = connection.inputStream
                    inputStream.copyTo(outputStream)
                    closeStreams(inputStream, outputStream)

                    val rawJson = outputStream.toString()
                    val returnType = requestInfo.returnType!!
                    val isReturnTypeArray = requestInfo.isArray!!

                    if (isReturnTypeArray) {
                        rawJson.jsonToList(returnType).let {
                            android.os.Handler(Looper.getMainLooper()).post {
                                request.resultListener.onResponse(it)
                            }
                        }
                    } else {
                        rawJson.jsonTo(returnType).let {
                            android.os.Handler(Looper.getMainLooper()).post {
                                request.resultListener.onResponse(it)
                            }
                        }
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun putHeaders(requestInfo: HttpRequestQueue.HttpRequestInfo<*>, connection: HttpURLConnection) {
        val headers = requestInfo.headers
        for (key in headers.keys)
            connection.setRequestProperty(key, headers[key])
    }

    @Throws(ProtocolException::class)
    private fun putHttpMethod(requestInfo: HttpRequestQueue.HttpRequestInfo<*>, connection: HttpURLConnection) {
        connection.requestMethod = requestInfo.method.name
    }
}