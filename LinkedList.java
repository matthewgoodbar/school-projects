import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class creates a singly linked list of objects. Also contains private class
 * Node to store elements, and private class MyIterator to iterate through the list.
 * 10/20/2018
 * @author Matthew
 *
 * @param <E> E is the declared type of the elements to be stored in the list.
 */
public class LinkedList<E> implements Collection<E>, Iterable<E> {
	
	private Node<E> head = null;
	private Node<E> tail = null;
	private int size = 0;
	
	/**
	 * No parameter constructor for a new LinkedList object.
	 */
	public LinkedList() {
		
	}
	
	/**
	 * Adds a new element of data type E to the end of the list.
	 * @param e Data to be stored in the list.
	 * @return Returns true if element was added correctly, false otherwise.
	 */
	public boolean add(E e) {
		
		if (e == null) { //List can't contain null elements
			return false;
		} else if (head == null){ //If e is the first element in the list, set head to e
			head = new Node<E>(e, null);
			tail = head;
			size++;
			return true;
		} else { //If e is not the first element in the list, add e after tail
			tail.setNext(new Node<E>(e, null));
			tail = tail.getNext();
			size++;
			return true;
		}
	}
	
	/**
	 * This method is unsupported for this implementation of LinkedList!
	 * @throws UnsupportedOperationException if used.
	 */
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Clears list of all elements.
	 */
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/**
	 * Checks if the list is empty.
	 * @return Returns true if there are no elements in the list, false otherwise.
	 */
	public boolean isEmpty() {
		if (head == null) { //List will only be empty if head is pointing to null
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks to see if Object o is in the list.
	 * @param o The object in question.
	 * @return Returns true if the object is in the list, false otherwise;
	 */
	public boolean contains(Object o) {
		return (indexOf(o) >= 0); //If o is in the list, then indexOf(o) should be a positive integer
	}
	
	/**
	 * Checks to see if all elements within the Collection c are in the list.
	 * @param c The collection in question.
	 * @return Returns true if all elements in c are elements in the list.
	 */
	public boolean containsAll(Collection<?> c) {
		if (c == null) { //List can't contain null elements
			return false;
		}
		for (Object o : c) { //Check to see if each element in c is also in the list
			if (!this.contains(o)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean equals(Object o) {
		if (o == null) { //The list can't be null
			return false;
		}
		if (o == this) { //The list is pointing to the same object in memory
			return true;
		}
		if (o.getClass() != this.getClass()) { //The two objects cannot be of differing classes
			return false;
		}
		if (((LinkedList) o).size() != size) { //The two lists can't be equal if they don't have the same cardinality
			return false;
		}
		Iterator itrThis = iterator();
		Iterator itrOther = ((LinkedList) o).iterator();
		
		while (itrThis.hasNext()) { //Iterate through both lists simultaneously
			Object elementThis = itrThis.next();
			itrOther.hasNext();
			Object elementOther = itrOther.next();
			
			if (!(elementThis).equals(elementOther)){ //Verify each element of c is also an element of the list
				return false;
			}
		}
		return true;
		
		
	}
	
	/**
	 * Removes Object o from the list if it is an element of the list.
	 * @param o The object to be removed.
	 * @return Returns true if the object was removed, false if such an element is not
	 * in the list.
	 */
	public boolean remove(Object o) { 
		
		if (o == null || head == null) { //Argument is null or list is empty
			return false;
		} else if (o.getClass() != head.getData().getClass()) { //Data type mismatch
			return false;
		} else if (head.getData() == o) { //Remove the head
			head = head.getNext();
			size--;
			return true;
		} 
		
		Node<E> beforeRemoval = head;
		while (beforeRemoval.getNext() != null) {
			if (beforeRemoval.getNext().getData() == o) { //Node after beforeRemoval matches
				if (beforeRemoval.getNext() == tail) { //Node after beforeRemoval is the tail
					beforeRemoval.setNext(null);
					size--;
					return true;
				} else { //Node after beforeRemoval is not the tail
					beforeRemoval.setNext(beforeRemoval.getNext().getNext());
					size--;
					return true;
				}
			} else { //Node after beforeRemoval isn't the node to remove, move to next node
				beforeRemoval = beforeRemoval.getNext();
			}
		}
		return false;
	}
	
	/**
	 * This method is unsupported for this implementation of LinkedList!
	 * @throws UnsupportedOperationException if used.
	 */
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * This method is unsupported for this implementation of LinkedList!
	 * @throws UnsupportedOperationException if used.
	 */
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @return Returns the number of elements in the list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns an array of all elements in the list as they appear in the list.
	 * @return Returns the list in array form.
	 */
	public Object[] toArray() { 
		Object[] resultArray = new Object[size]; //Make a new array, same size as the list
		Node<E> cursor = head;
		for (int i = 0; i < size; i++) {
			resultArray[i] = cursor.getData();
			cursor = cursor.getNext();
		}
		return resultArray;
	}
	
	/**
	 * Returns an array of specified type of all elements in the list as they
	 * appear in the list.
	 * @param a The array to be filled with elements in the list.
	 * @return Returns the list in array form.
	 * @throws IllegalArgumentException When method is called with null parameter.
	 */
	public <T> T[] toArray(T[] a) throws IllegalArgumentException{
		if (a == null) { //Throw exception if argument is null
			throw new IllegalArgumentException();
		}
		ArrayList<T> t = new ArrayList<>();
		Iterator itr = iterator();
		while (itr.hasNext()) { //Fill a new ArrayList with the elements of the list
			t.add((T) itr.next());
		}
		
		return t.toArray(a); //Utilize ArrayList's toArray(T[] a) method :)
	}
	
	/**
	 * Creates a new MyIterator object to iterate through the list
	 */
	public MyIterator<E> iterator() { 
		//Creating a cursor Node inside class MyIterator gave rise to several issues
		//So create the Node within class LinkedList and pass it as an argument
		Node<E> cursor = new Node<E>(null, head);
		return new MyIterator<E>(cursor);
	}
	
	/**
	 * Finds the index of Object o in the list, if it is in the list.
	 * @param o The object in question.
	 * @return Returns the index of the Object in the list, 
	 * or -1 if the Object is not in the list.
	 */
	public int indexOf(Object o) {
		if (o == null) { //null cannot be an element of the list
			return -1;
		}
		int index = 0;
		Iterator itr = iterator();
		//Iterate through the list and find the element o
		//Increment the index to return if itr.next() is not o
		while (itr.hasNext()) { 
			Object e = itr.next();
			if (o.equals(e)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	/**
	 * Retrieves the element at the specified index.
	 * @param index The index of the element to retrieve
	 * @return returns the element at the given index.
	 * @throws IndexOutOfBoundsException When the given index is not in the list.
	 */
	public E get(int index) throws IndexOutOfBoundsException{
		if (index < 0 || index >= size()) { //Index cannot be less than 0, or greater than the size of the list -1
			throw new IndexOutOfBoundsException();
		}
		if (head == null) {//List is empty, return null
			return null;
		}
		Node<E> cursor = head;
		for (int i = 0; i < index; i++) { //Iterate through list index times, return the index'th element
			cursor = cursor.getNext();
		}
		return cursor.getData();
	}
	
	public String toString() {
		String result = "[";
		int counter = 0;
		for (E e : this) {
			result = result + String.valueOf(e);
			counter++;
			if (counter != size) {
				result = result + ", ";
			}
		}
		result = result + "]";
		return result;
	}
	
	/**
	 * Sorts the list via the natural order of the elements.
	 */
	@SuppressWarnings("unchecked")
	public void sort() {
		Object[] array = this.toArray();
		Arrays.sort(array);
		this.clear();
		for (Object o : array) {
			this.add((E) o);
		}
	}
	
	
	/**
	 * This class creates an iterator that iterates through the list.
	 * @author Matthew
	 *
	 * @param <E> The declared type of the list.
	 */
	@SuppressWarnings("hiding")
	private class MyIterator<E> implements Iterator<E>{
		
		private Node<E> cursor;
		
		/**
		 * Constructor for MyIterator object.
		 * @param cursor Node created via calling LinkedList.iterator()
		 */
		public MyIterator(Node<E> cursor) {
			this.cursor = cursor;
		}
		
		/**
		 * Returns the next element in the list.
		 */
		public E next() throws NoSuchElementException{
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			cursor = cursor.getNext();
			return (E) cursor.getData();
		}
		
		/**
		 * Checks to see if next element in the list exists. 
		 * Must be called before calling next()
		 */
		public boolean hasNext() {
			if (cursor.getNext() == null) {
				return false;
			} else {
				return true;
			}
		}
		
	}
	
	/**
	 * This class creates Node objects in which the data for the list is stored.
	 * @author Matthew
	 *
	 * @param <E> The declared type of the list.
	 */
	@SuppressWarnings("hiding")
	private class Node<E>{
		
		private E data;
		private Node<E> next;
		
		/**
		 * Creates a new Node object.
		 * @param data The data to be stored in the Node.
		 * @param next The reference to the next Node in the list.
		 */
		public Node(E data, Node<E> next){
			this.data = data;
			this.next = next;
		}

		/**
		 * Getter method for the data stored in the Node.
		 * @return Returns data
		 */
		public E getData() {
			return data;
		}
		
		/**
		 * Getter method for the next Node in the list after this Node.
		 * @return Returns the next Node in the list.
		 */
		public Node<E> getNext(){
			return next;
		}
		
		/**
		 * Sets the reference for the next Node in the list after this Node.
		 * @param next The new next Node after this Node.
		 */
		public void setNext(Node<E> next) {
			this.next = next;
		}
		
	}

}
