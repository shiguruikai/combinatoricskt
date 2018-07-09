package com.github.shiguruikai.combinatoricskt

import com.github.shiguruikai.combinatoricskt.internal.combinations
import com.github.shiguruikai.combinatoricskt.internal.mapToArray
import java.math.BigInteger
import kotlin.coroutines.experimental.buildIterator

object CombinationGenerator {

    @PublishedApi
    internal inline fun <R> build(n: Int, r: Int, crossinline block: (IntArray) -> R): CombinatorialSequence<R> {
        val totalSize = combinations(n, r)

        val iterator = buildIterator {
            val indices = IntArray(r) { it }
            loop@ while (true) {
                yield(block(indices))
                for (i in r - 1 downTo 0) {
                    if (indices[i] != i + n - r) {
                        indices[i]++
                        for (j in i + 1 until r) {
                            indices[j] = indices[j - 1] + 1
                        }
                        continue@loop
                    }
                }
                break
            }
        }

        return CombinatorialSequence(totalSize, iterator)
    }

    @JvmStatic
    fun indices(n: Int, r: Int): CombinatorialSequence<IntArray> {
        require(r >= 0) { "r must be non-negative, was $r" }

        if (r == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(intArrayOf()))
        } else if (r > n) {
            return CombinatorialSequence(BigInteger.ZERO, emptySequence())
        }

        return build(n, r) { it.copyOf() }
    }

    @JvmStatic
    fun <T> generate(iterable: Iterable<T>, length: Int): CombinatorialSequence<List<T>> {
        require(length >= 0) { "length must be non-negative, was $length" }

        if (length == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyList()))
        }

        val pool = iterable.toList()
        val n = pool.size

        if (length > n) {
            return CombinatorialSequence(BigInteger.ZERO, emptySequence())
        }

        return build(n, length) { it.map { pool[it] } }
    }

    inline fun <reified T> generate(array: Array<T>, length: Int): CombinatorialSequence<Array<T>> {
        require(length >= 0) { "length must be non-negative, was $length" }

        val n = array.size

        if (length == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyArray()))
        } else if (length > n) {
            return CombinatorialSequence(BigInteger.ZERO, emptySequence())
        }

        val pool = array.copyOf()

        return build(n, length) { it.mapToArray { pool[it] } }
    }
}
