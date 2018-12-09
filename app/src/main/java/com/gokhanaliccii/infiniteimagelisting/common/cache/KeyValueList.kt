package com.gokhanaliccii.infiniteimagelisting.common.cache

interface KeyValueList<K, V> {

    fun storeItems(key: K, values: List<V>)

    fun getItems(key: K): List<V>?
}