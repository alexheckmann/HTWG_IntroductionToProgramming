package sorting;

public class MaxHeap {

    private Integer[] a;
    private int N;

    public MaxHeap(int capacity) {
        N = 0;
        a = new Integer[capacity + 1];
    }

    public void insert(int element) {
        a[++N] = element;
        swim(N);
    }

    private void swim(int i) {
        while (i > 1 && a[i / 2] < a[i]) {
            exchange(i, i / 2);
            i = i / 2;
        }
    }

    private void sink(int i) {
        while (2 * i <= N) {
            int j = 2 * i;
            if (j < N && a[j] < a[j + 1]) {
                j++;
            } else if (a[i] >= a[i + 1]) {
                break;
            }
            exchange(i, j);
            i = j;
        }
    }

    private void exchange(int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public void print() {
        for (int i = 1; i <= N / 2; i++) {
            System.out.print(" PARENT : " + (char) a[i].intValue() + " LEFT CHILD : " +
                    (char) a[2 * i].intValue() + " RIGHT CHILD : " + (char) a[2 * i + 1].intValue() + "\n");
        }
    }

    public int deleteMax() {
        int root = a[1];
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
        MaxHeap maxHeap = new MaxHeap(c.length);
        for (int i = 0; i < c.length; i++) {
            maxHeap.insert(c[i]);
        }

        System.out.println("The Max Heap is ");
        maxHeap.print();
        while (!maxHeap.isEmpty()) {
            StdOut.print((char) maxHeap.deleteMax() + " ");
        }

    }
}
