import java.util.ArrayList;
import java.util.Arrays;

/**
 * Program utama untuk mendemonstrasikan operasi dan menjalankan perbandingan.
 */
public class Main {

    public static void main(String[] args) {
        int[] array = {10, 20, 30, 40, 50};
        ArrayList<Integer> arrayList = new ArrayList<>(
                Arrays.asList(10, 20, 30, 40, 50));

        System.out.print("Array traversal: ");
        ArrayOperations.traverse(array);
        System.out.print("ArrayList traversal: ");
        ArrayListOperations.traverse(arrayList);

        int target = 30;
        System.out.println("\nPencarian linear nilai " + target + " pada Array: indeks "
                + ArrayOperations.linearSearch(array, target));
        System.out.println("Pencarian binary nilai " + target + " pada Array: indeks "
                + ArrayOperations.binarySearch(array, target));
        System.out.println("Pencarian nilai " + target + " pada ArrayList: indeks "
                + ArrayListOperations.searchElement(arrayList, target));

        array = ArrayOperations.insert(array, 2, 25);
        ArrayListOperations.addElement(arrayList, 2, 25);
        System.out.println("\nArray setelah penyisipan 25: " + Arrays.toString(array));
        System.out.println("ArrayList setelah penyisipan 25: " + arrayList);

        array = ArrayOperations.delete(array, 4);
        ArrayListOperations.removeElement(arrayList, 4);
        System.out.println("\nArray setelah penghapusan indeks 4: " + Arrays.toString(array));
        System.out.println("ArrayList setelah penghapusan indeks 4: " + arrayList);

        ArrayList<Integer> unsorted = new ArrayList<>(
                Arrays.asList(50, 10, 40, 20, 30));
        ArrayListOperations.sortElements(unsorted);
        System.out.println("\nArrayList setelah pengurutan: " + unsorted);

        Comparison.printComparison(100_000, 30);
    }
}
