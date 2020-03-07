package exercise12;

public class Main {

    public static void main(String[] args) {

        BinarySearchTree<Integer> bst = new BinarySearchTree<>(5);
        bst.insert(10);
        bst.insert(3);
        bst.insert(2);
        bst.insert(12);
        bst.insert(15);
        bst.print();
        System.out.println("size: " + bst.size());
        System.out.println("is complete? " + isComplete(bst, 0, bst.size()));
        System.out.println("is full? " + isFull(bst));
        System.out.println("Contains 3? " + bst.contains(6));


    }


    public static boolean isComplete(BinarySearchTree root, int index, int numberNodes) {
        // An empty tree is complete
        if (root == null)
            return true;
        
        // If index assigned to current node is more than
        // number of nodes in tree, then tree is not complete
        if (index >= numberNodes)
            return false;

        // Recur for left and right subtrees
        return (isComplete(root.getLeftSubTree(), 2 * index + 1, numberNodes)
                && isComplete(root.getRightSubTree(), 2 * index + 2, numberNodes));

    }

    public static boolean isFull(BinarySearchTree root) {

        // empty tree is full(?)
        if (root == null) {
            return false;
        }


        // if any root has only one child the tree isn't full
        if (root.getLeftSubTree() == null && root.getRightSubTree() != null || root.getLeftSubTree() != null && root.getRightSubTree() == null) {
            return false;
        }

        // if both children are null the tree is full
        if (root.getLeftSubTree() == null && root.getRightSubTree() == null) {
            return true;
        }

        // recur for left and right children
        return (isFull(root.getLeftSubTree()) && isFull(root.getRightSubTree()));

    }
}
