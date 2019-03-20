/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt

import com.github.shiguruikai.combinatoricskt.internal.mapToArray
import com.github.shiguruikai.combinatoricskt.internal.subFactorial
import com.github.shiguruikai.combinatoricskt.internal.swap
import java.math.BigInteger

/**
 * The class [DerangementGenerator] contains methods for generating derangement.
 */
object DerangementGenerator {

    @PublishedApi
    internal inline fun <R> build(n: Int,
                                  crossinline transform: (IntArray) -> R): CombinatorialSequence<R> {
        val totalSize = subFactorial(n)

        val iterator = object : Iterator<R> {
            val indices = IntArray(n) { it }
            val lastIndex = n - 1
            var hasNext = true

            fun nextPerm() {
                var i = lastIndex
                while (i > 0 && indices[i - 1] >= indices[i]) {
                    i--
                }
                if (i <= 0) {
                    hasNext = false
                    return
                }
                var j = lastIndex
                while (indices[j] <= indices[i - 1]) {
                    j--
                }
                indices.swap(i - 1, j)
                j = lastIndex
                while (i < j) {
                    indices.swap(i, j)
                    i++
                    j--
                }
            }

            override fun hasNext(): Boolean {
                while (hasNext) {
                    var i = 0
                    if (indices.all { it != i++ }) {
                        break
                    }
                    nextPerm()
                }
                return hasNext
            }

            override fun next(): R {
                if (!hasNext()) throw NoSuchElementException()
                val nextValue = transform(indices)
                nextPerm()
                return nextValue
            }
        }

        return CombinatorialSequence(totalSize, iterator)
    }

    /**
     * Returns a sequence of derangement of [n] elements.
     *
     * @throws IllegalArgumentException if [n] is negative.
     */
    @JvmStatic
    fun indices(n: Int): CombinatorialSequence<IntArray> {
        require(n >= 0) { "n must be non-negative, was $n" }

        if (n == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(intArrayOf()))
        } else if (n == 1) {
            return CombinatorialSequence(BigInteger.ZERO, sequenceOf())
        }

        return build(n) { it.copyOf() }
    }

    /**
     * Returns a sequence of derangement of the elements of [iterable].
     */
    @JvmStatic
    fun <T> generate(iterable: Iterable<T>): CombinatorialSequence<List<T>> {
        val pool = iterable.toList()
        val n = pool.size

        if (n == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyList()))
        } else if (n == 1) {
            return CombinatorialSequence(BigInteger.ZERO, sequenceOf())
        }

        return build(n) { it.map { pool[it] } }
    }

    /**
     * Returns a sequence of derangement of the elements of [array].
     */
    inline fun <reified T> generate(array: Array<T>): CombinatorialSequence<Array<T>> {
        val n = array.size

        if (n == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyArray()))
        } else if (n == 1) {
            return CombinatorialSequence(BigInteger.ZERO, sequenceOf())
        }

        val pool = array.copyOf()

        return build(n) { it.mapToArray { pool[it] } }
    }
}
