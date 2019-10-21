import java.util.Arrays;

/**
 * General Selection Sort
 * <p>
 * TODO if this is an off-the-shelf implementation, cite where you got it from.
 *
 * @author TODO put your username here
 * @version TODO put the date here
 * @pso TODO put your PSO section here
 */
public class Selection extends Sort {
    /**
     * this class should not be instantiated
     */
    private Selection() {
    }

    /**
     * sort the array
     *
     * https://www.geeksforgeeks.org/java-program-for-selection-sort/
     *
     * @param a - array
     */
    @SuppressWarnings("unchecked")
    public static void sort(Comparable[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                Comparable csr = a[j];
                Comparable min = a[minElementIndex];
                if (less(csr, min) ) {
                    minElementIndex = j;
                }
            }

            if (minElementIndex != i) {
                exch(a, i, minElementIndex);
            }
        }
    }

    public static void main(String[] args) {
        String[] arr = {"b", "c", "a"};
        Selection.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
