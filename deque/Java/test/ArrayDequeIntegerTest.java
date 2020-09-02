import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArrayDequeIntegerTest {
    ArrayDequeInteger dq;

    @Before
    public void setUp() {
        dq = new ArrayDequeInteger();
    }

    @After
    public void tearDown() {
        dq = null;
    }

    // empty when new

    @Test
    public void test_010_empty_queue_is_empty_when_new() {
        assertTrue(dq.empty());
    }

    // addFirst

    @Test
    public void test_020_add_first_is_non_empty() {
        assertTrue(dq.empty());
        dq.addFirst(10);
        assertFalse(dq.empty());
    }

    @Test
    public void test_030_add_first_updates_size() {
        assertEquals(0, dq.size());
        dq.addFirst(10);
        assertEquals(1, dq.size());
        dq.addFirst(20);
    }

    // addLast

    @Test
    public void test_040_add_last_is_non_empty() {
        assertTrue(dq.empty());
        dq.addLast(10);
        assertFalse(dq.empty());
    }

    @Test
    public void test_050_add_last_updates_size() {
        assertEquals(0, dq.size());
        dq.addLast(10);
        assertEquals(1, dq.size());
        dq.addLast(20);
    }

    // removeFirst

    @Test
    public void test_060_remove_first_on_empty_does_nothing() {
        assertTrue(dq.empty());
        assertEquals(null, dq.removeFirst());
        assertTrue(dq.empty());
    }

    @Test
    public void test_070_remove_first_on_singleton_removes_first() {
        assertTrue(dq.empty());
        dq.addFirst(10);
        assertEquals(10, (int) dq.removeFirst());
        assertEquals(null, dq.removeFirst());
        assertTrue(dq.empty());
    }

    @Test
    public void test_080_remove_first_on_pair_removes_first() {
        assertTrue(dq.empty());
        dq.addFirst(20);
        dq.addFirst(10);
        assertEquals(10, (int) dq.removeFirst());
        assertEquals(20, (int) dq.removeFirst());
        assertEquals(null, dq.removeFirst());
        assertTrue(dq.empty());
    }

    @Test
    public void test_090_remove_first_on_triple_removes_first() {
        assertTrue(dq.empty());
        dq.addFirst(30);
        dq.addFirst(20);
        dq.addFirst(10);
        assertEquals(10, (int) dq.removeFirst());
        assertEquals(20, (int) dq.removeFirst());
        assertEquals(30, (int) dq.removeFirst());
        assertEquals(null, dq.removeFirst());
        assertTrue(dq.empty());
    }

    // removeLast

    @Test
    public void test_100_remove_last_on_empty_does_nothing() {
        assertTrue(dq.empty());
        assertEquals(null, dq.removeFirst());
        assertTrue(dq.empty());
    }

    @Test
    public void test_110_remove_last_on_singleton_removes_last() {
        assertTrue(dq.empty());
        dq.addLast(10);
        assertEquals(10, (int) dq.removeLast());
        assertEquals(null, dq.removeLast());
        assertTrue(dq.empty());
    }

    @Test
    public void test_120_remove_last_on_pair_removes_last() {
        assertTrue(dq.empty());
        dq.addLast(10);
        dq.addLast(20);
        assertEquals(20, (int) dq.removeLast());
        assertEquals(10, (int) dq.removeLast());
        assertEquals(null, dq.removeLast());
        assertTrue(dq.empty());
    }

    @Test
    public void test_130_remove_last_on_triple_removes_last() {
        assertTrue(dq.empty());
        dq.addLast(10);
        dq.addLast(20);
        dq.addLast(30);
        assertEquals(30, (int) dq.removeLast());
        assertEquals(20, (int) dq.removeLast());
        assertEquals(10, (int) dq.removeLast());
        assertEquals(null, dq.removeLast());
        assertTrue(dq.empty());
    }

    // get()

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_140_get_on_empty_throws_out_of_range() {
        dq.get(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_150_get_throws_out_of_range() {
        dq.addFirst(10);
        dq.get(2);
    }

    @Test
    public void test_160_get_returns_correct_value() {
        Integer[] integers = new Integer[] {10, 20, 30, 40};
        for (Integer s : integers) {
            dq.addLast(s);
        }
        assertEquals(4, dq.size());
        for (int i = 0; i < integers.length; ++i) {
            assertEquals(integers[i], dq.get(i));
        }
    }

    // put

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_170_put_on_empty_throws_out_of_range() {
        dq.put(1, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_180_put_throws_out_of_range() {
        dq.addFirst(10);
        assertEquals(10, (int) dq.get(0));
        dq.put(1, 20);
    }

    @Test
    public void test_190_put_inserts_correct_value() {
        Integer[] integers = new Integer[] {10, 20, 30, 40};
        Integer[] INTEGERS = new Integer[] {100, 200, 300, 400};
        for (Integer s : integers) {
            dq.addLast(s);
        }
        assertEquals(4, dq.size());
        for (int i = 0; i < integers.length; ++i) {
            dq.put(i, dq.get(i) * 10);
        }
        for (int i = 0; i < integers.length; ++i) {
            assertEquals(INTEGERS[i], dq.get(i));
        }
    }

    // copy constructor

    @Test
    public void test_200_copy_constructor_from_empty_works() {
        ArrayDequeInteger copy = new ArrayDequeInteger(dq);
        assertEquals(0, dq.size());
        assertEquals(0, copy.size());
    }

    public boolean equals_integers_deque(Integer[] expect, ArrayDequeInteger actual) {
        if (expect == null && actual == null)
            return true;
        if (expect == null || actual == null)
            return false;
        if (expect.length != actual.size())
            return false;
        for (int i = 0; i < expect.length; ++i) {
            if (! expect[i].equals(actual.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test_210_copy_constructor_from_non_empty_works() {
        Integer[] integers = new Integer[] {10, 20, 30, 40};
        Integer[] INTEGERS = new Integer[] {100, 200, 300, 400};
        for (Integer s : integers) {
            dq.addLast(s);
        }
        ArrayDequeInteger copy = new ArrayDequeInteger(dq);
        assertEquals(4, dq.size());
        assertEquals(4, copy.size());
        for (int i = 0; i < integers.length; ++i) {
            copy.put(i, copy.get(i) * 10);
        }
        assertTrue(equals_integers_deque(integers, dq));
        assertTrue(equals_integers_deque(INTEGERS, copy));
    }

    // of

    @Test
    public void test_220_of_stores_no_args_correctly() {
        dq = ArrayDequeInteger.of();
        assertTrue(dq.size() == 0);
    }

    @Test
    public void test_230_of_stores_args_correctly() {
        Integer[] integers = new Integer[]{10, 20, 30, 40};
        dq = ArrayDequeInteger.of(10, 20, 30, 40);
        assertTrue(equals_integers_deque(integers, dq));
    }

    // equals

    @Test
    public void test_240_empty_deques_are_equal() {
        assertFalse(dq.equals(null));
        assertFalse(dq.equals(new Object()));
        assertTrue(dq.equals(dq));
        ArrayDequeInteger dq2 = ArrayDequeInteger.of();
        assertTrue(dq.equals(dq2));
    }

    @Test
    public void test_250_deques_are_equal() {
        ArrayDequeInteger dq2 = ArrayDequeInteger.of();
        assertTrue(dq.equals(dq2));
        // prepend
        dq.addFirst(10);
        assertFalse(dq.equals(dq2));
        dq2.addFirst(10);
        assertTrue(dq.equals(dq2));
        // append
        dq.addLast(20);
        assertFalse(dq.equals(dq2));
        dq2.addLast(20);
        assertTrue(dq.equals(dq2));
    }

    // test Iterator

    @Test
    public void test_300_iterator_on_empty_deque_has_no_items() {
        Iterator<Integer> iterator = dq.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void test_310_iterator_on_singleton_has_one_item() {
        dq.addFirst(10);
        Iterator<Integer> iterator = dq.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Integer(10), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void test_320_iterator_on_big_deque_returns_expected_items() {
        dq = ArrayDequeInteger.of(10, 20, 30, 40);
        // dq.printDeque();
        Integer[] expect = new Integer[] {10, 20, 30, 40};
        ArrayList<Integer> result = new ArrayList<>(5);
        for (Integer i : dq) {
            result.add(i);
        }
        assertArrayEquals(expect, result.toArray());
    }

    // reallocation

    @Test
    public void test_400_should_force_reallocate_on_expand() {
        ArrayList<Integer> strlist = new ArrayList<>();
        Integer s;
        int i;

        // start with default capacity
        int capacity = dq.capacity();
        assertEquals(0, dq.size());

        // fill to capacity
        for (i = 1; i <= capacity; ++i) {
            strlist.add(i);
            dq.addLast(i);
        }
        assertEquals(capacity, dq.capacity());
        assertEquals(capacity, dq.size());

        // add one more item - should reallocate
        strlist.add(i);
        dq.addLast(i);
        assertEquals(capacity+1, dq.size());
        assertTrue(dq.capacity() > capacity);

        // check contents copied correctly
        Integer[] integers = new Integer[strlist.size()];
        integers = strlist.toArray(integers);
        assertTrue(equals_integers_deque(integers, dq));
    }

    @Test
    public void test_410_should_force_reallocate_on_shrink() {
        // start with excess capacity
        int capacity = 10 * dq.DEFAULT_CAPACITY;
        dq = new ArrayDequeInteger(capacity);
        assertEquals(0, dq.size());

        // dq.DEBUG = true;

        // fill to capacity
        for (int i = 1; i <= capacity; ++i) {
            dq.addLast(i);
        }
        assertEquals(capacity, dq.size());
        float usage = dq.usageFactor();
        assertTrue(usage == 1.0);  // full

        // remove items until first shrink is triggered
        boolean itShrank = false;
        for (int i = 1; i <= capacity; ++i) {
            dq.removeLast();
            if (dq.usageFactor() > usage) {
                itShrank = true;
                break;
            }
            usage = dq.usageFactor();
        }
        // dq.printDeque(); dq.printState();
        assertTrue(itShrank);
    }

    @Test
    public void test_420_reallocate_shrink_should_approach_minimum_capacity() {
        // start with default capacity
        assertEquals(0, dq.size());

        // dq.DEBUG = true;

        int size = 10 * dq.MINIMUM_RUNNING_CAPACITY;

        // fill and expand capacity
        for (int i = 1; i <= size; ++i) {
            dq.addLast(i);
        }
        assertEquals(size, dq.size());

        // remove all items
        for (int i = 1; i <= size; ++i) {
            dq.removeLast();
            // dq.printDeque();
        }
        assertTrue(dq.capacity() >= dq.MINIMUM_RUNNING_CAPACITY);
        assertTrue(dq.size() == 0);
    }

}
