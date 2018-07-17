/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT license
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
    val result = ArrayList<R>(newSize)
    repeat(newSize) {
        result += transform(this[it])
    }
    return result
}

@PublishedApi
internal inline fun <reified R> IntArray.mapToArray(newSize: Int = size, transform: (Int) -> R): Array<R> {
    val result = arrayOfNulls<R>(newSize)
    repeat(newSize) {
        result[it] = transform(this[it])
    }
    @Suppress("UNCHECKED_CAST")
    return result as Array<R>
}

@PublishedApi
internal inline fun <reified R, T> Array<T>.mapToArray(newSize: Int = size, transform: (T) -> R): Array<R> {
    val result = arrayOfNulls<R>(newSize)
    repeat(newSize) {
        result[it] = transform(this[it])
    }
    @Suppress("UNCHECKED_CAST")
    return result as Array<R>
}

@Suppress("NOTHING_TO_INLINE")
@PublishedApi
internal inline fun IntArray.swap(i: Int, j: Int) {
    this[i] = this[j].also { this[j] = this[i] }
}
