package com.gokhanaliccii.infiniteimagelisting.datasource.image.remote

import com.gokhanaliccii.httpclient.Request
import com.gokhanaliccii.httpclient.annotation.method.GET
import com.gokhanaliccii.httpclient.annotation.method.TYPE
import com.gokhanaliccii.httpclient.annotation.url.Query

interface ImageService {
    @GET("/photos")
    @TYPE(value = Image::class, isArray = true)
    fun getImages(@Query("client_id") clientId: String): Request<Image>
}