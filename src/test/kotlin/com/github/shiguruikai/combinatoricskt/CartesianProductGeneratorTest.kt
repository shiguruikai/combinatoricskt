/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt

import com.github.shiguruikai.combinatoricskt.internal.times
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.random.Random

internal class CartesianProductGeneratorTest {

    private val emptyList = emptyList<Any>()
    private val emptyArray = emptyArray<Any>()
    private fun IntRange.toArray(): Array<Int> = toList().toTypedArray()

    @Test
    fun test_cartesianProduct_empty() {
        assertEquals("[]", emptyList.cartesianProduct().toList().toString())
        assertEquals("[]", emptyArray.cartesianProduct().toList().toString())
    }

    @Test
    fun test_cartesianProduct_size() {
        assertEquals(0, (0..3).cartesianProduct((0..4), emptyList).totalSize.intValueExact())
        assertEquals(0, (0..3).cartesianProduct((0..4), emptyList).count())
        assertEquals(60, (0..3).cartesianProduct((0..4), (0..2), repeat = 1).totalSize.intValueExact())
        assertEquals(60, (0..3).cartesianProduct((0..4), (0..2), repeat = 1).count())
        assertEquals(3600, (0..3).cartesianProduct((0..4), (0..2), repeat = 2).totalSize.intValueExact())
        assertEquals(3600, (0..3).cartesianProduct((0..4), (0..2), repeat = 2).count())

        assertEquals(0, (0..3).toArray().cartesianProduct((0..4).toArray(), arrayOf()).totalSize.intValueExact())
        assertEquals(0, (0..3).toArray().cartesianProduct((0..4).toArray(), arrayOf()).count())
        assertEquals(60, (0..3).toArray().cartesianProduct((0..4).toArray(), (0..2).toArray(), repeat = 1).totalSize.intValueExact())
        assertEquals(60, (0..3).toArray().cartesianProduct((0..4).toArray(), (0..2).toArray(), repeat = 1).count())
        assertEquals(3600, (0..3).toArray().cartesianProduct((0..4).toArray(), (0..2).toArray(), repeat = 2).totalSize.intValueExact())
        assertEquals(3600, (0..3).toArray().cartesianProduct((0..4).toArray(), (0..2).toArray(), repeat = 2).count())

        assertEquals(0, CartesianProductGenerator.indices(4, 5, 0).totalSize.intValueExact())
        assertEquals(0, CartesianProductGenerator.indices(4, 5, 0).count())
        assertEquals(60, CartesianProductGenerator.indices(4, 5, 3, repeat = 1).totalSize.intValueExact())
        assertEquals(60, CartesianProductGenerator.indices(4, 5, 3, repeat = 1).count())
        assertEquals(3600, CartesianProductGenerator.indices(4, 5, 3, repeat = 2).totalSize.intValueExact())
        assertEquals(3600, CartesianProductGenerator.indices(4, 5, 3, repeat = 2).count())
    }

    @Test
    fun test_cartesianProduct() {
        assertEquals(
                "[[0, a], [0, b], [1, a], [1, b], [2, a], [2, b]]",
                (0..2).cartesianProduct('a'..'b').toList().toString())

        assertEquals(
                "[[0, a, x], [0, b, x], [1, a, x], [1, b, x]]",
                (0..1).cartesianProduct('a'..'b', listOf('x')).toList().toString())

        assertEquals(
                "[[1, 4], [1, 5], [1, 6], [2, 4], [2, 5], [2, 6], [3, 4], [3, 5], [3, 6]]",
                CartesianProductGenerator.generate(
                        arrayOf(1, 2, 3), arrayOf(4, 5, 6)).toList().map { it.contentToString() }.toString())

        assertEquals(
                "[[1, 4], [1, 5], [1, 6], [2, 4], [2, 5], [2, 6], [3, 4], [3, 5], [3, 6]]",
                arrayOf(1, 2, 3).cartesianProduct(arrayOf(4, 5, 6)).toList().map { it.contentToString() }.toString())

        assertEquals(480, (0..3).cartesianProduct((0..4), (0..5), (0..3)).count())
        assertEquals(0, (0..3).cartesianProduct(emptyList, (0..4), (0..5), (0..3)).count())

        assertEquals(
                (0..4).cartesianProduct((0..4)).toList(),
                (0..4).permutationsWithRepetition(2).toList())
        assertEquals(
                (0..4).cartesianProduct((0..4), (0..4)).toList(),
                (0..4).permutationsWithRepetition(3).toList())

        fun <T> product1(vararg iterables: Iterable<T>, repeat: Int = 1): Sequence<List<T>> {
            val pools = iterables.map { it.toList() } * repeat
            val n = pools.size
            if (n == 0) {
                return sequenceOf(emptyList())
            }
            if (pools.any { it.isEmpty() }) {
                return emptySequence()
            }
            val indices = IntArray(n)

            return sequence {
                loop@ while (true) {
                    yield(indices.mapIndexed { index, it -> pools[index][it] })

                    for (i in n - 1 downTo 0) {
                        indices[i]++
                        if (indices[i] == pools[i].size) {
                            indices[i] = 0
                        } else {
                            continue@loop
                        }
                    }
                    break
                }
            }
        }

        fun <T> product2(vararg iterables: Iterable<T>, repeat: Int = 1): Sequence<List<T>> {
            if (repeat == 0) {
                return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyList()))
            }

            val pools = iterables.map { it.toList().asSequence() } * repeat

            var sequence = sequenceOf(emptyList<T>())
            pools.forEach { pool ->
                sequence = sequence.flatMap { a -> pool.map { b -> a + b } }
            }

            return sequence
        }

        val argTypes: Array<Iterable<Any>> = arrayOf(emptyList, 'a'..'c', 0..0, ('a'..'d').toSet(), 0..3, listOf(8, 9))
        val random = Random
        repeat(20) {
            val iterables = (0..2).map { argTypes[random.nextInt(argTypes.size)] }.toTypedArray()
            val arrays = iterables.map { it.toList().toTypedArray() }.toTypedArray()
            val repeat = random.nextInt(4)
            val expectedSize = iterables.fold(BigInteger.ONE) { acc, iter -> acc * iter.count().toBigInteger() }.pow(repeat)
            val list = CartesianProductGenerator.generate(*iterables, repeat = repeat).toList()

            assertEquals(expectedSize, list.size.toBigInteger())
            assertEquals(list, product1(*iterables, repeat = repeat).toList())
            assertEquals(list, product2(*iterables, repeat = repeat).toList())
            assertEquals(list,
                    CartesianProductGenerator.generate(*arrays, repeat = repeat).map { it.toList() }.toList())

            val indices = CartesianProductGenerator.indices(*iterables.map { it.count() }.toIntArray(), repeat = repeat).toList()
            val repeated = iterables.map { it.toList() } * repeat
            assertEquals(list, indices.map { it.zip(repeated) { a, b -> b[a] } })
        }
    }
}
