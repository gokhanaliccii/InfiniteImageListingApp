package com.gokhanaliccii.infiniteimagelisting.datasource.image.remote

import com.gokhanaliccii.infiniteimagelisting.datasource.image.ImageUIModel
import com.gokhanaliccii.jsonparser.annotation.JsonObject

class Image {
    var id: String? = null
    @JsonObject(Url::class)
    var urls: Url? = null
    @JsonObject(User::class)
    var user: User? = null
}

class Url {
    var thumb: String? = null
    var small: String? = null
    var regular: String? = null
}

class User {
    var id: String? = null
    var name: String? = null
}

fun Image.toIUModel(): ImageUIModel {
    val uiModel = ImageUIModel()
    id?.let { uiModel.id = it }
    urls?.small?.let { uiModel.imageUrl = it }
    user?.name?.let { uiModel.imageOwner = it }
    return uiModel
}