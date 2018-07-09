package com.github.shiguruikai.combinatoricskt

import com.github.shiguruikai.combinatoricskt.internal.permutations
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.coroutines.experimental.buildSequence

internal class PermutationGeneratorTest {

    private val emptyList = emptyList<Any>()
    private val emptyArray = emptyArray<Any>()

    @Test
    fun test_permutations_empty() {
        assertEquals("[[]]", emptyList.permutations().toList().toString())
        assertEquals("[[]]", emptyList.permutations(0).toList().toString())
        assertEquals("[]", emptyList.permutations(100).toList().toString())
        assertIterableEquals(listOf(emptyList), emptyList.permutations().toList())
        assertIterableEquals(listOf(emptyList), emptyList.permutations(0).toList())
        assertIterableEquals(emptyList, emptyList.permutations(100).toList())

        assertEquals("[[]]", emptyArray.permutations().toList().map { it.toList() }.toString())
        assertEquals("[[]]", emptyArray.permutations(0).toList().map { it.toList() }.toString())
        assertEquals("[]", emptyArray.permutations(100).toList().map { it.toList() }.toString())
        assertArrayEquals(arrayOf(emptyArray), emptyArray.permutations().toList().toTypedArray())
        assertArrayEquals(arrayOf(emptyArray), emptyArray.permutations(0).toList().toTypedArray())
        assertArrayEquals(emptyArray, emptyArray.permutations(100).toList().toTypedArray())
    }

    @Test
    fun test_permutations_exception() {
        assertThrows<IllegalArgumentException> { ('a'..'c').permutations(-1).toList() }
        assertThrows<IllegalArgumentException> { emptyList.permutations(-1).toList() }
    }

    @Test
    fun test_permutations() {

        assertEquals(
                (0..2).permutations().toList().toString(),
                (0..2).permutations((0..2).count()).toList().toString())

        val array = arrayOf(0, 1, 2)
        val expected = listOf(
                "[[]]",
                "[[0], [1], [2]]",
                "[[0, 1], [0, 2], [1, 0], [1, 2], [2, 0], [2, 1]]",
                "[[0, 1, 2], [0, 2, 1], [1, 0, 2], [1, 2, 0], [2, 0, 1], [2, 1, 0]]",
                "[]")
        expected.forEachIndexed { index, it ->
            assertEquals(it, array.toList().permutations(index).toList().toString())
            assertEquals(it, array.permutations(index).map { it.toList() }.toList().toString())
        }

        // permutations() は permutationsWithRepetition() のシーケンスから、
        // 重複 （入力プールの位置に応じた順序で）を除くようフィルタリングしたものと同じ
        fun <T> Iterable<T>.permutations2(length: Int? = null): Sequence<List<T>> = buildSequence {
            val pool = toList()
            val r = length ?: pool.size
            pool.indices.permutationsWithRepetition(r)
                    .filter { it.toSet().size == r }
                    .forEach { yield(it.map { pool[it] }) }
        }

        val comparator = Comparator<List<Int>> { o1, o2 -> Arrays.compare(o1.toTypedArray(), o2.toTypedArray()) }

        for (n in 0 until 7) {
            val values = (0 until n).map { 5 * it - 12 }
            for (r in 0 until n + 1) {
                val result = values.permutations(r).toList()

                assertEquals(result, values.toTypedArray().permutations(r).map { it.toList() }.toList())
                assertEquals(result, PermutationGenerator.indices(n, r).map { it.map { values[it] } }.toList())

                assertEquals(result.count(), if (r > n) 0 else permutations(n, r).intValueExact())
                assertEquals(result.count(), result.toSet().count())
                assertEquals(result, result.sortedWith(comparator))

                for (p in result) {
                    assertEquals(p.count(), r)
                    assertEquals(p.toSet().count(), r)
                    assertTrue(p.all { it in values })
                }

                assertEquals(result, values.permutations2(r).toList())

                if (r == n) {
                    assertEquals(result, values.permutations(null).toList())
                    assertEquals(result, values.permutations().toList())
                }
            }
        }
    }
}
