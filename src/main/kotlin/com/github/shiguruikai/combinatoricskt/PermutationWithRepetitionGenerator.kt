package com.github.shiguruikai.combinatoricskt

import java.math.BigInteger
import kotlin.coroutines.experimental.buildIterator

object PermutationWithRepetitionGenerator {

    @PublishedApi
    internal inline fun <R> build(n: Int, r: Int,
                                  crossinline condition: () -> Boolean,
                                  crossinline block: (IntArray) -> R): Iterator<R> = buildIterator {
        val indices = IntArray(r)
        while (condition()) {
            yield(block(indices))
            for (i in r - 1 downTo 0) {
                if (indices[i] >= n - 1) {
                    indices[i] = 0
                } else {
                    indices[i]++
                    break
                }
            }
        }
    }

    @JvmStatic
    fun <T> generate(vararg iterables: Iterable<T>, length: Int): CombinatorialSequence<List<T>> {
        require(length >= 0) { "length argument cannot be negative" }
        if (length == 0) return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyList()))
        if (iterables.isEmpty()) return CombinatorialSequence(BigInteger.ZERO, emptySequence())

        if (iterables.size == 1) {
            val pool = iterables[0].toList()
            val n = pool.size
            val total = n.toBigInteger().pow(length)

            val condition = if (total <= Long.MAX_VALUE.toBigInteger()) {
                var t = total.longValueExact();
                { t-- > 0 }
            } else {
                var t = total;
                { t-- > BigInteger.ZERO }
            }

            return CombinatorialSequence(total, build(n, length, condition) { it.map { pool[it] } })
        } else {
            var total = BigInteger.ONE
            val pools = iterables.map { it.toList().also { total *= it.size.toBigInteger() }.asSequence() } * length
            total = total.pow(length)

            if (total == BigInteger.ZERO) {
                return CombinatorialSequence(BigInteger.ZERO, emptySequence())
            }

            var sequence = sequenceOf(emptyList<T>())
            pools.forEach { pool ->
                sequence = sequence.flatMap { a -> pool.map { b -> a + b } }
            }

            return CombinatorialSequence(total, sequence)
        }
    }

    inline fun <reified T> generate(vararg arrays: Array<T>, length: Int): CombinatorialSequence<Array<T>> {
        require(length >= 0) { "length argument cannot be negative" }
        if (length == 0) return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyArray()))
        if (arrays.isEmpty()) return CombinatorialSequence(BigInteger.ZERO, emptySequence())

        if (arrays.size == 1) {
            val pool = arrays[0].copyOf()
            val n = pool.size
            val total = n.toBigInteger().pow(length)

            val condition = if (total <= Long.MAX_VALUE.toBigInteger()) {
                var t = total.longValueExact();
                { t-- > 0 }
            } else {
                var t = total;
                { t-- > BigInteger.ZERO }
            }

            return CombinatorialSequence(total, build(n, length, condition) { it.mapToArray { pool[it] } })
        } else {
            var total = BigInteger.ONE
            val pools = arrays.map { total *= it.size.toBigInteger(); it.copyOf().asSequence() } * length
            total = total.pow(length)

            if (total == BigInteger.ZERO) {
                return CombinatorialSequence(BigInteger.ZERO, emptySequence())
            }

            var sequence = sequenceOf(emptyArray<T>())
            pools.forEach { pool ->
                sequence = sequence.flatMap { a -> pool.map { b -> a + b } }
            }

            return CombinatorialSequence(total, sequence)
        }
    }
}
