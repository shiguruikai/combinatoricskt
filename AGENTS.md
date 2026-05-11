# Project Context for AI Agents

This document provides essential context and guidelines for AI agents working on the `combinatoricskt` library.

## Project Overview

`combinatoricskt` is a lightweight, high-performance combinatorics library for Kotlin/JVM. It provides sequences for
permutations, combinations, derangements, cartesian products, and power sets.

### Core Principles

1. **Efficiency First**: Combinatorial operations often deal with massive result sets. Implementation must prioritize
   lazy evaluation (using `Sequence`) and minimize memory allocations.
2. **API Consistency**: Maintain a consistent API between `Iterable` and `Array` extensions.
3. **Correctness**: Mathematical accuracy is paramount. Always verify changes with comprehensive unit tests, including
   edge cases like empty inputs or `r > n`.

## Architectural Patterns

### CombinatorialSequence

- All generators return a `CombinatorialSequence<T>`.
- It extends Kotlin's `Sequence<T>` and adds a `totalSize: BigInteger` property.
- **Rule**: Never use `totalSize` to pre-allocate memory unless it's guaranteed to be within safe limits (see
  `MAX_ARRAY_SIZE` in `CombinatorialSequence.kt`).
- **Rule**: When implementing `asStream()`, always provide `Spliterator.SIZED` and `SUBSIZED` if `totalSize` fits in a
  `Long`.

### Generator Objects

- Generators (e.g., `PermutationsGenerator`) follow a pattern:
    1. A public `generate` or `indices` function.
    2. An internal `@PublishedApi` `build` function that creates the `Iterator`.
- **Performance Note**: Prefer primitive `IntArray` for internal index tracking to avoid boxing.

## Coding Standards

### Inline and Generic Functions

- Many functions are `inline` with `reified` type parameters for `Array` support.
- Use `@PublishedApi` for internal functions called by public inline functions.
- **Warning**: Be careful with variable shadowing in mapping extensions. Always use explicit parameter names (e.g.,
  `i -> ...`) instead of implicit `it`.

### Mathematical Utilities

- Located in `com.github.shiguruikai.combinatoricskt.internal.Math.kt`.
- Use `BigInteger` for all combinatorial counts to prevent overflow.
- Implement optimizations like the multiplicative formula for combinations to avoid large intermediate `factorial`
  calculations.

## Testing Standards

- **JUnit 5**: Use `org.junit.jupiter.api`.
- **Comparison**: For large sequences, compare only a subset or the total size instead of converting to a list.
- **Modern JDKs**: Be aware that modern JDKs (16+) may have optimizations in `Stream` collectors that strictly check
  `Spliterator` sizes. Ensure `totalSize` is accurate.

## Modernization History (2026)

- Project was updated to Kotlin 2.3.21 and JDK 17.
- Gradle updated to 9.x.
- Major optimization of `CartesianProductGenerator` (Array) to eliminate `flatMap` overhead.
- Refactored array creation to use idiomatic `Array(size) { ... }`.
