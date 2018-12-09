package com.gokhanaliccii.infiniteimagelisting.ui.images

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gokhanaliccii.infiniteimagelisting.InfiniteImageListingApp
import com.gokhanaliccii.infiniteimagelisting.R
import com.gokhanaliccii.infiniteimagelisting.common.recyclerview.EndlessRecyclerViewScrollListener
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel
import com.gokhanaliccii.infiniteimagelisting.ui.images.adapter.ImageListAdapter
import com.gokhanaliccii.infiniteimagelisting.widget.LoadableRecyclerView

class ImageListFragment : Fragment(), ImageListContract.View {

    companion object {
        private const val COLUMN_COUNT = 2
        private const val CURRENT_PAGE = "current_page"
        fun newInstance(): ImageListFragment = ImageListFragment()
    }

    private lateinit var presenter: ImageListPresenter
    private lateinit var loadableRecycler: LoadableRecyclerView
    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener
    private val imageListAdapter by lazy { ImageListAdapter(mutableListOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ImageListPresenter(this, InfiniteImageListingApp.instance.imageDataSource())
                .apply {
                    savedInstanceState?.getInt(CURRENT_PAGE)?.apply {
                        setCurrentPage(this)
                    }
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_list, container, false)
                .apply { initUi(this) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.loadImages()
    }

    private fun initUi(view: View) {
        loadableRecycler = view.findViewById(R.id.recyclerview_image)

        val layoutManager = GridLayoutManager(context, COLUMN_COUNT)
        endlessRecyclerViewScrollListener = EndlessRecyclerViewScrollListener(layoutManager) {
            presenter.loadMoreImages()
            loadableRecycler.showBottomLoadingProgress()
        }

        loadableRecycler.initRecyclerView {
            recyclerView.adapter = imageListAdapter
            recyclerView.layoutManager = layoutManager
            recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState.apply {
            putInt(CURRENT_PAGE, presenter.getCurrentPage())
        })
    }

    override fun showImagesLoadingProgress() {

    }

    override fun hideImageLoadingProgress() {

    }

    override fun imagesLoadFailed() {
        Toast.makeText(context, getString(R.string.warning_image_load_failed), Toast.LENGTH_LONG)
    }

    override fun imagesLoaded(images: List<ImageUIModel>) {
        imageListAdapter.notifyNewImages(images)
    }

    override fun hideLoadMoreProgress() {
        endlessRecyclerViewScrollListener.newDataLoaded()
        loadableRecycler.hideBottomLoadingProgress()
    }
}
