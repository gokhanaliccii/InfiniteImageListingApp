package com.gokhanaliccii.infiniteimagelisting.ui.images

import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel

interface ImageListContract {

    interface View {
        fun showImagesLoadingProgress()

        fun hideImageLoadingProgress()

        fun hideLoadMoreProggress()

        fun imagesLoaded(images: List<ImageUIModel>)

    }

    interface Presenter {
        fun loadImages()

        fun loadMoreImages()
    }
}