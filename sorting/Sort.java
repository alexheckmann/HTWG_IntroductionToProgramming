import org.jetbrains.annotations.NotNull;

public class Sort {

    public static <T extends Comparable<T>> void mergeSort(T[] m) {
        if (m == null || m.length == 0) {
            throw new IllegalArgumentException();
        } else {
            if (m.length <= 10) {
                insertionSort(m);
            } else {
                T[] auxiliaryArray = (T[]) new Comparable[m.length];
                mergeSort(m, auxiliaryArray, 0, m.length - 1);
            }
        }
    }

    private static <T extends Comparable<T>> void mergeSort(T[] m, T[] auxiliaryArray, int from, int to) {

        if (to <= from) {
            return;
        }
        // declare middle
        int middle = from + (to - from) / 2;
        //sort left side
        mergeSort(m, auxiliaryArray, from, middle);
        //sort right side
        mergeSort(m, auxiliaryArray, middle + 1, to);
        // combine
        merge(m, auxiliaryArray, from, middle, to);
    }

    private static <T extends Comparable<T>> void merge(T[] m, T[] auxiliaryArray, int from, int middle, int to) {
        for (int k = from; k <= to; k++) {
            auxiliaryArray[k] = m[k];
        }

        int i = from;
        int j = middle + 1;
        int k = from;

        // kleinsten Wert in Array zurück kopieren
        while (i <= middle && j <= to) {
            if (auxiliaryArray[i].compareTo(auxiliaryArray[j]) <= 0) {
                m[k] = auxiliaryArray[i];
                i++;
            } else {
                m[k] = auxiliaryArray[j];
                j++;
            }
            k++;
        }
        // copy left side
        while (i <= middle) {
            m[k] = auxiliaryArray[i];
            k++;
            i++;
        }


    }

    public static <T extends Comparable<T>> void insertionSort(T[] m) {
        if (m == null || m.length == 0) {
            throw new IllegalArgumentException();
        } else {
            int N = m.length;

            for (int i = 0; i < N; i++) {
                for (int j = i; j > 0; j--) {
                    if (m[j - 1].compareTo(m[j]) > 0) {
                        swap(m, j - 1, j);
                    } else {
                        break;
                    }
                }

            }
        }
    }

    public static <T extends Comparable<T>> void quickSort(T[] m) {
        if (m == null || m.length == 0) {
            throw new IllegalArgumentException();
        } else {
            if (m.length <= 8) {
                insertionSort(m);
            } else {
                quickSort(m, 0, m.length - 1);
            }
        }
    }

    private static <T extends Comparable<T>> void quickSort(T[] m, int from, int to) {
        if (to <= from) {
            return;
        }

        int j = divide(m, from, to);
        quickSort(m, from, j - 1);
        quickSort(m, j + 1, to);
    }

    private static <T extends Comparable<T>> int divide(@NotNull T[] m, int from, int to) {
        int i = from;
        int j = to + 1;

        while (true) {
            while (m[++i].compareTo(m[from]) < 0) {
                if (i == to) {
                    break;
                }
            }

            while (m[--j].compareTo(m[from]) > 0) {
                if (j == from) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }
            swap(m, i, j);

        }
        swap(m, from, j);
        return j;
    }

    private static <T> void swap(@NotNull T[] m, int i, int j) {
        T t = m[i];
        m[i] = m[j];
        m[j] = t;
    }

}
