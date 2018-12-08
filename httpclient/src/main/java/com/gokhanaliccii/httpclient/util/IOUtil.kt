package com.gokhanaliccii.httpclient.util

import java.io.Closeable
import java.io.InputStream
import java.io.OutputStream

fun pipe(inputStream: InputStream, outputStream: OutputStream) {
        inputStream.copyTo(outputStream)
}

fun closeStreams(vararg streams: Closeable) {
    streams.forEach(Closeable::close)
}