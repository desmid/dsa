#include <gtest/gtest.h>
#include <memory>
#include <vector>

#include "counting_sort.h"

namespace {

    using namespace std;
    using CS::counting_sort;

    // test sort

    TEST(CountingSort_Vector_of_Int, test_010_empty_vector_is_sorted) {
        vector<int> source(0);
        vector<int> expect(0);
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Vector_of_Int, test_020_singleton_is_sorted) {
        vector<int> source {1};
        vector<int> expect {1};
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(1, source[0]);
        EXPECT_EQ(1, expect[0]);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Vector_of_Int, test_030_ordered_pair_is_sorted) {
        vector<int> source {1,2};
        vector<int> expect {1,2};
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(1, source[0]);
        EXPECT_EQ(1, expect[0]);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Vector_of_Int, test_040_reversed_pair_is_sorted) {
        vector<int> source {2,1};
        vector<int> expect {1,2};
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(2, source[0]);
        EXPECT_EQ(1, expect[0]);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Vector_of_Int, test_050_example_with_negative_start_is_sorted) {
        vector<int> source {4, -1, 3, 4, 3};
        vector<int> expect {-1, 3, 3, 4, 4};
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Vector_of_Int, test_060_example_with_zero_start_is_sorted) {
        vector<int> source {4, 0, 3, 4, 3};
        vector<int> expect {0, 3, 3, 4, 4};
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Vector_of_Int, test_050_example_with_unique_ints_is_sorted) {
        vector<int> source {7, 1, 0, 9};
        vector<int> expect {0, 1, 7, 9};
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Vector_of_Int, test_060_example_with_repeats_is_sorted) {
        vector<int> source {4, 1, 3, 4, 3};
        vector<int> expect {1, 3, 3, 4, 4};
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

    // test sort with explicit null key

    TEST(CountingSort_Vector_NullKey, test_090_sort_with_null_key_throws_invalid_exception) {
        vector<int> source {1};
        // naked nullptr does not compile; cast succeeds
        // unique_ptr<vector<int>> result = counting_sort(source, nullptr);
        EXPECT_THROW(counting_sort(source, (CS::key_func<int>) NULL), invalid_argument);
        EXPECT_THROW(counting_sort(source, (CS::key_func<int>) nullptr), invalid_argument);
    }

    // test sort with explicit key

    TEST(CountingSort_Vector_Key, test_100_sort_empty_vector_with_explicit_key_is_sorted) {
        vector<int> source;
        vector<int> expect;
        CS::key_func<int> key = [](const int& x) { return (CS::Key_t) x; };
        unique_ptr<vector<int>> result = counting_sort(source, key);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Vector_Key, test_110_sort_singleton_with_explicit_key_is_sorted) {
        vector<int> source {1};
        vector<int> expect {1};
        CS::key_func<int> key = [](const int& x) { return (CS::Key_t) x; };
        unique_ptr<vector<int>> result = counting_sort(source, key);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Vector_Key, test_120_sort_example_with_explicit_key_is_sorted) {
        vector<int> source = {4, 1, 3, 4, 3};
        vector<int> expect = {1, 3, 3, 4, 4};
        CS::key_func<int> key = [](const int& x) { return (CS::Key_t) x; };
        unique_ptr<vector<int>> result = counting_sort(source, key);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Vector_Key, test_130_sort_example_with_explicit_key_is_reverse_sorted) {
        vector<int> source = {4, 1, 3, 4, 3};
        vector<int> expect = {4, 4, 3, 3, 1};
        CS::key_func<int> key = [](const int& x) { return (CS::Key_t) -x; };
        unique_ptr<vector<int>> result = counting_sort(source, key);
        EXPECT_EQ(expect, *result);
    }

}
