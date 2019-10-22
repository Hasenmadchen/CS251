import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * General Heap Sort
 * <p>
 * TODO if this is an off-the-shelf implementation, cite where you got it from.
 *
 * @author TODO put your username here
 * @version TODO put the date here
 * @pso TODO put your PSO section here
 */
public class Heap extends Sort {
    /**
     * this class should not be instantiated
     */
    private Heap() {
    }

    /**
     * sort the array
     *
     * @param a - array
     */
    public static void sort(Comparable[] a) {
       heapSort(a);
    }


    /**
     * Min-heap implementation
     *
     * https://www.geeksforgeeks.org/heap-sort/
     * - modified to be iterative (non-recursive)
     */

    public static void heapSort(Comparable[] arr) {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            Comparable temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is an index in arr[]. n is size of heap
    public static <E extends Comparable<? super E>> void heapify(E[] arr, int n, int i) {
        while(true) {
            int largest = i; // Initialize largest as root
            int l = 2 * i + 1; // left = 2*i + 1
            int r = 2 * i + 2; // right = 2*i + 2

            // If left child is larger than root
            if (l < n && arr[l].compareTo(arr[largest]) > 0) {
                largest = l;
            }

            // If right child is larger than largest so far
            if (r < n && arr[r].compareTo(arr[largest]) > 0) {
                largest = r;
            }

            // If largest is not root
            if (largest != i) {
                E swap = arr[i];
                arr[i] = arr[largest];
                arr[largest] = swap;

                // iterator to heapify the affected sub-tree
                i = largest;
                continue;
            }

            // done
            break;
        }
    }

    public static void main(String[] args) {
//        String[] arr = {"z", "b", "c", "a", "e", "f", "g", "d"};
//        sort(arr);
//        System.out.println(Arrays.toString(arr));

        List<Integer> list = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("C:\\Users\\the_m\\cs251\\LocalityAwareSorting\\data\\10^3\\L5data.txt"))) {
                int num = Integer.parseInt(line);
                list.add(num);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Integer[] numArray = list.toArray(new Integer[0]);
        Heap.sort(numArray);
        System.out.println("list size: " + numArray.length);
        System.out.println("sorted: " + isSorted(numArray));
        Sort.show(numArray);
    }

}
