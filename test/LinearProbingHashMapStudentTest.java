import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * This is a basic set of unit tests for LinearProbingHashMap.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class LinearProbingHashMapStudentTest {

    private static final int TIMEOUT = 200;
    private LinearProbingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new LinearProbingHashMap<>();

    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testPut() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        String temp = "D";

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, temp));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D)X, (5, E), _, _, _, _, _, _, _]
        assertSame(temp, map.remove(4));
        assertEquals(4, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[
                LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[4].setRemoved(true);
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertEquals("A", map.get(1));
        assertEquals("B", map.get(2));
        assertEquals("C", map.get(3));
        assertEquals("D", map.get(4));
        assertEquals("E", map.get(5));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKey() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        assertTrue(map.containsKey(3));
        assertFalse(map.containsKey(6));
    }

    @Test(timeout = TIMEOUT)
    public void testKeySet() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);
        assertEquals(expected, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValues() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        List<String> expected = new LinkedList<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("D");
        expected.add("E");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    public void testResize() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        // [_, (1, A), (2, B), (3, C), (4, D), (5, E)]
        map.resizeBackingTable(6);
        assertEquals(5, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[6];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        expected[3] = new LinearProbingMapEntry<>(3, "C");
        expected[4] = new LinearProbingMapEntry<>(4, "D");
        expected[5] = new LinearProbingMapEntry<>(5, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        // [_, (1, A), (2, B), (3, C), (4, D), (5, E), _, _, _, _, _, _, _]
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertNull(map.put(5, "E"));

        map.clear();
        assertEquals(0, map.size());
        assertArrayEquals(new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());

    }

    @Test(expected= NoSuchElementException.class)
    public void testSearchingForDeleted() {
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));

        // delete 3
        assertEquals("C", map.remove(3));

        assertEquals("C", map.get(3));
    }

    @Test(timeout = TIMEOUT)
    public void testChangingSize() {
        assertEquals(0, map.size());

        assertNull(map.put(1, "A"));
        assertEquals(1, map.size());

        assertNull(map.put(2, "B"));
        assertEquals(2, map.size());

        assertNull(map.put(3, "C"));
        assertEquals(3, map.size());

        assertNull(map.put(4, "D"));
        assertEquals(4, map.size());

        assertEquals("D", map.remove(4));
        assertEquals(3, map.size());

        assertEquals("C", map.remove(3));
        assertEquals(2, map.size());

        assertEquals("B", map.remove(2));
        assertEquals(1, map.size());
    }

    @Test(timeout=TIMEOUT)
    public void testUpdatingExistingValue() {
        assertNull(map.put(1, "A"));
        assertNull(map.put(2, "B"));
        assertNull(map.put(3, "C"));
        assertNull(map.put(4, "D"));
        assertEquals(4, map.size());
        assertEquals("A", map.put(1, "E"));
        assertEquals(4, map.size());
        assertEquals("C", map.put(3, "F"));
        assertEquals(4, map.size());
    }
    @Test(timeout = TIMEOUT)
    public void extraTest1() {
        // Test 1:
        // removing a key and then adding at that key again, checking to see if it's not removed + size change
        // [_, (1, A), _, _, _, _, _, _, _, _, _, _, _]
        // -->
        // [_, (1, B), _, _, _, _, _, _, _, _, _, _, _]
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        System.out.println("Test 1:");
        map.put(1, "A");
        map.remove(1);
        map.put(1, "B");
        expected[1] = new LinearProbingMapEntry(1,"B");
        assertEquals(1, map.size());
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void extraTest2() {
        // Test 2:
        // filling holes with deleted nodes, attempt to add after
        // [(0, A), (1, B), (2, C), (3, D), (4, E), (5, F), (6, G)X, (7, H)X, (8, I)X, (9, J)X, (10, K)X, (11, L)X, (12, M)X]
        // -->
        // [(0, A), (1, B), (2, C), (3, D), (4, E), (5, F), (6, N), (7, H)X, (8, I)X, (9, J)X, (10, K)X, (11, L)X, (12, M)X]
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY];
        System.out.println("Test 2:");
        for(int i = 0; i < 6; i++) {
            String value = Character.toString((char)(i + 'A'));
            map.put(i, value);
            expected[i] = new LinearProbingMapEntry<>(i, value);
        }
        for(int i = 6; i < 13; i++) {
            String value = Character.toString((char)(i + 'A'));
            map.put(i, value);
            map.remove(i);
            expected[i] = new LinearProbingMapEntry<>(i, value);
            expected[i].setRemoved(true);
        }
        map.put(6, "N");
        expected[6] = new LinearProbingMapEntry<>(6, "N");
        // if you get a timeout here, you may not be stopping after iterating over all active elements (consider
        // keeping track of how many elements you've seen)
        assertEquals(7, map.size());
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void extraTest4() {
        // Test 4:
        // test auto resize upon adding
        // [(0, A), (14, O), (2, C), _, (4, E), _, (6, G), _, (8, I), _, (10, K), _, (12, M)]
        // -->
        // [(0, A), _, (2, C), _, (4, E), _, (6, G), _, (8, I), _, (10, K), _, (12, M), _, (14, O), _, (16, Q), _, _, _, _, _, _, _, _, _, _]
        LinearProbingMapEntry[] expected =
                new LinearProbingMapEntry[
                        LinearProbingHashMap.INITIAL_CAPACITY*2+1];
        System.out.println("Test 4:");
        // filling in every other to see if resize will rehash key value pair (14, O)
        for(int i = 0; i < 16; i+=2) {
            String value = Character.toString((char)(i + 'A'));
            map.put(i, value);
            expected[i] = new LinearProbingMapEntry(i, value);
        }
        map.put(16, "Q");
        expected[16] = new LinearProbingMapEntry(16, "Q");
        assertEquals(9, map.size());
        assertArrayEquals(expected, map.getTable());
    }
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void putNullKey() {
        map.put(null, "A");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void putNullValue() {
        map.put(1, null);
    }

    @Test(timeout = TIMEOUT)
    public void putRemovedKey() {
        map.put(1, "A");
        map.remove(1);
        map.put(1, "B");
        assertEquals(1, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "B");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void putProbingKey() {
        map.put(1, "A");
        map.put(14, "B");
        assertEquals(2, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(14, "B");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void putLoopingKey() {
        //cracked test
        map.put(0, "0");
        map.put(1, "1");
        map.put(12, "12");
        map.put(25, "25");
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[0] = new LinearProbingMapEntry<>(0, "0");
        expected[1] = new LinearProbingMapEntry<>(1, "1");
        expected[2] = new LinearProbingMapEntry<>(25, "25");
        expected[12] = new LinearProbingMapEntry<>(12, "12");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void putOutOfOrder() {
        map.put(8, "8");
        map.put(10, "10");
        map.put(21, "21");
        map.put(9, "9");
        map.put(11, "11");
        map.put(34, "34");
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[0] = new LinearProbingMapEntry<>(34, "34");
        expected[8] = new LinearProbingMapEntry<>(8, "8");
        expected[9] = new LinearProbingMapEntry<>(21, "21");
        expected[10] = new LinearProbingMapEntry<>(10, "10");
        expected[11] = new LinearProbingMapEntry<>(9, "9");
        expected[12] = new LinearProbingMapEntry<>(11, "11");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void removeNullKey() {
        map.put(1, "A");
        map.put(14, "B");
        map.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeSameHash() {
        map.put(1, "A");
        map.put(2, "B");
        map.remove(14);
        assertEquals(2, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFlaggedKey() {
        map.put(1, "A");
        map.put(2, "B");
        map.remove(2);
        map.remove(2);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeNonexistentKey() {
        map.put(1, "A");
        map.put(2, "B");
        map.remove(3);
    }

    @Test(timeout = TIMEOUT)
    public void removeOverRemoved() {
        //cracked test
        map.put(1, "1");
        map.put(14, "14");
        map.put(27, "27");
        map.remove(14);
        map.remove(27);
        assertEquals(1, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "1");
        expected[2] = new LinearProbingMapEntry<>(14, "14");
        expected[3] = new LinearProbingMapEntry<>(27, "27");
        expected[2].setRemoved(true);
        expected[3].setRemoved(true);
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void removeOverLoop() {
        map.put(12, "12");
        map.put(11, "11");
        map.put(10, "10");
        map.put(1, "1");
        map.put(0, "0");
        map.put(23, "23");
        map.remove(23);
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[0] = new LinearProbingMapEntry<>(0, "0");
        expected[1] = new LinearProbingMapEntry<>(1, "1");
        expected[2] = new LinearProbingMapEntry<>(23, "23");
        expected[2].setRemoved(true);
        expected[10] = new LinearProbingMapEntry<>(10, "10");
        expected[11] = new LinearProbingMapEntry<>(11, "11");
        expected[12] = new LinearProbingMapEntry<>(12, "12");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void getNullKey() {
        map.put(1, "A");
        map.put(2, "B");
        map.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getNonexistentKey() {
        map.put(1, "A");
        map.put(2, "B");
        map.get(3);
    }

    @Test(timeout = TIMEOUT)
    public void getOverRemoved() {
        map.put(1, "1");
        map.put(14, "14");
        map.put(27, "27");
        map.remove(14);
        assertEquals("27", map.get(27));
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getRemoved() {
        map.put(1, "1");
        map.put(14, "14");
        map.put(27, "27");
        map.remove(14);
        map.get(14);
    }

    @Test(timeout = TIMEOUT)
    public void getOverLoop() {
        map.put(12, "12");
        map.put(11, "11");
        map.put(10, "10");
        map.put(1, "1");
        map.put(0, "0");
        map.put(23, "23");
        assertEquals("23", map.get(23));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void containsKeyNull() {
        map.put(12, "12");
        map.put(11, "11");
        map.put(10, "10");
        map.put(1, "1");
        map.put(0, "0");
        map.put(23, "23");
        map.containsKey(null);
    }

    @Test(timeout = TIMEOUT)
    public void containsKeyLooped() {
        map.put(12, "12");
        map.put(11, "11");
        map.put(10, "10");
        map.put(1, "1");
        map.put(0, "0");
        map.put(23, "23");
        assertTrue(map.containsKey(23));
    }

    @Test(timeout = TIMEOUT)
    public void containsKeyIterateOverRemoved() {
        map.put(12, "12");
        map.put(11, "11");
        map.put(10, "10");
        map.put(1, "1");
        map.put(0, "0");
        map.put(23, "23");
        map.remove(0);
        map.remove(1);
        map.remove(12);
        assertTrue(map.containsKey(23));
    }

    @Test(timeout = TIMEOUT)
    public void doubleResize() {
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[(LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1) * 2 + 1];
        for (int i = 0; i < 20; i++) {
            map.put(i, i + "");
            expected[i] = new LinearProbingMapEntry<>(i, i + "");
        }
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void resizeRemovedHandling() {
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        for (int i = 0; i < 10; i++) {
            map.put(i, i + "");
            expected[i] = new LinearProbingMapEntry<>(i, i + "");
            if (i == 4) {
                map.remove(i);
                expected[i] = null;
            }
        }
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void resizeRehash() {
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        for (int i = 10; i < 19; i++) {
            map.put(i, i + "");
            expected[i] = new LinearProbingMapEntry<>(i, i + "");
        }
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void resizeOutOfOrder() {
        map.put(8, "8");
        map.put(10, "10");
        map.put(21, "21");
        map.put(9, "9");
        map.put(11, "11");
        map.put(34, "34");
        map.put(23, "23");
        map.put(35, "35");
        assertEquals(13, map.getTable().length);
        map.put(7, "7");
        assertEquals(27, map.getTable().length);
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        expected[7] = new LinearProbingMapEntry<>(34, "34");
        expected[8] = new LinearProbingMapEntry<>(35, "35");
        expected[9] = new LinearProbingMapEntry<>(8, "8");
        expected[10] = new LinearProbingMapEntry<>(10, "10");
        expected[11] = new LinearProbingMapEntry<>(9, "9");
        expected[12] = new LinearProbingMapEntry<>(11, "11");
        expected[13] = new LinearProbingMapEntry<>(7, "7");
        expected[21] = new LinearProbingMapEntry<>(21, "21");
        expected[23] = new LinearProbingMapEntry<>(23, "23");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void resizeComprehensive() {
        map.put(8, "8");
        map.put(10, "10");
        map.put(21, "21");
        map.put(9, "9");
        map.put(11, "11");
        map.put(34, "34");
        map.put(23, "23");
        map.put(35, "35");
        map.remove(35);
        map.put(1, "1");
        assertEquals(13, map.getTable().length);
        map.put(7, "7");
        assertEquals(27, map.getTable().length);
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        expected[1] = new LinearProbingMapEntry<>(1, "1");
        expected[7] = new LinearProbingMapEntry<>(34, "34");
        expected[8] = new LinearProbingMapEntry<>(8, "8");
        expected[9] = new LinearProbingMapEntry<>(9, "9");
        expected[10] = new LinearProbingMapEntry<>(10, "10");
        expected[11] = new LinearProbingMapEntry<>(11, "11");
        expected[12] = new LinearProbingMapEntry<>(7, "7");
        expected[21] = new LinearProbingMapEntry<>(21, "21");
        expected[23] = new LinearProbingMapEntry<>(23, "23");
        assertEquals(expected, map.getTable());
        assertTrue(!map.containsKey(35));
        map.remove(11);
        expected[11].setRemoved(true);
        map.put(37, "37");
        expected[11] = new LinearProbingMapEntry<>(37, "37");
        assertEquals(expected, map.getTable());
    }
}
