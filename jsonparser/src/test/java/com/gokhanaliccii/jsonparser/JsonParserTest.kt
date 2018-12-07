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

    @Test
    fun shouldParseStringFieldCorrectly() {
        val expectedName = "gokhan"
        val input = "{\"name\":\"gokhan\",\"age\":1}"
        val person = JsonParser().parse(input, NamedPerson::class.java)

        assertTrue(expectedName == person?.name)
    }

    @Test
    fun shouldParseJsonCorrectly() {
        val expectedName = "gokhan"
        val input = "{\"name\":\"gokhan\",\"age\":1,\"friends\":[{\"name\":\"ahmet\"}]}"
        val student = JsonParser().parse(input, Student::class.java)

        student.name


        assertTrue(expectedName == student?.name)
    }

    class Friend {
        lateinit var name: String
    }

    class Student {
        var age: Int = 0
        lateinit var name: String

        @JsonList(Friend::class)
        lateinit var friends: List<Friend>
    }

    class NamedPerson {
        lateinit var name: String
    }
}