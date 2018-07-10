package com.github.shiguruikai.combinatoricskt

import java.math.BigInteger
import java.util.*
import java.util.stream.Stream
import java.util.stream.StreamSupport

/**
 * This [CombinatorialSequence] class has the [totalSize] property of the total number of sequences.
 * This sequence can be iterated only once.
 *
 * @property totalSize Total number of elements of this sequence.
 *
 * @constructor Construct from sequence.
 *
 * @see kotlin.sequences.Sequence
 * @see kotlin.sequences.constrainOnce
 */
class CombinatorialSequence<T>(
        val totalSize: BigInteger,
        sequence: Sequence<T>
) : Sequence<T> by sequence.constrainOnce() {

    /**
     * Construct from iterator.
     */
    constructor(size: BigInteger, iterator: Iterator<T>) : this(size, iterator.asSequence())

    /**
     * Returns a [List] containing all elements.
     *
     * The returned instance is [ArrayList] if [totalSize] is less than or equal to [MAX_ARRAY_SIZE],
     * [LinkedList] otherwise.
     *
     * The operation is _terminal_.
     */
    fun toList(): List<T> = toMutableList()

    /**
     * Returns a [MutableList] containing all elements.
     *
     * The returned instance is [ArrayList] if [totalSize] is less than or equal to [MAX_ARRAY_SIZE],
     * [LinkedList] otherwise.
     *
     * The operation is _terminal_.
     */
    fun toMutableList(): MutableList<T> = if (totalSize <= MAX_ARRAY_SIZE) {
        toCollection(ArrayList(totalSize.intValueExact()))
    } else {
        toCollection(LinkedList())
    }

    /**
     * @see [kotlin.streams.asStream]
     */
    fun asStream(): Stream<T> = if (totalSize <= Long.MAX_VALUE.toBigInteger()) {
        StreamSupport.stream(Spliterators.spliterator(
                iterator(), totalSize.longValueExact(), Spliterator.ORDERED), false)
    } else {
        StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator(), Spliterator.ORDERED), false)
    }

    companion object {

        /**
         * The value is 2147483639.
         *
         * @see java.util.ArrayList.MAX_ARRAY_SIZE
         */
        val MAX_ARRAY_SIZE = (Int.MAX_VALUE - 8).toBigInteger()
    }
}
