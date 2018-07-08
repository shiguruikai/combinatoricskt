package com.github.shiguruikai.combinatoricskt

import java.math.BigInteger
import java.util.*
import java.util.stream.Stream
import java.util.stream.StreamSupport

class CombinatorialSequence<T>(val totalSize: BigInteger,
                               sequence: Sequence<T>
) : Sequence<T> by sequence.constrainOnce() {

    constructor(size: BigInteger, iterator: Iterator<T>) : this(size, iterator.asSequence())

    fun toList(): List<T> = toMutableList()

    fun toMutableList(): MutableList<T> = if (totalSize <= MAX_ARRAY_SIZE) {
        toCollection(ArrayList(totalSize.intValueExact()))
    } else {
        toCollection(LinkedList())
    }

    fun asStream(): Stream<T> = if (totalSize <= Long.MAX_VALUE.toBigInteger()) {
        StreamSupport.stream(Spliterators.spliterator(
                iterator(), totalSize.longValueExact(), Spliterator.ORDERED), false)
    } else {
        StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator(), Spliterator.ORDERED), false)
    }

    private companion object {
        private val MAX_ARRAY_SIZE = (Int.MAX_VALUE - 8).toBigInteger()
    }
}
