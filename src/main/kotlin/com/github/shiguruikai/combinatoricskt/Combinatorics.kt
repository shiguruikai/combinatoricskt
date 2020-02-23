/*
 * Copyright 2018 shiguruikai
 *
 * Released under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

@file:JvmName("Combinatorics")

package com.github.shiguruikai.combinatoricskt

/**
 * The class [Combinatorics] contains methods for generating combinatorial sequence.
 */
object Combinatorics {

    /**
     * Returns a sequence of permutations of [length] of the elements of [iterable].
     *
     * If [length] is not specified or is null, the default for [length] is the length of [iterable].
     *
     * @throws IllegalArgumentException if [length] is negative.
     */
    @JvmStatic
    @JvmOverloads
    fun <T> permutations(iterable: Iterable<T>, length: Int? = null): CombinatorialSequence<List<T>> =
            PermutationGenerator.generate(iterable, length)

    /**
     * Returns a sequence of permutations with repetition of [length] of the elements of [iterable].
     *
     * @throws IllegalArgumentException if [length] is negative.
     */
    @JvmStatic
    fun <T> permutationsWithRepetition(iterable: Iterable<T>, length: Int): CombinatorialSequence<List<T>> =
            PermutationWithRepetitionGenerator.generate(iterable, length)

    /**
     * Returns a sequence of derangement of the elements of [iterable].
     */
    @JvmStatic
    fun <T> derangements(iterable: Iterable<T>): CombinatorialSequence<List<T>> =
            DerangementGenerator.generate(iterable)

    /**
     * Returns a sequence of combinations of [length] of the elements of [iterable].
     *
     * @throws IllegalArgumentException if [length] is negative.
     */
    @JvmStatic
    fun <T> combinations(iterable: Iterable<T>, length: Int): CombinatorialSequence<List<T>> =
            CombinationGenerator.generate(iterable, length)

    /**
     * Returns a sequence of combinations with repetition of [length] of the elements of [iterable].
     *
     * @throws IllegalArgumentException if [length] is negative.
     */
    @JvmStatic
    fun <T> combinationsWithRepetition(iterable: Iterable<T>, length: Int): CombinatorialSequence<List<T>> =
            CombinationWithRepetitionGenerator.generate(iterable, length)

    /**
     * Returns a sequence of cartesian product of the elements of [iterables].
     *
     * To compute the cartesian product of [iterables] with itself,
     * specify the number of repetitions with the [repeat] named argument.
     *
     * @throws IllegalArgumentException if [repeat] is negative.
     */
    @SafeVarargs
    @JvmStatic
    @JvmOverloads
    fun <T> cartesianProduct(vararg iterables: Iterable<T>, repeat: Int = 1): CombinatorialSequence<List<T>> =
            CartesianProductGenerator.generate(*iterables, repeat = repeat)

    /**
     * Returns a sequence of power set of the elements of [iterable].
     */
    @JvmStatic
    fun <T> powerset(iterable: Iterable<T>): CombinatorialSequence<List<T>> =
            PowerSetGenerator.generate(iterable)

    /**
     * Returns a sequence of permutations of [length] of the elements of [array].
     *
     * If [length] is not specified or is null, the default for [length] is the length of [array].
     *
     * @throws IllegalArgumentException if [length] is negative.
     */
    inline fun <reified T> permutations(array: Array<T>, length: Int? = null): CombinatorialSequence<Array<T>> =
            PermutationGenerator.generate(array, length)

    /**
     * Returns a sequence of permutations with repetition of [length] of the elements of [array].
     *
     * @throws IllegalArgumentException if [length] is negative.
     */
    inline fun <reified T> permutationsWithRepetition(array: Array<T>, length: Int): CombinatorialSequence<Array<T>> =
            PermutationWithRepetitionGenerator.generate(array, length = length)

    /**
     * Returns a sequence of derangement of the elements of [array].
     */
    inline fun <reified T> derangements(array: Array<T>): CombinatorialSequence<Array<T>> =
            DerangementGenerator.generate(array)

    /**
     * Returns a sequence of combinations of [length] of the elements of [array].
     *
     * @throws IllegalArgumentException if [length] is negative.
     */
    inline fun <reified T> combinations(array: Array<T>, length: Int): CombinatorialSequence<Array<T>> =
            CombinationGenerator.generate(array, length)

    /**
     * Returns a sequence of combinations with repetition of [length] of the elements of [array].
     *
     * @throws IllegalArgumentException if [length] is negative.
     */
    inline fun <reified T> combinationsWithRepetition(array: Array<T>, length: Int): CombinatorialSequence<Array<T>> =
            CombinationWithRepetitionGenerator.generate(array, length)

    /**
     * Returns a sequence of cartesian product of the elements of [arrays].
     *
     * To compute the cartesian product of [arrays] with itself,
     * specify the number of repetitions with the [repeat] named argument.
     *
     * @throws IllegalArgumentException if [repeat] is negative.
     */
    inline fun <reified T> cartesianProduct(vararg arrays: Array<T>, repeat: Int = 1): CombinatorialSequence<Array<T>> =
            CartesianProductGenerator.generate(*arrays, repeat = repeat)

    /**
     * Returns a sequence of power set of the elements of [array].
     */
    inline fun <reified T> powerset(array: Array<T>): CombinatorialSequence<Array<T>> =
            PowerSetGenerator.generate(array)
}
