import java.util.Collection;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Guanming Chen
 * @userid gchen353
 * @GTID 903661790
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
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
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
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
    private AVLNode<T> rAdd(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        return updateTrees(curr);
    }

    /**
     * the method to update height and balanced factors of the current visited tree node
     * @param curr the current visited tree node
     */
    private void uHeightBalanced(AVLNode<T> curr) {
        int lHeight;
        int rHeight;
        if (curr.getLeft() == null) {
            lHeight = -1;
        } else {
            lHeight = curr.getLeft().getHeight();
        }
        if (curr.getRight() == null) {
            rHeight = -1;
        } else {
            rHeight = curr.getRight().getHeight();
        }
        curr.setHeight(Math.max(lHeight, rHeight) + 1);
        curr.setBalanceFactor(lHeight - rHeight);
    }

    /**
     * the method to re-struct the tree if data is founded
     * @param curr the current visited tree node
     * @return the new tree
     */
    private AVLNode<T> updateTrees(AVLNode<T> curr) {
        uHeightBalanced(curr);
        if (curr.getBalanceFactor() >= 2) {
            if (curr.getLeft().getBalanceFactor() <= -1) {
                curr.setLeft(leftRotation(curr.getLeft()));
                curr = rightRotation(curr);
            } else {
                curr = rightRotation(curr);
            }
        } else if (curr.getBalanceFactor() <= -2) {
            if (curr.getRight().getBalanceFactor() >= 1) {
                curr.setRight(rightRotation(curr.getRight()));
                curr = leftRotation(curr);
            } else {
                curr = leftRotation(curr);
            }
        }
        return curr;
    }

    /**
     * the method to conduct left rotation for the unbalanced subtree
     * @param nodeA current visited unbalanced tree node
     * @return the new root of the subtree after rotation
     */
    private AVLNode<T> leftRotation(AVLNode<T> nodeA) {
        AVLNode<T> nodeB = nodeA.getRight();
        nodeA.setRight(nodeB.getLeft());
        nodeB.setLeft(nodeA);
        uHeightBalanced(nodeA);
        uHeightBalanced(nodeB);
        return nodeB;
    }

    /**
     * the method to conduct right rotation for the unbalanced subtree
     * @param nodeC current visited unbalanced tree node
     * @return the new root of the subtree after rotation
     */
    private AVLNode<T> rightRotation(AVLNode<T> nodeC) {
        AVLNode<T> nodeB = nodeC.getLeft();
        nodeC.setLeft(nodeB.getRight());
        nodeB.setRight(nodeC);
        uHeightBalanced(nodeC);
        uHeightBalanced(nodeB);
        return nodeB;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        } else {
            AVLNode<T> dummy = new AVLNode<>(null);
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
    private AVLNode<T> rRemove(AVLNode<T> curr, T data, AVLNode<T> dummy) {
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
    private AVLNode<T> dataFound(AVLNode<T> curr, AVLNode<T> dummy) {
        dummy.setData(curr.getData());
        size--;
        if (curr.getLeft() == null && curr.getRight() == null) {
            return null;
        } else if (curr.getRight() == null) {
            return curr.getLeft();
        } else if (curr.getLeft() == null) {
            return curr.getRight();
        } else {
            AVLNode<T> dummy2 = new AVLNode<>(null);
            curr.setRight(removeSuccessor(curr.getRight(), dummy2));
            curr.setData(dummy2.getData());
            return updateTrees(curr);
        }
    }

    /**
     * the recursive helper method to find the predecessor of current node and update the subtree
     * @param curr the current visited tree node
     * @param dummy the node to save the Predecessor tree node data
     * @return the new subtree after removing
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        }
        return curr;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
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
    private T rGet(AVLNode<T> curr, T data) {
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
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
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
    private boolean rContains(AVLNode<T> curr, T data) {
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
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}