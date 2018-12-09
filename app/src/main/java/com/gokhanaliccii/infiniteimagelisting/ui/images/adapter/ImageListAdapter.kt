package com.gokhanaliccii.infiniteimagelisting.ui.images.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.gokhanaliccii.infiniteimagelisting.R
import com.gokhanaliccii.infiniteimagelisting.common.extension.loadImage
import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel
import com.gokhanaliccii.infiniteimagelisting.ui.images.adapter.ImageListAdapter.ImageViewHolder

class ImageListAdapter(private var images: MutableList<ImageUIModel>) : Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(vg: ViewGroup, index: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(vg.context)
        val view = inflater.inflate(R.layout.list_item_image, vg, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(viewHolder: ImageViewHolder, index: Int) {
        viewHolder.imageView.loadImage(images[index].imageUrl)
    }

    fun notifyNewImages(images: List<ImageUIModel>) {
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    class ImageViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val imageView: ImageView = item as ImageView
    }
}