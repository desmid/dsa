## Description

Counting sort is a stable linear sort.

Assumptions

- keys are integers that can be mapped into some narrow interval `[1, k]`.

- any range of integers, including negative numbers, can be mapped via a
  function `key(x) -> [1, k]` (equivalently `[0, k-1]`)

## Algorithm

The simplest demonstration implementation sorts a list of integers without any
intermediate key function:

Use three arrays:
```
items  1..n
sorted 1..n
sums   1..k

// initialise counters
for i = 1 to k
  do sums[i] = 0

// counting step, sums[i] = |{key == i}|
for i = 1 to n
  do sums[items[i]] = sums[items[i]] + 1  // increment

// prefix sums step: sums[i] = |{key <= i}|
for i = 2 to k
  do sums[i] = sums[i] + sums[i-1]

// ordering step, note: runs right to left
for i = n downto 1
  do sorted[ sums[items[i]] ] = items[i]
     sums[items[i]] = sums[items[i]] - 1  // decrement
```

Notes:

- The counting step gives the number of elements of each type to be reserved
  in the output array.

- The prefix sums step gives the next output index (1-based) for an element of
  that value; prefix sums are decremented as input elements are output from
  right to left, conferring stability.

## Running time

The algorithm is `O(k+n)`, by inspection of the loops: `O(k) + O(n) + O(k) +
O(n)`. This is:

- good: if `k = O(n)` algorithm is `O(n)` i.e., linear as desired.
- bad: if `k = O(nlog n)` as it is no better than comparison sort.
- very bad: if `k >> O(n)`.

## Practical limitations

- If the numbers we want to sort fit into a byte then `k = 2**8 = 256`, which
  is a small auxiliary size, and will be extremely efficient.

- On the other hand, if we try to process 32 bit unsigned ints, then with `k =
  2**32` the array would require about 4.2 billion words or `4 * 2^32 / 2^30 =
  16GB`, which is not very practicable.

## Implementations

```
Java
├── pom.xml
├── src
│   ├── Algorithm.java        // demo core algorithm
│   └── CountingSort.java     // implementation with keys
└── test
    ├── AlgorithmTest.java
    └── CountingSortTest.java
```

```
Python
├── src
│   ├── algorithm.py          // demo core algorithm
│   └── counting_sort.py      // generic implementation with keys
└── test
    ├── __init__.py
    ├── algorithm_test.py
    └── counting_sort_test.py
```

```
C++
├── CMakeLists.txt
├── build
├── include
│   ├── algorithm.h
│   ├── counting_sort.h
│   └── counting_sort.hpp  // C array, array, vector implementation with keys
├── src
│   ├── CMakeLists.txt
│   └── algorithm.cc       // demo core algorithm using std::vector
└── test
    ├── CMakeLists.txt
    ├── algorithm_test.cc                  // demo core with std::vector
    ├── counting_sort_carray_int_test.cc   // C array int
    ├── counting_sort_carray_long_test.cc  // C array long
    ├── counting_sort_array_int_test.cc    // std::array<int>
    ├── counting_sort_array_long_test.cc   // std::array<long>
    ├── counting_sort_vector_int_test.cc   // std::vector<int>
    ├── counting_sort_vector_long_test.cc  // std::vector<long>
    └── counting_sort_stability_test.cc    // test stability
```
