package com.github.shiguruikai.combinatoricskt

import com.github.shiguruikai.combinatoricskt.internal.times
import java.math.BigInteger

object CartesianProductGenerator {

    /**
     * Returns a sequence of cartesian product of the elements of [iterables].
     *
     * To compute the cartesian product of [iterables] with itself, specify the number of repetitions with the [repeat] named argument.
     * @throws IllegalArgumentException if [repeat] is negative.
     */
    @JvmStatic
    fun <T> generate(vararg iterables: Iterable<T>, repeat: Int = 1): CombinatorialSequence<List<T>> {
        require(repeat >= 0) { "repeat must be non-negative, was $repeat" }

        if (repeat == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyList()))
        }

        var total = BigInteger.ONE
        val pools = iterables.map { it.toList().also { total *= it.size.toBigInteger() }.asSequence() } * repeat
        total = total.pow(repeat)

        if (total == BigInteger.ZERO) {
            return CombinatorialSequence(BigInteger.ZERO, emptySequence())
        }

        var sequence = sequenceOf(emptyList<T>())
        pools.forEach { pool ->
            sequence = sequence.flatMap { a -> pool.map { b -> a + b } }
        }

        return CombinatorialSequence(total, sequence)
    }

    /**
     * Returns a sequence of cartesian product of the elements of [arrays].
     *
     * To compute the cartesian product of [arrays] with itself, specify the number of repetitions with the [repeat] named argument.
     * @throws IllegalArgumentException if [repeat] is negative.
     */
    inline fun <reified T> generate(vararg arrays: Array<T>, repeat: Int = 1): CombinatorialSequence<Array<T>> {
        require(repeat >= 0) { "repeat must be non-negative, was $repeat" }

        if (repeat == 0) {
            return CombinatorialSequence(BigInteger.ONE, sequenceOf(emptyArray()))
        }

        var total = BigInteger.ONE
        val pools = arrays.map { total *= it.size.toBigInteger(); it.copyOf().asSequence() } * repeat
        total = total.pow(repeat)

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
