package com.github.shiguruikai.combinatoricskt

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger
import java.util.*
import kotlin.coroutines.experimental.buildSequence

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
                (0..2).permutationsWithRepetition((0..2), (0..2), (0..2), length = 1).toList(),
                (0..2).permutationsWithRepetition(4).toList())
        assertEquals(
                (0..2).permutationsWithRepetition(4).toList(),
                (0..2).cartesianProduct((0..2), (0..2), (0..2)).toList())

        assertEquals(60, (0..3).permutationsWithRepetition((0..4), (0..2), length = 1).count())
        assertEquals(3600, (0..3).permutationsWithRepetition((0..4), (0..2), length = 2).count())

        fun <T> pwr1(vararg iterables: Iterable<T>, length: Int = 1): Sequence<List<T>> {
            var total = BigInteger.ONE
            val pools = iterables.map { it.toList().also { total *= it.size.toBigInteger() } } * length
            total = total.pow(length)
            val n = pools.size
            if (n == 0) {
                return sequenceOf(emptyList())
            }
            if (pools.any { it.isEmpty() }) {
                return emptySequence()
            }
            val indices = IntArray(n)
            var t = total.longValueExact()

            return buildSequence {
                while (t-- > 0) {
                    yield(indices.mapIndexed { index, it -> pools[index][it] })

                    for (i in n - 1 downTo 0) {
                        indices[i]++
                        if (indices[i] == pools[i].size) {
                            indices[i] = 0
                        } else {
                            break
                        }
                    }
                }
            }
        }

        val argTypes: Array<Iterable<Any>> = arrayOf(emptyList, 'a'..'c', 0..0, ('a'..'d').toSet(), 0..3, listOf(8, 9))
        val random = Random()
        repeat(10) {
            val iterables = (0..2).map { argTypes[random.nextInt(argTypes.size)] }.toTypedArray()
            val arrays = iterables.map { it.toList().toTypedArray() }.toTypedArray()
            val length = random.nextInt(4)
            val expectedSize = iterables.fold(BigInteger.ONE) { acc, iter -> acc * iter.count().toBigInteger() }.pow(length)
            val list = PermutationWithRepetitionGenerator.generate(*iterables, length = length).toList()
            assertEquals(expectedSize, list.size.toBigInteger())
            assertEquals(list, pwr1(*iterables, length = length).toList())
            assertEquals(list,
                    PermutationWithRepetitionGenerator.generate(*arrays, length = length).toList().map { it.toList() })
            assertEquals(list,
                    arrays[0].permutationsWithRepetition(arrays[1], arrays[2], length = length).toList().map { it.toList() })
        }

    }
}
