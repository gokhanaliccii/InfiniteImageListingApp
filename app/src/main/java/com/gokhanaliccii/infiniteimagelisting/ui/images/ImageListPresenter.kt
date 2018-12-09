package com.gokhanaliccii.infiniteimagelisting.ui.images

import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageDataSource
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel

class ImageListPresenter(private val view: ImageListContract.View,
                         private val imageListRepository: ImageDataSource) :ImageListContract.Presenter {

    override fun loadImages() {
        imageListRepository.loadImages(loadCallBack = object : ImageDataSource.ImageLoadCallBack {
            override fun onImagesLoaded(images: List<ImageUIModel>) {
                view.imagesLoaded(images)
                view.hideImageLoadimgProgess()
            }
        })
    }

    override fun loadMoreImages() {
        imageListRepository.loadImages(loadCallBack = object : ImageDataSource.ImageLoadCallBack {
            override fun onImagesLoaded(images: List<ImageUIModel>) {
                view.imagesLoaded(images)
            }
        })
    }
}