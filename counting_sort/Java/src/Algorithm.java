/**
 * Demonstrates the core idea of the counting sort algorithm.
 *
 * Implements counting sort for an array of integers as keys.
 *
 * Limitations:
 * - integers >= 0,
 * - stability can't be demonstated without a secondary key.
 */
public class Algorithm {

    private static final int[] EMPTY_ARRAY = new int[0];

    /**
     * Sort an array of integers >= 0 and return the result. The input is
     * unchanged.
     */
    public static int[] countingSort(int[] items) {

        // items        - input array of integers
        // size         - number of items
        // k            - number of keys, assuming keys start at 0
        // sums         - prefix sums
        // sorted       - sorted array of integers

        int size = items.length;

        if (size < 1) {
            return EMPTY_ARRAY;
        }

        // Create the mapping from [0, nmax] to [0, k-1].
        int k = max(items)+1;

        // Stage 1: compute k counts, one for each input key.
        int[] sums = new int[k];
        for (int key : items) {
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
        int[] sorted = new int[size];
        for (int i = size-1; i > -1; i--) {  // all downto
            int item_key = items[i];         // next key

            int j = sums[item_key]-1;        // decrement 1-based prefix sum
            sums[item_key] = j;              // update prefix sum

            sorted[j] = item_key;            // write item at prefix sum index
        }

        return sorted;
    }

    private static int max(int[] items) {
        int max = items[0];
        for (int i = items.length-1; i > -1; i--) {
            max = items[i] > max ? items[i] : max;
        }
        return max;
    }

}
