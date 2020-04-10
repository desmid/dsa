#include <gtest/gtest.h>
#include <c++14/make_unique.h>
#include <memory>
#include <vector>

#include "algorithm.h"

using namespace std;

namespace {

    // test sort

    TEST(AlgorithmTest, test_010_empty_array_is_sorted) {
        vector<int> source(0);
        vector<int> expect(0);
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

    TEST(AlgorithmTest, test_020_singleton_is_sorted) {
        vector<int> source {1};
        vector<int> expect {1};
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(1, source[0]);
        EXPECT_EQ(1, expect[0]);
        EXPECT_EQ(expect, *result);
    }

    TEST(AlgorithmTest, test_030_ordered_pair_is_sorted) {
        vector<int> source {1,2};
        vector<int> expect {1,2};
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(1, source[0]);
        EXPECT_EQ(1, expect[0]);
        EXPECT_EQ(expect, *result);
    }

    TEST(AlgorithmTest, test_040_reversed_pair_is_sorted) {
        vector<int> source {2,1};
        vector<int> expect {1,2};
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(2, source[0]);
        EXPECT_EQ(1, expect[0]);
        EXPECT_EQ(expect, *result);
    }

    TEST(AlgorithmTest, test_050_example_with_unique_ints_is_sorted) {
        vector<int> source {7, 1, 0, 9};
        vector<int> expect {0, 1, 7, 9};
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

    TEST(AlgorithmTest, test_060_example_with_repeats_is_sorted) {
        vector<int> source {4, 1, 3, 4, 3};
        vector<int> expect {1, 3, 3, 4, 4};
        unique_ptr<vector<int>> result = counting_sort(source);
        EXPECT_EQ(expect, *result);
    }

}
