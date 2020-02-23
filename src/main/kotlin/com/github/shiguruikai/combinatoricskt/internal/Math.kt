/*
 * Copyright (c) 2020 shiguruikai
 *
 * Licensed under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

@file:JvmName("Math")

package com.github.shiguruikai.combinatoricskt.internal

import java.math.BigInteger
import kotlin.math.max

@PublishedApi
internal fun factorial(n: Int): BigInteger {
    require(n >= 0) { "n must be non-negative, was $n" }
    return factorialHelper(n)
}

@PublishedApi
internal fun permutations(n: Int, r: Int): BigInteger {
    require(n >= 0 && r >= 0 && n >= r)
    return permutationsHelper(n, r)
}

@PublishedApi
internal fun combinations(n: Int, r: Int): BigInteger {
    require(n >= 0 && r >= 0 && n >= r)
    return permutationsHelper(n, r) / factorialHelper(r)
}

@PublishedApi
internal fun combinationsWithRepetition(n: Int, r: Int): BigInteger {
    require(n >= 1 && r >= 0)
    return factorialHelper(n + r - 1) / factorialHelper(r) / factorialHelper(n - 1)
}

@PublishedApi
internal fun subFactorial(n: Int): BigInteger {
    require(n >= 0) { "n must be non-negative, was $n" }
    if (n == 0) return BigInteger.ONE
    var prev = BigInteger.ONE
    var acc = BigInteger.ZERO
    for (i in 2..n) {
        val next = (i - 1).toBigInteger() * (acc + prev)
        prev = acc
        acc = next
    }
    return acc
}

private fun factorialHelper(n: Int): BigInteger {
    var acc = BigInteger.ONE
    for (i in 2..n) {
        acc *= i.toBigInteger()
    }
    return acc
}

private fun permutationsHelper(n: Int, r: Int): BigInteger {
    var acc = BigInteger.ONE
    for (i in max(2, n - r + 1)..n) {
        acc *= i.toBigInteger()
    }
    return acc
}
