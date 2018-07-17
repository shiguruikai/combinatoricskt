/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt

import java.util.*
import kotlin.collections.ArrayList

/**
 * The class [PowerSetGenerator] contains methods for generating power set.
 */
object PowerSetGenerator {

    @PublishedApi
    internal inline fun <R, T : R> build(
            poolSize: Int,
            crossinline initial: (size: Int) -> T,
            crossinline transform: (index: Int, acc: T, poolIndex: Int) -> Unit
    ): CombinatorialSequence<R> {
        val totalSize = 2.toBigInteger().pow(poolSize)

        val iterator = object : Iterator<R> {
            val bitLength = totalSize.bitLength()
            val bitSet = BitSet(bitLength)
            val lastIndex = bitLength - 1
            var hasNext = true

            override fun hasNext(): Boolean = hasNext

            override fun next(): R {
                if (!hasNext()) throw NoSuchElementException()
                val acc = initial(bitSet.cardinality())
                var i = bitSet.nextSetBit(0)
                var index = 0
                while (i != -1) {
                    transform(index++, acc, i)
                    i = bitSet.nextSetBit(i + 1)
                }

                i = bitSet.nextClearBit(0)
                if (i >= lastIndex) {
                    hasNext = false
                } else {
                    bitSet.flip(0, i + 1)
                }

                return acc
            }
        }

        /* SequenceBuilderIterator is not good performance
        val iterator = buildIterator {
            val bitLength = totalSize.bitLength()
            val bitSet = BitSet(bitLength)
            val lastIndex = bitLength - 1

            while (true) {
                val acc = initial(bitSet.cardinality())
                var i = bitSet.nextSetBit(0)
                var index = 0
                while (i != -1) {
                    transform(index++, acc, i)
                    i = bitSet.nextSetBit(i + 1)
                }
                yield(acc)

                i = bitSet.nextClearBit(0)
                if (i >= lastIndex) {
                    break
                }
                bitSet.flip(0, i + 1)
            }
        }
        */

        return CombinatorialSequence(totalSize, iterator)
    }

    /**
     * Returns a sequence of power set of [n] elements.
     *
     * @throws IllegalArgumentException if [n] is negative.
     */
    @JvmStatic
    fun indices(n: Int): CombinatorialSequence<IntArray> {
        require(n >= 0) { "n must be non-negative, was $n" }

        return build(n, { IntArray(it) }, { index, acc, poolIndex -> acc[index] = poolIndex })
    }

    /**
     * Returns a sequence of power set of the elements of [iterable].
     */
    @JvmStatic
    fun <T> generate(iterable: Iterable<T>): CombinatorialSequence<List<T>> {
        val pool = iterable.toList()

        return build(pool.size, { ArrayList<T>(it) }, { _, acc, poolIndex -> acc += pool[poolIndex] })
    }

    /**
     * Returns a sequence of power set of the elements of [array].
     */
    inline fun <reified T> generate(array: Array<T>): CombinatorialSequence<Array<T>> {
        val pool = array.copyOf()

        @Suppress("UNCHECKED_CAST")
        return build(pool.size, { arrayOfNulls<T>(it) }, { index, acc, poolIndex -> acc[index] = pool[poolIndex] })
                as CombinatorialSequence<Array<T>>
    }
}
