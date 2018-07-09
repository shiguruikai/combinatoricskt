package com.github.shiguruikai.combinatoricskt

import java.math.BigInteger
import java.util.*
import java.util.stream.Stream
import java.util.stream.StreamSupport

/**
 * This [Sequence] class has the [totalSize] property of the total number of sequences.
 * This sequence can be iterated only once.
 *
 * @see kotlin.sequences.constrainOnce
 */
class CombinatorialSequence<T>(
        /** Total number of elements in this sequence. */
        val totalSize: BigInteger,
        sequence: Sequence<T>
) : Sequence<T> by sequence.constrainOnce() {

    constructor(size: BigInteger, iterator: Iterator<T>) : this(size, iterator.asSequence())

    /**
     * @see kotlin.sequences.toList
     */
    fun toList(): List<T> = toMutableList()

    /**
     * @see kotlin.sequences.toMutableList
     */
    fun toMutableList(): MutableList<T> = if (totalSize <= MAX_ARRAY_SIZE) {
        toCollection(ArrayList(totalSize.intValueExact()))
    } else {
        toCollection(LinkedList())
    }

    /**
     * @see kotlin.streams.asStream
     */
    fun asStream(): Stream<T> = if (totalSize <= Long.MAX_VALUE.toBigInteger()) {
        StreamSupport.stream(Spliterators.spliterator(
                iterator(), totalSize.longValueExact(), Spliterator.ORDERED), false)
    } else {
        StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator(), Spliterator.ORDERED), false)
    }

    private companion object {
        /**
         * @see java.util.ArrayList.MAX_ARRAY_SIZE
         */
        private val MAX_ARRAY_SIZE = (Int.MAX_VALUE - 8).toBigInteger()
    }
}
