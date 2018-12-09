package com.gokhanaliccii.infiniteimagelisting.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.gokhanaliccii.infiniteimagelisting.R

class LoadableRecyclerView(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    val recyclerView: RecyclerView
    val bottomLoadingProgress: View

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loadable_recyclerview, this, true)
        bottomLoadingProgress = findViewById(R.id.loading_bottom)
        recyclerView = findViewById(R.id.recyclerview)
    }

    fun initRecyclerView(init: () -> Unit) {
        init()
    }

    fun showBottomLoadingProgress() {
        bottomLoadingProgress.visibility = View.VISIBLE
    }

}