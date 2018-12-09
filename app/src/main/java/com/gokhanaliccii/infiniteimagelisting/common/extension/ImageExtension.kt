package com.gokhanaliccii.infiniteimagelisting.common.extension

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Looper
import android.support.v4.widget.ImageViewCompat
import android.widget.ImageView
import com.gokhanaliccii.infiniteimagelisting.R
import com.gokhanaliccii.infiniteimagelisting.common.cache.ImageCache
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


private val executorService: ExecutorService by lazy { Executors.newFixedThreadPool(10) }
private val imageCache: ImageCache  by lazy { ImageCache() }

fun ImageView.loadImage(url: String) {
    tag = url
    val bitmapFromCache = imageCache.getBitmapFromMemCache(url)
    if (bitmapFromCache != null) {
        setImageBitmap(bitmapFromCache)
    } else {
        loadAndCacheImages(this, url)
    }
}

private fun loadAndCacheImages(imageView: ImageView, url: String) {
    executorService.submit {
        try {
            val urlConnection = URL(url).openConnection()
            urlConnection.connect()

            val bitmap = BitmapFactory.decodeStream(urlConnection.inputStream)
            urlConnection.inputStream.close()
            imageCache.addBitmapToMemoryCache(url, bitmap)

            if (imageView.tag == url) {//ensure not reused
                android.os.Handler(Looper.getMainLooper()).post {
                    imageView.setImageBitmap(bitmap)
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
