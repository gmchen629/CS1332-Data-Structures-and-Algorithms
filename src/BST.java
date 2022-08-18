import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Collection;
/**
 * Your implementation of a BST.
 *
 * @author Guanming Chen
 * @version 1.0
 * @userid gchen353
 * @GTID 903661790
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Error, data is null");
        } else {
            for (T ele : data) {
                if (ele == null) {
                    throw new java.lang.IllegalArgumentException("Error, one of the elements in the data is null");
                } else {
                    add(ele);
                }
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Error, data is null");
        } else {
            root = rAdd(root, data);
        }
    }

    /**
     * the recursive helper method to add data to the tree
     * @param curr the current visited tree node
     * @param data the data to add
     * @return the new tree
     */
    private BSTNode<T> rAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Error, data is null");
        } else {
            BSTNode<T> dummy = new BSTNode<>(null);
            root = rRemove(root, data, dummy);
            return dummy.getData();
        }
    }

    /**
     * the recursive helper method to remove data from the tree
     * @param curr the current visited tree node
     * @param data the data to remove
     * @param dummy the node to save the removed data
     * @return the new tree
     */
    private BSTNode<T> rRemove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Error, data is not in the tree");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(curr.getRight(), data, dummy));
        } else {
            return dataFound(curr, dummy);
        }
        return curr;
    }

    /**
     * the method to re-struct the tree if data is founded
     * @param curr the current visited tree node
     * @param dummy the node to save the removed data
     * @return the new tree
     */
    private BSTNode<T> dataFound(BSTNode<T> curr, BSTNode<T> dummy) {
        dummy.setData(curr.getData());
        size--;
        if (curr.getLeft() == null && curr.getRight() == null) {
            return null;
        } else if (curr.getRight() == null) {
            return curr.getLeft();
        } else if (curr.getLeft() == null) {
            return curr.getRight();
        } else {
            BSTNode<T> dummy2 = new BSTNode<>(null);
            curr.setLeft(removePredecessor(curr.getLeft(), dummy2));
            curr.setData(dummy2.getData());
            return curr;
        }
    }

    /**
     * the recursive helper method to find the predecessor of current node and update the subtree
     * @param curr the current visited tree node
     * @param dummy the node to save the Predecessor tree node data
     * @return the new subtree after removing
     */

    private BSTNode<T> removePredecessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(removePredecessor(curr.getRight(), dummy));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Error, data is null");
        } else {
            return rGet(root, data);
        }
    }

    /**
     * the recursive method to get the data if it is in the tree
     * @param curr current visited tree node
     * @param data the data to search for
     * @return the data to search for
     */
    private T rGet(BSTNode<T> curr, T data) {
        T res;
        if (curr == null) {
            throw new java.util.NoSuchElementException("Error, data is not in the tree");
        } else if (data.compareTo(curr.getData()) == 0) {
            res = curr.getData();
        } else if (data.compareTo(curr.getData()) < 0) {
            res = (rGet(curr.getLeft(), data));
        } else {
            res = rGet(curr.getRight(), data);
        }
        return res;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Error, data is null");
        } else {
            return rContains(root, data);
        }
    }

    /**
     * the recursive method to check if the data is in the tree
     * @param curr current visited tree node
     * @param data the data to search for
     * @return true if the tree contains data, otherwise false
     */
    private boolean rContains(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (data.compareTo(curr.getData()) == 0) {
            return true;
        } else if (data.compareTo(curr.getData()) < 0) {
            return rContains(curr.getLeft(), data);
        } else {
            return rContains(curr.getRight(), data);
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preOrder = new LinkedList<>();
        preOrderTraversal(preOrder, root);
        return preOrder;
    }

    /**
     * the recursive method to traverse data in preoder
     * @param list the data list in preorder
     * @param curr the current visited tree node
     */
    private void preOrderTraversal(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            list.add(curr.getData());
            preOrderTraversal(list, curr.getLeft());
            preOrderTraversal(list, curr.getRight());
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inOrder = new LinkedList<>();
        inOrderTraversal(inOrder, root);
        return inOrder;
    }

    /**
     * the recursive method to traverse data in inoder
     * @param list the data list in preorder
     * @param curr the current visited tree node
     */
    private void inOrderTraversal(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            inOrderTraversal(list, curr.getLeft());
            list.add(curr.getData());
            inOrderTraversal(list, curr.getRight());
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postOrder = new LinkedList<>();
        postOrderTraversal(postOrder, root);
        return postOrder;
    }

    /**
     * the recursive method to traverse data in postorder
     * @param list the data list in preorder
     * @param curr the current visited tree node
     */
    private void postOrderTraversal(List<T> list, BSTNode<T> curr) {
        if (curr != null) {
            postOrderTraversal(list, curr.getLeft());
            postOrderTraversal(list, curr.getRight());
            list.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> levelOrder = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> curr = queue.remove();
            if (curr != null) {
                levelOrder.add(curr.getData());
                queue.add(curr.getLeft());
                queue.add(curr.getRight());
            }
        }
        return levelOrder;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return heightHelper(root);
        }
    }

    /**
     * the recursive method to calculate the height of the root
     * @param curr the current visited tree node
     * @return the tree height
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            int left = heightHelper(curr.getLeft());
            int right = heightHelper(curr.getRight());
            return Math.max(left, right) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
