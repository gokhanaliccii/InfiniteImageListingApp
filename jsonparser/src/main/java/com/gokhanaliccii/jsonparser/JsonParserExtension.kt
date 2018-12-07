package com.gokhanaliccii.jsonparser

fun <T> String.jsonTo(clazz: Class<T>): T = JsonParser().parse(this, clazz)
