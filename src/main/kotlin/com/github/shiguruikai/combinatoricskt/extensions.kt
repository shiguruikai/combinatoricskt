@file:Suppress("NOTHING_TO_INLINE")

package com.github.shiguruikai.combinatoricskt

inline fun <T> Iterable<T>.permutations(length: Int? = null) = Combinatorics.permutations(this, length)

inline fun <T> Iterable<T>.permutationsWithRepetition(length: Int) = Combinatorics.permutationsWithRepetition(this, length)

inline fun <T> Iterable<T>.derangements() = Combinatorics.derangements(this)

inline fun <T> Iterable<T>.combinations(length: Int) = Combinatorics.combinations(this, length)

inline fun <T> Iterable<T>.combinationsWithRepetition(length: Int) = Combinatorics.combinationsWithRepetition(this, length)

inline fun <T> Iterable<T>.cartesianProduct(vararg others: Iterable<T>, repeat: Int = 1) = Combinatorics.cartesianProduct(this, *others, repeat = repeat)

inline fun <T> Iterable<T>.powerset() = Combinatorics.powerset(this)

inline fun <reified T> Array<T>.permutations(length: Int? = null) = Combinatorics.permutations(this, length)

inline fun <reified T> Array<T>.permutationsWithRepetition(length: Int) = Combinatorics.permutationsWithRepetition(this, length)

inline fun <reified T> Array<T>.derangements() = Combinatorics.derangements(this)

inline fun <reified T> Array<T>.combinations(length: Int) = Combinatorics.combinations(this, length)

inline fun <reified T> Array<T>.combinationsWithRepetition(length: Int) = Combinatorics.combinationsWithRepetition(this, length)

inline fun <reified T> Array<T>.cartesianProduct(vararg others: Array<T>, repeat: Int = 1) = Combinatorics.cartesianProduct(this, *others, repeat = repeat)

inline fun <reified T> Array<T>.powerset() = Combinatorics.powerset(this)
