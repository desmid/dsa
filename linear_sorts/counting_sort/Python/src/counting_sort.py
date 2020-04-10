"""
Implements counting sort for a list of iterable with a key function.
"""


def counting_sort(items, key=None):
    """Sort a list of items using key and return the result. If key is None
    use the identity function. The input list is unchanged.

    items        - input items
    keys         - keys of input items
    size         - input size
    koffset      - offset to map input keys into [0, k-1]
    k            - number of keys
    sums         - prefix sums
    sorted       - sorted items
    """

    size = len(items)

    if size < 1:
        return []

    # Create key function.
    if key is None:
        def key(x): return x

    # Compute keys.
    keys = [key(item) for item in items]

    # Create the mapping from [nmin, nmax] to [0, k-1].
    koffset = min(keys)
    k = max(keys)-koffset+1

    # Stage 1: compute k counts, one for each input key.
    sums = [0] * k
    for i in range(0, size):
        keys[i] -= koffset    # adjust key once
        item_key = keys[i]
        sums[item_key] += 1

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
        item_key = keys[i]           # next key

        j = sums[item_key]-1         # decrement 1-based prefix sum
        sums[item_key] = j           # update prefix sum

        sorted[j] = items[i]         # write item at prefix sum index

    return sorted
