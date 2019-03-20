/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt

import com.github.shiguruikai.combinatoricskt.internal.combinations
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CombinationGeneratorTest {

    private val emptyList = emptyList<Any>()
    private val emptyArray = emptyArray<Any>()

    @Test
    fun test_combinations_empty() {
        assertEquals("[[]]", emptyList.combinations(0).toList().toString())
        assertEquals("[]", emptyList.combinations(100).toList().toString())
        assertIterableEquals(listOf(emptyList), emptyList.combinations(0).toList())
        assertIterableEquals(emptyList, emptyList.combinations(100).toList())

        assertEquals("[[]]", emptyArray.combinations(0).toList().map { it.toList() }.toString())
        assertEquals("[]", emptyArray.combinations(100).toList().map { it.toList() }.toString())
        assertArrayEquals(arrayOf(emptyArray), emptyArray.combinations(0).toList().toTypedArray())
        assertArrayEquals(emptyArray, emptyArray.combinations(100).toList().toTypedArray())
    }

    @Test
    fun test_combinations_exception() {
        assertThrows<IllegalArgumentException> { ('a'..'c').combinations(-1) }
        assertThrows<IllegalArgumentException> { emptyList.combinations(-1) }
    }

    @Test
    fun test_combinations() {
        val array = arrayOf(0, 1, 2)
        val expected = listOf(
                "[[]]",
                "[[0], [1], [2]]",
                "[[0, 1], [0, 2], [1, 2]]",
                "[[0, 1, 2]]",
                "[]")

        expected.forEachIndexed { index, it ->
            assertEquals(it, array.toList().combinations(index).toList().toString())
            assertEquals(it, array.combinations(index).map { it.toList() }.toList().toString())
            assertEquals(it, CombinationGenerator.indices(array.size, index).map { ints -> ints.map { array[it] } }.toList().toString())
        }

        // combinations() は permutations() のシーケンスから、
        // 要素が（入力プールの位置に応じた順序で）ソート順になっていない項目をフィルタリングしたものと同じ
        fun <T> Iterable<T>.combinations2(length: Int): Sequence<List<T>> = sequence {
            val pool = toList()
            pool.indices.permutations(length)
                    .filter { it.sorted() == it }
                    .forEach { list -> yield(list.map { pool[it] }) }
        }

        fun <T> Iterable<T>.combinations3(length: Int): Sequence<List<T>> = sequence {
            val pool = toList()
            pool.indices.combinationsWithRepetition(length)
                    .filter { it.toSet().size == length }
                    .forEach { list -> yield(list.map { pool[it] }) }
        }

        val comparator = ListComparator<Int>()

        for (n in 0 until 7) {
            val values = (0 until n).map { 5 * it - 12 }
            for (r in 0 until n + 1) {
                val result = values.combinations(r).toList()

                assertEquals(result, values.toTypedArray().combinations(r).map { it.toList() }.toList())
                assertEquals(result, CombinationGenerator.indices(n, r).map { ints -> ints.map { values[it] } }.toList())

                assertEquals(result.count(), if (r > n) 0 else combinations(n, r).intValueExact())
                assertEquals(result.count(), result.toSet().count())
                assertEquals(result, result.sortedWith(comparator))

                for (c in result) {
                    assertEquals(c.count(), r)
                    assertEquals(c.toSet().count(), r)
                    assertEquals(c, c.sorted())
                    assertTrue(c.all { it in values })
                    assertEquals(c, values.filter { it in c })
                }

                assertEquals(result, values.combinations2(r).toList())
                assertEquals(result, values.combinations3(r).toList())
            }
        }
    }
}

