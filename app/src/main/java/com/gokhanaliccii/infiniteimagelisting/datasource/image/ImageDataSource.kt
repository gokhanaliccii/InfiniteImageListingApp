package com.gokhanaliccii.infiniteimagelisting.datasource.image

interface ImageDataSource {

    fun loadImages(count: Int = 20, page: Int = 1, loadCallBack: ImageLoadCallBack)

    interface ImageLoadCallBack {
        fun onImagesLoaded(images: List<ImageUIModel>)

        fun imagesNotExist(){}
    }
}