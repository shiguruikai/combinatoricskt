package com.github.shiguruikai.combinatoricskt

import java.math.BigInteger

object CartesianProductGenerator {

    @JvmStatic
    fun <T> generate(vararg iterables: Iterable<T>, repeat: Int = 1): CombinatorialSequence<List<T>> {
        require(repeat >= 0) { "repeat argument cannot be negative" }

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

    inline fun <reified T> generate(vararg arrays: Array<T>, repeat: Int = 1): CombinatorialSequence<Array<T>> {
        require(repeat >= 0) { "repeat argument cannot be negative" }

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
