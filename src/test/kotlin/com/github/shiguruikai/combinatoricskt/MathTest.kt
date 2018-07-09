package com.github.shiguruikai.combinatoricskt

import com.github.shiguruikai.combinatoricskt.internal.combinations
import com.github.shiguruikai.combinatoricskt.internal.combinationsWithRepetition
import com.github.shiguruikai.combinatoricskt.internal.factorial
import com.github.shiguruikai.combinatoricskt.internal.permutations
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class MathTest {

    @Test
    fun test_factorial() {
        for (n in -2..2) {
            if (n < 0) {
                assertThrows<IllegalArgumentException> { factorial(n) }
            } else {
                assertDoesNotThrow { factorial(n) }
            }
        }

        val expectedArray = intArrayOf(1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880)
        expectedArray.forEachIndexed { i, expected ->
            assertEquals(expected, factorial(i).intValueExact())
        }
    }

    @Test
    fun test_permutations() {
        for (n in -2..6) {
            for (r in -2..6) {
                if (n < 0 || r < 0 || n < r) {
                    assertThrows<IllegalArgumentException> { permutations(n, r) }
                } else {
                    assertEquals(permutations(n, r), factorial(n) / factorial(n - r))
                }
            }
        }

        val expectedArray = intArrayOf(1, 10, 90, 720, 5040, 30240, 151200, 604800, 1814400, 3628800)
        expectedArray.forEachIndexed { i: Int, expected: Int ->
            assertEquals(expected, permutations(expectedArray.size, i).intValueExact())
        }
    }

    @Test
    fun test_combinations() {
        for (n in -2..6) {
            for (r in -2..6) {
                if (n < 0 || r < 0 || n < r) {
                    assertThrows<IllegalArgumentException> { combinations(n, r) }
                } else {
                    assertEquals(combinations(n, r), factorial(n) / factorial(r) / factorial(n - r))
                }
            }
        }

        val expectedArray = intArrayOf(1, 10, 45, 120, 210, 252, 210, 120, 45, 10)
        expectedArray.forEachIndexed { i: Int, expected: Int ->
            assertEquals(expected, combinations(expectedArray.size, i).intValueExact())
        }
    }

    @Test
    fun test_combinationsWithRepetition() {
        for (n in -2..6) {
            for (r in -2..6) {
                if (n < 1 || r < 0) {
                    assertThrows<IllegalArgumentException> { combinationsWithRepetition(n, r) }
                } else {
                    assertEquals(combinationsWithRepetition(n, r),
                            factorial(n + r - 1) / factorial(r) / factorial(n - 1))
                }
            }
        }

        val expectedArray = intArrayOf(1, 10, 55, 220, 715, 2002, 5005, 11440, 24310, 48620)
        expectedArray.forEachIndexed { i: Int, expected: Int ->
            assertEquals(expected, combinationsWithRepetition(expectedArray.size, i).intValueExact())
        }
    }
}
