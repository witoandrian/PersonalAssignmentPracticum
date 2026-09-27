import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Membandingkan waktu eksekusi operasi dasar Array dan ArrayList.
 */
public final class Comparison {

    private static volatile long blackhole;

    private Comparison() {
        // Kelas utilitas tidak perlu dibuat sebagai objek.
    }

    /** Menjalankan seluruh pengujian dan menampilkan tabel hasilnya. */
    public static void printComparison(int size, int repetitions) {
        if (size < 2 || repetitions < 1) {
            throw new IllegalArgumentException("Ukuran data dan jumlah pengulangan tidak valid.");
        }

        int[] orderedArray = createOrderedArray(size);
        ArrayList<Integer> orderedList = toArrayList(orderedArray);
        int target = size - 1;

        // Pemanasan singkat membantu JVM menyelesaikan proses JIT sebelum pengukuran.
        warmUp(orderedArray, orderedList, target);

        List<Result> results = new ArrayList<>();
        results.add(new Result(
                "Traversal",
                benchmarkArrayTraversal(orderedArray, repetitions),
                benchmarkListTraversal(orderedList, repetitions)));
        results.add(new Result(
                "Linear search",
                benchmarkArrayLinearSearch(orderedArray, target, repetitions),
                benchmarkListLinearSearch(orderedList, target, repetitions)));
        results.add(new Result(
                "Binary search",
                benchmarkArrayBinarySearch(orderedArray, target, repetitions),
                benchmarkListBinarySearch(orderedList, target, repetitions)));
        results.add(new Result(
                "Penyisipan tengah",
                benchmarkArrayInsertion(orderedArray, repetitions),
                benchmarkListInsertion(orderedList, repetitions)));
        results.add(new Result(
                "Penghapusan tengah",
                benchmarkArrayDeletion(orderedArray, repetitions),
                benchmarkListDeletion(orderedList, repetitions)));

        int[] randomArray = createRandomArray(size);
        ArrayList<Integer> randomList = toArrayList(randomArray);
        warmUpSorting(randomArray, randomList);
        results.add(new Result(
                "Pengurutan",
                benchmarkArraySort(randomArray, repetitions),
                benchmarkListSort(randomList, repetitions)));

        System.out.printf(Locale.US, "%nPerbandingan waktu rata-rata (%d elemen, %d pengulangan)%n",
                size, repetitions);
        System.out.println("+----------------------+----------------+----------------+----------------+");
        System.out.println("| Operasi              | Array (ms)     | ArrayList (ms) | Lebih cepat    |");
        System.out.println("+----------------------+----------------+----------------+----------------+");
        for (Result result : results) {
            String faster = result.arrayNanoseconds <= result.listNanoseconds
                    ? "Array" : "ArrayList";
            System.out.printf(Locale.US, "| %-20s | %14.6f | %14.6f | %-14s |%n",
                    result.operation,
                    toMilliseconds(result.arrayNanoseconds),
                    toMilliseconds(result.listNanoseconds),
                    faster);
        }
        System.out.println("+----------------------+----------------+----------------+----------------+");
    }

    private static void warmUp(int[] array, ArrayList<Integer> list, int target) {
        for (int i = 0; i < 30; i++) {
            blackhole ^= ArrayOperations.traversalSum(array);
            blackhole ^= ArrayListOperations.traversalSum(list);
            blackhole ^= ArrayOperations.linearSearch(array, target);
            blackhole ^= ArrayListOperations.searchElement(list, target);
            blackhole ^= ArrayOperations.binarySearch(array, target);
            blackhole ^= ArrayListOperations.binarySearch(list, target);
        }

        for (int i = 0; i < 8; i++) {
            int index = array.length / 2;
            int[] insertedArray = ArrayOperations.insert(array, index, -1);
            int[] deletedArray = ArrayOperations.delete(array, index);

            ArrayList<Integer> insertedList = new ArrayList<>(list);
            ArrayListOperations.addElement(insertedList, index, -1);
            ArrayList<Integer> deletedList = new ArrayList<>(list);
            int removed = ArrayListOperations.removeElement(deletedList, index);

            blackhole ^= insertedArray[index];
            blackhole ^= deletedArray[index];
            blackhole ^= insertedList.get(index);
            blackhole ^= removed;
        }
    }

    private static void warmUpSorting(int[] array, ArrayList<Integer> list) {
        for (int i = 0; i < 5; i++) {
            int[] arrayCopy = array.clone();
            ArrayList<Integer> listCopy = new ArrayList<>(list);
            Arrays.sort(arrayCopy);
            ArrayListOperations.sortElements(listCopy);
            blackhole ^= arrayCopy[arrayCopy.length / 2];
            blackhole ^= listCopy.get(listCopy.size() / 2);
        }
    }

    private static double benchmarkArrayTraversal(int[] data, int repetitions) {
        long elapsed = 0;
        for (int i = 0; i < repetitions; i++) {
            long start = System.nanoTime();
            long result = ArrayOperations.traversalSum(data);
            elapsed += System.nanoTime() - start;
            blackhole ^= result;
        }
        return (double) elapsed / repetitions;
    }

