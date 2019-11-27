package sorting;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Sort {
    private static double[] grades = {1.0, 1.3, 1.7, 2.0, 2.3, 2.7, 3.0, 3.3, 3.7, 4.0, 5.0};
    private static Student[] students;


    private static void mergeSort(Student[] m, Student[] hilfsarray, int from, int to) {
        if (m == null || m.length < 1) {
            throw new IllegalArgumentException();
        } else {
            if (to <= from) {
                return;
            }
            // declare middle
            int middle = from + (to - from) / 2;
            //sort left side
            mergeSort(m, hilfsarray, from, middle);
            //sort right side
            mergeSort(m, hilfsarray, middle + 1, to);
            // combine
            merge(m, hilfsarray, from, middle, to);
        }
    }

    private static void merge(Student[] m, Student[] hilfsarray, int from, int middle, int to) {
        for (int k = from; k <= to; k++) {
            hilfsarray[k] = m[k];
        }

        int i = from;
        int j = middle + 1;
        int k = from;

        // kleinsten Wert in Array zurück kopieren
        while (i <= middle && j <= to) {
            if (hilfsarray[i].getGrade() <= hilfsarray[j].getGrade()) {
                m[k] = hilfsarray[i];
                i++;
            } else {
                m[k] = hilfsarray[j];
                j++;
            }
            k++;
        }
        // copy left side
        while (i <= middle) {
            m[k] = hilfsarray[i];
            k++;
            i++;
        }


    }

    private static void insertionSort(Student[] m) {
        if (m == null || m.length < 1) {
            throw new IllegalArgumentException();
        } else {
            int N = m.length;

            for (int i = 0; i < N; i++) {
                for (int j = i; j > 0; j--) {
                    if (m[j - 1].getGrade() > m[j].getGrade()) {
                        exchange(m, j - 1, j);
                    } else {
                        break;
                    }
                }

            }
        }
    }

    private static void exchange(@NotNull Student[] m, int i, int j) {
        Student t = m[i];
        m[i] = m[j];
        m[j] = t;
    }

    private static void input() {
        In in = new In("mails");
        Random random = new Random();
        String[] mails = in.readAllStrings();
        students = new Student[mails.length];

        for (int i = 0; i < mails.length; i++) {
            int randomIndex = random.nextInt(grades.length);
            students[i] = new Student();
            students[i].setMail(mails[i]);
            students[i].setGrade(grades[randomIndex]);

        }


    }

    public static void main(String[] args) {
        input();
        int N = students.length;
        Stopwatch stopwatch = new Stopwatch();
        insertionSort(students);

        Student[] hilfsarray = new Student[N];
        mergeSort(students, hilfsarray, 0, N - 1);
        StdOut.println(stopwatch.elapsedTime());
        for (Student s : students) {
            StdOut.print(s.getMail() + " " + s.getGrade() + "\n");
        }

    }
}
