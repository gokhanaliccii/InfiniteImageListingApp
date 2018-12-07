package com.gokhanaliccii.jsonparser

import org.junit.Assert.*
import org.junit.Test

class JsonParserTest {

    @Test
    fun shouldParsedObjectNotNull() {
        val input = "{\"name\":\"gokhan\",\"age\":1}"
        val person = JsonParser().parse(input, NamedPerson::class.java)

        assertNotNull(person)
    }

    class NamedPerson {
        lateinit var name: String
    }


}