    private static double benchmarkListTraversal(List<Integer> data, int repetitions) {
        long elapsed = 0;
        for (int i = 0; i < repetitions; i++) {
            long start = System.nanoTime();
            long result = ArrayListOperations.traversalSum(data);
            elapsed += System.nanoTime() - start;
            blackhole ^= result;
        }
        return (double) elapsed / repetitions;
    }

    private static double benchmarkArrayLinearSearch(int[] data, int target, int repetitions) {
        long elapsed = 0;
        for (int i = 0; i < repetitions; i++) {
            long start = System.nanoTime();
            int result = ArrayOperations.linearSearch(data, target);
            elapsed += System.nanoTime() - start;
            blackhole ^= result;
        }
        return (double) elapsed / repetitions;
    }

    private static double benchmarkListLinearSearch(List<Integer> data, int target, int repetitions) {
        long elapsed = 0;
        for (int i = 0; i < repetitions; i++) {
            long start = System.nanoTime();
            int result = ArrayListOperations.searchElement(data, target);
            elapsed += System.nanoTime() - start;
            blackhole ^= result;
        }
        return (double) elapsed / repetitions;
    }

    private static double benchmarkArrayBinarySearch(int[] data, int target, int repetitions) {
        long elapsed = 0;
        for (int i = 0; i < repetitions; i++) {
            long start = System.nanoTime();
            int result = ArrayOperations.binarySearch(data, target);
            elapsed += System.nanoTime() - start;
            blackhole ^= result;
        }
        return (double) elapsed / repetitions;
    }

    private static double benchmarkListBinarySearch(List<Integer> data, int target, int repetitions) {
        long elapsed = 0;
        for (int i = 0; i < repetitions; i++) {
            long start = System.nanoTime();
            int result = ArrayListOperations.binarySearch(data, target);
            elapsed += System.nanoTime() - start;
            blackhole ^= result;
        }
        return (double) elapsed / repetitions;
    }

    private static double benchmarkArrayInsertion(int[] base, int repetitions) {
        long elapsed = 0;
        int index = base.length / 2;
        for (int i = 0; i < repetitions; i++) {
            int[] working = base.clone();
            long start = System.nanoTime();
            int[] result = ArrayOperations.insert(working, index, -1);
            elapsed += System.nanoTime() - start;
            blackhole ^= result[index];
        }
        return (double) elapsed / repetitions;
    }

    private static double benchmarkListInsertion(ArrayList<Integer> base, int repetitions) {
        long elapsed = 0;
        int index = base.size() / 2;
        for (int i = 0; i < repetitions; i++) {
            ArrayList<Integer> working = new ArrayList<>(base);
            long start = System.nanoTime();
            ArrayListOperations.addElement(working, index, -1);
            elapsed += System.nanoTime() - start;
            blackhole ^= working.get(index);
        }
        return (double) elapsed / repetitions;
    }

    private static double benchmarkArrayDeletion(int[] base, int repetitions) {
        long elapsed = 0;
        int index = base.length / 2;
        for (int i = 0; i < repetitions; i++) {
            int[] working = base.clone();
            long start = System.nanoTime();
            int[] result = ArrayOperations.delete(working, index);
            elapsed += System.nanoTime() - start;
            blackhole ^= result[index];
        }
        return (double) elapsed / repetitions;
    }

    private static double benchmarkListDeletion(ArrayList<Integer> base, int repetitions) {
        long elapsed = 0;
        int index = base.size() / 2;
        for (int i = 0; i < repetitions; i++) {
            ArrayList<Integer> working = new ArrayList<>(base);
            long start = System.nanoTime();
            int removed = ArrayListOperations.removeElement(working, index);
            elapsed += System.nanoTime() - start;
            blackhole ^= removed;
        }
        return (double) elapsed / repetitions;
    }

    private static double benchmarkArraySort(int[] base, int repetitions) {
        long elapsed = 0;
        for (int i = 0; i < repetitions; i++) {
            int[] working = base.clone();
            long start = System.nanoTime();
            Arrays.sort(working);
            elapsed += System.nanoTime() - start;
            blackhole ^= working[working.length / 2];
        }
        return (double) elapsed / repetitions;
    }

    private static double benchmarkListSort(ArrayList<Integer> base, int repetitions) {
        long elapsed = 0;
        for (int i = 0; i < repetitions; i++) {
            ArrayList<Integer> working = new ArrayList<>(base);
            long start = System.nanoTime();
            ArrayListOperations.sortElements(working);
            elapsed += System.nanoTime() - start;
            blackhole ^= working.get(working.size() / 2);
        }
        return (double) elapsed / repetitions;
    }

    private static int[] createOrderedArray(int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = i;
        }
        return data;
    }

    private static int[] createRandomArray(int size) {
        Random random = new Random(42);
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt(size * 10);
        }
        return data;
    }

    private static ArrayList<Integer> toArrayList(int[] data) {
        ArrayList<Integer> result = new ArrayList<>(data.length);
        for (int value : data) {
            result.add(value);
        }
        return result;
    }

    private static double toMilliseconds(double nanoseconds) {
        return nanoseconds / 1_000_000.0;
    }

    private static final class Result {
        private final String operation;
        private final double arrayNanoseconds;
        private final double listNanoseconds;

        private Result(String operation, double arrayNanoseconds, double listNanoseconds) {
            this.operation = operation;
            this.arrayNanoseconds = arrayNanoseconds;
            this.listNanoseconds = listNanoseconds;
        }
    }
}
