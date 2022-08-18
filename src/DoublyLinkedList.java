/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new java.lang.IndexOutOfBoundsException("Error, the index should be in range [0,size]");
        } else if (data == null) {
            throw new java.lang.IllegalArgumentException("Error, the data is null");
        } else {
            DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<>(data);
            if (size == 0) {
                head = temp;
                tail = temp;
                size++;
            } else {
                if (index == 0) {
                    temp.setNext(head);
                    head.setPrevious(temp);
                    head = temp;
                    size++;
                } else if (index == size) {
                    tail.setNext(temp);
                    temp.setPrevious(tail);
                    tail = temp;
                    size++;
                } else {
                    int position = 0;
                    DoublyLinkedListNode<T> curr = head;
                    while (position < index - 1) {
                        curr = curr.getNext();
                        position++;
                    }
                    temp.setNext(curr.getNext());
                    curr.getNext().setPrevious(temp);
                    curr.setNext(temp);
                    temp.setPrevious(curr);
                    size++;
                }
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Error, the data is null");
        } else {
            addAtIndex(0, data);
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Error, the data is null");
        } else {
            addAtIndex(size, data);
        }
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Error, the index should be in range [0,size)");
        } else {
            if (index == 0) {
                T res = head.getData();
                if (size == 1) {
                    head = null;
                    tail = null;
                    size--;
                } else {
                    head = head.getNext();
                    head.setPrevious(null);
                    size--;
                }
                return res;
            } else if (index == size - 1) {
                T res = tail.getData();
                if (size == 1) {
                    head = null;
                    tail = null;
                    size--;
                } else {
                    tail = tail.getPrevious();
                    tail.setNext(null);
                    size--;
                }
                return res;
            } else {
                int position = 0;
                DoublyLinkedListNode<T> curr = head;
                while (position < index) {
                    curr = curr.getNext();
                    position++;
                }
                T res = curr.getData();
                curr.getPrevious().setNext(curr.getNext());
                curr.getNext().setPrevious(curr.getPrevious());
                size--;
                return res;
            }
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Error, the list is empty");
        } else {
            return removeAtIndex(0);
        }
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Error, the list is empty");
        } else {
            return removeAtIndex(size - 1);
        }
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Error, the index should be in range [0,size)");
        } else {
            if (index == 0) {
                return head.getData();
            } else if (index == size - 1) {
                return tail.getData();
            } else {
                int position = 0;
                DoublyLinkedListNode<T> curr = head;
                while (position < index) {
                    curr = curr.getNext();
                    position++;
                }
                return curr.getData();
            }
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */

    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Error, data is null");
        } else {
            if (tail != null) {
                if (data.equals(tail.getData())) {
                    return removeAtIndex(size - 1);
                }
            }
            int position = size - 1;
            DoublyLinkedListNode<T> curr = tail;
            while (curr != null && !curr.getData().equals(data)) {
                curr = curr.getPrevious();
                position--;
            }
            if (curr == null) {
                throw new java.util.NoSuchElementException("Error, data is not found");
            } else {
                return removeAtIndex(position);
            }
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        int count = 0;
        DoublyLinkedListNode<T> curr = head;
        Object[] arr = new Object[size];
        while (curr != null) {
            arr[count] = curr.getData();
            curr = curr.getNext();
            count++;
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
