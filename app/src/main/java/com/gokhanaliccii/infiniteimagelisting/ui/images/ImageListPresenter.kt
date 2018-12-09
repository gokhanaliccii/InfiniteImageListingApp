package com.gokhanaliccii.infiniteimagelisting.ui.images

import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageDataSource
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel

class ImageListPresenter(private val view: ImageListContract.View,
                         private val imageListRepository: ImageDataSource) : ImageListContract.Presenter {

    private var currentPage = 1

    override fun loadImages() {
        imageListRepository.loadImages(page = currentPage, loadCallBack = object : ImageDataSource.ImageLoadCallBack {
            override fun onImagesLoaded(images: List<ImageUIModel>) {
                view.imagesLoaded(images)
                view.hideImageLoadingProgress()
            }
        })
    }

    override fun loadMoreImages() {
        loadImagesAt(++currentPage)
    }

    override fun loadImagesAt(page: Int) {
        imageListRepository.loadImages(page = currentPage, loadCallBack = object : ImageDataSource.ImageLoadCallBack {
            override fun onImagesLoaded(images: List<ImageUIModel>) {
                view.hideLoadMoreProgress()
                view.imagesLoaded(images)
            }
        })
    }

    fun getCurrentPage() = currentPage

    fun setCurrentPage(page: Int) {
        currentPage = page
    }
}