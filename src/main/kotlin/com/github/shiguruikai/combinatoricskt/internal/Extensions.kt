/*
 * Copyright (c) 2020 shiguruikai
 *
 * Licensed under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt.internal

@PublishedApi
internal operator fun <T> List<T>.times(n: Int): List<T> {
    if (n <= 0) return emptyList()
    if (n == 1) return this

    val newSize = Math.multiplyExact(size, n)
    val result = ArrayList<T>(newSize)
    repeat(n) {
        result.addAll(this)
    }
    return result
}

@PublishedApi
internal operator fun IntArray.times(n: Int): IntArray {
    if (n <= 0) return intArrayOf()
    if (n == 1) return this

    val newSize = Math.multiplyExact(size, n)
    val result = IntArray(newSize)
    repeat(n) {
        System.arraycopy(this, 0, result, size * it, size)
    }
    return result
}

@PublishedApi
internal inline fun <R> IntArray.mapToList(newSize: Int = size, transform: (Int) -> R): List<R> {
    return List(newSize) { i -> transform(this[i]) }
}

@PublishedApi
internal inline fun <reified R> IntArray.mapToArray(newSize: Int = size, transform: (Int) -> R): Array<R> {
    return Array(newSize) { i -> transform(this[i]) }
}

@PublishedApi
internal inline fun <reified R> IntArray.mapIndexedToArray(newSize: Int = size, transform: (Int, Int) -> R): Array<R> {
    return Array(newSize) { i -> transform(i, this[i]) }
}

@PublishedApi
internal inline fun <reified R, T> Array<T>.mapToArray(newSize: Int = size, transform: (T) -> R): Array<R> {
    return Array(newSize) { i -> transform(this[i]) }
}

@Suppress("NOTHING_TO_INLINE")
@PublishedApi
internal inline fun IntArray.swap(i: Int, j: Int) {
    this[i] = this[j].also { this[j] = this[i] }
}
