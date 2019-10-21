import java.util.Arrays;

public class Divide {
    static void divide(int[] A) {
        int middle = A[0];
        int middleIndex = 0;
        for( int i = 1; i < A.length; i++) {
            int value = A[i];
            if( value < middle) {
                shift( A, 0, i );
                A[0] = value;
                middleIndex++;
            }
            else if( value == middle ) {
                shift( A, middleIndex, i );
                A[middleIndex] = value;
                middleIndex++;
            }
        }
    }

    static void shift(int[] A, int leftIndex, int rightIndex) {
       System.arraycopy( A, leftIndex, A, leftIndex + 1, rightIndex - leftIndex );
    }

    public static void main(String[] args) {
//        int[] a = {5, 1, 2, 4, 7, 3, 2, 5, 6};
//        int[] a = {5, 8, 2, 4, 7, 3, 2, 5, 0};
//        int[] a = {5};
//        int[] a = {5, 5, 5};
        int[] a = {2, 3, 1};
        divide(a);
        System.out.println(Arrays.toString(a));
    }
}
