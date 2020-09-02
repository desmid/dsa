#include <algorithm>
#include <c++14/make_unique.h>
#include <cstring>
#include <vector>

#include "algorithm.h"

using namespace std;

/**
 * Demonstrates the core idea of the counting sort algorithm.
 *
 * Implements counting sort for a vector of integers as keys.
 *
 * Limitations:
 * - integers >= 0,
 * - stability can't be demonstated without a secondary key.
 */
unique_ptr<vector<int>> counting_sort(const vector<int>& items)
{
    // items        - input vector of integers
    // size         - input size
    // k            - number of keys, assuming keys start at 0
    // sums         - prefix sums
    // sorted       - sorted vector of integers

    int size = items.size();

    unique_ptr<vector<int>> sorted = make_unique<vector<int>>(size);

    if (size < 1) {
        return sorted;
    }

    // Create the mapping from [0, nmax] to [0, k-1].
    vector<int>::const_iterator max;
    max = max_element(items.cbegin(), items.cend());
    int k = *max+1;

    // Stage 1: compute k counts, one for each input key.
    int* sums = new int[k];
    memset(sums, 0, k*sizeof sums[0]);
    for (auto key : items) {
        sums[key] += 1;
    }

    // Stage 2: compute k prefix sums from counts. Each prefix sum is the
    // number of predecessor keys <= key, which is also the position in the
    // required ordering when filled in from the right side.
    for (int i = 1; i < k; i++) {
        sums[i] += sums[i-1];
    }

    // Stage 3: compute the new key ordering from the prefix sums.
    // The loop traverses the keys from right to left. We write each item
    // into sorted at the index given by its prefix sum, then decrement the
    // sum ready for the next item of the same value.
    for (int i = size-1; i > -1; i--) {  // all downto
        int item_key = items[i];         // next key

        int j = sums[item_key]-1;        // decrement 1-based prefix sum
        sums[item_key] = j;              // update prefix sum

        (*sorted)[j] = item_key;         // write item at prefix sum index
    }

    delete[] sums;

    return sorted;
}
