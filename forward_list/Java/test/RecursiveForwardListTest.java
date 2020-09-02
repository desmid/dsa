import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.FixMethodOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecursiveForwardListTest {
    RecursiveForwardList<Integer> list;

    @Before
    public void setUp() {
        list = new RecursiveForwardList<>();
    }

    @After
    public void tearDown() {
        list = null;
    }

    // test constructor, empty, size

    @Test
    public void test_010_empty_list_is_empty() {
        assertTrue(list.empty());
    }

    @Test
    public void test_020_empty_list_has_size_zero() {
        assertEquals(0, list.size());
    }

    // test append, empty, size

    @Test
    public void test_030_after_append_is_not_empty() {
        assertTrue(list.empty());
        list.append(10);
        assertFalse(list.empty());
    }

    @Test
    public void test_040_append_increases_size() {
        assertEquals(0, list.size());
        list.append(10);
        assertEquals(1, list.size());
        list.append(20);
        assertEquals(2, list.size());
    }

    @Test
    public void test_050_append_null_is_ignored() {
        Integer undefined = null;
        assertTrue(list.empty());
        list.append(undefined);
        assertTrue(list.empty());
        list.append(10);
        list.append(undefined);
        list.append(20);
        assertEquals(2, list.size());
    }

    // test static factory

    @Test
    public void test_060_static_factory_of_empty() {
        list = RecursiveForwardList.of();
        assertEquals(0, list.size());
    }

    @Test
    public void test_070_static_factory_of_one() {
        list = RecursiveForwardList.of(10);
        assertEquals(1, list.size());
    }

    @Test
    public void test_080_static_factory_of_many() {
        list = RecursiveForwardList.of(10, 20, 30);
        assertEquals(3, list.size());
    }

    // test append another list

    @Test
    public void test_090_after_append_empty_list_is_still_empty() {
        assertTrue(list.empty());
        RecursiveForwardList<Integer> list2 = new RecursiveForwardList<>();
        list.append(list2);
        assertTrue(list.empty());
    }

    @Test
    public void test_100_after_append_list_is_not_empty() {
        assertEquals(0, list.size());
        // create and append a list
        RecursiveForwardList<Integer> list2 = RecursiveForwardList.of(10);
        list.append(list2);
        assertEquals(1, list.size());
        // create and append another list: NOT list2 as would introduce a cycle
        RecursiveForwardList<Integer> list3 = RecursiveForwardList.of(20, 30);
        list.append(list3);
        assertEquals(3, list.size());
    }

    // test first

    @Test(expected = NoSuchElementException.class)
    public void test_110_first_on_empty_list_throws() {
        list.first();
    }

    @Test
    public void test_120_first_on_non_empty_list_works() {
        list.append(10);
        assertTrue(10 == list.first());
        list.append(20);
        assertTrue(10 == list.first());
    }

    // test last

    @Test(expected=NoSuchElementException.class)
    public void test_130_last_on_empty_throws() {
        list.last();
    }

    @Test
    public void test_140_last_on_non_empty_list_works() {
        list.append(10);
        assertTrue(10 == list.last());
        list.append(20);
        assertTrue(20 ==  list.last());
    }

    // test list equals

    @Test
    public void test_150_empty_lists_are_equal() {
        assertFalse(list.equals(null));
        assertFalse(list.equals(new Object()));
        assertTrue(list.equals(list));
        RecursiveForwardList<Integer> list2 = RecursiveForwardList.of();
        assertTrue(list.equals(list2));
    }

    @Test
    public void test_160_lists_are_equal() {
        RecursiveForwardList<Integer> list2 = RecursiveForwardList.of();
        assertTrue(list.equals(list2));
        // append first
        list.append(10);
        assertFalse(list.equals(list2));
        list2.append(10);
        assertTrue(list.equals(list2));
        // append another
        list.append(20);
        assertFalse(list.equals(list2));
        list2.append(20);
        assertTrue(list.equals(list2));
    }

    // test delete

    @Test
    public void test_170_delete_on_empty_list_is_silent() {
        list.delete(10);
        assertTrue(list.size() == 0);
    }

    @Test
    public void test_180_delete_on_absent_value_is_unchanged() {
        list.append(10);
        assertTrue(list.size() == 1);
        list.delete(99);
        assertTrue(list.size() == 1);
    }

    @Test
    public void test_190_delete_on_singleton_list_is_empty_list() {
        list.append(10);
        assertTrue(list.size() == 1);
        list.delete(10);
        assertTrue(list.size() == 0);
    }

    @Test
    public void test_200_delete_list_items_forwards() {
        list = RecursiveForwardList.of(1, 2, 3);
        assertTrue(list.equals(RecursiveForwardList.of(1, 2, 3)));
        list.delete(1); assertTrue(list.equals(RecursiveForwardList.of(2, 3)));
        list.delete(2); assertTrue(list.equals(RecursiveForwardList.of(3)));
        list.delete(3); assertTrue(list.equals(RecursiveForwardList.of()));
    }

    @Test
    public void test_210_delete_list_items_backwards() {
        list = RecursiveForwardList.of(1, 2, 3);
        assertTrue(list.equals(RecursiveForwardList.of(1, 2, 3)));
        list.delete(3); assertTrue(list.equals(RecursiveForwardList.of(1, 2)));
        list.delete(2); assertTrue(list.equals(RecursiveForwardList.of(1)));
        list.delete(1); assertTrue(list.equals(RecursiveForwardList.of()));
    }

    @Test
    public void test_220_delete_item_from_list_internally() {
        list = RecursiveForwardList.of(1, 2, 3);
        assertTrue(list.equals(RecursiveForwardList.of(1, 2, 3)));
        list.delete(2); assertTrue(list.equals(RecursiveForwardList.of(1, 3)));
    }

    // test reverse (in place)

    @Test
    public void test_230_reverse_empty_list_is_still_empty() {
        assertTrue(list.empty());
        list.reverse();
        assertTrue(list.empty());
    }

    @Test
    public void test_240_singleton_list_is_singleton() {
        list = RecursiveForwardList.of(10);
        assertTrue(list.size() == 1);
        assertTrue(list.first() == 10);
        list.reverse();
        assertTrue(list.size() == 1);
        assertTrue(list.first() == 10);
    }

    @Test
    public void test_250_reverse_list_pair() {
        list = RecursiveForwardList.of(10, 20);
        assertTrue(list.size() == 2);
        assertTrue(list.first() == 10);
        assertTrue(list.last() == 20);
        list.reverse();
        assertTrue(list.size() == 2);
        assertTrue(list.first() == 20);
        assertTrue(list.last() == 10);
    }

    @Test
    public void test_260_reverse_list_triple() {
        list = RecursiveForwardList.of(10, 20, 30);
        assertTrue(list.size() == 3);
        assertTrue(list.first() == 10);
        assertTrue(list.last() == 30);
        list.reverse();
        assertTrue(list.size() == 3);
        assertTrue(list.first() == 30);
        assertTrue(list.last() == 10);
    }

    @Test
    public void test_270_reverse_big_list() {
        list = RecursiveForwardList.of(10, 20, 30, 40, 50);
        assertTrue(list.size() == 5);
        list.reverse();
        assertTrue(list.size() == 5);
        assertTrue(list.equals(RecursiveForwardList.of(50, 40, 30, 20, 10)));
    }

    // test reversed (copy)

    @Test
    public void test_280_reversed_empty_list_is_still_empty() {
        assertTrue(list.empty());
        RecursiveForwardList<Integer> result = list.reversed();
        assertTrue(result.empty());
    }

    @Test
    public void test_290_singleton_list_is_singleton() {
        list = RecursiveForwardList.of(10);
        assertTrue(list.size() == 1);
        assertTrue(list.first() == 10);
        RecursiveForwardList<Integer> result = list.reversed();
        assertTrue(result.size() == 1);
        assertTrue(result.first() == 10);
    }

    @Test
    public void test_300_reversed_list_pair() {
        list = RecursiveForwardList.of(10, 20);
        assertTrue(list.size() == 2);
        assertTrue(list.first() == 10);
        assertTrue(list.last() == 20);
        RecursiveForwardList<Integer> result = list.reversed();
        assertTrue(result.size() == 2);
        assertTrue(result.first() == 20);
        assertTrue(result.last() == 10);
    }

    @Test
    public void test_310_reversed_list_triple() {
        list = RecursiveForwardList.of(10, 20, 30);
        assertTrue(list.size() == 3);
        assertTrue(list.first() == 10);
        assertTrue(list.last() == 30);
        RecursiveForwardList<Integer> result = list.reversed();
        assertTrue(result.size() == 3);
        assertTrue(result.first() == 30);
        assertTrue(result.last() == 10);
    }

    @Test
    public void test_320_reversed_big_list() {
        list = RecursiveForwardList.of(10, 20, 30, 40, 50);
        assertTrue(list.size() == 5);
        RecursiveForwardList<Integer> result = list.reversed();
        assertTrue(result.size() == 5);
        assertTrue(result.equals(RecursiveForwardList.of(50, 40, 30, 20, 10)));
    }

    // test Iterator

    @Test
    public void test_330_iterator_on_empty_list_has_no_items() {
        Iterator<Integer> iterator = list.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void test_340_iterator_on_singleton_has_one_item() {
        list.append(10);
        Iterator<Integer> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Integer(10), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void test_350_iterator_on_big_list_returns_expected_items() {
        list = RecursiveForwardList.of(10, 20, 30, 40, 50);
        Integer[] expect = new Integer[] {10, 20, 30, 40, 50};
        ArrayList<Integer> result = new ArrayList<>(5);
        for (Integer i : list) {
            result.add(i);
        }
        assertArrayEquals(expect, result.toArray());
    }

}
