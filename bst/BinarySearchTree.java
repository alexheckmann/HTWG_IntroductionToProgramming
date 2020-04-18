package bst;

public class BinarySearchTree<T extends Comparable<T>> {

    private T value;
    private BinarySearchTree<T> leftSubTree;
    private BinarySearchTree<T> rightSubTree;

    public BinarySearchTree(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public BinarySearchTree<T> getLeftSubTree() {
        return leftSubTree;
    }

    public void setLeftSubTree(BinarySearchTree<T> leftSubTree) {
        this.leftSubTree = leftSubTree;
    }

    public BinarySearchTree<T> getRightSubTree() {
        return rightSubTree;
    }

    public void setRightSubTree(BinarySearchTree<T> rightSubTree) {
        this.rightSubTree = rightSubTree;
    }

    /**
     * Inserts a given value at the appropriate position of the tree.
     *
     * @param value value to be inserted
     */
    public void insert(T value) {

        if (value.compareTo(this.value) < 0) {

            if (leftSubTree != null) {
                leftSubTree.insert(value);
            } else {
                leftSubTree = new BinarySearchTree<>(value);
            }
        }

        if (value.compareTo(this.value) > 0) {
            if (rightSubTree != null) {
                rightSubTree.insert(value);
            } else {
                rightSubTree = new BinarySearchTree<>(value);
            }
        }
    }

    public void print() {

        if (leftSubTree != null) {
            leftSubTree.print();
        }
        System.out.println(this.value);
        if (rightSubTree != null) {
            rightSubTree.print();
        }
    }

    /**
     * Calculates the tree's current size.
     *
     * @return the tree's current size
     */
    public int size() {

        int size = 1;

        if (leftSubTree != null) {
            size += leftSubTree.size();
        }

        if (rightSubTree != null) {
            size += rightSubTree.size();
        }
        return size;
    }

    /**
     * Recursively checks whether the tree contains a given value.
     *
     * @param value value to search for
     * @return whether or not the tree contains the value.
     */
    public boolean contains(T value) {

        if (value.equals(this.value)) {
            return true;

        } else if (value.compareTo(this.value) < 0) {

            if (leftSubTree != null) {
                return leftSubTree.contains(value);
            }

        } else {

            if (rightSubTree != null) {
                return rightSubTree.contains(value);
            }

        }

        return false;
    }
}
