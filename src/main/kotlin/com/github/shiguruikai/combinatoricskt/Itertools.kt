@file:JvmName("Itertools")

package com.github.shiguruikai.combinatoricskt

fun <T> Iterable<T>.permutations(length: Int? = null): CombinatorialSequence<List<T>> =
        PermutationGenerator.generate(this, length)

fun <T> Iterable<T>.permutationsWithRepetition(length: Int): CombinatorialSequence<List<T>> =
        PermutationWithRepetitionGenerator.generate(this, length = length)

fun <T> Iterable<T>.combinations(length: Int): CombinatorialSequence<List<T>> =
        CombinationGenerator.generate(this, length)

fun <T> Iterable<T>.combinationsWithRepetition(length: Int): CombinatorialSequence<List<T>> =
        CombinationWithRepetitionGenerator.generate(this, length)

fun <T> Iterable<T>.cartesianProduct(vararg others: Iterable<T>, repeat: Int = 1): CombinatorialSequence<List<T>> =
        CartesianProductGenerator.generate(this, *others, repeat = repeat)

fun <T> Iterable<T>.powerset(): CombinatorialSequence<List<T>> =
        PowerSetGenerator.generate(this)

inline fun <reified T> Array<T>.permutations(length: Int? = null): CombinatorialSequence<Array<T>> =
        PermutationGenerator.generate(this, length)

inline fun <reified T> Array<T>.permutationsWithRepetition(length: Int): CombinatorialSequence<Array<T>> =
        PermutationWithRepetitionGenerator.generate(this, length = length)

inline fun <reified T> Array<T>.combinations(length: Int): CombinatorialSequence<Array<T>> =
        CombinationGenerator.generate(this, length)

inline fun <reified T> Array<T>.combinationsWithRepetition(length: Int): CombinatorialSequence<Array<T>> =
        CombinationWithRepetitionGenerator.generate(this, length)

inline fun <reified T> Array<T>.cartesianProduct(vararg others: Array<T>, repeat: Int = 1): CombinatorialSequence<Array<T>> =
        CartesianProductGenerator.generate(this, *others, repeat = repeat)

inline fun <reified T> Array<T>.powerset(): CombinatorialSequence<Array<T>> =
        PowerSetGenerator.generate(this)
