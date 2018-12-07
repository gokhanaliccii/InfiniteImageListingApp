package com.gokhanaliccii.jsonparser

fun <T> String.fromJson(clazz: Class<T>): T = JsonParser().parse(this, clazz)

