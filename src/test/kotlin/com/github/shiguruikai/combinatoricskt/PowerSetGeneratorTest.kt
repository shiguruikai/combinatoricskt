package com.github.shiguruikai.combinatoricskt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PowerSetGeneratorTest {

    private val emptyList = emptyList<Any>()
    private val emptyArray = emptyArray<Any>()

    @Test
    fun test_powerset() {
        assertEquals("[[]]", emptyList.powerset().toList().toString())
        assertEquals("[[]]", emptyArray.powerset().toList().map { it.contentToString() }.toString())

        val array = arrayOf(0, 1, 2)
        val expected = listOf(
                "[[]]",
                "[[], [0]]",
                "[[], [0], [1], [0, 1]]",
                "[[], [0], [1], [0, 1], [2], [0, 2], [1, 2], [0, 1, 2]]")
        expected.forEachIndexed { index, it ->
            assertEquals(it, array.take(index).powerset().toList().toString())
            assertEquals(it, array.copyOf(index).powerset().toList().map { it.toList() }.toString())
        }
    }
}
