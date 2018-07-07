package com.github.shiguruikai.combinatoricskt

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PermutationWithRepetitionGeneratorTest {

    private val emptyList = emptyList<Any>()
    private val emptyArray = emptyArray<Any>()

    @Test
    fun test_permutationsWithRepetition_empty() {
        assertEquals("[[]]", emptyList.permutationsWithRepetition(0).toList().toString())
        assertEquals("[]", emptyList.permutationsWithRepetition(100).toList().toString())
        assertIterableEquals(listOf(emptyList), emptyList.permutationsWithRepetition(0).toList())
        assertIterableEquals(emptyList, emptyList.permutationsWithRepetition(100).toList())

        assertEquals("[[]]", emptyArray.permutationsWithRepetition(0).toList().map { it.toList() }.toString())
        assertEquals("[]", emptyArray.permutationsWithRepetition(100).toList().map { it.toList() }.toString())
        assertArrayEquals(arrayOf(emptyArray), emptyArray.permutationsWithRepetition(0).toList().toTypedArray())
        assertArrayEquals(emptyArray, emptyArray.permutationsWithRepetition(100).toList().toTypedArray())
    }

    @Test
    fun test_permutationsWithRepetition_exception() {
        assertThrows<IllegalArgumentException> { ('a'..'c').permutationsWithRepetition(-1) }
        assertThrows<IllegalArgumentException> { emptyList.permutationsWithRepetition(-1) }
    }

    @Test
    fun test_permutationsWithRepetition() {
        assertEquals(
                "[[]]",
                (0..2).permutationsWithRepetition(0).toList().toString())
        assertEquals(
                "[[0], [1], [2]]",
                (0..2).permutationsWithRepetition(1).toList().toString())
        assertEquals(
                "[[0, 0], [0, 1], [0, 2], [1, 0], [1, 1], [1, 2], [2, 0], [2, 1], [2, 2]]",
                (0..2).permutationsWithRepetition(2).toList().toString())
        assertEquals("[" +
                "[0, 0, 0], [0, 0, 1], [0, 0, 2], [0, 1, 0], [0, 1, 1], [0, 1, 2], [0, 2, 0], [0, 2, 1], [0, 2, 2], " +
                "[1, 0, 0], [1, 0, 1], [1, 0, 2], [1, 1, 0], [1, 1, 1], [1, 1, 2], [1, 2, 0], [1, 2, 1], [1, 2, 2], " +
                "[2, 0, 0], [2, 0, 1], [2, 0, 2], [2, 1, 0], [2, 1, 1], [2, 1, 2], [2, 2, 0], [2, 2, 1], [2, 2, 2]]",
                (0..2).permutationsWithRepetition(3).toList().toString())
        assertEquals("[" +
                "[0, 0, 0, 0], [0, 0, 0, 1], [0, 0, 0, 2], [0, 0, 1, 0], [0, 0, 1, 1], [0, 0, 1, 2], [0, 0, 2, 0], [0, 0, 2, 1], [0, 0, 2, 2], " +
                "[0, 1, 0, 0], [0, 1, 0, 1], [0, 1, 0, 2], [0, 1, 1, 0], [0, 1, 1, 1], [0, 1, 1, 2], [0, 1, 2, 0], [0, 1, 2, 1], [0, 1, 2, 2], " +
                "[0, 2, 0, 0], [0, 2, 0, 1], [0, 2, 0, 2], [0, 2, 1, 0], [0, 2, 1, 1], [0, 2, 1, 2], [0, 2, 2, 0], [0, 2, 2, 1], [0, 2, 2, 2], " +
                "[1, 0, 0, 0], [1, 0, 0, 1], [1, 0, 0, 2], [1, 0, 1, 0], [1, 0, 1, 1], [1, 0, 1, 2], [1, 0, 2, 0], [1, 0, 2, 1], [1, 0, 2, 2], " +
                "[1, 1, 0, 0], [1, 1, 0, 1], [1, 1, 0, 2], [1, 1, 1, 0], [1, 1, 1, 1], [1, 1, 1, 2], [1, 1, 2, 0], [1, 1, 2, 1], [1, 1, 2, 2], " +
                "[1, 2, 0, 0], [1, 2, 0, 1], [1, 2, 0, 2], [1, 2, 1, 0], [1, 2, 1, 1], [1, 2, 1, 2], [1, 2, 2, 0], [1, 2, 2, 1], [1, 2, 2, 2], " +
                "[2, 0, 0, 0], [2, 0, 0, 1], [2, 0, 0, 2], [2, 0, 1, 0], [2, 0, 1, 1], [2, 0, 1, 2], [2, 0, 2, 0], [2, 0, 2, 1], [2, 0, 2, 2], " +
                "[2, 1, 0, 0], [2, 1, 0, 1], [2, 1, 0, 2], [2, 1, 1, 0], [2, 1, 1, 1], [2, 1, 1, 2], [2, 1, 2, 0], [2, 1, 2, 1], [2, 1, 2, 2], " +
                "[2, 2, 0, 0], [2, 2, 0, 1], [2, 2, 0, 2], [2, 2, 1, 0], [2, 2, 1, 1], [2, 2, 1, 2], [2, 2, 2, 0], [2, 2, 2, 1], [2, 2, 2, 2]]",
                (0..2).permutationsWithRepetition(4).toList().toString())
        assertEquals(
                (0..2).cartesianProduct(repeat = 4).toList(),
                (0..2).permutationsWithRepetition(4).toList())
        assertEquals(
                (0..2).permutationsWithRepetition(4).toList(),
                (0..2).cartesianProduct((0..2), (0..2), (0..2)).toList())
    }
}
