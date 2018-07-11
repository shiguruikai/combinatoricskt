# combinatoricskt

A combinatorics library for Kotlin.

Generate the following sequence from Iterable or Array.  
Iterable または Array から以下のシーケンスを生成する。

- [Permutations](/README.md#permutations) （順列）
- [Permutations with Repetition](/README.md#permutations-with-repetition) （重複順列）
- [Combinations](/README.md#combinations) （組合せ）
- [Combinations with Repetition](/README.md#combinations-with-repetition) （重複組合せ）
- [Cartesian Product](/README.md#cartesian-product) （デカルト積）
- [Power Set](/README.md#power-set) （冪集合）

## Download

Gradle:

```gradle 
implementation 'com.github.shiguruikai:combinatoricskt:1.0.0'
```

Maven:

```xml 
<dependency>
    <groupId>com.github.shiguruikai</groupId>
    <artifactId>combinatoricskt</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

There are three ways to generate a sequence.

1. Use extension functions. (Alias of 2.)
2. Use the generate method of each Generator class.
3. Use the indices method of each Generator class.

The returned [CombinatorialSequence](/src/main/kotlin/com/github/shiguruikai/combinatoricskt/CombinatorialSequence.kt) is Sequence, but it can be iterated only once.

### 1. Use extension functions

See [Itertools.kt](/src/main/kotlin/com/github/shiguruikai/combinatoricskt/Itertools.kt)

Iterable Extension Functions:

```kotlin 
fun <T> Iterable<T>.permutations(length: Int? = null): CombinatorialSequence<List<T>>

fun <T> Iterable<T>.permutationsWithRepetition(length: Int): CombinatorialSequence<List<T>>

fun <T> Iterable<T>.combinations(length: Int): CombinatorialSequence<List<T>>

fun <T> Iterable<T>.combinationsWithRepetition(length: Int): CombinatorialSequence<List<T>>

fun <T> Iterable<T>.cartesianProduct(vararg others: Iterable<T>, repeat: Int = 1): CombinatorialSequence<List<T>>

fun <T> Iterable<T>.powerset(): CombinatorialSequence<List<T>>
```

Array Extension Functions:

```kotlin 
inline fun <reified T> Array<T>.permutations(length: Int? = null): CombinatorialSequence<Array<T>>

inline fun <reified T> Array<T>.permutationsWithRepetition(length: Int): CombinatorialSequence<Array<T>>

inline fun <reified T> Array<T>.combinations(length: Int): CombinatorialSequence<Array<T>>

inline fun <reified T> Array<T>.combinationsWithRepetition(length: Int): CombinatorialSequence<Array<T>>

inline fun <reified T> Array<T>.cartesianProduct(vararg others: Array<T>, repeat: Int = 1): CombinatorialSequence<Array<T>>

inline fun <reified T> Array<T>.powerset(): CombinatorialSequence<Array<T>>
```

### 2. Use the generate method

Example:

```kotlin 
PermutationGenerator.generate('a'..'c', length = 2).forEach(::println)
// [a, b]
// [a, c]
// [b, a]
// [b, c]
// [c, a]
// [c, b]
```

### 3. Use the indices method

The `indices` method generate the indices sequence of IntArray.

Example:

```kotlin 
val array = arrayOf('a', 'b', 'c')

PermutationGenerator.indices(n = 3, r = 2)
        .map { indices: IntArray ->
            indices.map { index -> array[index] }
        }
        .forEach {
            println(it)
        }
// [a, b]
// [a, c]
// [b, a]
// [b, c]
// [c, a]
// [c, b]
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
