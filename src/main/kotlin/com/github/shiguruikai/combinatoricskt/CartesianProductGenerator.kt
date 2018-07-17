/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt

import com.github.shiguruikai.combinatoricskt.internal.mapToArray
import com.github.shiguruikai.combinatoricskt.internal.times
import java.math.BigInteger

/**
 * The class [CartesianProductGenerator] contains methods for generating cartesian product.
 */
object CartesianProductGenerator {

    private inline fun <R> build(sizes: IntArray,
                                 repeat: Int,
                                 crossinline transform: (IntArray) -> R): CombinatorialSequence<R> {
        val totalSize = sizes.fold(BigInteger.ONE) { acc, size -> acc * size.toBigInteger() }.pow(repeat)

        val iterator = object : Iterator<R> {
            val dimensions = sizes * repeat
            val indices = IntArray(dimensions.size)
            val lastIndex = indices.lastIndex
            var hasNext = true

            override fun hasNext(): Boolean = hasNext

            override fun next(): R {
                if (!hasNext()) throw NoSuchElementException()
                val nextValue = transform(indices)
                for (i in lastIndex downTo 0) {
                    if (indices[i] >= dimensions[i] - 1) {
                        indices[i] = 0
                    } else {
                        indices[i]++
                        return nextValue
                    }
                }
                hasNext = false
                return nextValue
            }
        }

        return CombinatorialSequence(totalSize, iterator)
    }

    /**
     * Returns a sequence of cartesian product of [dimensions].
     *
     * To compute the cartesian product of [dimensions] with itself,
     * specify the number of repetitions with the [repeat] named argument.
     *
     * @throws IllegalArgumentException if [repeat] is negative, or [dimensions] contains negative.
     */
    @JvmStatic
    fun indices(vararg dimensions: Int, repeat: Int = 1): CombinatorialSequence<IntArray> {
        require(repeat >= 0) { "repeat must be non-negative, was $repeat" }

        if (repeat == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(intArrayOf()))
        }

        dimensions.forEach {
            require(it >= 0) { "dimensions must not contain negative, was ${dimensions.contentToString()}" }
            if (it == 0) {
                return CombinatorialSequence(BigInteger.ZERO, emptySequence())
            }
        }

        return build(dimensions.copyOf(), repeat) { it.copyOf() }
    }

    /**
     * Returns a sequence of cartesian product of the elements of [iterables].
     *
     * To compute the cartesian product of [iterables] with itself,
     * specify the number of repetitions with the [repeat] named argument.
     *
     * @throws IllegalArgumentException if [repeat] is negative.
     */
    @JvmStatic
    fun <T> generate(vararg iterables: Iterable<T>, repeat: Int = 1): CombinatorialSequence<List<T>> {
        require(repeat >= 0) { "repeat must be non-negative, was $repeat" }

        if (repeat == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyList()))
        }

        val sizes = mutableListOf<Int>()
        val pools = iterables.mapToArray {
            it.toList().also {
                if (it.isEmpty()) {
                    return CombinatorialSequence(BigInteger.ZERO, emptySequence())
                }
                sizes += it.size
            }
        }

        return if (repeat == 1) {
            build(sizes.toIntArray(), repeat) { indices ->
                indices.mapIndexed { index, it -> pools[index][it] }
            }
        } else {
            build(sizes.toIntArray(), repeat) { indices ->
                var index = 0
                indices.map {
                    pools[index++][it].also {
                        if (index >= pools.size) {
                            index = 0
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns a sequence of cartesian product of the elements of [arrays].
     *
     * To compute the cartesian product of [arrays] with itself,
     * specify the number of repetitions with the [repeat] named argument.
     *
     * @throws IllegalArgumentException if [repeat] is negative.
     */
    inline fun <reified T> generate(vararg arrays: Array<T>, repeat: Int = 1): CombinatorialSequence<Array<T>> {
        require(repeat >= 0) { "repeat must be non-negative, was $repeat" }

        if (repeat == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyArray()))
        }

        var total = BigInteger.ONE
        val pools = arrays.map {
            if (it.isEmpty()) {
                return CombinatorialSequence(BigInteger.ZERO, emptySequence())
            }
            total *= it.size.toBigInteger()
            it.copyOf().asSequence()
        } * repeat
        total = total.pow(repeat)

        var sequence = sequenceOf(emptyArray<T>())
        pools.forEach { pool ->
            sequence = sequence.flatMap { a -> pool.map { b -> a + b } }
        }

        return CombinatorialSequence(total, sequence)
    }
}
