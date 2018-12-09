package com.gokhanaliccii.infiniteimagelisting.ui.images

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gokhanaliccii.infiniteimagelisting.R
import com.gokhanaliccii.infiniteimagelisting.common.recyclerview.EndlessRecyclerViewScrollListener
import com.gokhanaliccii.infiniteimagelisting.ui.images.adapter.ImageListAdapter
import com.gokhanaliccii.infiniteimagelisting.widget.LoadableRecyclerView
import kotlinx.android.synthetic.main.view_loadable_recyclerview.*

class ImageListFragment : Fragment() {

    companion object {
        const val COLUMN_COUNT = 2

        fun newInstance(): ImageListFragment = ImageListFragment()
    }

    private lateinit var loadableRecycler: LoadableRecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_list, container, false)

        initUi(view)

        return  view
    }

    private fun initUi(view: View) {
        loadableRecycler = view.findViewById(R.id.recyclerview_image)

        val imageListAdapter = ImageListAdapter(emptyList())
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


}