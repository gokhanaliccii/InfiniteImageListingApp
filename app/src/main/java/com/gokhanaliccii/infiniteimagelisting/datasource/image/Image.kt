package com.gokhanaliccii.infiniteimagelisting.datasource.image

import com.gokhanaliccii.jsonparser.annotation.JsonObject

class Image {
    lateinit var id: String
    @JsonObject(Url::class)
    lateinit var urls: Url
    @JsonObject(User::class)
    lateinit var user: User
}

class Url {
    lateinit var thumb: String
    lateinit var small: String
    lateinit var regular: String
}

class User {
    lateinit var id: String
    lateinit var name: String
}