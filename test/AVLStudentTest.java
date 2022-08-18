import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * These tests are not exhaustive.
 * @author CS 1332 TAs
 * @version 1.0
 */
public class AVLStudentTest {
    private static final int TIMEOUT = 200;
    private AVL<Integer> avlTree;

    @Before
    public void setup() {
        avlTree = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void testAddRightRotation() {
        /*
                    5                   4
                   /                   / \
                  4         ->        3   5
                 /
                3
         */
        avlTree.add(5);
        avlTree.add(4);
        avlTree.add(3);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddRightLeftRotationRoot() {
        /*
                3               4
                 \             / \
                  5     ->    3   5
                 /
                4
         */
        avlTree.add(3);
        avlTree.add(5);
        avlTree.add(4);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        /*
                    646                     646
                   /   \                   /   \
                 477   856      ->       526   856
                 / \                     /
               386 526                 386
         */
        Integer toBeRemoved = new Integer(477);
        avlTree.add(646);
        avlTree.add(toBeRemoved);
        avlTree.add(856);
        avlTree.add(386);
        avlTree.add(526);

        assertSame(toBeRemoved, avlTree.remove(new Integer(477)));

        assertEquals(4, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 646, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        assertEquals((Integer) 526, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 856, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 386, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        /*
                        477
                       /   \
                     386   526
                              \
                              646
         */
        Integer maximum = new Integer(646);
        avlTree.add(477);
        avlTree.add(526);
        avlTree.add(386);
        avlTree.add(maximum);

        assertSame(maximum, avlTree.get(new Integer(646)));
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        /*
                        477
                       /   \
                     386   526
                              \
                              646
         */
        avlTree.add(new Integer(477));
        avlTree.add(new Integer(526));
        avlTree.add(new Integer(386));
        avlTree.add(new Integer(646));

        assertEquals(true, avlTree.contains(new Integer(477)));
        assertEquals(true, avlTree.contains(new Integer(386)));
        assertEquals(true, avlTree.contains(new Integer(646)));
        assertEquals(false, avlTree.contains(new Integer(387)));
        assertEquals(false, avlTree.contains(new Integer(700)));
        assertEquals(false, avlTree.contains(new Integer(500)));
    }



    @Test(timeout = TIMEOUT)
    public void testHeight() {
        /*
                     646
                    /   \
                  477   856
                  / \
                386 526
         */
        avlTree.add(646);
        avlTree.add(386);
        avlTree.add(856);
        avlTree.add(526);
        avlTree.add(477);

        assertEquals(2, avlTree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructorAndClear() {
        /*
                     7
                    / \
                   1   24
        */

        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(7);
        toAdd.add(24);
        toAdd.add(1);
        avlTree = new AVL<>(toAdd);

        avlTree.clear();
        assertEquals(null, avlTree.getRoot());
        assertEquals(0, avlTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void testMassAddAndRemove() {
        List<Integer> toAdd = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            toAdd.add(i);
        }

        avlTree = new AVL<>(toAdd);

        assertEquals((Integer) 1, avlTree.getRoot().getLeft().getLeft().getLeft().getData());
        assertEquals((Integer) 2, avlTree.getRoot().getLeft().getLeft().getData());
        assertEquals((Integer) 3, avlTree.getRoot().getLeft().getLeft().getRight().getData());
        assertEquals((Integer) 4, avlTree.getRoot().getLeft().getData());
        assertEquals((Integer) 5, avlTree.getRoot().getLeft().getRight().getLeft().getData());
        assertEquals((Integer) 6, avlTree.getRoot().getLeft().getRight().getData());
        assertEquals((Integer) 7, avlTree.getRoot().getLeft().getRight().getRight().getData());
        assertEquals((Integer) 8, avlTree.getRoot().getData());
        assertEquals((Integer) 9, avlTree.getRoot().getRight().getLeft().getLeft().getData());
        assertEquals((Integer) 10, avlTree.getRoot().getRight().getLeft().getData());
        assertEquals((Integer) 11, avlTree.getRoot().getRight().getLeft().getRight().getData());
        assertEquals((Integer) 12, avlTree.getRoot().getRight().getData());
        assertEquals((Integer) 13, avlTree.getRoot().getRight().getRight().getLeft().getData());
        assertEquals((Integer) 14, avlTree.getRoot().getRight().getRight().getData());
        assertEquals((Integer) 15, avlTree.getRoot().getRight().getRight().getRight().getData());

        List<Integer> toRemove = new ArrayList<>();
        toRemove.add(8);
        toRemove.add(14);
        toRemove.add(5);
        toRemove.add(7);
        Integer tmp;
        for (Integer element : toRemove) {
            tmp = avlTree.remove(element);
            assertEquals(element, tmp);
        }
        assertEquals(11, avlTree.size());
    }
}