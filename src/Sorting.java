import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Guanming Chen
 * @version 1.0
 * @userid gchen353
 * @GTID 903661790
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("Error, the array or comparator is null");
        } else {
            if (arr.length <= 1) {
                return;
            }
            for (int n = 1; n < arr.length; n++) {
                int index = n;
                while (index > 0 && comparator.compare(arr[index], arr[index - 1]) < 0) {
                    T temp = arr[index];
                    arr[index] = arr[index - 1];
                    arr[index - 1] = temp;
                    index--;
                }
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("Error, the array or comparator is null");
        } else {
            if (arr.length <= 1) {
                return;
            }
            boolean swapsMade = true;
            int start = 0;
            int end = arr.length - 1;
            int lastSwapped = 0;
            while (swapsMade) {
                swapsMade = false;
                for (int i = start; i < end; i++) {
                    if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                        T temp = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = temp;
                        swapsMade = true;
                        lastSwapped = i;
                    }
                }
                end = lastSwapped;
                if (swapsMade) {
                    swapsMade = false;
                    for (int m = end; m > start; m--) {
                        if (comparator.compare(arr[m - 1], arr[m]) > 0) {
                            T temp = arr[m];
                            arr[m] = arr[m - 1];
                            arr[m - 1] = temp;
                            swapsMade = true;
                            lastSwapped = m;
                        }
                    }
                    start = lastSwapped;
                }
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("Error, the array or comparator is null");
        } else {
            int length = arr.length;
            if (length <= 1) {
                return;
            } else {
                int mid = length / 2;
                T[] left = (T[]) new Object[mid];
                T[] right = (T[]) new Object[arr.length - mid];
                for (int i = 0; i < left.length; i++) {
                    left[i] = arr[i];
                }
                for (int j = 0; j < right.length; j++) {
                    right[j] = arr[j + mid];
                }
                mergeSort(left, comparator);
                mergeSort(right, comparator);
                int m = 0;
                int n = 0;
                while (m < left.length && n < right.length) {
                    if (comparator.compare(left[m], right[n]) <= 0) {
                        arr[m + n] = left[m];
                        m++;
                    } else {
                        arr[m + n] = right[n];
                        n++;
                    }
                }
                while (m < left.length) {
                    arr[m + n] = left[m];
                    m++;
                }
                while (n < right.length) {
                    arr[m + n] = right[n];
                    n++;
                }
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new java.lang.IllegalArgumentException("Error, the array is null");
        } else {
            if (arr.length <= 1) {
                return;
            }
            LinkedList<Integer>[] buckets = new LinkedList[19];
            for (int i = 0; i < 19; i++) {
                buckets[i] = new LinkedList<>();
            }
            int max = arr[0];
            for (int i = 1; i < arr.length; i++) {
                if (max <= arr[i]) {
                    max = arr[i];
                }
            }
            int count = 0;
            while (max != 0) {
                count++;
                max = max / 10;
            }
            int exp = 1;
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < arr.length; j++) {
                    int curDigit = (arr[j] / exp) % 10;
                    buckets[curDigit + 9].add(arr[j]);
                }
                int index = 0;
                for (LinkedList<Integer> bucket : buckets) {
                    while (!bucket.isEmpty()) {
                        arr[index++] = bucket.remove();
                    }
                }
                exp *= 10;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new java.lang.IllegalArgumentException("Error, the array or comparator or rand is null ");
        } else {
            if (arr.length <= 1) {
                return;
            }
            int start = 0;
            int end = arr.length - 1;
            sortHelper(arr, start, end, comparator, rand);
        }
    }

    /**
     * the method to sort the array recursively
     *
     * @param arr the array that must be sorted after the method runs
     * @param start the start index when sorting in the subproblem
     * @param end the end index when sorting in the subproblem
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param <T> data type to sort
     */

    public static <T> void sortHelper(T[] arr, int start, int end, Comparator<T> comparator,
                                  Random rand) {
        if (end - start < 1) {
            return;
        } else {
            int pivotIdx = rand.nextInt(end - start + 1) + start;
            T pivotVal = arr[pivotIdx];
            T temp = arr[start];
            arr[start] = pivotVal;
            arr[pivotIdx] = temp;
            int i = start + 1;
            int j = end;
            while (i <= j) {
                while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                    i++;
                }
                while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                    j--;
                }
                if (i <= j) {
                    T temp1 = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp1;
                    i++;
                    j--;
                }
            }
            T temp2 = arr[start];
            arr[start] = arr[j];
            arr[j] = temp2;
            sortHelper(arr, start, j - 1, comparator, rand);
            sortHelper(arr, j + 1, end, comparator, rand);
        }
    }
}
