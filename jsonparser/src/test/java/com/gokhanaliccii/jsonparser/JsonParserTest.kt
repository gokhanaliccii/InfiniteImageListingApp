package com.gokhanaliccii.jsonparser

import org.junit.Assert.*
import org.junit.Test
import java.lang.reflect.Constructor

class JsonParserTest {

    @Test
    fun shouldParsedObjectNotNull() {
        val input = "{\"name\":\"gokhan\",\"age\":1}"
        val person = JsonParser().parse(input, Person::class.java)

        assertNotNull(person)
    }

     class Person

}