[![JitPack](https://jitpack.io/v/shiguruikai/combinatoricskt.svg)](https://jitpack.io/#shiguruikai/combinatoricskt)

# combinatoricskt

A combinatorics library for Kotlin.

Generate the following sequence from Iterable or Array.  
Iterable または Array から以下のシーケンスを生成する。

- Permutations （順列）
- Permutations With Repetition （重複順列）
- Combinations （組合せ）
- Combinations With Repetition （重複組合せ）
- Cartesian Product （デカルト積）
- Power Set （冪集合）

## Download

Add the following to `build.gradle` file:

```gradle 
repositories {
    maven { url "https://jitpack.io" }
}
 
dependencies {
    implementation 'com.github.shiguruikai:combinatoricskt:0.2.0'
}
```

## Extension Functions

Iterable:

```kotlin 
fun <T> Iterable<T>.permutations(length: Int? = null): CombinatorialSequence<List<T>>

fun <T> Iterable<T>.permutationsWithRepetition(length: Int): CombinatorialSequence<List<T>>

fun <T> Iterable<T>.permutationsWithRepetition(vararg others: Iterable<T>, length: Int): CombinatorialSequence<List<T>>

fun <T> Iterable<T>.combinations(length: Int): CombinatorialSequence<List<T>>

fun <T> Iterable<T>.combinationsWithRepetition(length: Int): CombinatorialSequence<List<T>>

fun <T> Iterable<T>.cartesianProduct(): CombinatorialSequence<List<T>>

fun <T> Iterable<T>.cartesianProduct(vararg others: Iterable<T>): CombinatorialSequence<List<T>>

fun <T> Iterable<T>.powerset(): CombinatorialSequence<List<T>>
```

Array:

```kotlin 
inline fun <reified T> Array<T>.permutations(length: Int? = null): CombinatorialSequence<Array<T>>

inline fun <reified T> Array<T>.permutationsWithRepetition(length: Int): CombinatorialSequence<Array<T>>

inline fun <reified T> Array<T>.permutationsWithRepetition(vararg others: Array<T>, length: Int): CombinatorialSequence<Array<T>>

inline fun <reified T> Array<T>.combinations(length: Int): CombinatorialSequence<Array<T>>

inline fun <reified T> Array<T>.combinationsWithRepetition(length: Int): CombinatorialSequence<Array<T>>

inline fun <reified T> Array<T>.cartesianProduct(): CombinatorialSequence<Array<T>>

inline fun <reified T> Array<T>.cartesianProduct(vararg others: Array<T>): CombinatorialSequence<Array<T>>

inline fun <reified T> Array<T>.powerset(): CombinatorialSequence<Array<T>>
```
