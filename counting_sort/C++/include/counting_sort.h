#pragma once

#include <array>
#include <memory>  // std::unique_ptr
#include <vector>

#include <c++14/make_unique.h>

namespace CS {

    /** Type alias for key function return type. */
    using Key_t = long;  // must be signed integer (short, int, long)

    /** Type alias for key function. */
    template<class T>
    using key_func = Key_t (*)(const T&);

    /** Identity key function maps item to itself. */
    template<class T>
    Key_t identity_key(const T& x) { return Key_t(x); }


    /**
     * Sorts a C-style array of T into a same size preallocated array sorted of T,
     * using key to collate items.
     */
    template<class T>
    void counting_sort(const T items[], T sorted[], std::size_t size, key_func<T> key);


    // Process C-array, std::array, std::vector of items

    /**
     * Takes a C-style array of T of size N and returns the sorted result.
     */
    template<class T, std::size_t N>
    std::unique_ptr<T[]>
    counting_sort(const T (&items)[N], key_func<T> key = identity_key) {
        std::unique_ptr<T[]> sorted = std::make_unique<T[]>(N);
        counting_sort(items, sorted.get(), N, key);
        return sorted;
    }

    /**
     * Takes a std::array of T of size N and returns the sorted result.
     */
    template<class T, std::size_t N>
    std::unique_ptr<std::array<T, N>>
    counting_sort(const std::array<T, N>& items, key_func<T> key = identity_key)
    {
        std::unique_ptr<std::array<T, N>> sorted = std::make_unique<std::array<T, N>>();
        counting_sort(items.data(), sorted->data(), N, key);
        return sorted;
    }

    /**
     * Takes a std::vector of T and returns the sorted result.
     */
    template<class T>
    std::unique_ptr<std::vector<T>>
    counting_sort(const std::vector<T>& items, key_func<T> key = identity_key)
    {
        std::size_t size = items.size();
        std::unique_ptr<std::vector<T>> sorted = std::make_unique<std::vector<T>>(size);
        counting_sort(items.data(), sorted->data(), size, key);
        return sorted;
    }


    // Process unique_ptr to items

    /**
     * Takes a unique_ptr to a C-style array of T of size N and returns the sorted result.
     * The array is decayed: caller must supply N as the first template argument.
     */
    template<std::size_t N, class T>
    std::unique_ptr<T[]>
    counting_sort(const std::unique_ptr<T[]> &items, key_func<T> key = identity_key)
    {
        std::unique_ptr<T[]> sorted = std::make_unique<T[]>(N);
        counting_sort(items.get(), sorted.get(), N, key);
        return sorted;
    }

    /**
     * Takes a unique_ptr to a container C of T and returns the sorted result.
     */
    template<class C, class T>
    std::unique_ptr<C>
    counting_sort(const std::unique_ptr<C>& items, key_func<T> key = identity_key)
    {
        return counting_sort<T>(*items, key);
    }

}

#include "counting_sort.hpp"
