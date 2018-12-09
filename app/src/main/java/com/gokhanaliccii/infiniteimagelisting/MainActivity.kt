package com.gokhanaliccii.infiniteimagelisting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.gokhanaliccii.infiniteimagelisting.common.recyclerview.EndlessRecyclerViewScrollListener
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageDataSource
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel
import com.gokhanaliccii.infiniteimagelisting.ui.images.adapter.ImageListAdapter
import com.gokhanaliccii.infiniteimagelisting.widget.LoadableRecyclerView
import kotlinx.android.synthetic.main.view_loadable_recyclerview.*

class MainActivity : AppCompatActivity() {

    private val loadableRecycler by lazy { findViewById<LoadableRecyclerView>(R.id.recyclerview_image) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val imageListAdapter = ImageListAdapter(emptyList())
        val layoutManager = GridLayoutManager(this, 2)
        val endlessRecyclerViewScrollListener = EndlessRecyclerViewScrollListener(layoutManager) {
            loadableRecycler.showBottomLoadingProgress()
        }

        loadableRecycler.initRecyclerView {
            recyclerview.adapter = imageListAdapter
            recyclerview.layoutManager = layoutManager
            recyclerview.addOnScrollListener(endlessRecyclerViewScrollListener)
        }

        InfiniteImageListingApp.instance.imageDataSource()
            .loadImages(loadCallBack = object : ImageDataSource.ImageLoadCallBack {

                override fun onImagesLoaded(images: List<ImageUIModel>) {
                    imageListAdapter.notify2(images)
                }
            })


    }
}