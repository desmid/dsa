import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlgorithmTest {

    // test sort

    @Test
    public void test_010_empty_array_is_sorted() {
        int[] source = new int[] {};
        int[] expect = new int[] {};
        int[] result = Algorithm.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_020_singleton_array_is_sorted() {
        int[] source = new int[] {1};
        int[] expect = new int[] {1};
        int[] result = Algorithm.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_030_ordered_pair_is_sorted() {
        int[] source = new int[] {1, 2};
        int[] expect = new int[] {1, 2};
        int[] result = Algorithm.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_040_reversed_pair_is_sorted() {
        int[] source = new int[] {2, 1};
        int[] expect = new int[] {1, 2};
        int[] result = Algorithm.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_050_example_with_unique_ints_is_sorted() {
        int[] source = new int[] {7, 1, 0, 9};
        int[] expect = new int[] {0, 1, 7, 9};
        int[] result = Algorithm.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_060_example_with_repeats_is_sorted() {
        int[] source = new int[] {4, 1, 3, 4, 3};
        int[] expect = new int[] {1, 3, 3, 4, 4};
        int[] result = Algorithm.countingSort(source);
        assertArrayEquals(expect, result);
    }

}
