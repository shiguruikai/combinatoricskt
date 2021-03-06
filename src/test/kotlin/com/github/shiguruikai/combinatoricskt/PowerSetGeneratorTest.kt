/*
 * Copyright (c) 2020 shiguruikai
 *
 * Licensed under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class PowerSetGeneratorTest {

    private val emptyList = emptyList<Any>()
    private val emptyArray = emptyArray<Any>()

    @Test
    fun test_powerset_empty() {
        assertEquals("[[]]", emptyList.powerset().toList().toString())
        assertEquals("[[]]", emptyArray.powerset().toList().map { it.contentToString() }.toString())
    }

    @Test
    fun test_powerset() {
        val array = arrayOf(0, 1, 2)
        val expected = listOf(
                "[[]]",
                "[[], [0]]",
                "[[], [0], [1], [0, 1]]",
                "[[], [0], [1], [0, 1], [2], [0, 2], [1, 2], [0, 1, 2]]")
        expected.forEachIndexed { index, it ->
            assertEquals(it, array.take(index).powerset().toList().toString())
            assertEquals(it, array.copyOf(index).powerset().map { it.toList() }.toList().toString())
        }

        fun <T> Iterable<T>.powerset1(): CombinatorialSequence<List<T>> {
            val pool = toList()
            val totalSize = 2.toBigInteger().pow(pool.size)

            val iterator = iterator {
                val bitLength = totalSize.bitLength()
                val max = totalSize.intValueExact() - 1
                var i = 0
                while (true) {
                    val acc = ArrayList<T>(Integer.bitCount(i))
                    for (index in 0..bitLength) {
                        if ((1 shl index) and i != 0) {
                            acc += pool[index]
                        }
                    }
                    yield(acc)

                    if (i >= max) {
                        break
                    }
                    i++
                }
            }

            return CombinatorialSequence(totalSize, iterator)
        }

        // ソート順かつサイズ順で生成
        fun <T> Iterable<T>.powerset2(): Sequence<List<T>> {
            val pool = toList()

            var sequence: Sequence<List<T>> = emptySequence()
            for (r in 0..pool.size) {
                sequence += pool.combinations(r)
            }

            return sequence
        }

        fun <T> Iterable<T>.powerset3(): Sequence<List<T>> {

            tailrec fun <T> powerset3Tailrec(left: Collection<T>, acc: Sequence<List<T>>): Sequence<List<T>> = when {
                left.isEmpty() -> acc
                else -> powerset3Tailrec(left.drop(1), acc + acc.map { it + left.first() })
            }

            return powerset3Tailrec(toList(), sequenceOf(listOf()))
        }

        val comparator = ListComparator<Char>()

        val range = 'a'..'f'
        for (i in 0..7) {
            val v = range.take(i)
            val ps = v.powerset()
            val psList = ps.toList()
            val ps1 = v.powerset1()
            val ps2 = v.powerset2()
            val ps3 = v.powerset3()

            val size = 2.toBigInteger().pow(v.size)

            assertTrue(ps.totalSize == size && size == ps1.totalSize)
            assertEquals(psList, ps1.toList())
            assertEquals(psList.sortedWith(comparator).sortedBy { it.size }, ps2.toList())
            assertEquals(psList, ps3.toList())
        }
    }
}
