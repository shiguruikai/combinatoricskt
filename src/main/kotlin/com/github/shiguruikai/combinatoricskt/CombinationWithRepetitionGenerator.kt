package com.github.shiguruikai.combinatoricskt

import java.math.BigInteger
import kotlin.coroutines.experimental.buildIterator

object CombinationWithRepetitionGenerator {

    @PublishedApi
    internal inline fun <R> build(n: Int, r: Int,
                                  crossinline block: (IntArray) -> R): CombinatorialSequence<R> {
        val totalSize = combinationsWithRepetition(n, r)

        val iterator = buildIterator {
            val indices = IntArray(r)
            loop@ while (true) {
                yield(block(indices))
                for (i in r - 1 downTo 0) {
                    if (indices[i] != n - 1) {
                        indices[i]++
                        for (j in i + 1 until r) {
                            indices[j] = indices[i]
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
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(IntArray(0)))
        } else if (n < 1) {
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

        if (n == 0) {
            return CombinatorialSequence(BigInteger.ZERO, emptySequence())
        }

        return build(n, length) { it.map { pool[it] } }
    }

    inline fun <reified T> generate(array: Array<T>, length: Int): CombinatorialSequence<Array<T>> {
        require(length >= 0) { "length must be non-negative, was $length" }

        if (length == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyArray()))
        }

        val pool = array.copyOf()
        val n = pool.size

        if (n == 0) {
            return CombinatorialSequence(BigInteger.ZERO, emptySequence())
        }

        return build(n, length) { it.mapToArray { pool[it] } }
    }
}
