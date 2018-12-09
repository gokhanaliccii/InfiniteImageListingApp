package com.gokhanaliccii.infiniteimagelisting.datasource.image.local

import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageDataSource
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel

class LocalImageDataSource : ImageDataSource {

    private val pageImageListMap: Map<Int, List<ImageUIModel>> by lazy { mutableMapOf<Int, List<ImageUIModel>>() }

    override fun loadImages(count: Int, page: Int, loadCallBack: ImageDataSource.ImageLoadCallBack) {
        if (pageImageListMap.containsKey(page)) {
            loadCallBack.onImagesLoaded(pageImageListMap.getValue(page))
        } else {
            loadCallBack.imagesNotExist()
        }
    }
}