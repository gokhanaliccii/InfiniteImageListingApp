package com.gokhanaliccii.infiniteimagelisting

import android.app.Application
import com.gokhanaliccii.httpclient.EasyHttpClient
import com.gokhanaliccii.httpclient.JsonRequestQueue
import com.gokhanaliccii.infiniteimagelisting.common.lifecycle.ApplicationLifeCycleRegistry
import com.gokhanaliccii.infiniteimagelisting.common.lifecycle.LifeCycleBag
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageDataSource
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageRepository
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModelStore
import com.gokhanaliccii.infiniteimagelisting.datasource.image.local.LocalImageDataSource
import com.gokhanaliccii.infiniteimagelisting.datasource.image.remote.ImageService
import com.gokhanaliccii.infiniteimagelisting.datasource.image.remote.RemoteImageDataSource


class InfiniteImageListingApp : Application() {

    private val BASE_URL = "https://api.unsplash.com"

    companion object {
        lateinit var instance: InfiniteImageListingApp
            private set
        lateinit var activityLifeCycleBag: LifeCycleBag
            private set
        lateinit var fragmentLifeCycleBag: LifeCycleBag
            private set
    }

    private lateinit var imageDataSource: ImageDataSource
    private val jsonRequestQueue = JsonRequestQueue()

    override fun onCreate() {
        super.onCreate()

        instance = this
        initLifeCycle()

        val imageUIModelStore = ImageUIModelStore()
        val easyHttpClient = EasyHttpClient.with(jsonRequestQueue, BASE_URL)
        val imageService = easyHttpClient.create(ImageService::class.java)
        val localImageDataSource = LocalImageDataSource(imageUIModelStore)
        val remoteImageDataSource = RemoteImageDataSource(imageService, imageUIModelStore)

        imageDataSource = ImageRepository(localImageDataSource, remoteImageDataSource)
    }

    private fun initLifeCycle() {
        activityLifeCycleBag = LifeCycleBag()
        fragmentLifeCycleBag = LifeCycleBag()
        ApplicationLifeCycleRegistry().registerAppLifeCycleCallback(this)
    }

    fun interruptOutCalls(){
        jsonRequestQueue.stop()
    }

    fun imageDataSource(): ImageDataSource = imageDataSource
}