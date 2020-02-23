/*
 * Copyright (c) 2020 shiguruikai
 *
 * Licensed under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

package com.github.shiguruikai.combinatoricskt

class ListComparator<T : Comparable<T>> : Comparator<List<T>> {
    override fun compare(a: List<T>, b: List<T>): Int {
        if (a === b) return 0

        a.asSequence().zip(b.asSequence()).forEach { (oa, ob) ->
            if (oa !== ob) {
                val v = oa.compareTo(ob)
                if (v != 0) {
                    return v
                }
            }
        }

        return a.size - b.size
    }
}
