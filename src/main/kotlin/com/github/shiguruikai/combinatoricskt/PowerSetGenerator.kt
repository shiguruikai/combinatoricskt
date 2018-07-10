package com.github.shiguruikai.combinatoricskt

import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.experimental.buildIterator

/**
 * The class [PowerSetGenerator] contains methods for generating power set.
 */
object PowerSetGenerator {

    @PublishedApi
    internal inline fun <R, T : R> build(
            poolSize: Int,
            crossinline initial: (size: Int) -> T,
            crossinline operation: (index: Int, acc: T, poolIndex: Int) -> Unit
    ): CombinatorialSequence<R> {
        val totalSize = 2.toBigInteger().pow(poolSize)

        val iterator = buildIterator {
            val bitLength = totalSize.bitLength()
            val bitSet = BitSet(bitLength)
            val lastIndex = bitLength - 1

            while (true) {
                val acc = initial(bitSet.cardinality())
                var i = bitSet.nextSetBit(0)
                var index = 0
                while (i != -1) {
                    operation(index++, acc, i)
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
