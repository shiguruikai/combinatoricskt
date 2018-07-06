package com.github.shiguruikai.combinatoricskt

object CartesianProductGenerator {

    @JvmStatic
    fun <T> generate(vararg iterables: Iterable<T>): CombinatorialSequence<List<T>> {
        return PermutationWithRepetitionGenerator.generate(*iterables, length = 1)
    }

    inline fun <reified T> generate(vararg arrays: Array<T>): CombinatorialSequence<Array<T>> {
        return PermutationWithRepetitionGenerator.generate(*arrays, length = 1)
    }
}
