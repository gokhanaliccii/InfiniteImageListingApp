package com.gokhanaliccii.infiniteimagelisting

import android.app.Application
import com.gokhanaliccii.httpclient.EasyHttpClient
import com.gokhanaliccii.httpclient.JsonRequestQueue
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageDataSource
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageRepository
import com.gokhanaliccii.infiniteimagelisting.datasource.image.local.LocalImageDataSource
import com.gokhanaliccii.infiniteimagelisting.datasource.image.remote.ImageService
import com.gokhanaliccii.infiniteimagelisting.datasource.image.remote.RemoteImageDataSource


class InfiniteImageListingApp : Application() {

    private lateinit var imageDataSource: ImageDataSource

    override fun onCreate() {
        super.onCreate()

        instance = this

        val easyHttpClient = EasyHttpClient.with(JsonRequestQueue(), "https://api.unsplash.com")
        val imageService = easyHttpClient.create(ImageService::class.java)
        val localImageDataSource = LocalImageDataSource()
        val remoteImageDataSource = RemoteImageDataSource(imageService)

        imageDataSource = ImageRepository(localImageDataSource, remoteImageDataSource)
    }

    fun imageDataSource(): ImageDataSource = imageDataSource

    companion object {
        lateinit var instance: InfiniteImageListingApp
            private set
    }
}