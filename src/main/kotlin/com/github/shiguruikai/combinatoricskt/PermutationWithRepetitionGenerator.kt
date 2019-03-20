/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt

import com.github.shiguruikai.combinatoricskt.internal.mapToArray
import java.math.BigInteger

/**
 * The class [PermutationWithRepetitionGenerator] contains methods for generating permutations with repetition.
 */
object PermutationWithRepetitionGenerator {

    @PublishedApi
    internal inline fun <R> build(n: Int, r: Int,
                                  crossinline transform: (IntArray) -> R): CombinatorialSequence<R> {
        val totalSize = n.toBigInteger().pow(r)

        val iterator = if (totalSize <= Long.MAX_VALUE.toBigInteger()) {
            object : Iterator<R> {
                val indices = IntArray(r)
                var t = totalSize.longValueExact()

                override fun hasNext(): Boolean = t > 0

                override fun next(): R {
                    if (!hasNext()) throw NoSuchElementException()
                    t--
                    val nextValue = transform(indices)
                    for (i in r - 1 downTo 0) {
                        if (indices[i] >= n - 1) {
                            indices[i] = 0
                        } else {
                            indices[i]++
                            break
                        }
                    }
                    return nextValue
                }
            }
        } else {
            object : Iterator<R> {
                val indices = IntArray(r)
                var t = totalSize

                override fun hasNext(): Boolean = t > BigInteger.ZERO

                override fun next(): R {
                    if (!hasNext()) throw NoSuchElementException()
                    t--
                    val nextValue = transform(indices)
                    for (i in r - 1 downTo 0) {
                        if (indices[i] >= n - 1) {
                            indices[i] = 0
                        } else {
                            indices[i]++
                            break
                        }
                    }
                    return nextValue
                }
            }
        }

        return CombinatorialSequence(totalSize, iterator)
    }

    /**
     * Returns a sequence of [r] number of permutations with repetition of [n] elements.
     *
     * @throws IllegalArgumentException if [r] is negative.
     */
    @JvmStatic
    fun indices(n: Int, r: Int): CombinatorialSequence<IntArray> {
        require(r >= 0) { "r must be non-negative, was $r" }

        if (r == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(intArrayOf()))
        }

        return build(n, r) { it.copyOf() }
    }

    /**
     * Returns a sequence of permutations with repetition of [length] of the elements of [iterable].
     *
     * @throws IllegalArgumentException if [length] is negative.
     */
    @JvmStatic
    fun <T> generate(iterable: Iterable<T>, length: Int): CombinatorialSequence<List<T>> {
        require(length >= 0) { "length must be non-negative, was $length" }

        if (length == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyList()))
        }

        val pool = iterable.toList()

        return build(pool.size, length) { it.map { pool[it] } }
    }

    /**
     * Returns a sequence of permutations with repetition of [length] of the elements of [array].
     *
     * @throws IllegalArgumentException if [length] is negative.
     */
    inline fun <reified T> generate(array: Array<T>, length: Int): CombinatorialSequence<Array<T>> {
        require(length >= 0) { "length must be non-negative, was $length" }

        if (length == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyArray()))
        }

        val pool = array.copyOf()

        return build(pool.size, length) { it.mapToArray { pool[it] } }
    }
}
