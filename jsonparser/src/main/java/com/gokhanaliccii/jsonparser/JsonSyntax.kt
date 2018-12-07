package com.gokhanaliccii.jsonparser


const val JSON_OBJECT_START = "{"
const val JSON_OBJECT_END = "}"
const val JSON_ARRAY_START = "["
const val JSON_ARRAY_END = "]"

internal fun String.hasValidSyntax(): Boolean {
    return startsWith(JSON_ARRAY_START) && endsWith(JSON_ARRAY_END)
            || startsWith(JSON_OBJECT_START) && endsWith(JSON_OBJECT_END)
}

internal fun String.isJsonObjectStart(): Boolean {
    return startsWith(JSON_OBJECT_START)
}

internal fun String.isJsonArrayStart(): Boolean {
    return startsWith(JSON_ARRAY_START)
}
