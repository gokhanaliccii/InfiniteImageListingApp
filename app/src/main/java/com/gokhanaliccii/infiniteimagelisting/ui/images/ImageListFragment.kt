package com.gokhanaliccii.infiniteimagelisting.ui.images

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gokhanaliccii.infiniteimagelisting.InfiniteImageListingApp
import com.gokhanaliccii.infiniteimagelisting.R
import com.gokhanaliccii.infiniteimagelisting.common.recyclerview.EndlessRecyclerViewScrollListener
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel
import com.gokhanaliccii.infiniteimagelisting.ui.images.adapter.ImageListAdapter
import com.gokhanaliccii.infiniteimagelisting.widget.LoadableRecyclerView
import kotlinx.android.synthetic.main.view_loadable_recyclerview.*

class ImageListFragment : Fragment(), ImageListContract.View {

    companion object {
        const val COLUMN_COUNT = 2
        fun newInstance(): ImageListFragment = ImageListFragment()
    }

    private lateinit var loadableRecycler: LoadableRecyclerView
    private lateinit var presenter: ImageListPresenter
    private val imageListAdapter by lazy { ImageListAdapter(emptyList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ImageListPresenter(this, InfiniteImageListingApp.instance.imageDataSource())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter.loadImages()
    }

    private fun initUi(view: View) {
        loadableRecycler = view.findViewById(R.id.recyclerview_image)

        val layoutManager = GridLayoutManager(context, COLUMN_COUNT)
        val endlessRecyclerViewScrollListener = EndlessRecyclerViewScrollListener(layoutManager) {
            loadableRecycler.showBottomLoadingProgress()
        }


        loadableRecycler.initRecyclerView {
            recyclerview.adapter = imageListAdapter
            recyclerview.layoutManager = layoutManager
            recyclerview.addOnScrollListener(endlessRecyclerViewScrollListener)
        }
    }

    override fun showImagesLoadingProgress() {

    }

    override fun hideImageLoadimgProgess() {

    }

    override fun imagesLoaded(images: List<ImageUIModel>) {
        imageListAdapter.notify2(images)
    }
}