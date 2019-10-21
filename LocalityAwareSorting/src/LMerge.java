import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Locality-Aware Merge Sort
 *
 * @author TODO put your username here
 * @version TODO put the date here
 * @pso TODO put your PSO section here
 */
public class LMerge extends Sort {
    /**
     * this class should not be instantiated
     */
    private LMerge() {
    }

    /**
     * sort the array
     *
     * @param a - array
     * @param d - locality
     */
    public static void sort(Comparable[] a, int d) {
        mergeSort(a, a.length, d);
    }

    /**
     * Iterative merge sort function to sort arr[0...n-1]
     * <p>
     * https://www.geeksforgeeks.org/iterative-merge-sort/
     */
    public static void mergeSort(Comparable[] arr, int n, int d) {

        // For current size of subarrays to be merged curr_size varies from 1 to n/2
        int curr_size;

        // For picking starting index of left subarray to be merged
        int left_start;


        // Merge subarrays in bottom up manner. First merge subarrays of size 1 to create sorted subarrays of size 2,
        // then merge subarrays of size 2 to create sorted subarrays of size 4, and so on.
        for (curr_size = 1; curr_size <= n - 1;
             curr_size = 2 * curr_size) {

            // Pick starting point of different subarrays of current size
            for (left_start = 0; left_start < n - 1;
                 left_start += 2 * curr_size) {
                // Find ending point of left subarray. mid+1 is starting point of right
                int mid = Math.min(left_start + curr_size - 1, n - 1);

                int right_end = Math.min(left_start
                    + 2 * curr_size - 1, n - 1);

                //////////////////////////////////////////
                // Narrow array size according to locality `d`
                //////////////////////////////////////////
                int left = Math.min(d, mid - left_start + 1);
                int right = d < right_end - mid ? mid + d : right_end;
                //////////////////////////////////////////

                // Merge Subarrays arr[left_start...mid] & arr[mid+1...right_end]
                merge(arr, left, mid, right, d);
            }
        }
    }

    /* Function to merge the two haves arr[l..m] and arr[m+1..r] of array arr[] */
    @SuppressWarnings("unchecked")
    static void merge(Comparable[] arr, int l, int m, int r, int d) {
        int n1 = m - l + 1;
        int n2 = r - m;

        // create temp arrays
        Comparable[] L = new Comparable[n1];
        Comparable[] R = new Comparable[n2];

        // Copy data to temp arrays L[] and R[]
        System.arraycopy(arr, l, L, 0, n1);
        System.arraycopy(arr, m+1, R, 0, n2);

        // Merge the temp arrays back into arr[l..r]
        int i = 0;
        int j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].compareTo(R[j]) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy the remaining elements of L[], if there are any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Copy the remaining elements of R[], if there are any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
//        String[] arr = {"z", "b", "c", "a", "e", "f", "g", "d"};
//        LMerge.sort(arr, arr.length-1);
//        System.out.println(Arrays.toString(arr));

        List<Integer> list = new ArrayList<>();
        try {
            for(String line: Files.readAllLines(Paths.get("C:\\Users\\the_m\\cs251\\LocalityAwareSorting\\data\\10^3\\L5data.txt")))
            {
                int num = Integer.parseInt(line);
                list.add(num);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Integer[] numArray = list.toArray(new Integer[0]);
        LMerge.sort(numArray, 5);
        System.out.println( "list size: " + numArray.length);
        System.out.println( "sorted: " + isSorted(numArray));
    }
}
