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
    private val bottomLoadingProgress: View
    private val bottomLoadingError: View

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loadable_recyclerview, this, true)
        bottomLoadingProgress = findViewById(R.id.loading_bottom)
        bottomLoadingError = findViewById(R.id.loading_error)
        recyclerView = findViewById(R.id.recyclerview)
    }

    fun initRecyclerView(init: LoadableRecyclerView.() -> Unit) {
        init()
    }

    fun onFinishClicked(finishFunc: () -> Unit) {
        bottomLoadingError.findViewById<View>(R.id.action_retry).setOnClickListener {
            hideBottomLoadingError()
            finishFunc.invoke()
        }
    }

    fun showBottomLoadingProgress() {
        bottomLoadingProgress.visibility = View.VISIBLE
    }

    fun hideBottomLoadingProgress() {
        bottomLoadingProgress.visibility = View.GONE
    }

    fun showBottomLoadingError() {
        bottomLoadingError.visibility = View.VISIBLE
    }

    fun hideBottomLoadingError() {
        bottomLoadingError.visibility = View.GONE
    }
}