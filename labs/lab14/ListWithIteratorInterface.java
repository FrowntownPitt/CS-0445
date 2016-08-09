import java.util.ListIterator;

/** An interface for a list that has an iterator implemented using
 * an inner class
 *
 * This code is from Chapter 15 of
 * Data Structures and Abstractions with Java 4/e
 *      by Carrano
 */


import java.util.Iterator;

public interface ListWithIteratorInterface<T> extends ListInterface<T>, Iterable<T>
{
    public Iterator<T> getIterator();
} // end ListWithIteratorInterface
