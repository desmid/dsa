import pytest

from algorithm import counting_sort


# test sort

def test_empty_list_is_sorted():
    input, expect = [], []
    assert counting_sort(input) == expect


def test_singleton_is_sorted():
    input, expect = [1], [1]
    assert counting_sort(input) == expect


def test_ordered_pair_is_sorted():
    input, expect = [1, 2], [1, 2]
    assert counting_sort(input) == expect


def test_reversed_pair_is_sorted():
    input, expect = [2, 1], [1, 2]
    assert counting_sort(input) == expect


def test_example_with_unique_ints_is_sorted():
    input = [7, 1, 0, 9]
    expect = sorted(input)
    assert counting_sort(input) == expect


def test_example_with_repeats_is_sorted():
    input, expect = [4, 1, 3, 4, 3], [1, 3, 3, 4, 4]
    assert counting_sort(input) == expect
