package com.gokhanaliccii.jsonparser

class JsonParser {

    fun <T> parse(json: String, clazz: Class<T>): T? {
        clazz.constructors

        return clazz.classLoader.loadClass(clazz.name).newInstance()  as T
    }


}