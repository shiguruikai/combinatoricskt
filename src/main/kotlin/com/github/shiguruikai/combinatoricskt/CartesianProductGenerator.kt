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

    @PublishedApi
    internal inline fun <R> build(sizes: IntArray,
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
                    indices[i]++
                    if (indices[i] >= dimensions[i]) {
                        indices[i] = 0
                    } else {
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
        val pools = iterables.map {
            it.toList().also {
                if (it.isEmpty()) {
                    return CombinatorialSequence(BigInteger.ZERO, emptySequence())
                }
                sizes += it.size
            }
        }

        val transform: (IntArray) -> List<T> = if (repeat == 1) {
            { indices: IntArray ->
                indices.mapIndexed { index, it -> pools[index][it] }
            }
        } else {
            { indices: IntArray ->
                var index = 0
                indices.map {
                    pools[index][it].also {
                        if (++index >= pools.size) {
                            index = 0
                        }
                    }
                }
            }
        }

        return build(sizes.toIntArray(), repeat, transform)
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

        val sizes = mutableListOf<Int>()
        val pools = arrays.mapToArray {
            if (it.isEmpty()) {
                return CombinatorialSequence(BigInteger.ZERO, emptySequence())
            }
            sizes += it.size
            it.copyOf()
        }

        val transform: (IntArray) -> Array<T> = if (repeat == 1) {
            { indices: IntArray ->
                var index = 0
                indices.mapToArray { pools[index++][it] }
            }
        } else {
            { indices: IntArray ->
                var index = 0
                indices.mapToArray {
                    pools[index][it].also {
                        if (++index >= pools.size) {
                            index = 0
                        }
                    }
                }
            }
        }

        return build(sizes.toIntArray(), repeat, transform)
    }
}
