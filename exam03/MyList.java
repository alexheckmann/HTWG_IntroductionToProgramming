package exam01;

import java.util.ArrayList;
import java.util.Collections;

public class MyList<T extends Comparable<T>> extends ArrayList<T> {

    public T getSmallestElement() {
        Collections.sort(this);
        return this.get(0);
    }
}
