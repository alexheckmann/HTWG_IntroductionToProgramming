package sorting;

public class MaxHeap<T extends Comparable<T>> {

    private T[] a;
    private int N;

    public MaxHeap(int capacity) {
        N = 0;
        a = (T[]) new Comparable[capacity + 1];
    }

    public void insert(T element) {
        a[++N] = element;
        swim(N);
    }

    private void swim(int i) {
        while (i > 1 && a[i / 2].compareTo(a[i]) < 0) {
            swap(i, i / 2);
            i = i / 2;
        }
    }

    private void sink(int i) {
        while (2 * i <= N) {
            int j = 2 * i;
            if (j < N && a[j].compareTo(a[j + 1]) < 0) {
                j++;
            } else if (a[i].compareTo(a[i + 1]) >= 0) {
                break;
            }
            exchange(i, j);
            i = j;
        }
    }

    private void swap(int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public void print() {
        for (int i = 1; i <= N / 2; i++) {
            System.out.println(" PARENT : " + a[i].toString() + " LEFT CHILD : " +
                    a[2 * i].toString() + " RIGHT CHILD : " + a[2 * i + 1].toString());
        }
    }

    public T deleteMax() {
        T root = a[1];
        exchange(1, N--);
        a[N + 1] = null;
        sink(1);
        return root;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public static void main(String[] args) {

        char[] c = "Seezeit".toCharArray();
        MaxHeap<Character> maxHeap = new MaxHeap<>(c.length);
        for (char value : c) {
            maxHeap.insert(value);
        }

        System.out.println("The Max Heap is ");
        maxHeap.print();
        while (!maxHeap.isEmpty()) {
            StdOut.print((char) maxHeap.deleteMax() + " ");
        }

    }
}
