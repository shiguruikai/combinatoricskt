package com.github.shiguruikai.combinatoricskt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
    }
}
