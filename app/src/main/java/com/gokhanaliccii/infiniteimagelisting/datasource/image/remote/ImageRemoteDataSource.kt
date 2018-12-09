package com.gokhanaliccii.infiniteimagelisting.datasource.image.remote

import com.gokhanaliccii.httpclient.HttpRequestQueue
import com.gokhanaliccii.infiniteimagelisting.BuildConfig
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageDataSource

class ImageRemoteDataSource(private val imageService: ImageService) : ImageDataSource {

    override fun loadImages(count: Int, page: Int, loadCallBack: ImageDataSource.ImageLoadCallBack) {
        imageService.getImages(BuildConfig.UNSPLASH_APIKEY)
            .enqueue(object : HttpRequestQueue.HttpResult<Image> {
                override fun onResponse(images: List<Image>) {
                    val imageUIModels = images.map { it.toIUModel() }
                    loadCallBack.onImagesLoaded(imageUIModels)
                }

                override fun onResponse(image: Image) {
                    loadCallBack.onImagesLoaded(listOf(image.toIUModel()))
                }
            }, false, false)
    }
}