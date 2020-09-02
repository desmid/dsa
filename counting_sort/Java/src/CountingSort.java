import java.util.Arrays;
import java.util.function.Function;

/**
 * Implements counting sort for an iterable with a key function.
 */
public class CountingSort {

    /**
     * Sort an array of T using the items themselves as keys and return the
     * result. The input is unchanged.
     *
     * @param values the array to be sorted
     */
    public static <T> T[] countingSort(T[] items) {
        return countingSort(items, null);
    }

    /**
     * Sort an array of T and return the result. The input is unchanged. The
     * key function must return an Integer or int for the collation order.
     *
     * @param items the array to be sorted
     * @param key a function returning the integer collation key of an element
     */
    public static <T> T[] countingSort(T[] items, Function<T, Integer> key) {

        // items        - input items
        // keys         - keys of input items
        // size         - input size
        // koffset      - offset to map input keys into [0, k-1]
        // k            - number of keys
        // sums         - prefix sums
        // sorted       - sorted items

        int size = items.length;

        if (size < 1) {
            return Arrays.copyOf(items, 0);  // can't return (T[]) new Object[0];
        }

        // Create key function.
        if (key == null) {
            key = (x) -> (Integer) x;
        }

        // Compute keys.
        int[] keys = new int[size];
        for (int i = 0; i < size; i++) {
            keys[i] = key.apply(items[i]);
        }

        // Create the mapping from [0, nmax] to [0, k-1].
        int[] tmp = minmax(keys);
        int koffset = tmp[0];
        int k = tmp[1]-koffset+1;

        // Stage 1: compute k counts, one for each input key.
        int[] sums = new int[k];
        for (int i = 0; i < size; i++) {
            keys[i] -= koffset;  // adjust key once
            int item_key = keys[i];
            sums[item_key] += 1;
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
        T[] sorted = Arrays.copyOf(items, size);
        for (int i = size-1; i > -1; i--) {  // all downto
            int item_key = keys[i];          // next key

            int j = sums[item_key]-1;        // decrement 1-based prefix sum
            sums[item_key] = j;              // update prefix sum

            sorted[j] = items[i];            // write item at prefix sum index
        }

        return sorted;
    }

    // Return the range of keys.
    private static int[] minmax(int[] keys) {
        int min = keys[0], max = min;
        for (int v : keys) {
            if (v < min)
                min = v;
            else if (v > max)
                max = v;
        }
        return new int[] {min, max};
    }

}
