#include <array>
#include <gtest/gtest.h>
#include <memory>

#include "counting_sort.h"

namespace {

    using namespace std;
    using CS::counting_sort;

    // test sort

    TEST(CountingSort_Array_of_Long, test_010_empty_array_is_sorted) {
        array<long, 0> source;
        array<long, 0> expect;
        unique_ptr<array<long, 0>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Array_of_Long, test_020_singleton_is_sorted) {
        array<long, 1> source {1};
        array<long, 1> expect {1};
        unique_ptr<array<long, 1>> result = counting_sort(source);
        EXPECT_EQ(1, source[0]);
        EXPECT_EQ(1, expect[0]);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Array_of_Long, test_030_ordered_pair_is_sorted) {
        array<long, 2> source {1,2};
        array<long, 2> expect {1,2};
        unique_ptr<array<long, 2>> result = counting_sort(source);
        EXPECT_EQ(1, source[0]);
        EXPECT_EQ(1, expect[0]);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Array_of_Long, test_040_reversed_pair_is_sorted) {
        array<long, 2> source {2,1};
        array<long, 2> expect {1,2};
        unique_ptr<array<long, 2>> result = counting_sort(source);
        EXPECT_EQ(2, source[0]);
        EXPECT_EQ(1, expect[0]);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Array_of_Long, test_050_example_with_negative_start_is_sorted) {
        array<long, 5> source {4, -1, 3, 4, 3};
        array<long, 5> expect {-1, 3, 3, 4, 4};
        unique_ptr<array<long, 5>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Array_of_Long, test_060_example_with_zero_start_is_sorted) {
        array<long, 5> source {4, 0, 3, 4, 3};
        array<long, 5> expect {0, 3, 3, 4, 4};
        unique_ptr<array<long, 5>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Array_of_Long, test_050_example_with_unique_longs_is_sorted) {
        array<long, 4> source {7, 1, 0, 9};
        array<long, 4> expect {0, 1, 7, 9};
        unique_ptr<array<long, 4>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Array_of_Long, test_060_example_with_repeats_is_sorted) {
        array<long, 5> source {4, 1, 3, 4, 3};
        array<long, 5> expect {1, 3, 3, 4, 4};
        unique_ptr<array<long, 5>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

    // test sort with explicit null key

    TEST(CountingSort_Array_NullKey, test_090_sort_with_null_key_throws_invalid_exception) {
        array<long, 1> source {1};
        // naked nullptr does not compile; cast succeeds
        // unique_ptr<array<long, 5>> result = counting_sort(source, nullptr);
        EXPECT_THROW(counting_sort(source, (CS::key_func<long>) NULL), invalid_argument);
        EXPECT_THROW(counting_sort(source, (CS::key_func<long>) nullptr), invalid_argument);
    }

    // // test sort with explicit key

    TEST(CountingSort_Array_Key, test_100_sort_empty_array_with_explicit_key_is_sorted) {
        array<long, 0> source;
        array<long, 0> expect;
        CS::key_func<long> key = [](const long& x) { return (CS::Key_t) x; };
        unique_ptr<array<long, 0>> result = counting_sort(source, key);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Array_Key, test_110_sort_singleton_with_explicit_key_is_sorted) {
        array<long, 1> source {1};
        array<long, 1> expect {1};
        CS::key_func<long> key = [](const long& x) { return (CS::Key_t) x; };
        unique_ptr<array<long, 1>> result = counting_sort(source, key);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Array_Key, test_120_sort_example_with_explicit_key_is_sorted) {
        array<long, 5> source = {4, 1, 3, 4, 3};
        array<long, 5> expect = {1, 3, 3, 4, 4};
        CS::key_func<long> key = [](const long& x) { return (CS::Key_t) x; };
        unique_ptr<array<long, 5>> result = counting_sort(source, key);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Array_Key, test_130_sort_example_with_explicit_key_is_reverse_sorted) {
        array<long, 5> source = {4, 1, 3, 4, 3};
        array<long, 5> expect = {4, 4, 3, 3, 1};
        CS::key_func<long> key = [](const long& x) { return (CS::Key_t) -x; };
        unique_ptr<array<long, 5>> result = counting_sort(source, key);
        EXPECT_EQ(expect, *result);
    }

}
