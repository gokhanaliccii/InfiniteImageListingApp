package com.gokhanaliccii.infiniteimagelisting.datasource.image

class ImageRepository(
    private val localSource: ImageDataSource,
    private val remoteSource: ImageDataSource
) : ImageDataSource {

    override fun loadImages(count: Int, page: Int, loadCallBack: ImageDataSource.ImageLoadCallBack) {
        localSource.loadImages(count, page, object : ImageDataSource.ImageLoadCallBack {

            override fun onImagesLoaded(images: List<ImageUIModel>) = loadCallBack.onImagesLoaded(images)

            override fun imagesNotExist() = remoteSource.loadImages(count, page, loadCallBack)

            override fun onImagesLoadFailed() {
                loadCallBack.onImagesLoadFailed()
            }
        })
    }

    override fun stopOnGoingProcess() {
        localSource.stopOnGoingProcess()
        remoteSource.stopOnGoingProcess()
    }
}