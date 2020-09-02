"""
Demonstrates the core idea of the counting sort algorithm.

Implements counting sort for a list of integers as keys.

Limitations:
- integers >= 0,
- stability can't be demonstated without a secondary key.
"""


def counting_sort(items):
    """Sort a list of integers >= 0 and return the result. The input list is
    unchanged.

    items        - input items (list of integers)
    size         - input size
    k            - number of keys, assuming keys start at 0
    sums         - prefix sums
    sorted       - sorted items

    """

    size = len(items)

    if size < 1:
        return []

    # Create the mapping from [0, nmax] to [0, k-1].
    k = max(items)+1

    # Stage 1: compute k counts, one for each input key.
    sums = [0] * k
    for key in items:
        sums[key] += 1

    # Stage 2: compute k prefix sums from counts. Each prefix sum is the
    # number of predecessor keys <= key, which is also the position in the
    # required ordering when filled in from the right side.
    for i in range(1, k):
        sums[i] += sums[i-1]

    # Stage 3: compute the new key ordering from the prefix sums.
    # The loop traverses the keys from right to left. We write each item
    # into sorted at the index given by its prefix sum, then decrement the
    # sum ready for the next item of the same value.
    sorted = [None] * size
    for i in range(size-1, -1, -1):  # all downto
        item_key = items[i]          # next key

        j = sums[item_key]-1         # decrement 1-based prefix sum
        sums[item_key] = j           # update prefix sum

        sorted[j] = item_key         # write item at prefix sum index

    return sorted
