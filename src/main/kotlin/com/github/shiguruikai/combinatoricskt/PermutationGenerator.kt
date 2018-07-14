/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt

import com.github.shiguruikai.combinatoricskt.internal.mapToArray
import com.github.shiguruikai.combinatoricskt.internal.mapToList
import com.github.shiguruikai.combinatoricskt.internal.permutations
import com.github.shiguruikai.combinatoricskt.internal.swap
import java.math.BigInteger

/**
 * The class [PermutationGenerator] contains methods for generating permutations.
 */
object PermutationGenerator {

    @PublishedApi
    internal inline fun <R> build(n: Int, r: Int, crossinline block: (IntArray) -> R): CombinatorialSequence<R> {
        val totalSize = permutations(n, r)

        val iterator = if (n == r) {
            object : Iterator<R> {
                val indices = IntArray(n) { it }
                val lastIndex = n - 1
                var hasNext = true

                override fun hasNext(): Boolean = hasNext

                override fun next(): R {
                    if (!hasNext()) throw NoSuchElementException()
                    val nextValue = block(indices)
                    var i = lastIndex
                    while (i > 0 && indices[i - 1] >= indices[i]) {
                        i--
                    }
                    if (i <= 0) {
                        hasNext = false
                        return nextValue
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
                    return nextValue
                }
            }

            /* SequenceBuilderIterator is not good performance
            buildIterator {
                val indices = IntArray(n) { it }
                val lastIndex = n - 1
                while (true) {
                    yield(block(indices))
                    var i = lastIndex
                    while (i > 0 && indices[i - 1] >= indices[i]) {
                        i--
                    }
                    if (i <= 0) {
                        break
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
            }
            */
        } else {
            object : Iterator<R> {
                val indices = IntArray(n) { it }
                val cycles = IntArray(r) { n - it }
                var hasNext = true

                override fun hasNext(): Boolean = hasNext

                override fun next(): R {
                    if (!hasNext()) throw NoSuchElementException()
                    val nextValue = block(indices)
                    for (i in r - 1 downTo 0) {
                        cycles[i]--
                        if (cycles[i] == 0) {
                            val first = indices[i]
                            for (j in i until n - 1) {
                                indices[j] = indices[j + 1]
                            }
                            indices[n - 1] = first
                            cycles[i] = n - i
                        } else {
                            indices.swap(i, n - cycles[i])
                            return nextValue
                        }
                    }
                    hasNext = false
                    return nextValue
                }
            }

            /* SequenceBuilderIterator is not good performance
            buildIterator {
                val indices = IntArray(n) { it }
                val cycles = IntArray(r) { n - it }
                loop@ while (true) {
                    yield(block(indices))
                    for (i in r - 1 downTo 0) {
                        cycles[i]--
                        if (cycles[i] == 0) {
                            val first = indices[i]
                            for (j in i until n - 1) {
                                indices[j] = indices[j + 1]
                            }
                            indices[n - 1] = first
                            cycles[i] = n - i
                        } else {
                            indices.swap(i, n - cycles[i])
                            continue@loop
                        }
                    }
                    break
                }
            }
            */
        }

        return CombinatorialSequence(totalSize, iterator)
    }

    /**
     * Returns a sequence of [r] number of permutations [n] elements.
     *
     * If [r] is not specified or is null, the default for [r] is the length of [n].
     *
     * @throws IllegalArgumentException if [r] is negative.
     */
    @JvmStatic
    fun indices(n: Int, r: Int? = null): CombinatorialSequence<IntArray> {
        if (r != null) {
            require(r >= 0) { "r must be non-negative, was $r" }
        }

        val r1 = r ?: n

        if (r1 > n) {
            return CombinatorialSequence(BigInteger.ZERO, emptySequence())
        } else if (r1 == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(intArrayOf()))
        }

        return build(n, r1) { it.copyOf(r1) }
    }

    /**
     * Returns a sequence of permutations of [length] of the elements of [iterable].
     *
     * If [length] is not specified or is null, the default for [length] is the length of [iterable].
     *
     * @throws IllegalArgumentException if [length] is negative.
     */
    @JvmStatic
    fun <T> generate(iterable: Iterable<T>, length: Int? = null): CombinatorialSequence<List<T>> {
        if (length != null) {
            require(length >= 0) { "length must be non-negative, was $length" }
        }

        val pool = iterable.toList()
        val n = pool.size
        val r = length ?: n

        if (r > n) {
            return CombinatorialSequence(BigInteger.ZERO, emptySequence())
        } else if (r == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyList()))
        }

        return build(n, r) { indices -> indices.mapToList(r) { pool[it] } }
    }

    /**
     * Returns a sequence of permutations of [length] of the elements of [array].
     *
     * If [length] is not specified or is null, the default for [length] is the length of [array].
     *
     * @throws IllegalArgumentException if [length] is negative.
     */
    inline fun <reified T> generate(array: Array<T>, length: Int? = null): CombinatorialSequence<Array<T>> {
        if (length != null) {
            require(length >= 0) { "length must be non-negative, was $length" }
        }

        val n = array.size
        val r = length ?: n

        if (r > n) {
            return CombinatorialSequence(BigInteger.ZERO, emptySequence())
        } else if (r == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyArray()))
        }

        val pool = array.copyOf()

        return build(n, r) { indices -> indices.mapToArray(r) { pool[it] } }
    }
}
