import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Menyediakan operasi dasar untuk ArrayList bertipe Integer.
 */
public final class ArrayListOperations {

    private ArrayListOperations() {
        // Kelas utilitas tidak perlu dibuat sebagai objek.
    }

    /** Menampilkan seluruh isi ArrayList. */
    public static void traverse(List<Integer> data) {
        System.out.println(data);
    }

    /** Mengunjungi seluruh elemen dan mengembalikan jumlahnya. */
    public static long traversalSum(List<Integer> data) {
        long total = 0;
        for (int value : data) {
            total += value;
        }
        return total;
    }

    /** Menambahkan elemen di posisi paling akhir. */
    public static void addElement(ArrayList<Integer> data, int value) {
        data.add(value);
    }

    /** Menyisipkan elemen pada indeks tertentu. */
    public static void addElement(ArrayList<Integer> data, int index, int value) {
        data.add(index, value);
    }

    /** Menghapus dan mengembalikan elemen pada indeks tertentu. */
    public static int removeElement(ArrayList<Integer> data, int index) {
        return data.remove(index);
    }

    /** Mencari indeks elemen secara linear. */
    public static int searchElement(List<Integer> data, int target) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == target) {
                return i;
            }
        }
        return -1;
    }

    /** Mencari nilai pada ArrayList yang sudah terurut. */
    public static int binarySearch(List<Integer> sortedData, int target) {
        return Collections.binarySearch(sortedData, target);
    }

    /** Mengurutkan elemen secara menaik. */
    public static void sortElements(ArrayList<Integer> data) {
        Collections.sort(data);
    }
}
