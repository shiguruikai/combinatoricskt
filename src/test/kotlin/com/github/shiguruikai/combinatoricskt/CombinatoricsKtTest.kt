package com.github.shiguruikai.combinatoricskt

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigInteger
import java.util.*

internal class CombinatoricsKtTest {

    @Test
    @DisplayName("シーケンスのサイズが正しいかチェック")
    fun test_check_size() {
        val list = ('A'..'D').toList()
        for (n in list.indices) {
            val values = list.take(n)
            for (r in list.indices) {

                values.permutationsWithRepetition(r).let {
                    val size = n.toBigInteger().pow(r)
                    assertTrue(it.count().toBigInteger() == size && size == it.totalSize)
                }

                values.permutations(r).let {
                    val size = if (n >= 0 && r >= 0 && n >= r) permutations(n, r) else BigInteger.ZERO
                    assertTrue(it.count().toBigInteger() == size && size == it.totalSize)
                }

                values.combinations(r).let {
                    val size = if (n >= 0 && r >= 0 && n >= r) combinations(n, r) else BigInteger.ZERO
                    assertTrue(it.count().toBigInteger() == size && size == it.totalSize)
                }

                values.combinationsWithRepetition(r).let {
                    val size = when {
                        n >= 1 && r >= 0 && (n != 0 || r != 0) -> combinationsWithRepetition(n, r)
                        n >= 0 && r == 0 -> BigInteger.ONE
                        else -> BigInteger.ZERO
                    }
                    assertTrue(it.count().toBigInteger() == size && size == it.totalSize)
                }

                values.cartesianProduct().let {
                    val size = n.toBigInteger()
                    assertTrue(it.count().toBigInteger() == size && size == it.totalSize)
                }

                values.cartesianProduct(values).let {
                    val size = (n * n).toBigInteger()
                    assertTrue(it.count().toBigInteger() == size && size == it.totalSize)
                }

                values.cartesianProduct(values, repeat = r).let {
                    val size = (n * n).toBigInteger().pow(r)
                    assertTrue(it.count().toBigInteger() == size && size == it.totalSize)
                }

                values.powerset().let {
                    val size = BigInteger.TWO.pow(n)
                    assertTrue(it.count().toBigInteger() == size && size == it.totalSize)
                }
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
    @DisplayName("シークエンスの再消費で IllegalStateException が発生するかテスト")
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
        range.permutationsWithRepetition(n).testReuse()
        range.combinations(n).testReuse()
        range.combinationsWithRepetition(n).testReuse()
        range.cartesianProduct().testReuse()
        range.cartesianProduct(range).testReuse()
        range.cartesianProduct(range, repeat = n).testReuse()
        range.powerset().testReuse()
    }
}
