import pytest

from counting_sort import counting_sort


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


def test_example_with_negative_start_is_sorted():
    input, expect = [4, -1, 3, 4, 3], [-1, 3, 3, 4, 4]
    assert counting_sort(input) == expect


def test_example_with_zero_start_is_sorted():
    input, expect = [4, 0, 3, 4, 3], [0, 3, 3, 4, 4]
    assert counting_sort(input) == expect


def test_example_with_unique_ints_is_sorted():
    input = [7, 1, 0, 9]
    expect = sorted(input)
    assert counting_sort(input) == expect


def test_example_with_repeats_is_sorted():
    input, expect = [4, 1, 3, 4, 3], [1, 3, 3, 4, 4]
    assert counting_sort(input) == expect


# test sort with explicit null key

def test_sort_empty_list_with_null_key_is_sorted():
    input, expect = [], []
    assert counting_sort(input, None) == expect


def test_sort_singleton_with_null_key_is_sorted():
    input, expect = [1], [1]
    assert counting_sort(input, None) == expect


def test_sort_example_with_null_key_is_sorted():
    input, expect = [4, 1, 3, 4, 3], [1, 3, 3, 4, 4]
    assert counting_sort(input, None) == expect


# test sort with explicit key

def test_sort_empty_list_with_explicit_key_is_sorted():
    input, expect = [], []
    assert counting_sort(input, lambda x: x) == expect


def test_sort_singleton_with_explicit_key_is_sorted():
    input, expect = [1], [1]
    assert counting_sort(input, lambda x: x) == expect


def test_sort_example_with_explicit_key_is_sorted():
    input, expect = [4, 1, 3, 4, 3], [1, 3, 3, 4, 4]
    assert counting_sort(input, lambda x: x) == expect


# test sort stability

def test_sort_second_then_first_fields_is_stable():
    input = [("b", 3), ("b", 4), ("c", 2), ("c", 1), ("a", 4)]
    expect = [("a", 4), ("b", 3), ("b", 4), ("c", 1), ("c", 2)]
    result = counting_sort(input, lambda x: x[1])
    result = counting_sort(result, lambda x: ord(x[0]))
    assert result == expect


def test_sort_first_then_second_fields_is_stable():
    input = [("b", 3), ("b", 4), ("c", 2), ("c", 1), ("a", 4)]
    expect = [("c", 1), ("c", 2), ("b", 3), ("a", 4), ("b", 4)]
    result = counting_sort(input, lambda x: ord(x[0]))
    result = counting_sort(result, lambda x: x[1])
    assert result == expect
