package com.gokhanaliccii.infiniteimagelisting.datasource.image.remote

import com.gokhanaliccii.httpclient.HttpRequestQueue
import com.gokhanaliccii.infiniteimagelisting.BuildConfig
import com.gokhanaliccii.infiniteimagelisting.InfiniteImageListingApp
import com.gokhanaliccii.infiniteimagelisting.common.cache.KeyValueList
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageDataSource
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel

class RemoteImageDataSource(private val imageService: ImageService,
                            private val store: KeyValueList<Int, ImageUIModel>) : ImageDataSource {

    override fun loadImages(count: Int, page: Int, loadCallBack: ImageDataSource.ImageLoadCallBack) {
        imageService.getImages(BuildConfig.UNSPLASH_APIKEY, page)
                .enqueue(object : HttpRequestQueue.HttpResult<Image> {
                    override fun onResponse(images: List<Image>) {
                        val uiModels = images.map { it.toIUModel() }
                        store.storeItems(page, uiModels)
                        loadCallBack.onImagesLoaded(uiModels)
                    }

                    override fun onResponse(image: Image) {
                        loadCallBack.onImagesLoaded(listOf(image.toIUModel()))
                    }
                }, false, false)
    }

    override fun stopOnGoingProcess() {
        InfiniteImageListingApp.instance.interruptOutCalls()
    }
}