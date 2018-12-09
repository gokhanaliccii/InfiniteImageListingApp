package com.gokhanaliccii.infiniteimagelisting.common.extension

import android.graphics.BitmapFactory
import android.os.Looper
import android.widget.ImageView
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