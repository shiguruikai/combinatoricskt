/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT License.
 * see https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

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
        val array = arrayOf(0, 1, 2)
        val expected = listOf(
                "[[]]",
                "[[0], [1], [2]]",
                "[[0, 0], [0, 1], [0, 2], [1, 0], [1, 1], [1, 2], [2, 0], [2, 1], [2, 2]]",
                "[" +
                        "[0, 0, 0], [0, 0, 1], [0, 0, 2], [0, 1, 0], [0, 1, 1], [0, 1, 2], [0, 2, 0], [0, 2, 1], [0, 2, 2], " +
                        "[1, 0, 0], [1, 0, 1], [1, 0, 2], [1, 1, 0], [1, 1, 1], [1, 1, 2], [1, 2, 0], [1, 2, 1], [1, 2, 2], " +
                        "[2, 0, 0], [2, 0, 1], [2, 0, 2], [2, 1, 0], [2, 1, 1], [2, 1, 2], [2, 2, 0], [2, 2, 1], [2, 2, 2]]",
                "[" +
                        "[0, 0, 0, 0], [0, 0, 0, 1], [0, 0, 0, 2], [0, 0, 1, 0], [0, 0, 1, 1], [0, 0, 1, 2], [0, 0, 2, 0], [0, 0, 2, 1], [0, 0, 2, 2], " +
                        "[0, 1, 0, 0], [0, 1, 0, 1], [0, 1, 0, 2], [0, 1, 1, 0], [0, 1, 1, 1], [0, 1, 1, 2], [0, 1, 2, 0], [0, 1, 2, 1], [0, 1, 2, 2], " +
                        "[0, 2, 0, 0], [0, 2, 0, 1], [0, 2, 0, 2], [0, 2, 1, 0], [0, 2, 1, 1], [0, 2, 1, 2], [0, 2, 2, 0], [0, 2, 2, 1], [0, 2, 2, 2], " +
                        "[1, 0, 0, 0], [1, 0, 0, 1], [1, 0, 0, 2], [1, 0, 1, 0], [1, 0, 1, 1], [1, 0, 1, 2], [1, 0, 2, 0], [1, 0, 2, 1], [1, 0, 2, 2], " +
                        "[1, 1, 0, 0], [1, 1, 0, 1], [1, 1, 0, 2], [1, 1, 1, 0], [1, 1, 1, 1], [1, 1, 1, 2], [1, 1, 2, 0], [1, 1, 2, 1], [1, 1, 2, 2], " +
                        "[1, 2, 0, 0], [1, 2, 0, 1], [1, 2, 0, 2], [1, 2, 1, 0], [1, 2, 1, 1], [1, 2, 1, 2], [1, 2, 2, 0], [1, 2, 2, 1], [1, 2, 2, 2], " +
                        "[2, 0, 0, 0], [2, 0, 0, 1], [2, 0, 0, 2], [2, 0, 1, 0], [2, 0, 1, 1], [2, 0, 1, 2], [2, 0, 2, 0], [2, 0, 2, 1], [2, 0, 2, 2], " +
                        "[2, 1, 0, 0], [2, 1, 0, 1], [2, 1, 0, 2], [2, 1, 1, 0], [2, 1, 1, 1], [2, 1, 1, 2], [2, 1, 2, 0], [2, 1, 2, 1], [2, 1, 2, 2], " +
                        "[2, 2, 0, 0], [2, 2, 0, 1], [2, 2, 0, 2], [2, 2, 1, 0], [2, 2, 1, 1], [2, 2, 1, 2], [2, 2, 2, 0], [2, 2, 2, 1], [2, 2, 2, 2]]")

        expected.forEachIndexed { index, it ->
            assertEquals(it, array.toList().permutationsWithRepetition(index).toList().toString())
            assertEquals(it, array.permutationsWithRepetition(index).map { it.toList() }.toList().toString())
            assertEquals(it, PermutationWithRepetitionGenerator.indices(array.size, index).map { it.map { array[it] } }.toList().toString())
        }

        assertArrayEquals(
                array.cartesianProduct(repeat = 4).toList().toTypedArray(),
                array.permutationsWithRepetition(4).toList().toTypedArray())
        assertArrayEquals(
                array.permutationsWithRepetition(4).toList().toTypedArray(),
                array.cartesianProduct(array, array, array).toList().toTypedArray())
    }
}
