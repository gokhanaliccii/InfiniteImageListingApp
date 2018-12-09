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
import com.gokhanaliccii.infiniteimagelisting.datasource.image.remote.Image

class ImageListAdapter(private var images: List<ImageUIModel>) : Adapter<ImageListAdapter.ImageViewHolder>() {

    fun notify2(images: List<ImageUIModel>){
        this.images=images
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(vg: ViewGroup, p1: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(vg.context)
        val view = inflater.inflate(R.layout.list_item_image, vg, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(viewHolder: ImageViewHolder, index: Int) {
        viewHolder.imageView.loadImage(images[index].imageUrl)
    }

    inner class ImageViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val imageView: ImageView = item as ImageView
    }
}