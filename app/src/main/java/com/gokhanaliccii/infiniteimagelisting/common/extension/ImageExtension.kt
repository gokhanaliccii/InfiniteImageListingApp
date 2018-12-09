package com.gokhanaliccii.infiniteimagelisting.common.extension

import android.graphics.BitmapFactory
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private val executorService: ExecutorService by lazy { Executors.newFixedThreadPool(10) }

fun ImageView.loadImage(url: String) {
    executorService.submit {
        try {
            Log.i("ImageLoader", url)

            val url = URL(url)
            val urlConnection = url.openConnection()
            urlConnection.connect()
            val bitmap = BitmapFactory.decodeStream(urlConnection.inputStream)
            urlConnection.inputStream.close()

            android.os.Handler(Looper.getMainLooper()).post {
                setImageBitmap(bitmap)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}