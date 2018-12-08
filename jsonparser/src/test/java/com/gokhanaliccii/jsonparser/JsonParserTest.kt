package com.gokhanaliccii.jsonparser

import com.gokhanaliccii.jsonparser.annotation.JsonList
import com.gokhanaliccii.jsonparser.annotation.JsonObject
import org.junit.Assert.*
import org.junit.Test

class JsonParserTest {

    @Test
    fun shouldParsedObjectNotNull() {
        val input = "{\"name\":\"gokhan\",\"age\":1}"
        val person = input.jsonTo(NamedPerson::class.java)

        assertNotNull(person)
    }

    @Test
    fun shouldParseStringFieldCorrectly() {
        val expectedName = "gokhan"
        val input = "{\"name\":\"gokhan\",\"age\":1}"
        val person = input.jsonTo(NamedPerson::class.java)

        assertTrue(expectedName == person?.name)
    }

    @Test
    fun shouldParseJsonCorrectly() {
        val expectedFrientCount = 1
        val input = "{\"name\":\"gokhan\",\"age\":1,\"friends\":[{\"name\":\"ahmet\"}]}"
        val student = input.jsonTo(Student::class.java)

        assertTrue(expectedFrientCount == student?.friends?.size)
    }

    @Test
    fun shouldParseInnerObjectCorrectly() {
        val expectedFriendName = "ahmet"
        val input = "{\"name\":\"gokhan\",\"age\":1,\"friend\":{\"name\":\"ahmet\"}}"
        val student = input.jsonTo(SingleStudent::class.java)

        assertTrue(expectedFriendName == student?.friend?.name)
    }

    @Test
    fun shouldParseListObjectCorrectly() {
        val expectedFriendName = "gokhan"
        val input = "[{\"name\":\"gokhan\",\"age\":1}]"
        val student = input.jsonToList(NamedPerson::class.java)

        assertTrue(expectedFriendName == student?.first().name)
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

    class SingleStudent {
        var age: Int = 0
        lateinit var name: String

        @JsonObject(Friend::class)
        lateinit var friend: Friend
    }

    class NamedPerson {
        lateinit var name: String
    }
}