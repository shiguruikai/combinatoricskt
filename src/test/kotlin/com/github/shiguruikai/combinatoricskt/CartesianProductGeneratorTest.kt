package com.github.shiguruikai.combinatoricskt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger
import java.util.*
import kotlin.coroutines.experimental.buildSequence

internal class CartesianProductGeneratorTest {

    private val emptyList = emptyList<Any>()
    private val emptyArray = emptyArray<Any>()

    @Test
    fun test_cartesianProduct_empty() {
        assertEquals("[]", emptyList.cartesianProduct().toList().toString())
        assertEquals("[]", emptyArray.cartesianProduct().toList().toString())
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

        assertEquals(60, (0..3).cartesianProduct((0..4), (0..2), repeat = 1).count())
        assertEquals(3600, (0..3).cartesianProduct((0..4), (0..2), repeat = 2).count())

        fun <T> product1(vararg iterables: Iterable<T>, length: Int = 1): Sequence<List<T>> {
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
            val list = CartesianProductGenerator.generate(*iterables, repeat = length).toList()
            assertEquals(expectedSize, list.size.toBigInteger())
            assertEquals(list, product1(*iterables, length = length).toList())
            assertEquals(list,
                    CartesianProductGenerator.generate(*arrays, repeat = length).toList().map { it.toList() })
            assertEquals(list,
                    arrays[0].cartesianProduct(arrays[1], arrays[2], repeat = length).toList().map { it.toList() })
        }
    }
}
