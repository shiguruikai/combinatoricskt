[![Maven Central](https://img.shields.io/maven-central/v/com.github.shiguruikai/combinatoricskt.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.shiguruikai%22%20AND%20a%3A%22combinatoricskt%22)
[![MIT License](https://img.shields.io/badge/license-MIT-green.svg)](/LICENSE)

# combinatoricskt

A combinatorics library for Kotlin.

Generate the following sequence from Iterable or Array.  
Iterable または Array から以下のシーケンスを生成する。

- [Permutations](/README.md#permutations) （順列）
- [Permutations with Repetition](/README.md#permutations-with-repetition) （重複順列）
- [Derangements](/README.md#derangements) （完全順列）
- [Combinations](/README.md#combinations) （組合せ）
- [Combinations with Repetition](/README.md#combinations-with-repetition) （重複組合せ）
- [Cartesian Product](/README.md#cartesian-product) （デカルト積）
- [Power Set](/README.md#power-set) （冪集合）

Extension Functions: [Itertools.kt](/src/main/kotlin/com/github/shiguruikai/combinatoricskt/Itertools.kt)

## Download

Gradle:

```gradle 
implementation 'com.github.shiguruikai:combinatoricskt:1.3.0'
```

Maven:

```xml 
<dependency>
    <groupId>com.github.shiguruikai</groupId>
    <artifactId>combinatoricskt</artifactId>
    <version>1.3.0</version>
</dependency>
```

## Permutations

```kotlin 
(1..3).permutations().toList()
// [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]

(1..3).permutations(1).toList()
// [[1], [2], [3]]

(1..3).permutations(2).toList()
// [[1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2]]

(1..3).permutations(3).toList()
// [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]

PermutationGenerator.indices(3, 2).map { it.contentToString() }.toList()
// [[0, 1], [0, 2], [1, 0], [1, 2], [2, 0], [2, 1]]
```

## Permutations with Repetition

```kotlin 
(1..3).permutationsWithRepetition(1).toList()
// [[1], [2], [3]]

(1..3).permutationsWithRepetition(2).toList()
// [[1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3]]

(1..3).permutationsWithRepetition(3).toList()
// [[1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 3], [1, 3, 1], [1, 3, 2], [1, 3, 3], [2, 1, 1], [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 2], [2, 2, 3], [2, 3, 1], [2, 3, 2], [2, 3, 3], [3, 1, 1], [3, 1, 2], [3, 1, 3], [3, 2, 1], [3, 2, 2], [3, 2, 3], [3, 3, 1], [3, 3, 2], [3, 3, 3]]

PermutationWithRepetitionGenerator.indices(3, 2).map { it.contentToString() }.toList()
// [[0, 0], [0, 1], [0, 2], [1, 0], [1, 1], [1, 2], [2, 0], [2, 1], [2, 2]]
```

## Derangements

```kotlin 
(1..3).derangements().toList()
// [[2, 3, 1], [3, 1, 2]]

('a'..'d').derangements().toList()
// [[b, a, d, c], [b, c, d, a], [b, d, a, c], [c, a, d, b], [c, d, a, b], [c, d, b, a], [d, a, b, c], [d, c, a, b], [d, c, b, a]]

DerangementGenerator.indices(4).map { it.contentToString() }.toList()
// [[1, 0, 3, 2], [1, 2, 3, 0], [1, 3, 0, 2], [2, 0, 3, 1], [2, 3, 0, 1], [2, 3, 1, 0], [3, 0, 1, 2], [3, 2, 0, 1], [3, 2, 1, 0]]
```

## Combinations

```kotlin 
(1..3).combinations(1).toList()
// [[1], [2], [3]]

(1..3).combinations(2).toList()
// [[1, 2], [1, 3], [2, 3]]

(1..3).combinations(3).toList()
// [[1, 2, 3]]

CombinationGenerator.indices(3, 2).map { it.contentToString() }.toList()
// [[0, 1], [0, 2], [1, 2]]
```

## Combinations with Repetition

```kotlin 
(1..3).combinationsWithRepetition(1).toList()
// [[1], [2], [3]]

(1..3).combinationsWithRepetition(2).toList()
// [[1, 1], [1, 2], [1, 3], [2, 2], [2, 3], [3, 3]]

(1..3).combinationsWithRepetition(3).toList()
// [[1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 2], [1, 2, 3], [1, 3, 3], [2, 2, 2], [2, 2, 3], [2, 3, 3], [3, 3, 3]]

CombinationWithRepetitionGenerator.indices(3, 2).map { it.contentToString() }.toList()
// [[0, 0], [0, 1], [0, 2], [1, 1], [1, 2], [2, 2]]
```

## Cartesian Product

```kotlin 
(0..1).cartesianProduct('a'..'b').toList()
// [[0, a], [0, b], [1, a], [1, b]]

(0..1).cartesianProduct('a'..'b', listOf('x')).toList()
// [[0, a, x], [0, b, x], [1, a, x], [1, b, x]]

(1..3).cartesianProduct(repeat = 2).toList()
// [[1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3]]

(0..1).cartesianProduct('a'..'b', repeat = 2).toList()
// [[0, a, 0, a], [0, a, 0, b], [0, a, 1, a], [0, a, 1, b], [0, b, 0, a], [0, b, 0, b], [0, b, 1, a], [0, b, 1, b], [1, a, 0, a], [1, a, 0, b], [1, a, 1, a], [1, a, 1, b], [1, b, 0, a], [1, b, 0, b], [1, b, 1, a], [1, b, 1, b]]

CartesianProductGenerator.indices(1, 3, 2).map { it.contentToString() }.toList()
// [[0, 0, 0], [0, 0, 1], [0, 1, 0], [0, 1, 1], [0, 2, 0], [0, 2, 1]]
```

## Power Set

```kotlin 
(1..3).powerset().toList()
// [[], [1], [2], [1, 2], [3], [1, 3], [2, 3], [1, 2, 3]]

PowerSetGenerator.indices(3).map { it.contentToString() }.toList()
// [[], [0], [1], [0, 1], [2], [0, 2], [1, 2], [0, 1, 2]]
```

## License

[MIT](/LICENSE)
