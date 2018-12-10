package com.gokhanaliccii.infiniteimagelisting.ui.images

import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageDataSource
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel

class ImageListPresenter(
    private var view: ImageListContract.View?,
    private val imageListRepository: ImageDataSource
) : ImageListContract.Presenter {

    private var currentPage = 1

    override fun loadImages() {
        view?.showImagesLoadingProgress()

        imageListRepository.loadImages(page = currentPage, loadCallBack = object : ImageDataSource.ImageLoadCallBack {
            override fun onImagesLoaded(images: List<ImageUIModel>) {
                view?.imagesLoaded(images)
                view?.hideImageLoadingProgress()
            }

            override fun onImagesLoadFailed() {
                view?.hideImageLoadingProgress()
                view?.imagesLoadFailed()
            }
        })
    }

    override fun loadMoreImages() {
        loadImagesAt(++currentPage)
    }

    override fun loadImagesAt(page: Int) {
        imageListRepository.loadImages(page = currentPage, loadCallBack = object : ImageDataSource.ImageLoadCallBack {
            override fun onImagesLoaded(images: List<ImageUIModel>) {
                view?.hideLoadMoreProgress()
                view?.imagesLoaded(images)
            }

            override fun onImagesLoadFailed() {
                view?.hideLoadMoreProgress()

                if (isFirstPage())
                    view?.imagesLoadFailed()
            }
        })
    }

    override fun getCurrentPage() = currentPage

    override fun setCurrentPage(page: Int) {
        currentPage = page
    }

    override fun stop() {
        imageListRepository.stopOnGoingProcess()
        view = null
    }

    private fun isFirstPage() = currentPage == 0
}
