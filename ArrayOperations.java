import java.util.Arrays;

/**
 * Menyediakan operasi dasar untuk struktur data array bertipe int.
 */
public final class ArrayOperations {

    private ArrayOperations() {
        // Kelas utilitas tidak perlu dibuat sebagai objek.
    }

    /** Menampilkan seluruh isi array. */
    public static void traverse(int[] data) {
        System.out.println(Arrays.toString(data));
    }

    /**
     * Mengunjungi seluruh elemen dan mengembalikan jumlahnya.
     * Metode ini digunakan saat pengujian agar traversal tidak dipengaruhi
     * oleh kecepatan pencetakan ke layar.
     */
    public static long traversalSum(int[] data) {
        long total = 0;
        for (int value : data) {
            total += value;
        }
        return total;
    }

    /** Mencari nilai secara berurutan dari indeks pertama. */
    public static int linearSearch(int[] data, int target) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Mencari nilai dengan binary search.
     * Array harus sudah dalam keadaan terurut menaik.
     */
    public static int binarySearch(int[] sortedData, int target) {
        int left = 0;
        int right = sortedData.length - 1;

        while (left <= right) {
            int middle = left + (right - left) / 2;
            int value = sortedData[middle];

            if (value == target) {
                return middle;
            }
            if (value < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return -1;
    }

    /**
     * Menyisipkan nilai pada indeks tertentu dan menghasilkan array baru.
     */
    public static int[] insert(int[] data, int index, int value) {
        validateInsertIndex(data.length, index);

        int[] result = new int[data.length + 1];
        System.arraycopy(data, 0, result, 0, index);
        result[index] = value;
        System.arraycopy(data, index, result, index + 1, data.length - index);
        return result;
    }

    /**
     * Menghapus elemen pada indeks tertentu dan menghasilkan array baru.
     */
    public static int[] delete(int[] data, int index) {
        validateExistingIndex(data.length, index);

        int[] result = new int[data.length - 1];
        System.arraycopy(data, 0, result, 0, index);
        System.arraycopy(data, index + 1, result, index, data.length - index - 1);
        return result;
    }

    private static void validateInsertIndex(int length, int index) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException(
                    "Indeks penyisipan harus berada pada rentang 0 sampai " + length);
        }
    }

    private static void validateExistingIndex(int length, int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException(
                    "Indeks harus berada pada rentang 0 sampai " + (length - 1));
        }
    }
}
