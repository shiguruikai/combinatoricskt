/*
 * Copyright (c) 2020 shiguruikai
 *
 * Licensed under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DerangementGeneratorTest {

    private val emptyList = emptyList<Any>()
    private val emptyArray = emptyArray<Any>()

    @Test
    fun test_derangements_empty() {
        assertEquals("[[]]", emptyList.derangements().toList().toString())
        assertEquals("[[]]", emptyArray.derangements().map { it.toList() }.toList().toString())

    }

    @Test
    fun test_derangements() {
        val array = arrayOf(0, 1, 2, 3)
        val expected = listOf(
                "[[]]",
                "[]",
                "[[1, 0]]",
                "[[1, 2, 0], [2, 0, 1]]",
                "[[1, 0, 3, 2], [1, 2, 3, 0], [1, 3, 0, 2], [2, 0, 3, 1], [2, 3, 0, 1], [2, 3, 1, 0], [3, 0, 1, 2], [3, 2, 0, 1], [3, 2, 1, 0]]")

        expected.forEachIndexed { index, it ->
            assertEquals(it, DerangementGenerator.indices(index).map { it.toList() }.toList().toString())
            assertEquals(it, array.take(index).derangements().toList().toString())
            assertEquals(it, array.copyOf(index).derangements().map { it.toList() }.toList().toString())
        }

        val range = 0..6
        for (r in range) {
            val x = range.take(r)
            assertEquals(
                    x.permutations().filter { list -> var i = 0; list.all { it != i++ } }.toList(),
                    x.derangements().toList())
            assertEquals(x.derangements().totalSize, x.derangements().toList().size.toBigInteger())
        }
    }
}
