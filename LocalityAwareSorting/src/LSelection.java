import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Locality-Aware Selection Sort
 *
 * @author TODO put your username here
 * @version TODO put the date here
 * @pso TODO put your PSO section here
 */
public class LSelection extends Sort {
    /**
     * this class should not be instantiated
     */
    private LSelection() {
    }

    /**
     * sort the array
     *
     * https://www.geeksforgeeks.org/java-program-for-selection-sort/
     *
     * @param a - array
     * @param d - locality
     */
    @SuppressWarnings("unchecked")
    public static void sort(Comparable[] a, int d) {
        for (int i = 0; i < a.length - 1; i++) {
            int minElementIndex = i;
            int start = i + 1;

            //////////////////////////////////////////
            // Limit distance in array to locality `d`
            //////////////////////////////////////////
            int len = Math.min(start + d, a.length);
            //////////////////////////////////////////

            for (int j = start; j < len; j++) {
                Comparable csr = a[j];
                Comparable min = a[minElementIndex];
                if (less(csr, min)) {
                    minElementIndex = j;
                }
            }

            if (minElementIndex != i) {
                exch(a, i, minElementIndex);
            }
        }
    }

    public static void main(String[] args) {
//        String[] arr = {"b", "c", "a", "e", "f", "g", "d"};
//        LSelection.sort(arr, 3);
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
        LSelection.sort(numArray, 5);
        System.out.println( "list size: " + numArray.length);
        System.out.println( "sorted: " + isSorted(numArray));
    }

}
