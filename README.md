[![JitPack](https://jitpack.io/v/shiguruikai/combinatoricskt.svg)](https://jitpack.io/#shiguruikai/combinatoricskt)

# combinatoricskt

A combinatorics library for Kotlin.

Generate the following sequence from Iterable or Array.  
Iterable または Array から以下のシーケンスを生成する。

- Permutations （順列）
- Permutations with Repetition （重複順列）
- Combinations （組合せ）
- Combinations with Repetition （重複組合せ）
- Cartesian Product （デカルト積）
- Power Set （冪集合）

## Download

Add the following to `build.gradle` file:

```gradle 
repositories {
    maven { url "https://jitpack.io" }
}
 
dependencies {
    implementation 'com.github.shiguruikai:combinatoricskt:0.5.0'
}
```

## Usage

[CombinatorialSequence](/src/main/kotlin/com/github/shiguruikai/combinatoricskt/CombinatorialSequence.kt) is Sequence, but it can be iterated only once.

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

### Permutations （順列）

```kotlin 
(1..3).permutations().toList()
// [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]

(1..3).permutations(1).toList()
// [[1], [2], [3]]

(1..3).permutations(2).toList()
// [[1, 2], [1, 3], [2, 1], [2, 3], [3, 1], [3, 2]]

(1..3).permutations(3).toList()
// [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
```

### Permutations With Repetition （重複順列）

```kotlin 
(1..3).permutationsWithRepetition(1).toList()
// [[1], [2], [3]]

(1..3).permutationsWithRepetition(2).toList()
// [[1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3]]

(1..3).permutationsWithRepetition(3).toList()
// [[1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 1], [1, 2, 2], [1, 2, 3], [1, 3, 1], [1, 3, 2], [1, 3, 3], [2, 1, 1], [2, 1, 2], [2, 1, 3], [2, 2, 1], [2, 2, 2], [2, 2, 3], [2, 3, 1], [2, 3, 2], [2, 3, 3], [3, 1, 1], [3, 1, 2], [3, 1, 3], [3, 2, 1], [3, 2, 2], [3, 2, 3], [3, 3, 1], [3, 3, 2], [3, 3, 3]]
```

### Combinations （組合せ）

```kotlin 
(1..3).combinations(1).toList()
// [[1], [2], [3]]

(1..3).combinations(2).toList()
// [[1, 2], [1, 3], [2, 3]]

(1..3).combinations(3).toList()
// [[1, 2, 3]]
```

### Combinations With Repetition （重複組合せ）

```kotlin 
(1..3).combinationsWithRepetition(1).toList()
// [[1], [2], [3]]

(1..3).combinationsWithRepetition(2).toList()
// [[1, 1], [1, 2], [1, 3], [2, 2], [2, 3], [3, 3]]

(1..3).combinationsWithRepetition(3).toList()
// [[1, 1, 1], [1, 1, 2], [1, 1, 3], [1, 2, 2], [1, 2, 3], [1, 3, 3], [2, 2, 2], [2, 2, 3], [2, 3, 3], [3, 3, 3]]
```

### Cartesian Product （デカルト積）

```kotlin 
(1..3).cartesianProduct(repeat = 1).toList()
// [[1], [2], [3]]

(1..3).cartesianProduct(repeat = 2).toList()
// [[1, 1], [1, 2], [1, 3], [2, 1], [2, 2], [2, 3], [3, 1], [3, 2], [3, 3]]

(0..1).cartesianProduct('a'..'b').toList()
// [[0, a], [0, b], [1, a], [1, b]]

(0..1).cartesianProduct('a'..'b', listOf('x')).toList()
// [[0, a, x], [0, b, x], [1, a, x], [1, b, x]]

(0..1).cartesianProduct('a'..'b', repeat = 2).toList()
// [[0, a, 0, a], [0, a, 0, b], [0, a, 1, a], [0, a, 1, b], [0, b, 0, a], [0, b, 0, b], [0, b, 1, a], [0, b, 1, b], [1, a, 0, a], [1, a, 0, b], [1, a, 1, a], [1, a, 1, b], [1, b, 0, a], [1, b, 0, b], [1, b, 1, a], [1, b, 1, b]]

```
### Power Set （冪集合）

```kotlin 
(1..3).powerset().toList()
// [[], [1], [2], [1, 2], [3], [1, 3], [2, 3], [1, 2, 3]]
```

## License
[MIT](/LICENSE)
