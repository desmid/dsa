import java.util.Arrays;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

class Pair {
    char c;
    int v;
    Pair(char c, int v) { this.c = c; this.v = v; }
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Pair)) return false;
        Pair other = (Pair) o;
        return this.c == other.c && this.v == other.v;
    }
    @Override
    public String toString() {
        return "(" + String.valueOf(c) + ", " + String.valueOf(v) + ")";
    }
    public int firstKey() { return c; }
    public int secondKey() { return v; }
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CountingSortTest {

    // test sort

    @Test
    public void test_010_empty_array_is_sorted() {
        Integer[] source = new Integer[] {};
        Integer[] expect = new Integer[] {};
        Integer[] result = CountingSort.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_020_singleton_is_sorted() {
        Integer[] source = new Integer[] {1};
        Integer[] expect = new Integer[] {1};
        Integer[] result = CountingSort.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_030_ordered_pair_is_sorted() {
        Integer[] source = new Integer[] {1, 2};
        Integer[] expect = new Integer[] {1, 2};
        Integer[] result = CountingSort.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_040_reversed_pair_is_sorted() {
        Integer[] source = new Integer[] {2, 1};
        Integer[] expect = new Integer[] {1, 2};
        Integer[] result = CountingSort.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_050_example_with_negative_start_is_sorted() {
        Integer[] source = new Integer[] {4, -1, 3, 4, 3};
        Integer[] expect = new Integer[] {-1, 3, 3, 4, 4};
        Integer[] result = CountingSort.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_060_example_with_zero_start_is_sorted() {
        Integer[] source = new Integer[] {4, 0, 3, 4, 3};
        Integer[] expect = new Integer[] {0, 3, 3, 4, 4};
        Integer[] result = CountingSort.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_070_example_with_unique_ints_is_sorted() {
        Integer[] source = new Integer[] {7, 1, 0, 9};
        Integer[] expect = new Integer[source.length];
        System.arraycopy(source, 0, expect, 0, source.length);
        Arrays.sort(expect);
        Integer[] result = CountingSort.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_080_example_with_repeats_is_sorted() {
        Integer[] source = new Integer[] {4, 1, 3, 4, 3};
        Integer[] expect = new Integer[] {1, 3, 3, 4, 4};
        Integer[] result = CountingSort.countingSort(source);
        assertArrayEquals(expect, result);
    }

    // test sort with explicit null key

    @Test()
    public void test_090_sort_array_with_null_key_uses_identity() {
        Integer[] source = new Integer[] {4, 1, 3, 4, 3};
        Integer[] expect = new Integer[] {1, 3, 3, 4, 4};
        Integer[] result = CountingSort.countingSort(source, null);  // note
        assertArrayEquals(expect, result);
    }

    // test sort with explicit key

    @Test
    public void test_100_sort_empty_array_with_explicit_key_is_sorted() {
        Integer[] source = new Integer[] {};
        Integer[] expect = new Integer[] {};
        Integer[] result = CountingSort.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_110_sort_singleton_with_explicit_key_is_sorted() {
        Integer[] source = new Integer[] {1};
        Integer[] expect = new Integer[] {1};
        Integer[] result = CountingSort.countingSort(source);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_120_sort_example_with_explicit_key_is_sorted() {
        Integer[] source = new Integer[] {4, 1, 3, 4, 3};
        Integer[] expect = new Integer[] {1, 3, 3, 4, 4};
        Integer[] result = CountingSort.countingSort(source);
        assertArrayEquals(expect, result);
    }

    // test sort stability

    @Test
    public void test_130_sort_by_second_then_first_fields_is_stable() {
        Pair[] source = new Pair[] {new Pair('b', 3), new Pair('b', 4), new Pair('c', 2), new Pair('c', 1), new Pair('a', 4)};
        Pair[] expect = new Pair[] {new Pair('a', 4), new Pair('b', 3), new Pair('b', 4), new Pair('c', 1), new Pair('c', 2)};
        Pair[] result;
        result = CountingSort.countingSort(source, Pair::secondKey);
        result = CountingSort.countingSort(result, Pair::firstKey);
        assertArrayEquals(expect, result);
    }

    @Test
    public void test_140_sort_by_first_then_second_fields_is_stable() {
        Pair[] source = new Pair[] {new Pair('b', 3), new Pair('b', 4), new Pair('c', 2), new Pair('c', 1), new Pair('a', 4)};
        Pair[] expect = new Pair[] {new Pair('c', 1), new Pair('c', 2), new Pair('b', 3), new Pair('a', 4), new Pair('b', 4)};
        Pair[] result;
        result = CountingSort.countingSort(source, Pair::firstKey);
        result = CountingSort.countingSort(result, Pair::secondKey);
        assertArrayEquals(expect, result);
    }

}
