package exercise12;

public class BinarySearchTree {

    private int value;
    private BinarySearchTree leftSubTree;
    private BinarySearchTree rightSubTree;

    public BinarySearchTree(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BinarySearchTree getLeftSubTree() {
        return leftSubTree;
    }

    public void setLeftSubTree(BinarySearchTree leftSubTree) {
        this.leftSubTree = leftSubTree;
    }

    public BinarySearchTree getRightSubTree() {
        return rightSubTree;
    }

    public void setRightSubTree(BinarySearchTree rightSubTree) {
        this.rightSubTree = rightSubTree;
    }

    public void insert(int value) {

        if (value < this.value) {

            if (leftSubTree != null) {
                leftSubTree.insert(value);
            } else {
                leftSubTree = new BinarySearchTree(value);
            }
        }

        if (value > this.value) {
            if (rightSubTree != null) {
                rightSubTree.insert(value);
            } else {
                rightSubTree = new BinarySearchTree(value);
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

    public boolean contains(int value) {

        if (value == this.value) {
            return true;

        } else if (value < this.value) {

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
