/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO
import kotlin.streams.toList

internal class CombinatorialSequenceTest {

    @Test
    fun toList() {
        assertEquals(listOf("test"), CombinatorialSequence(ZERO, sequenceOf("test")).toList())
        assertEquals(listOf("test"), CombinatorialSequence(ZERO, sequenceOf("test")).toList())
        assertEquals(listOf('a', 'b', 'c'), CombinatorialSequence(ZERO, ('a'..'c').asSequence()).toList())
    }

    @Test
    fun toMutableList() {
        assertEquals(listOf("test"), CombinatorialSequence(ZERO, sequenceOf("test")).toMutableList())
        assertEquals(listOf("test"), CombinatorialSequence(ZERO, sequenceOf("test")).toMutableList())
        assertEquals(listOf('a', 'b', 'c'), CombinatorialSequence(ZERO, ('a'..'c').asSequence()).toMutableList())
    }

    @Test
    fun asStream() {
        assertEquals(listOf("test"), CombinatorialSequence(ZERO, sequenceOf("test")).asStream().toList())
        assertEquals(listOf("test"), CombinatorialSequence(ZERO, sequenceOf("test")).asStream().toList())
        assertEquals(listOf('a', 'b', 'c'), CombinatorialSequence(ZERO, ('a'..'c').asSequence()).asStream().toList())
    }

    @Test
    fun getTotalSize() {
        assertEquals(ZERO, CombinatorialSequence(ZERO, sequenceOf("test")).totalSize)
        assertEquals(ONE, CombinatorialSequence(ONE, sequenceOf("test")).totalSize)
        assertEquals(123.toBigInteger(), CombinatorialSequence(123.toBigInteger(), sequenceOf("test")).totalSize)
    }

    @Test
    fun test_stream_count() {
        // stream の count() はシーケンスのサイズに関わらず spliterator で指定した totalSize のサイズになる
        for (i in 2..10) {
            val getSequence = { CombinatorialSequence(i.toBigInteger(), sequenceOf("test")) }
            assertEquals(getSequence().totalSize, getSequence().asStream().count().toBigInteger())
            // リストにすると実際の要素の数を取得できる
            assertNotEquals(getSequence().totalSize, getSequence().asStream().toList().count().toBigInteger())
        }
    }
}
