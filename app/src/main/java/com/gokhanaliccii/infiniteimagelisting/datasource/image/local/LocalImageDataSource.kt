package com.gokhanaliccii.infiniteimagelisting.datasource.image.local

import com.gokhanaliccii.infiniteimagelisting.common.cache.KeyValueList
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageDataSource
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel

class LocalImageDataSource(private val store: KeyValueList<Int, ImageUIModel>) : ImageDataSource {

    override fun loadImages(count: Int, page: Int, loadCallBack: ImageDataSource.ImageLoadCallBack) {
        val items = store.getItems(page)
        if (items == null) {
            loadCallBack.imagesNotExist()
        } else {
            loadCallBack.onImagesLoaded(items)
        }
    }

    override fun stopOnGoingProcess() {

    }
}