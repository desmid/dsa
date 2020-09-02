#include <array>
#include <gtest/gtest.h>
#include <memory>
#include <ostream>
#include <vector>

#include "counting_sort.h"

namespace {

    using namespace std;
    using CS::counting_sort;

    struct Pair {
        char c;
        int v;

        Pair() : Pair(0, 0) {}
        Pair(char c, int v) : c{c}, v{v} {}

        bool operator==(const Pair& other) const {
            return c == other.c && v == other.v;
        }

        static CS::Key_t first_key (const Pair& p) { return (CS::Key_t) p.c; }
        static CS::Key_t second_key(const Pair& p) { return (CS::Key_t) p.v; }
    };

    static ostream& operator<<(ostream& strm, const Pair& p) {
        return strm << "(" << p.c << ", " << p.v  << ")";
    }

    // test C array

    template<class T, std::size_t N>
    void compare_arrays(const T (&a)[N], const unique_ptr<T[]> &b) {
        for (int i = 0; i < N; ++i) {
            EXPECT_EQ(a[i], b[i]) << "Arrays differ at index " << i;
        }
    }

    TEST(CountingSort_C_Array_Stability, test_10_sort_by_second_then_first_fields_is_stable) {
        Pair source[] {Pair('b', 3), Pair('b', 4), Pair('c', 2), Pair('c', 1), Pair('a', 4)};
        Pair expect[] {Pair('a', 4), Pair('b', 3), Pair('b', 4), Pair('c', 1), Pair('c', 2)};
        unique_ptr<Pair[]> result;
        result = counting_sort(source, Pair::second_key);
        result = counting_sort<5>(result, Pair::first_key);  // supply size on decayed ptr
        compare_arrays(expect, result);
    }

    TEST(CountingSort_C_Array_Stability, test_20_sort_by_first_then_second_fields_is_stable) {
        Pair source[] {Pair('b', 3), Pair('b', 4), Pair('c', 2), Pair('c', 1), Pair('a', 4)};
        Pair expect[] {Pair('c', 1), Pair('c', 2), Pair('b', 3), Pair('a', 4), Pair('b', 4)};
        unique_ptr<Pair[]> result;
        result = counting_sort(source, Pair::first_key);
        result = counting_sort<5>(result, Pair::second_key);  // supply size on decayed ptr
        compare_arrays(expect, result);
    }

    // test std::array

    TEST(CountingSort_Array_Stability, test_30_sort_by_second_then_first_fields_is_stable) {
        array<Pair, 5> source {Pair('b', 3), Pair('b', 4), Pair('c', 2), Pair('c', 1), Pair('a', 4)};
        array<Pair, 5> expect {Pair('a', 4), Pair('b', 3), Pair('b', 4), Pair('c', 1), Pair('c', 2)};
        unique_ptr<array<Pair, 5>> result;
        result = counting_sort(source, Pair::second_key);
        result = counting_sort(result, Pair::first_key);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Array_Stability, test_40_sort_by_first_then_second_fields_is_stable) {
        array<Pair, 5> source {Pair('b', 3), Pair('b', 4), Pair('c', 2), Pair('c', 1), Pair('a', 4)};
        array<Pair, 5> expect {Pair('c', 1), Pair('c', 2), Pair('b', 3), Pair('a', 4), Pair('b', 4)};
        unique_ptr<array<Pair, 5>> result;
        result = counting_sort(source, Pair::first_key);
        result = counting_sort(result, Pair::second_key);
        EXPECT_EQ(expect, *result);
    }

    // test std::vector

    TEST(CountingSort_Vector_Stability, test_50_sort_by_second_then_first_fields_is_stable) {
        vector<Pair> source {Pair('b', 3), Pair('b', 4), Pair('c', 2), Pair('c', 1), Pair('a', 4)};
        vector<Pair> expect {Pair('a', 4), Pair('b', 3), Pair('b', 4), Pair('c', 1), Pair('c', 2)};
        unique_ptr<vector<Pair>> result;
        result = counting_sort(source, Pair::second_key);
        result = counting_sort(result, Pair::first_key);
        EXPECT_EQ(expect, *result);
    }

    TEST(CountingSort_Vector_Stability, test_60_sort_by_first_then_second_fields_is_stable) {
        vector<Pair> source {Pair('b', 3), Pair('b', 4), Pair('c', 2), Pair('c', 1), Pair('a', 4)};
        vector<Pair> expect {Pair('c', 1), Pair('c', 2), Pair('b', 3), Pair('a', 4), Pair('b', 4)};
        unique_ptr<vector<Pair>> result;
        result = counting_sort(source, Pair::first_key);
        result = counting_sort(result, Pair::second_key);
        EXPECT_EQ(expect, *result);
    }

}
