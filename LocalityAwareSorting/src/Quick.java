import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * General Quick Sort
 * 
 * TODO if this is an off-the-shelf implementation, cite where you got it from.
 * 
 * @author TODO put your username here
 * @version TODO put the date here
 * @pso   TODO put your PSO section here
 *
 */
public class Quick extends Sort {
	/**
	 * this class should not be instantiated
	 */
	private Quick() {}

	/**
	 * sort the array
	 * @param a - array
	 */
	public static void sort(Comparable[] a) {
		quickSort(a, 0, a.length-1);
	}

	public static <E extends Comparable<? super E>> void quickSort(E[] arr, int begin, int end) {
		if (begin < end) {
			int partitionIndex = partition(arr, begin, end);

			quickSort(arr, begin, partitionIndex-1);
			quickSort(arr, partitionIndex+1, end);
		}
	}

	public static <E extends Comparable<? super E>> int partition(E[] arr, int begin, int end) {
		E pivot = arr[end];
		int i = (begin-1);

		for (int j = begin; j < end; j++) {
			if (arr[j].compareTo(pivot) <= 0) {
				i++;

				E swapTemp = arr[i];
				arr[i] = arr[j];
				arr[j] = swapTemp;
			}
		}

		E swapTemp = arr[i+1];
		arr[i+1] = arr[end];
		arr[end] = swapTemp;

		return i+1;
	}

	public static void main(String[] args) {
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
		Quick.sort(numArray);
		System.out.println("list size: " + numArray.length);
		System.out.println("sorted: " + isSorted(numArray));
	}
}