@file:JvmName("Math")

package com.github.shiguruikai.combinatoricskt

import java.math.BigInteger

fun factorial(n: Int): BigInteger {
    require(n >= 0)
    return factorialHelper(n)
}

fun permutations(n: Int, r: Int): BigInteger {
    require(n >= 0 && r >= 0 && n >= r)
    return permutationsHelper(n, r)
}

fun combinations(n: Int, r: Int): BigInteger {
    require(n >= 0 && r >= 0 && n >= r)
    return permutationsHelper(n, r) / factorialHelper(r)
}

fun combinationsWithRepetition(n: Int, r: Int): BigInteger {
    require(n >= 1 && r >= 0)
    return factorialHelper(n + r - 1) / factorialHelper(r) / factorialHelper(n - 1)
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
    for (i in n - r + 1..n) {
        acc *= i.toBigInteger()
    }
    return acc
}
