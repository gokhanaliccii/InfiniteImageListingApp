package com.gokhanaliccii.infiniteimagelisting.datasource.image

import com.gokhanaliccii.infiniteimagelisting.common.cache.KeyValueList

class ImageUIModelStore : KeyValueList<Int, ImageUIModel> {

    private val keyUIModelListMap: MutableMap<Int, List<ImageUIModel>> by lazy { mutableMapOf<Int, List<ImageUIModel>>() }

    override fun storeItems(key: Int, values: List<ImageUIModel>) {
        keyUIModelListMap[key] = values
    }

    override fun getItems(key: Int): List<ImageUIModel>? = keyUIModelListMap[key]
}