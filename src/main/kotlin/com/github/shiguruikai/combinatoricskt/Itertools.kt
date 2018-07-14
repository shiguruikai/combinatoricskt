/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

@file:JvmName("Itertools")

package com.github.shiguruikai.combinatoricskt

/** @see PermutationGenerator */
fun <T> Iterable<T>.permutations(length: Int? = null): CombinatorialSequence<List<T>> =
        PermutationGenerator.generate(this, length)

/** @see PermutationWithRepetitionGenerator */
fun <T> Iterable<T>.permutationsWithRepetition(length: Int): CombinatorialSequence<List<T>> =
        PermutationWithRepetitionGenerator.generate(this, length = length)

/** @see DerangementGenerator */
fun <T> Iterable<T>.derangements(): CombinatorialSequence<List<T>> =
        DerangementGenerator.generate(this)

/** @see CombinationGenerator */
fun <T> Iterable<T>.combinations(length: Int): CombinatorialSequence<List<T>> =
        CombinationGenerator.generate(this, length)

/** @see CombinationWithRepetitionGenerator */
fun <T> Iterable<T>.combinationsWithRepetition(length: Int): CombinatorialSequence<List<T>> =
        CombinationWithRepetitionGenerator.generate(this, length)

/** @see CartesianProductGenerator */
fun <T> Iterable<T>.cartesianProduct(vararg others: Iterable<T>, repeat: Int = 1): CombinatorialSequence<List<T>> =
        CartesianProductGenerator.generate(this, *others, repeat = repeat)

/** @see PowerSetGenerator */
fun <T> Iterable<T>.powerset(): CombinatorialSequence<List<T>> =
        PowerSetGenerator.generate(this)

/** @see PermutationGenerator */
inline fun <reified T> Array<T>.permutations(length: Int? = null): CombinatorialSequence<Array<T>> =
        PermutationGenerator.generate(this, length)

/** @see PermutationWithRepetitionGenerator */
inline fun <reified T> Array<T>.permutationsWithRepetition(length: Int): CombinatorialSequence<Array<T>> =
        PermutationWithRepetitionGenerator.generate(this, length = length)

/** @see DerangementGenerator */
inline fun <reified T> Array<T>.derangements(): CombinatorialSequence<Array<T>> =
        DerangementGenerator.generate(this)

/** @see CombinationGenerator */
inline fun <reified T> Array<T>.combinations(length: Int): CombinatorialSequence<Array<T>> =
        CombinationGenerator.generate(this, length)

/** @see CombinationWithRepetitionGenerator */
inline fun <reified T> Array<T>.combinationsWithRepetition(length: Int): CombinatorialSequence<Array<T>> =
        CombinationWithRepetitionGenerator.generate(this, length)

/** @see CartesianProductGenerator */
inline fun <reified T> Array<T>.cartesianProduct(vararg others: Array<T>, repeat: Int = 1): CombinatorialSequence<Array<T>> =
        CartesianProductGenerator.generate(this, *others, repeat = repeat)

/** @see PowerSetGenerator */
inline fun <reified T> Array<T>.powerset(): CombinatorialSequence<Array<T>> =
        PowerSetGenerator.generate(this)
