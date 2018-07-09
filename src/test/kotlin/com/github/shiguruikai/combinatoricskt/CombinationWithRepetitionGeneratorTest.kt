package com.github.shiguruikai.combinatoricskt

import com.github.shiguruikai.combinatoricskt.internal.combinationsWithRepetition
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger
import java.util.*
import kotlin.coroutines.experimental.buildSequence

internal class CombinationWithRepetitionGeneratorTest {

    private val emptyList = emptyList<Any>()
    private val emptyArray = emptyArray<Any>()

    @Test
    fun test_combinationsWithRepetition_empty() {
        assertEquals("[[]]", emptyList.combinationsWithRepetition(0).toList().toString())
        assertEquals("[]", emptyList.combinationsWithRepetition(100).toList().toString())
        assertIterableEquals(listOf(emptyList), emptyList.combinationsWithRepetition(0).toList())
        assertIterableEquals(emptyList, emptyList.combinationsWithRepetition(100).toList())

        assertEquals("[[]]", emptyArray.combinationsWithRepetition(0).toList().map { it.toList() }.toString())
        assertEquals("[]", emptyArray.combinationsWithRepetition(100).toList().map { it.toList() }.toString())
        assertArrayEquals(arrayOf(emptyArray), emptyArray.combinationsWithRepetition(0).toList().toTypedArray())
        assertArrayEquals(emptyArray, emptyArray.combinationsWithRepetition(100).toList().toTypedArray())
    }

    @Test
    fun test_combinationsWithRepetition_exception() {
        assertThrows<IllegalArgumentException> { ('a'..'c').combinationsWithRepetition(-1).toList() }
        assertThrows<IllegalArgumentException> { emptyList.combinationsWithRepetition(-1).toList() }
    }

    @Test
    fun test_combinationsWithRepetition() {
        assertEquals(
                "[[]]",
                (0..2).combinationsWithRepetition(0).toList().toString())
        assertEquals(
                "[[0], [1], [2]]",
                (0..2).combinationsWithRepetition(1).toList().toString())
        assertEquals(
                "[[0, 0], [0, 1], [0, 2], [1, 1], [1, 2], [2, 2]]",
                (0..2).combinationsWithRepetition(2).toList().toString())
        assertEquals("[" +
                "[0, 0, 0], [0, 0, 1], [0, 0, 2], [0, 1, 1], [0, 1, 2], " +
                "[0, 2, 2], [1, 1, 1], [1, 1, 2], [1, 2, 2], [2, 2, 2]]",
                (0..2).combinationsWithRepetition(3).toList().toString())
        assertEquals("[" +
                "[0, 0, 0, 0], [0, 0, 0, 1], [0, 0, 0, 2], [0, 0, 1, 1], [0, 0, 1, 2], " +
                "[0, 0, 2, 2], [0, 1, 1, 1], [0, 1, 1, 2], [0, 1, 2, 2], [0, 2, 2, 2], " +
                "[1, 1, 1, 1], [1, 1, 1, 2], [1, 1, 2, 2], [1, 2, 2, 2], [2, 2, 2, 2]]",
                (0..2).combinationsWithRepetition(4).toList().toString())

        // permutationsWithRepetition() は permutationsWithRepetition() のシーケンスから、
        // 要素が（入力プールの位置に応じた順序で）ソート順になっていない項目をフィルタリングしたものと同じ
        fun <T> Iterable<T>.cwr2(length: Int): List<List<T>> = buildSequence {
            val pool = toList()
            pool.indices.permutationsWithRepetition(length)
                    .filter { it.sorted() == it }
                    .forEach { yield(it.map { pool[it] }) }
        }.toList()

        fun numcombs(n: Int, r: Int): BigInteger = when (n) {
            0 -> if (r > 0) BigInteger.ZERO else BigInteger.ONE
            else -> combinationsWithRepetition(n, r)
        }

        val comparator = Comparator<List<Int>> { o1, o2 -> Arrays.compare(o1.toTypedArray(), o2.toTypedArray()) }

        for (n in 0 until 7) {
            val values = (0 until n).map { 5 * it - 12 }
            for (r in 0 until n + 1) {
                val result = values.combinationsWithRepetition(r).toList()

                assertEquals(result, values.toTypedArray().combinationsWithRepetition(r).map { it.toList() }.toList())
                assertEquals(result, CombinationWithRepetitionGenerator.indices(n, r).map { it.map { values[it] } }.toList())

                assertEquals(result.count(), numcombs(n, r).intValueExact())
                assertEquals(result.count(), result.toSet().count())
                assertEquals(result, result.sortedWith(comparator))

                val regularCombs = values.combinations(r).toList()
                if (n == 0 || r <= 1) {
                    assertEquals(result, regularCombs)
                } else {
                    assertTrue(result.toSet().containsAll(regularCombs.toSet()))
                }

                for (c in result) {
                    assertEquals(c.count(), r)
                    val noruns = c.groupBy { it }.map { it.key }
                    assertEquals(noruns.count(), noruns.count())
                    assertEquals(c, c.sorted())
                    assertTrue(c.all { it in values })
                    assertEquals(noruns, values.filter { it in c })
                }

                assertEquals(result, values.cwr2(r))
            }
        }
    }
}
