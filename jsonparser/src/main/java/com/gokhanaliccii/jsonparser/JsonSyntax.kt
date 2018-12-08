package com.gokhanaliccii.jsonparser


const val JSON_OBJECT_START = "{"
const val JSON_OBJECT_END = "}"
const val JSON_ARRAY_START = "["
const val JSON_ARRAY_END = "]"

internal fun String.hasValidObjectSyntax(): Boolean = startsWith(JSON_OBJECT_START) && endsWith(JSON_OBJECT_END)

internal fun String.hasValidArraySyntax(): Boolean = startsWith(JSON_ARRAY_START) && endsWith(JSON_ARRAY_END)