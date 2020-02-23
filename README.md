[![Maven Central](https://img.shields.io/maven-central/v/com.github.shiguruikai/combinatoricskt.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.shiguruikai%22%20AND%20a%3A%22combinatoricskt%22)
[![MIT License](https://img.shields.io/badge/license-MIT-green.svg)](/LICENSE)

# combinatoricskt

A combinatorics library for Kotlin.

Generate the following sequence from Iterable or Array.<br>
Iterable または Array から以下のシーケンスを生成する。

- [Permutations](/README.md#permutations) （順列）
- [Permutations with Repetition](/README.md#permutations-with-repetition) （重複順列）
- [Derangements](/README.md#derangements) （完全順列）
- [Combinations](/README.md#combinations) （組合せ）
- [Combinations with Repetition](/README.md#combinations-with-repetition) （重複組合せ）
- [Cartesian Product](/README.md#cartesian-product) （デカルト積）
- [Power Set](/README.md#power-set) （冪集合）

## Download

Gradle Groovy DSL
```groovy
implementation 'com.github.shiguruikai:combinatoricskt:1.6.0'
```

Gradle Kotlin DSL
```kotlin
implementation("com.github.shiguruikai:combinatoricskt:1.6.0")
```

Apache Maven
```xml
<dependency>
  <groupId>com.github.shiguruikai</groupId>
  <artifactId>combinatoricskt</artifactId>
  <version>1.6.0</version>
</dependency>
```

## Permutations

```kotlin
listOf(1, 2, 3).permutations()   // [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
listOf(1, 2, 3).permutations(0)  // [[]]
listOf(1, 2, 3).permutations(1)  // [[1], [2], [3]]
listOf(1, 2, 3).permutations(2)  // [[1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2]]
listOf(1, 2, 3).permutations(3)  // [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
listOf(1, 2, 3).permutations(4)  // []
Combinatorics.permutations(listOf(1, 2, 3))  // [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
PermutationsGenerator.indices(3, 2)          // [[0, 1], [0, 2], [1, 0], [1, 2], [2, 0], [2, 1]]
```

## Permutations with Repetition

```kotlin
listOf(1, 2, 3).permutationsWithRepetition(0)  // [[]]
listOf(1, 2, 3).permutationsWithRepetition(1)  // [[1], [2], [3]]
listOf(1, 2, 3).permutationsWithRepetition(2)  // [[1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3]]
listOf(1, 2, 3).permutationsWithRepetition(3)  // [[1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 3], [1, 3, 1], [1, 3, 2], [1, 3, 3], [2, 1, 1], [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 2], [2, 2, 3], [2, 3, 1], [2, 3, 2], [2, 3, 3], [3, 1, 1], [3, 1, 2], [3, 1, 3], [3, 2, 1], [3, 2, 2], [3, 2, 3], [3, 3, 1], [3, 3, 2], [3, 3, 3]]
Combinatorics.permutationsWithRepetition(listOf(1, 2, 3), 2)  // [[1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3]]
PermutationsWithRepetitionGenerator.indices(3, 2)             // [[0, 0], [0, 1], [0, 2], [1, 0], [1, 1], [1, 2], [2, 0], [2, 1], [2, 2]]
```

## Derangements

```kotlin
listOf(1).derangements()           // []
listOf(1, 2).derangements()        // [[2, 1]]
listOf(1, 2, 3).derangements()     // [[2, 3, 1], [3, 1, 2]]
listOf(1, 2, 3, 4).derangements()  // [[2, 1, 4, 3], [2, 3, 4, 1], [2, 4, 1, 3], [3, 1, 4, 2], [3, 4, 1, 2], [3, 4, 2, 1], [4, 1, 2, 3], [4, 3, 1, 2], [4, 3, 2, 1]]
Combinatorics.derangements(listOf(1, 2, 3))  // [[2, 3, 1], [3, 1, 2]]
DerangementGenerator.indices(3)              // [[1, 2, 0], [2, 0, 1]]
```

## Combinations

```kotlin
listOf(1, 2, 3).combinations(0)  // [[]]
listOf(1, 2, 3).combinations(1)  // [[1], [2], [3]]
listOf(1, 2, 3).combinations(2)  // [[1, 2], [1, 3], [2, 3]]
listOf(1, 2, 3).combinations(3)  // [[1, 2, 3]]
listOf(1, 2, 3).combinations(4)  // []
Combinatorics.combinations(listOf(1, 2, 3), 2)  // [[1, 2], [1, 3], [2, 3]]
CombinationsGenerator.indices(3, 2)             // [[0, 1], [0, 2], [1, 2]]
```

## Combinations with Repetition

```kotlin
listOf(1, 2, 3).combinationsWithRepetition(0)  // [[]]
listOf(1, 2, 3).combinationsWithRepetition(1)  // [[1], [2], [3]]
listOf(1, 2, 3).combinationsWithRepetition(2)  // [[1, 1], [1, 2], [1, 3], [2, 2], [2, 3], [3, 3]]
listOf(1, 2, 3).combinationsWithRepetition(3)  // [[1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 2], [1, 2, 3], [1, 3, 3], [2, 2, 2], [2, 2, 3], [2, 3, 3], [3, 3, 3]]
listOf(1, 2, 3).combinationsWithRepetition(4)  // [[1, 1, 1, 1], [1, 1, 1, 2], [1, 1, 1, 3], [1, 1, 2, 2], [1, 1, 2, 3], [1, 1, 3, 3], [1, 2, 2, 2], [1, 2, 2, 3], [1, 2, 3, 3], [1, 3, 3, 3], [2, 2, 2, 2], [2, 2, 2, 3], [2, 2, 3, 3], [2, 3, 3, 3], [3, 3, 3, 3]]
Combinatorics.combinationsWithRepetition(listOf(1, 2, 3), 2) // [[1, 1], [1, 2], [1, 3], [2, 2], [2, 3], [3, 3]]
CombinationsWithRepetitionGenerator.indices(3, 2)            // [[0, 0], [0, 1], [0, 2], [1, 1], [1, 2], [2, 2]]
```

## Cartesian Product

```kotlin
listOf(1, 2).cartesianProduct(listOf(3, 4, 5), listOf(6))        // [[1, 3, 6], [1, 4, 6], [1, 5, 6], [2, 3, 6], [2, 4, 6], [2, 5, 6]]
listOf('A', 'B').cartesianProduct(listOf('X', 'Y'))              // [[A, X], [A, Y], [B, X], [B, Y]]
listOf('A', 'B').cartesianProduct(listOf('X', 'Y'), repeat = 2)  // [[A, X, A, X], [A, X, A, Y], [A, X, B, X], [A, X, B, Y], [A, Y, A, X], [A, Y, A, Y], [A, Y, B, X], [A, Y, B, Y], [B, X, A, X], [B, X, A, Y], [B, X, B, X], [B, X, B, Y], [B, Y, A, X], [B, Y, A, Y], [B, Y, B, X], [B, Y, B, Y]]
Combinatorics.cartesianProduct(listOf("Java"), listOf(8, 11))    // [[Java, 8], [Java, 11]]
CartesianProductGenerator.indices(2, 2, repeat = 1)              // [[0, 0], [0, 1], [1, 0], [1, 1]]
```

## Power Set

```kotlin
listOf(1, 2, 3).powerset()                // [[], [1], [2], [1, 2], [3], [1, 3], [2, 3], [1, 2, 3]]
Combinatorics.powerset(listOf('A', 'B'))  // [[], [A], [B], [A, B]]
PowerSetGenerator.indices(3)              // [[], [0], [1], [0, 1], [2], [0, 2], [1, 2], [0, 1, 2]]
```

## License

[MIT](/LICENSE)
