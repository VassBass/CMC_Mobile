package com.vassbassapp.cmc_mobile.model

import org.junit.Assert.*

import org.junit.Test

class ControlTest {

    private val testModel = Control(8, 9, "name", "23.02.2022")

    @Test
    fun testGetLocalId() {
        val expected = 8

        assertEquals(expected, testModel.localId)
    }

    @Test
    fun getServerId() {
        val expected = 9

        assertEquals(expected, testModel.serverId)
    }

    @Test
    fun getName() {
        val expected = "name"

        assertEquals(expected, testModel.name)
    }

    @Test
    fun getDate() {
        val expected = "23.02.2022"

        assertEquals(expected, testModel.date)
    }

    @Test
    fun copy() {
        val expected = testModel.copy()

        assertEquals(expected, testModel)
    }

    @Test
    fun testToString() {
        val expected = "Control(localId=8, serverId=9, name=name, date=23.02.2022)"

        assertEquals(expected, testModel.toString())
    }

    @Test
    fun testHashCode() {
        val expected = Control(8, 9, "name", "23.02.2022")

        assertEquals(expected.hashCode(), testModel.hashCode())
    }

    @Test
    fun testEquals() {
        val expected = Control(8, 9, "name", "23.02.2022")

        assertTrue(expected.equals(testModel))
    }
}