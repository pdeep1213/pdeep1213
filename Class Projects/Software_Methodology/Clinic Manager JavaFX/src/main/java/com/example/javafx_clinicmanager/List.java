package com.example.javafx_clinicmanager;
import java.util.Iterator;

/**
 * The List</E> List class that implements a simple array-based list for Appointment and Provider.
 * This class allows adding, removing, and retrieving elements, along with iterator support.
 * @author Deep Patel,Manan Patel
 */

public class List<E> implements Iterable<E> {

    private E[] objects;
    private int size;

    /**
     * Default constructor that start the List object with array 4.
     */
    public List() {
        this.objects = (E[]) new Object[4];
        this.size = 0;
    }

    /**
     * Finds the index of the given object in list.
     * @param object the object being searched
     * @return the index of the object if found, otherwise -1
     */
    private int find(E object) {
        for(int i = 0; i < this.size; i++) {
            if(objects[i].equals(object)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Makes the array 4 elements longer.
     */
    private void grow() {
        E[] newObjects = (E[]) new Object[objects.length + 4];
        for(int i = 0; i < size(); i++) {
            newObjects[i] = objects[i];
        }
        objects = newObjects;
    }

    /**
     * Checks if the list has the given object.
     * @param object the object being searched
     * @return true if the object is present, false otherwise
     */
    public boolean contains(E object) {
        return find(object) != -1;
    }

    /**
     * Adds the new object to the list.
     * If its full, we send it to grow.
     * @param object object that needs to be added.
     */
    public void add(E object){
        if(!contains(object)){
            if(size == objects.length){
                grow();
            }
            objects[size++] = object;
        }
    }

    /**
     * Removes the given object from the list, if present.
     * @param object the object that needs to removed
     */
    public void remove(E object){
        if(contains(object)){
            int index = find(object);
            if(index != -1){
                for(int i = index; i < size - 1; i++){
                    objects[i] = objects[i + 1];
                }
                objects[--size] = null;
            }
        }
    }

    /**
     * Checks if the list is empty
     * @return true if list is empty, false otherwise
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Gives the number of elements in list
     * @return the size of list
     */
    public int size(){
        return size;
    }

    /**
     * Returns an iterator over the elements in the list.
     * @return an iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /**
     * Takes out the element at the given index.
     * @param index the index of the element
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public E get(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        return objects[index];
    }

    /**
     * Sets the element at the given index to the given object.
     * @param index the index of the element to set
     * @param object the object to set at the given index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void set(int index, E object){
        if(index < 0 || index >= size()){
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        objects[index] = object;
    }

    /**
     * Returns the index of the given object in the list.
     * @param object the object being searched
     * @return the index of the object, or -1 if not found
     */
    public int indexOf(E object){
        return find(object);
    }

    /**
     * Inner class that implements the Iterator for the List.
     */
    private class ListIterator implements Iterator<E>{
        private int current = 0;

        /**
         * Checks if there are more elements to iterate over.
         * @return true if there are more elements, false otherwise
         */
        public boolean hasNext(){
            return current < size();
        }

        /**
         * Returns the next element in the iteration.
         * @return the next element
         * @throws IndexOutOfBoundsException if there are no more elements
         */
        public E next(){
            if(!hasNext()){
                throw new IndexOutOfBoundsException();
            }
            return objects[current++];
        }
    }

    /**
     * Swaps the elements at the two given positions.
     * @param i the index of the first element to swap
     * @param j the index of the second element to swap
     * @throws IndexOutOfBoundsException if either index is out of range
     */
    public void swap(int i, int j){
        if(i>=size || j>=size || i<0 || j<0){
            throw new IndexOutOfBoundsException();
        }
        E temp = objects[i];
        objects[i] = objects[j];
        objects[j] = temp;
    }
}