/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt

import com.github.shiguruikai.combinatoricskt.internal.combinations
import com.github.shiguruikai.combinatoricskt.internal.combinationsWithRepetition
import com.github.shiguruikai.combinatoricskt.internal.factorial
import com.github.shiguruikai.combinatoricskt.internal.permutations
import com.github.shiguruikai.combinatoricskt.internal.subFactorial
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger
import java.util.*

internal class CombinatoricsKtTest {

    @Test
    @DisplayName("CombinatorialSequence の totalSize と実際のサイズが等しいかテスト")
    fun test_check_size() {
        fun <T> CombinatorialSequence<T>.testCheckSize(expected: BigInteger) {
            assertEquals(expected, totalSize)
            assertEquals(expected, count().toBigInteger())
        }

        val list = ('A'..'D').toList()
        for (n in list.indices) {
            val values = list.take(n)
            for (r in list.indices) {
                values.permutations(r).testCheckSize(if (n >= 0 && r >= 0 && n >= r) permutations(n, r) else BigInteger.ZERO)
                values.permutationsWithRepetition(r).testCheckSize(n.toBigInteger().pow(r))
                values.derangements().testCheckSize(subFactorial(n))
                values.combinations(r).testCheckSize(if (n >= 0 && r >= 0 && n >= r) combinations(n, r) else BigInteger.ZERO)
                values.combinationsWithRepetition(r).testCheckSize(when {
                    n >= 1 && r >= 0 -> combinationsWithRepetition(n, r)
                    r == 0 -> BigInteger.ONE
                    else -> BigInteger.ZERO
                })
                values.cartesianProduct().testCheckSize(n.toBigInteger())
                values.cartesianProduct(values).testCheckSize((n * n).toBigInteger())
                values.cartesianProduct(values, repeat = r).testCheckSize((n * n).toBigInteger().pow(r))
                values.powerset().testCheckSize(BigInteger.TWO.pow(n))
            }
        }
    }

    @Test
    @DisplayName("組合せの関係をテスト")
    fun test_combinatorics() {
        val sortedList: List<Char> = ('A'..'F').sorted()
        val comparator = Comparator<List<Char>> { o1, o2 -> Arrays.compare(o1.toTypedArray(), o2.toTypedArray()) }

        for (n in sortedList.indices) {
            val s = sortedList.take(n)
            for (r in sortedList.indices) {
                val pwr = s.permutationsWithRepetition(r).toList()
                val cwr = s.combinationsWithRepetition(r).toList()
                val perm = s.permutations(r).toList()
                val comb = s.combinations(r).toList()

                // サイズをチェックする
                assertEquals(
                        pwr.count().toBigInteger(),
                        n.toBigInteger().pow(r))
                assertEquals(
                        cwr.count().toBigInteger(),
                        when {
                            n > 0 -> factorial(n + r - 1) / factorial(r) / factorial(n - 1)
                            r == 0 -> BigInteger.ONE
                            else -> BigInteger.ZERO
                        })
                assertEquals(
                        perm.count().toBigInteger(),
                        if (r > n) BigInteger.ZERO else factorial(n) / factorial(n - r))
                assertEquals(
                        comb.count().toBigInteger(),
                        if (r > n) BigInteger.ZERO else factorial(n) / factorial(r) / factorial(n - r))

                // 辞書順に並んでいるかチェックする
                assertEquals(pwr, pwr.sortedWith(comparator))
                assertEquals(cwr, cwr.sortedWith(comparator))
                assertEquals(perm, perm.sortedWith(comparator))
                assertEquals(comb, comb.sortedWith(comparator))

                // 相互関係をチェックする
                assertEquals(cwr, pwr.filter { it.sorted() == it })
                assertEquals(perm, pwr.filter { it.toSet().count() == r })
                assertEquals(comb, perm.filter { it.sorted() == it })
                assertEquals(comb, cwr.filter { it.toSet().count() == r })
                assertEquals(comb, cwr.toSet().filter { it in perm })
                assertEquals(comb, perm.toSet().filter { it in cwr })
                assertEquals(comb, cwr.intersect(perm).sortedWith(comparator))
            }
        }
    }

    @Test
    @DisplayName("Sequence の再消費で IllegalStateException が発生するかテスト")
    fun test_sequence_can_be_consumed_only_once() {
        fun <T> Sequence<T>.testReuse() {
            //assertTrue(this is kotlin.sequences.ConstrainedOnceSequence)
            assertDoesNotThrow { count() }
            assertThrows<IllegalStateException> { count() }
        }

        val range = 0..4
        val n = 2

        range.permutations(n).testReuse()
        range.permutationsWithRepetition(n).testReuse()
        range.derangements().testReuse()
        range.combinations(n).testReuse()
        range.combinationsWithRepetition(n).testReuse()
        range.cartesianProduct().testReuse()
        range.cartesianProduct(range).testReuse()
        range.cartesianProduct(range, repeat = n).testReuse()
        range.powerset().testReuse()
    }

    @Test
    @DisplayName("次の要素がない Iterator で next() を呼んだとき NoSuchElementException が発生するかテスト")
    fun test_iterator() {
        fun <T> CombinatorialSequence<T>.testIterator() {
            val iterator = iterator()
            while (iterator.hasNext()) {
                iterator.next()
            }
            assertThrows<NoSuchElementException> { iterator.next() }
        }

        for (n in 0..4) {
            for (r in 0..4) {
                PermutationGenerator.indices(n, r).testIterator()
                PermutationWithRepetitionGenerator.indices(n, r).testIterator()
                DerangementGenerator.indices(n).testIterator()
                CombinationGenerator.indices(n, r).testIterator()
                CombinationWithRepetitionGenerator.indices(n, r).testIterator()
                CartesianProductGenerator.indices(n, r).testIterator()
                CartesianProductGenerator.indices(n, r, repeat = 2).testIterator()
                PowerSetGenerator.indices(n).testIterator()
            }
        }
    }
}
