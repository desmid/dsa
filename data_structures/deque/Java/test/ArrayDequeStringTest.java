import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArrayDequeStringTest {
    ArrayDequeString dq;

    @Before
    public void setUp() {
        dq = new ArrayDequeString();
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
        dq.addFirst("a");
        assertFalse(dq.empty());
    }

    @Test
    public void test_030_add_first_updates_size() {
        assertEquals(0, dq.size());
        dq.addFirst("a");
        assertEquals(1, dq.size());
        dq.addFirst("b");
    }

    // addLast

    @Test
    public void test_040_add_last_is_non_empty() {
        assertTrue(dq.empty());
        dq.addLast("a");
        assertFalse(dq.empty());
    }

    @Test
    public void test_050_add_last_updates_size() {
        assertEquals(0, dq.size());
        dq.addLast("a");
        assertEquals(1, dq.size());
        dq.addLast("b");
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
        dq.addFirst("a");
        assertEquals("a", dq.removeFirst());
        assertEquals(null, dq.removeFirst());
        assertTrue(dq.empty());
    }

    @Test
    public void test_080_remove_first_on_pair_removes_first() {
        assertTrue(dq.empty());
        dq.addFirst("b");
        dq.addFirst("a");
        assertEquals("a", dq.removeFirst());
        assertEquals("b", dq.removeFirst());
        assertEquals(null, dq.removeFirst());
        assertTrue(dq.empty());
    }

    @Test
    public void test_090_remove_first_on_triple_removes_first() {
        assertTrue(dq.empty());
        dq.addFirst("c");
        dq.addFirst("b");
        dq.addFirst("a");
        assertEquals("a", dq.removeFirst());
        assertEquals("b", dq.removeFirst());
        assertEquals("c", dq.removeFirst());
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
        dq.addLast("a");
        assertEquals("a", dq.removeLast());
        assertEquals(null, dq.removeLast());
        assertTrue(dq.empty());
    }

    @Test
    public void test_120_remove_last_on_pair_removes_last() {
        assertTrue(dq.empty());
        dq.addLast("a");
        dq.addLast("b");
        assertEquals("b", dq.removeLast());
        assertEquals("a", dq.removeLast());
        assertEquals(null, dq.removeLast());
        assertTrue(dq.empty());
    }

    @Test
    public void test_130_remove_last_on_triple_removes_last() {
        assertTrue(dq.empty());
        dq.addLast("a");
        dq.addLast("b");
        dq.addLast("c");
        assertEquals("c", dq.removeLast());
        assertEquals("b", dq.removeLast());
        assertEquals("a", dq.removeLast());
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
        dq.addFirst("a");
        dq.get(2);
    }

    @Test
    public void test_160_get_returns_correct_value() {
        String[] strings = new String[] {"a", "b", "c", "d"};
        for (String s : strings) {
            dq.addLast(s);
        }
        assertEquals(4, dq.size());
        for (int i = 0; i < strings.length; ++i) {
            assertEquals(strings[i], dq.get(i));
        }
    }

    // put

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_170_put_on_empty_throws_out_of_range() {
        dq.put(1, "a");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_180_put_throws_out_of_range() {
        dq.addFirst("a");
        assertEquals("a", dq.get(0));
        dq.put(1, "b");
    }

    @Test
    public void test_190_put_inserts_correct_value() {
        String[] strings = new String[] {"a", "b", "c", "d"};
        String[] STRINGS = new String[] {"A", "B", "C", "D"};
        for (String s : strings) {
            dq.addLast(s);
        }
        assertEquals(4, dq.size());
        for (int i = 0; i < strings.length; ++i) {
            dq.put(i, dq.get(i).toUpperCase());
        }
        for (int i = 0; i < strings.length; ++i) {
            assertEquals(STRINGS[i], dq.get(i));
        }
    }

    // copy constructor

    @Test
    public void test_200_copy_constructor_from_empty_works() {
        ArrayDequeString copy = new ArrayDequeString(dq);
        assertEquals(0, dq.size());
        assertEquals(0, copy.size());
    }

    public boolean equals_strings_deque(String[] expect, ArrayDequeString actual) {
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
        String[] strings = new String[] {"a", "b", "c", "d"};
        String[] STRINGS = new String[] {"A", "B", "C", "D"};
        for (String s : strings) {
            dq.addLast(s);
        }
        ArrayDequeString copy = new ArrayDequeString(dq);
        assertEquals(4, dq.size());
        assertEquals(4, copy.size());
        for (int i = 0; i < strings.length; ++i) {
            copy.put(i, copy.get(i).toUpperCase());
        }
        assertTrue(equals_strings_deque(strings, dq));
        assertTrue(equals_strings_deque(STRINGS, copy));
    }

    // of

    @Test
    public void test_220_of_stores_no_args_correctly() {
        dq = ArrayDequeString.of();
        assertTrue(dq.size() == 0);
    }

    @Test
    public void test_230_of_stores_args_correctly() {
        String[] strings = new String[]{"a", "b", "c", "d"};
        dq = ArrayDequeString.of("a", "b", "c", "d");
        assertTrue(equals_strings_deque(strings, dq));
    }

    // equals

    @Test
    public void test_240_empty_deques_are_equal() {
        assertFalse(dq.equals(null));
        assertFalse(dq.equals(new Object()));
        assertTrue(dq.equals(dq));
        ArrayDequeString dq2 = ArrayDequeString.of();
        assertTrue(dq.equals(dq2));
    }

    @Test
    public void test_250_deques_are_equal() {
        ArrayDequeString dq2 = ArrayDequeString.of();
        assertTrue(dq.equals(dq2));
        // prepend
        dq.addFirst("a");
        assertFalse(dq.equals(dq2));
        dq2.addFirst("a");
        assertTrue(dq.equals(dq2));
        // append
        dq.addLast("b");
        assertFalse(dq.equals(dq2));
        dq2.addLast("b");
        assertTrue(dq.equals(dq2));
    }

    // test Iterator

    @Test
    public void test_300_iterator_on_empty_deque_has_no_items() {
        Iterator<String> iterator = dq.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void test_310_iterator_on_singleton_has_one_item() {
        dq.addFirst("a");
        Iterator<String> iterator = dq.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new String("a"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void test_320_iterator_on_big_deque_returns_expected_items() {
        dq = ArrayDequeString.of("a", "b", "c", "d");
        // dq.printDeque();
        String[] expect = new String[] {"a", "b", "c", "d"};
        ArrayList<String> result = new ArrayList<>(5);
        for (String i : dq) {
            result.add(i);
        }
        assertArrayEquals(expect, result.toArray());
    }

    // reallocation

    @Test
    public void test_400_should_force_reallocate_on_expand() {
        ArrayList<String> strlist = new ArrayList<>();
        String s;
        int i;

        // start with default capacity
        int capacity = dq.capacity();
        assertEquals(0, dq.size());

        // fill to capacity
        for (i = 1; i <= capacity; ++i) {
            s = Integer.toString(i);
            strlist.add(s);
            dq.addLast(s);
        }
        assertEquals(capacity, dq.capacity());
        assertEquals(capacity, dq.size());

        // add one more item - should reallocate
        s = Integer.toString(i);
        strlist.add(s);
        dq.addLast(s);
        assertEquals(capacity+1, dq.size());
        assertTrue(dq.capacity() > capacity);

        // check contents copied correctly
        String[] strings = new String[strlist.size()];
        strings = strlist.toArray(strings);
        assertTrue(equals_strings_deque(strings, dq));
    }

    @Test
    public void test_410_should_force_reallocate_on_shrink() {
        // start with excess capacity
        int capacity = 10 * dq.DEFAULT_CAPACITY;
        dq = new ArrayDequeString(capacity);
        assertEquals(0, dq.size());

        // dq.DEBUG = true;

        // fill to capacity
        for (int i = 1; i <= capacity; ++i) {
            dq.addLast(Integer.toString(i));
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
            dq.addLast(Integer.toString(i));
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
