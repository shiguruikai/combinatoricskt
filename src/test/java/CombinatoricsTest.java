/*
 * Copyright (c) 2020 shiguruikai
 *
 * Licensed under the MIT license
 * https://github.com/shiguruikai/combinatoricskt/blob/master/LICENSE
 */

import com.github.shiguruikai.combinatoricskt.Combinatorics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CombinatoricsTest {

    private <T> List<List<T>> asList(T[][] array) {
        return Arrays.stream(array).map(Arrays::asList).collect(Collectors.toList());
    }

    @Test
    void permutations() {
        Assertions.assertEquals(
                asList(new Integer[][]{{1, 2, 3}, {1, 3, 2}, {2, 1, 3}, {2, 3, 1}, {3, 1, 2}, {3, 2, 1}}),
                Combinatorics.permutations(Arrays.asList(1, 2, 3)).toList()
        );

        Assertions.assertEquals(
                asList(new Integer[][]{{1, 2}, {1, 3}, {2, 1}, {2, 3}, {3, 1}, {3, 2}}),
                Combinatorics.permutations(Arrays.asList(1, 2, 3), 2).toList()
        );
    }

    @Test
    void permutationsWithRepetition() {
        Assertions.assertEquals(
                asList(new Integer[][]{{1}, {2}, {3}}),
                Combinatorics.permutationsWithRepetition(Arrays.asList(1, 2, 3), 1).toList()
        );

        Assertions.assertEquals(
                asList(new Integer[][]{{1, 1}, {1, 2}, {1, 3}, {2, 1}, {2, 2}, {2, 3}, {3, 1}, {3, 2}, {3, 3}}),
                Combinatorics.permutationsWithRepetition(Arrays.asList(1, 2, 3), 2).toList()
        );
    }

    @Test
    void derangements() {
        Assertions.assertEquals(
                asList(new Integer[][]{{2, 3, 1}, {3, 1, 2}}),
                Combinatorics.derangements(Arrays.asList(1, 2, 3)).toList()
        );
    }

    @Test
    void combinations() {
        Assertions.assertEquals(
                asList(new Integer[][]{{1}, {2}, {3}}),
                Combinatorics.combinations(Arrays.asList(1, 2, 3), 1).toList()
        );

        Assertions.assertEquals(
                asList(new Integer[][]{{1, 2}, {1, 3}, {2, 3}}),
                Combinatorics.combinations(Arrays.asList(1, 2, 3), 2).toList()
        );

        Assertions.assertEquals(
                asList(new Integer[][]{{1, 2, 3}}),
                Combinatorics.combinations(Arrays.asList(1, 2, 3), 3).toList()
        );
    }

    @Test
    void combinationsWithRepetition() {
        Assertions.assertEquals(
                asList(new Integer[][]{{1}, {2}, {3}}),
                Combinatorics.combinationsWithRepetition(Arrays.asList(1, 2, 3), 1).toList()
        );

        Assertions.assertEquals(
                asList(new Integer[][]{{1, 1}, {1, 2}, {1, 3}, {2, 2}, {2, 3}, {3, 3}}),
                Combinatorics.combinationsWithRepetition(Arrays.asList(1, 2, 3), 2).toList()
        );
    }

    @Test
    void cartesianProduct() {
        Assertions.assertEquals(
                asList(new Object[][]{{0, 'a'}, {0, 'b'}, {1, 'a'}, {1, 'b'}}),
                Combinatorics.cartesianProduct(Arrays.asList(0, 1), Arrays.asList('a', 'b')).toList()
        );

        Assertions.assertEquals(
                asList(new Integer[][]{{1, 1}, {1, 2}, {1, 3}, {2, 1}, {2, 2}, {2, 3}, {3, 1}, {3, 2}, {3, 3}}),
                Combinatorics.cartesianProduct(new List[]{Arrays.asList(1, 2, 3)}, 2).toList()
        );
    }

    @Test
    void powerset() {
        Assertions.assertEquals(
                asList(new Integer[][]{{}, {1}, {2}, {1, 2}, {3}, {1, 3}, {2, 3}, {1, 2, 3}}),
                Combinatorics.powerset(Arrays.asList(1, 2, 3)).toList()
        );
    }
}
