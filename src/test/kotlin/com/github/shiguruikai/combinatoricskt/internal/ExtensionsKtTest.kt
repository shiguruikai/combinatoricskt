/*
 * Copyright (c) 2020 shiguruikai
 *
 * Licensed under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt.internal

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ExtensionsKtTest {

    @Test
    fun test_List_times() {
        val list = listOf(1, 2, 3)
        assertTrue(list === list * 1)
        assertIterableEquals(emptyList<Any>(), list * 0)
        assertIterableEquals(list, list * 1)
        assertIterableEquals(list + list, list * 2)
        assertIterableEquals(list + list + list, list * 3)
    }

    @Test
    fun test_IntArray_times() {
        val ints = intArrayOf(1, 2, 3)
        assertTrue(ints === ints * 1)
        assertArrayEquals(intArrayOf(), ints * 0)
        assertArrayEquals(ints, ints * 1)
        assertArrayEquals(ints + ints, ints * 2)
        assertArrayEquals(ints + ints + ints, ints * 3)
    }

    @Test
    fun test_IntArray_mapToList() {
        assertIterableEquals(listOf(1, 2, 3), intArrayOf(1, 2, 3, 4, 5).mapToList(3) { it })
        assertIterableEquals(listOf("1", "2", "3"), intArrayOf(1, 2, 3).mapToList { it.toString() })
    }

    @Test
    fun test_IntArray_mapToArray() {
        assertArrayEquals(arrayOf(1, 2, 3), intArrayOf(1, 2, 3, 4, 5).mapToArray(3) { it })
        assertArrayEquals(arrayOf("1", "2", "3"), intArrayOf(1, 2, 3).mapToArray { it.toString() })
    }

    @Test
    fun test_Array_mapToArray() {
        assertArrayEquals(arrayOf(1, 2, 3), arrayOf(1, 2, 3, 4, 5).mapToArray(3) { it })
        assertArrayEquals(arrayOf("1", "2", "3"), arrayOf(1, 2, 3).mapToArray { it.toString() })
    }

    @Test
    fun test_IntArray_swap() {
        assertArrayEquals(intArrayOf(1, 0), intArrayOf(0, 1).apply { swap(0, 1) })
        assertArrayEquals(intArrayOf(1, 3, 2), intArrayOf(1, 2, 3).apply { swap(2, 1) })
    }
}
