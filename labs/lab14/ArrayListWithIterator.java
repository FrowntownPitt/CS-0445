
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

 /* This class implements a list using an array with an inner class for 
 * an iterator.
 * 
 * This code is from Chapter 15 of 
 * Data Structures and Abstractions with Java 4/e
 * @author Frank M. Carrano
 *      
 */

public class ArrayListWithIterator<T> implements
        ListWithIteratorInterface<T> {

    private T[] list; //Array of list entries; ignore list[0]
    private int numberOfEntries; // current number of entries in list
    private boolean initialized = false;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;

    public ArrayListWithIterator() {
        this(DEFAULT_CAPACITY);
    } // end default constructor

    public ArrayListWithIterator(int initialCapacity) {
        // Is initialCapacity too small?
        if (initialCapacity < DEFAULT_CAPACITY)
            initialCapacity = DEFAULT_CAPACITY;
        else // Is initialCapacity too big?
            checkCapacity(initialCapacity);
        
        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        T[] tempList = (T[]) new Object[initialCapacity + 1];
        list = tempList;
        numberOfEntries = 0;
        initialized = true;
    } // end constructor

    /** Throws an exception if this object is not initialized.
     * 
     */
    private void checkInitialization(){
        if (!initialized)
             throw new SecurityException("ArrayBag object is not initialized " +
                                        "properly.");
    } // end checkInitialization

    /** Throws an exception if the desired capacity exceeds the maximum.
     * 
     */
    private void checkCapacity(int desiredCapacity){
        if(desiredCapacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a bag " +
                                            "whose capacity exceeds " +
                                            "allowed maximum.");
    } // end checkCapacity
    
    public void add(T newEntry) {
        checkInitialization();
        list[numberOfEntries+1] = newEntry;
        numberOfEntries++;
        ensureCapacity();
    } // end add

    // Precondition: The array list has room for another entry
    public void add(int newPosition, T newEntry) {
        checkInitialization();

        if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
            if (newPosition <= numberOfEntries) {
                makeRoom(newPosition);
            }

            list[newPosition] = newEntry;
            numberOfEntries++;
            ensureCapacity(); // Ensure enough room for next add
        } else {
            throw new IndexOutOfBoundsException("Illegal position given to add operation.");
        }
    } // end add

    public T remove(int givenPosition) {
        checkInitialization();
        
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            assert !isEmpty();
            T result = list[givenPosition ]; // Get entry to be removed
            // Move subsequent entries toward entry to be removed,
            // unless it is last in list
            if (givenPosition < numberOfEntries) {
                removeGap(givenPosition);
            }
            numberOfEntries--;
            return result; // Return reference to removed entry
        }
        else
             throw new IndexOutOfBoundsException("Illegal position given to remove operation.");

    } // end remove

    public void clear() {
        numberOfEntries = 0;
    } // end clear

    public T replace(int givenPosition, T newEntry) {
        checkInitialization();

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            assert !isEmpty();
            T originalEntry = list[givenPosition];
            list[givenPosition] = newEntry;
            return originalEntry;
        } else 
             throw new IndexOutOfBoundsException("Illegal position give to replace operation.");
 
    } // end replace

    public T getEntry(int givenPosition) {
        checkInitialization();
        
        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            assert !isEmpty();
            return list[givenPosition];
        } else 
             throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
    } // end getEntry

    public boolean contains(T anEntry) {
        checkInitialization();
        
        boolean found = false;
        int index = 1;

        while (!found && (index <= numberOfEntries)) {
            if (anEntry.equals(list[index])) {
                found = true;
            }
            index++;
        } // end for

        return found;
    } // end contains

    public int getLength() {
        return numberOfEntries;
    } // end getLength

    public boolean isEmpty() {
        return numberOfEntries == 0;
    } // end isEmpty

    public T[] toArray() {
        checkInitialization();
        
        // the cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[numberOfEntries];

        for (int index = 0; index < numberOfEntries; index++) {
            result[index] = list[index+1];
        } // end for

        return result;
    } // end toArray

    // Doubles the size of the array list if it is full.
    private void ensureCapacity() {
        int capacity = list.length - 1;
        
        if (numberOfEntries >= capacity) {
            int newCapacity = 2 * capacity;
            checkCapacity(newCapacity);  // Is capacity too big?
            list = Arrays.copyOf(list, newCapacity + 1);
        }
    } // end ensureCapacity

    
    /** Makes room for a new entry at newPosition.
     * Precondition: 1 <= newPosition <= numberOfEntries+1;
     * numberOfEntries is list's length before addition. 
     * checkInitialization has been called.
     */
    private void makeRoom(int newPosition) {
        assert (newPosition >= 1) && (newPosition <= numberOfEntries + 1);
        int newIndex = newPosition;
        int lastIndex = numberOfEntries;
        // Move each entry to next higher index, starting at end of
        // array and continuing until the entry at newIndex is moved
        for (int index = lastIndex; index >= newIndex; index--) {
            list[index + 1] = list[index];
        }
    } // end makeRoom

    /** Shifts entries that are beyond the entry to be removed
     * to the next lower position.
     * Precondition: 1 <= givenPosition < numberOfEntries;
     * numberOfEntries is list's length before removal. 
     * checkInitialization has been called.
     */
    private void removeGap(int givenPosition) {
        assert (givenPosition >= 1) && (givenPosition < numberOfEntries);

        int removedIndex = givenPosition;
        int lastIndex = numberOfEntries;
        for (int index = removedIndex; index < lastIndex; index++) 
            list[index] = list[index + 1];
    } // end removeGap


    /** Build a string representation of the list
     *
     * @return A string showing the state of the list.
     */
    public String toString() {
        String result = "{ ";
        for (int i = 0; i < numberOfEntries; i++) {
            result = result + "<" + list[i+1] + "> ";
        }
        result = result + "}";

        return result;
    }

    public Iterator<T> iterator(){
        return new IteratorForArrayList();
    } // end iterator
    
    public Iterator<T> getIterator() {
        return new IteratorForArrayList();
    } // end getIterator

    private class IteratorForArrayList implements Iterator<T> {

        private int nextIndex;          // Index of next entry
        private boolean wasNextCalled;  // Needed by remove
        
        private IteratorForArrayList() {
            nextIndex = 1;
            wasNextCalled = false;
        } // end default constructor

        public T next() {
            if (hasNext()) {
                wasNextCalled = true;
                T nextEntry = list[nextIndex];
                nextIndex++; // Advance iterator
                return nextEntry;
            } else {
                throw new NoSuchElementException("Illegal call to next(); "
                        + "iterator is after end of list.");
            }
        } // end next

        public boolean hasNext() {
            return nextIndex <= numberOfEntries;
        } // end hasNext

        public void remove() {
            if (wasNextCalled) {
                // nextIndex was incremented by the call to next, so it
                // is 1 larger than the position number of the entry to be removed
                ArrayListWithIterator.this.remove(nextIndex - 1);
                nextIndex--; // Index of next entry in iteration
                wasNextCalled = false; // Reset flag
            } else {
                throw new IllegalStateException("Illegal call to remove(); "
                        + "next() was not called.");
            }
        } // end remove
    } // end IteratorForArrayList
} // end ArrayListWithIterator

