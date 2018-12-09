package com.gokhanaliccii.infiniteimagelisting.common.cache

import android.graphics.Bitmap
import android.support.v4.util.LruCache

class ImageCache {

    private val urlBitmapLruCache: LruCache<String, Bitmap> by lazy {
        initMemoryCache(findCacheSize())
    }

    private fun findCacheSize(): Int {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        return maxMemory / 8
    }

    fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            urlBitmapLruCache.put(key, bitmap)
        }
    }

    fun getBitmapFromMemCache(key: String): Bitmap? {
        return urlBitmapLruCache.get(key)
    }

    private fun initMemoryCache(cacheSize: Int): LruCache<String, Bitmap> {
        return object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.byteCount
            }
        }
    }
}