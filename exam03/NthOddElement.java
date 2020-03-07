package exam03;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class NthOddElement {

    public static int findNthOddElement(List<Integer> list, int n) {
        if (list.get(2 * n - 1) % 2 == 0) {
            throw new NoSuchElementException();
        } else {
            return list.get(2 * n - 1);
        }
    }

    @Test
    public void testFindNthOddElement() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);

        int n = findNthOddElement(list, 1);

        assertEquals(n, 1);

    }

    @Test(expected = NoSuchElementException.class)
    public void testFindNthOddElement_even() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        int n = findNthOddElement(list, 1);

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testFindNthOddElement_outOfBounds() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        int n = findNthOddElement(list, 3);

    }
}
