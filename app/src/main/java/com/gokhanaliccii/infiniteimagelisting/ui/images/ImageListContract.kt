package com.gokhanaliccii.infiniteimagelisting.ui.images

import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel

interface ImageListContract {

    interface View {
        fun showImagesLoadingProgress()

        fun hideImageLoadingProgress()

        fun hideLoadMoreProgress()

        fun imagesLoaded(images: List<ImageUIModel>)

        fun imagesLoadFailed()
    }

    interface Presenter {
        fun loadImages()

        fun loadMoreImages()

        fun loadImagesAt(page: Int)

        fun getCurrentPage(): Int

        fun setCurrentPage(page: Int)

        fun stop()
    }
}