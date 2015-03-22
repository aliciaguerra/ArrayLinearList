/*
**Alicia Guerra
**Professor Steve Price
**CS 310: Data Structures
**masc 0879
*/

package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E> {
    private int currentSize, maxSize;
    private E[] storage;

    public ArrayLinearList() {
    currentSize=0;
    maxSize=DEFAULT_MAX_CAPACITY;
    storage=(E[]) new Object[maxSize];
    }

/*Adds the object obj to the end of the list.*/   
    public void addLast(E obj) {
       growStorage();
       storage[currentSize+1]= obj;
       currentSize++;
    }

/*Adds the Object obj to the beginning of the list.*/
    @Override
    public void addFirst(E obj) {
            growStorage();
            int location=1;
/*We shift everything to the right of the insertion point.*/
        for(int i=currentSize;i>location;i--){
            storage[i+1]=storage[i];
            }       
        storage[location]=obj;
/*We're adding elements so currentSize is increasing.*/
        currentSize++;
    }

    public void growStorage(){
        if(currentSize>=maxSize){
        setMaxSize(maxSize*2);
    }
        else if(currentSize==maxSize/4)
        {
        setMaxSize(maxSize/2);
        }
       E[] tempStorage = (E[]) new Object[maxSize];
       for(int i=1;i<=currentSize;i++){
           tempStorage[i]=storage[i];
       }
       storage=tempStorage;
    }   
    private void setMaxSize(int size){
        maxSize=size;
    }  
    

/*Inserts the Object obj at the position indicated. If there is an element at that
position, all elements from that location to the end of the list are shifted down
to make room for that new insertion. The location is one-based. If the 
location>size()+1, then a RunTime Exception is thrown. List elements must be
contiguous*/   
    @Override
    public void insert(E obj, int location) {
        growStorage();
/*If the location is greater than the currentSize, a runtime exception is thrown.*/        
        if(location>currentSize||location<1){
        throw new RuntimeException("Index Out Of Bounds");    
        }
/*If there is an element at that position, move all elements from that location to
the end of the list to make room for that new insertion.*/
        for(int i=currentSize;i>location;i--){
            storage[i+1]=storage[i];
        }
        storage[location]=obj;
/*We performing an insertion, so the currentSize increases.*/
        currentSize++;
    }
    
/*Removes the object at the parameter location (one-based) Throws a runtime
exception if the location does not map to a valid position within the list.*/
    @Override
    public E remove(int location) {
/*Throws a runtime exception if the location does not map to a valid position
within the list.*/
    if(location>currentSize || location<1){
        throw new RuntimeException("Index Out of Bounds");
        }
     E tempObject=storage[location];
/*We shift everything to the right of the index.*/
     for(int i=location;i>=currentSize;i--){
         storage[i+1]=storage[i];
     }
/*The size is decreasing since we're removing.*/
     currentSize--;
     return tempObject;
    }

/*Removes and returns the parameter object obj from the list if the list contains
it, null otherwise. The ordering of the list is preserved. The list may contain
deuplicate elements. This method removes and returns the first matching element
found when removing the list from the first position.*/
    @Override
    public E remove(E obj) {
      int location=locate(obj); 
      if(location==-1){
          return null;
      }
      /*Shifts the elements to the left of the list.*/
      for(int i=location;i<=currentSize;i++){
          storage[i]=storage[i+1];
      }
      E tempObject=storage[location];
      growStorage();
      currentSize--;
      return tempObject;
      }

/*Removes and returns the parameter object obj in the first position in the list
if the list is not empty, null if the list is empty. The ordering of the list is
preserved.*/
    @Override
    public E removeFirst() {
    growStorage();
    int location=1;
    E tempObject=storage[location];
/*Move the objects in the array to the right.*/
    for(int i=currentSize;i>1;i--){
       storage[i+1]=storage[i];
       }
/*We're removing things so currentSize is decreasing.*/
       currentSize--;
    return tempObject;
    }

/*Removes and returns the parameter object obj in the last position in the list if
the list is not empty, null if not empty. The order of the list is preserved.*/    
    @Override
    public E removeLast() {
    growStorage();
    E tempObject=storage[currentSize];
/*We're removing things so currentSize is decreasing.*/
    currentSize--;
    return tempObject;
    }

/*Returns the parameter object located at the parameter location position (one
based). Throws a runtime exception if the location does not map to a valid position
within the list.*/
    @Override
    public E get(int location) {
/*Throws a runtime exception if the location does not map to a valid position
within the list.*/
        if(location>storage.length|| location<1){
      throw new RuntimeException("Index Out of Bounds");
              }
/*Returns the parameter object located at the parameter location position.*/
      return storage[location];
    }

/**Returns true if the parameter object is in the list, false otherwise.*/    
    @Override
    public boolean contains(E obj) {
        for(int i=1;i<=currentSize;i++)
        //for (int i = 0; i <=currentSize-1; i++)
            if (((Comparable<E>) obj).compareTo(storage[i]) == 0)
                return true;
                return false;         
    }

/*Returns the one-based location of the parameter object obj is in the list,
false otherwise.*/    
    @Override
    public int locate(E obj) {
        //for(int i=0;i<=currentSize;i++){
        for(int i=1;i<=currentSize;i++){
            if(this.storage[i].equals(obj))
                return i;
        }
         return -1;
    }

 /**The list is returned to an empty state.*/
    @Override
    public void clear() { 
       currentSize=0;
    }

/*Returns true if the list is empty, otherwise false.*/    
    @Override
    public boolean isEmpty() {
        return currentSize==0;
    }

/*Returns the number of objects currently in the list.*/    
    @Override
    public int size() {
        return currentSize;
    }

/*Returns an iterator of the values in the list, presented in the same order
as the underlying order of the list.*/    
    @Override
    public Iterator<E> iterator() {
        return new IteratorHelper();
    }
 class IteratorHelper implements Iterator<E> {
        int iterIndex;
        public IteratorHelper() {
            iterIndex=1;
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
    }   
}
   
