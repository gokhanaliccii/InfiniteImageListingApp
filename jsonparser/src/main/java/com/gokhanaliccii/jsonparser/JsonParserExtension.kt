package com.gokhanaliccii.jsonparser

fun <T> String.jsonTo(clazz: Class<T>): T = JsonParser().parse(this, clazz)

fun <T> String.jsonToList(clazz: Class<T>): List<T> = JsonParser().parseList(this, clazz)
