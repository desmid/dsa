#include <algorithm>  // minmax_element
#include <cstddef>    // size_t
#include <cstring>    // memset
#include <iostream>
#include <memory>     // unique_ptr
#include <stdexcept>  // invalid_argument

#define TYPE_NAMES 0

#if TYPE_NAMES
#include <c++11/type_name.h>
#endif

#include <c++14/make_unique.h>

#include "counting_sort.h"

/**
 * Sorts a C-style array of T into a same size preallocated array sorted, using
 * key to collate items.
 */
template<class T>
void CS::counting_sort(const T items[], T sorted[], std::size_t size, key_func<T> key)
{
    // items        - input items
    // sorted       - preallocated array for sorted items
    // size         - input size
    // key          - function to compute input item key

    // k            - number of keys
    // koffset      - offset to map input keys into [0, k-1]
    // keys         - input item keys
    // sums         - prefix sums

    using namespace std;

#if TYPE_NAMES
    cout << endl;
    cout << "items is:  " << type_name<decltype(items)>() << endl;
    cout << "keyfun is: " << type_name<decltype(key)>()   << endl;
    cout << "size_t is: " << type_name<decltype(size)>()  << endl;
#endif

    if (size < 1) {
        return;
    }

    // Create key function.
    if (key == nullptr || key == NULL) {
        throw invalid_argument("key function must not be null");
    }

    // Allocate keys array on heap.
    unique_ptr<Key_t[]> keys = make_unique<Key_t[]>(size);

    // Compute keys.
    for (size_t i = 0; i < size; i++) {
        keys[i] = key(items[i]);
    }

    // Create the mapping from [0, nmax] to [0, k-1].
    auto p = minmax_element(keys.get(), keys.get()+size);
    Key_t koffset = *p.first;
    size_t k  = *p.second - koffset + 1;

    // Allocate and initialise prefix sums array on heap.
    unique_ptr<Key_t[]> sums = make_unique<Key_t[]>(k);
    memset(sums.get(), 0, k * sizeof sums[0]);

    // Stage 1: compute k counts, one for each input key.
    for (size_t i = 0; i < size; i++) {
        keys[i] -= koffset;  // adjust key once
        Key_t item_key = keys[i];
        sums[item_key] += 1;
    }

    // Stage 2: compute k prefix sums from counts. Each prefix sum is the
    // number of predecessor keys <= key, which is also the position in the
    // required ordering when filled in from the right side.
    for (size_t i = 1; i < k; i++) {
        sums[i] += sums[i-1];
    }

    // Stage 3: compute the new key ordering from the prefix sums.
    // The loop traverses the keys from right to left. We write each item
    // into sorted at the index given by its prefix sum, then decrement the
    // sum ready for the next item of the same value.
    for (size_t i = size; i-- > 0; ) {  // all downto
        Key_t item_key = keys[i];       // next key

        Key_t j = sums[item_key]-1;     // decrement 1-based prefix sum
        sums[item_key] = j;             // update prefix sum

        sorted[j] = items[i];           // write item at prefix sum index
    }
}
