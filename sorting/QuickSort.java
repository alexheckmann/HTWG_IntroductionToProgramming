package sorting;

import org.jetbrains.annotations.NotNull;

public class QuickSort {

    private static void quickSort(char[] m, int from, int to) {
        if (to <= from) {
            return;
        }

        int j = divide(m, from, to);
        quickSort(m, from, j - 1);
        quickSort(m, j + 1, to);
    }

    private static int divide(@NotNull char[] m, int from, int to) {
        int i = from;
        int j = to + 1;
        StdOut.println("Pivot: " + m[from]);

        while (true) {
            while (m[++i] < m[from]) {
                if (i == to) {
                    break;
                }
            }

            while (m[--j] > m[from]) {
                if (j == from) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }
            exchange(m, i, j);

        }
        exchange(m, from, j);
        return j;
    }

    private static void exchange(@NotNull char[] m, int i, int j) {
        char t = m[i];
        m[i] = m[j];
        m[j] = t;
        for (int k = 0; k < m.length; k++) {
            if (k == m.length - 1) {
                StdOut.println(m[k]);
            } else {
                StdOut.print(m[k] + " ");
            }
        }
    }

    public static void main(String[] args) {
        char[] chars = "prozedur".toCharArray();
        StdOut.println("p r o z e d u r");
        quickSort(chars, 0, chars.length - 1);
        StdOut.println("Sorted:");
        for (int i = 0; i < chars.length; i++) {
            if (i == chars.length - 1) {
                StdOut.println(chars[i]);
            } else {
                StdOut.print(chars[i] + " ");
            }
        }
    }
}
