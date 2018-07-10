package com.github.shiguruikai.combinatoricskt.internal

@PublishedApi
internal operator fun <T> List<T>.times(n: Int): List<T> {
    if (n <= 0) return emptyList()
    if (n == 1) return this

    val result = toMutableList()
    repeat(n - 1) {
        result.addAll(this)
    }
    return result
}

internal inline fun <R> IntArray.mapToList(listSize: Int = size, transform: (Int) -> R): List<R> {
    val result = ArrayList<R>(listSize)
    repeat(listSize) {
        result += transform(this[it])
    }
    return result
}

@PublishedApi
internal inline fun <reified R> IntArray.mapToArray(arraySize: Int = size, transform: (Int) -> R): Array<R> {
    val result = arrayOfNulls<R>(arraySize)
    repeat(arraySize) {
        result[it] = transform(this[it])
    }
    @Suppress("UNCHECKED_CAST")
    return result as Array<R>
}
