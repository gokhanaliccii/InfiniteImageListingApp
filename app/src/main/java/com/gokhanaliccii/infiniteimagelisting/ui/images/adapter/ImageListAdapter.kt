package com.gokhanaliccii.infiniteimagelisting.ui.images.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gokhanaliccii.infiniteimagelisting.R
import com.gokhanaliccii.infiniteimagelisting.datasource.image.Image

class ImageListAdapter(private val images: List<Image>) : Adapter<ImageListAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(vg: ViewGroup, p1: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(vg.context)
        val view = inflater.inflate(R.layout.list_item_image, vg, false)

        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(viewHolder: ImageViewHolder, index: Int) {
        viewHolder.text.text = images[index].name
    }

    inner class ImageViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val text: TextView = item as TextView
    }
}