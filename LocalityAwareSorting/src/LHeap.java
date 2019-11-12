import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Locality-Aware Heap Sort
 *
 * @author TODO put your username here
 * @version TODO put the date here
 * @pso TODO put your PSO section here
 */
public class LHeap extends Sort {
    /**
     * this class should not be instantiated
     */
    private LHeap() {
    }

    /**
     * sort the array
     *
     * @param a - array
     * @param d - locality
     */
    public static void sort(Comparable[] a, int d) {
        //noinspection unchecked
        heapSort(a, d);
    }

    /**
     * Min-heap implementation
     * <p>
     * https://www.geeksforgeeks.org/heap-sort/
     */

    public static <E extends Comparable<? super E>> void heapSort(E[] arr, int d) {
        int n = arr.length;
        int window = d + 1;

        for (int i = 0; i < n - 1; i++) {
            heapifyWindow(arr, window, i);
        }
    }

    private static <E extends Comparable<? super E>> void heapifyWindow(E[] arr, int window, int offset) {
        for (int i = offset + (window / 2 - 1); i >= offset; i--) {
            heapify(arr, offset + window, i, offset);
        }
    }

    // To heapify a subtree rooted with node i which is an index in arr[]. n is size of heap
    public static <E extends Comparable<? super E>> void heapify(E[] arr, int n, int i, int offset) {
        n = n > arr.length ? arr.length : n;
        while (true) {
            int smallest = i; // Initialize smalles as root
            int l = 2 * (i - offset) + 1 + offset; // left = 2*i + 1
            int r = 2 * (i - offset) + 2 + offset; // right = 2*i + 2

            // If left child is smaller than root
            if (l < n && arr[l].compareTo(arr[smallest]) < 0) {
                smallest = l;
            }

            // If right child is smaller than smallest so far
            if (r < n && arr[r].compareTo(arr[smallest]) < 0) {
                smallest = r;
            }

            // If smallest is not root
            if (smallest != i) {
                E temp = arr[i];
                arr[i] = arr[smallest];
                arr[smallest] = temp;

                // Iteratively heapify the affected sub-tree
                i = smallest;
                continue;
            }

            break;
        }
    }

    public static void main(String[] args) {
//        String[] arr = {"z", "b", "c", "a", "e", "f", "g", "d"};
//        sort(arr);
//        System.out.println(Arrays.toString(arr));

        List<Integer> list = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("C:\\Users\\the_m\\cs251\\LocalityAwareSorting\\data\\10^3\\L45data.txt"))) {
                int num = Integer.parseInt(line);
                list.add(num);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Integer[] numArray = list.toArray(new Integer[0]);
        LHeap.sort(numArray, 45);
        System.out.println("list size: " + numArray.length);
        System.out.println("sorted: " + isSorted(numArray));
        Sort.show(numArray);
    }
}
