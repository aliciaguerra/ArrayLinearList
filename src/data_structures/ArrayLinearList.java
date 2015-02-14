 /*
 *Alicia Guerra
 *Professor Steve Price
 *Computer Science 310
 *masc1529
 */
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E> {

    private int currentSize, maxSize;
    private E[] storage;

    public ArrayLinearList() {
        currentSize = 0;
        maxSize = 1000;
        storage = (E[]) new Object[maxSize];
    }

    public void insert(E obj) {
        checkSize();
        /*checkSize after the iterator in Step 12.*/
       storage[currentSize++]=obj;
    }
    /*2. Removes the object located at the paramater index position (zero-based).*/
    /*3. Throws IndexOutofBoundsException if the index does not map to a valid
     position within the list.*/

    public E remove(int location) {
        return shiftDelete(location);
    }
    /*4. Removes and returns the parameter object obj from the list if the list
     contains it, null otherwise.*/

    public E remove(E obj) {
        int check = binSearchFindObjectIndex(obj, 0, currentSize - 1);
        return shiftDelete(check);
    }
    /*5. Returns the parameter object located at the parameter index position
     (zero-based).*/

    public E get(int location) {
        E check;
        try {
            check = storage[location];
        } 
    /*6. Throws IndexOutofBoundsException if the index does not map to a valid 
    position within the underlying array.*/ 
        catch (IndexOutOfBoundsException E) {
            check = null;
        }
        return check;
    }
    /*7. Returns the parameter object obj if the list contains it, null otherwise.*/

    public E get(E obj) {
        int check = binSearchFindObjectIndex(obj, 0, currentSize - 1);
        return storage[check];}
    /*8. Returns true if the parameter object obj is in the list, false otherwise.*/
    /*Just like the code in Riggins' SlowContainer data structure.*/

    public boolean contains(E obj) {
        for (int i = 0; i < currentSize; i++) 
            if (((Comparable<E>) obj).compareTo(storage[i]) == 0)
                return true;
                return false;
    }
    /*9. The list is returned to an empty state.*/

    public void clear() {
        setMaxSize(100);
        storage = (E[]) new Object[maxSize];
    }
    /*10. Returns true if the list is empty, otherwise false.*/
    /*Just like the code in Riggins' SlowContainer data structure.*/

    public boolean isEmpty() {
        return currentSize == 0;
    }
    /*11. Returns the number of objects currently in the list*/
    /*Just like the code in Riggins' SlowContainer data structure*/

    public int size() {
        return currentSize;
    }
    /*12. Returns an iterator of the values in the list, presented in the same
     order as the list.*/
    /*This part of the code was copied from Riggins' SlowContainer program.*/

    public Iterator<E> iterator() {
        return new IteratorHelper();
    }

    class IteratorHelper implements Iterator<E> {

        int iterIndex;

        public IteratorHelper() {
            iterIndex = 0;
        }

        public boolean hasNext() {
            return iterIndex < currentSize;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return storage[iterIndex++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    /*checkSize from part 1*/
    
    public void checkSize() {
        if (currentSize == maxSize) {
            setMaxSize(maxSize * 2);
        } else if (currentSize <= maxSize / 4) {
            setMaxSize(maxSize / 2);
        }
        E[] tempStorage = (E[]) new Object[maxSize];
        for (int i = 0; i < currentSize; i++) {
            tempStorage[i] = storage[i];
        }
        storage = tempStorage;
    }

    private void setMaxSize(int size) {
        maxSize = size;
    }

    private E shiftDelete(int dIndex) {
        checkSize();
        E check;
        try {
            check = storage[dIndex];
        } catch (IndexOutOfBoundsException e) {
            check = null;
        }
        for (int i = currentSize - 1; dIndex <= i; dIndex++) {
            storage[dIndex] = storage[dIndex + 1];
        }
        currentSize--;
        return check;
    }
    /*Binary Search Method*/
    private int binSearchFindObjectIndex(E obj, int low, int high) {
        if (low > high) {
            return -1;
        }
        int mid = (low + high) / 2, comp = 1;
        if (comp == 0) {
            return mid;
        } else if (comp < 0) {
            return binSearchFindObjectIndex(obj, low, mid - 1);
        }
        return binSearchFindObjectIndex(obj, mid + 1, high);
    }

    private int binSearchFindInsertionPoint(E obj, int low, int high) {
        if (obj == null) {
            return 0;
        }
        if (low > high) {
            return low;
        }
        int mid = (low + high) / 2, comp = 1;
        if (comp == 0) {
            return mid;
        } else if (comp < 0) {
            return binSearchFindInsertionPoint(obj, low, mid - 1);
        }
        return binSearchFindInsertionPoint(obj, mid + 1, high);
    }
}
   