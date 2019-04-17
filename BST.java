import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

/**
 * This class creates a binary search tree. The tree cannot contain 
 * null or duplicate elements. 
 * 12/5/2018
 * @author Matthew Goodbar
 *
 * @param <E> Must implement comparable interface
 */
public class BST<E extends Comparable<E>> implements Collection<E>, Iterable<E>{

	private BSTNode<E> root;
	private int size = 0;
	
	public BST() {}

	/**
	 * Adds an element to the tree. Cannot add null or duplicate elements.
	 * @param e Element to be added
	 * @return Returns true if the element was added successfully, false if otherwise.
	 */
	public boolean add(E e) {
		//Don't allow null elements
		if (e == null) 
			return false;
		//Tree is empty, set a new root
		if (root == null) {
			root = new BSTNode(e);
			size++;
			return true;
		}
		return addRec(root, e);
	}
	private boolean addRec(BSTNode<E> currentNode, E e) {
		//Don't allow duplicates
		if ((currentNode.data).equals(e))
			return false;
		//Node value is greater than e, go left
		if ((currentNode.data).compareTo(e) > 0) {
			//Left node is empty, create new node
			if (currentNode.left == null) {
				currentNode.left = new BSTNode(e);
				size++;
				return true;
			}
			//Left node is populated, recursive call
			return addRec(currentNode.left, e);
		}
		//Node value is smaller than e, go right
		if (currentNode.right == null) {//Right node is empty, create new node
			currentNode.right = new BSTNode(e);
			size++;
			return true;
		}
		//Right node is populated, recursive call
		return addRec(currentNode.right, e);
	}

	/**
	 * This method is unsupported for this implementation of binary search tree.
	 */
	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Clears the tree of all elements.
	 */
	public void clear() {
		root = null;
		size = 0;
	}

	/**
	 * Checks to see if the given argument is contained within the tree
	 * @param o The element to be searched for in the tree.
	 * @return Returns true if the given argument is in the tree, false if otherwise.
	 */
	public boolean contains(Object o) {
		if (isEmpty())
			return false;
		//No null elements in the tree
		if (o == null)
			return false;
		//Only elements of type E in the tree
		if (o.getClass() != (root.data).getClass())
			return false;
		return containsRec(root, o);
	}
	private boolean containsRec(BSTNode currentNode, Object o) {
		//If the node is empty where o should be, then it is not in the tree
		if (currentNode == null)
			return false;
		//o is in the tree
		if ((currentNode.data).equals(o))
			return true;
		if ((currentNode.data).compareTo((E) o) > 0)
			//o should be to the left of the currentNode
			return containsRec(currentNode.left, o);
		//o should be to the right of the currentNode
		return containsRec(currentNode.right, o);
	}

	/**
	 * Checks the tree to see if every element of given collection c is in the tree.
	 * @param c The collection of elements to be searched for in the tree.
	 * @return Returns true if all elements in c are in the tree, false if otherwise.
	 */
	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (!this.contains(o))
				return false;
		}
		return true;
	}
	
	/**
	 * Checks to see if this instance of binary search tree is equal to 
	 * the given argument. The two are considered equal if they are both binary search
	 * trees with the same elements. Tree structure is not taken into account.
	 * @param o The object to be checked for equality.
	 * @return Returns true if the two objects are equal, false if otherwise.
	 */
	public boolean equals(Object o) {
		if (o == null)
			return false;
		try {
			BST<E> arg = (BST<E>) o;
			if (size != arg.size)
				return false;
			for (E e : arg) {
				if (!this.contains(e))
					return false;
				return true;
			}
		} catch (ClassCastException e) {
			return false;
		}
		return false;
	}

	/**
	 * Checks to see if the tree is empty.
	 * @return Returns true if the tree has no elements, false if otherwise.
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * Creates and returns an iterator that iterates through
	 * the tree via the natural ordering of the elements.
	 * @return Returns an instance of the iterator.
	 */
	public Iterator<E> iterator() {
		return new BSTIter(root, 0);
	}
	
	/**
	 * Creates and returns an iterator that iterates through
	 * the tree via the preorder listing of the elements.
	 * @return Returns an instance of the iterator.
	 */
	public Iterator<E> preorderIterator() {
		return new BSTIter(root, 1);
	}
	
	/**
	 * Creates and returns an iterator that iterates through
	 * the tree via the postorder listing of the elements.
	 * @return returns an instance of the iterator.
	 */
	public Iterator<E> postorderIterator() {
		return new BSTIter(root, 2);
	}

	/**
	 * Removes the given object from the tree if it exists.
	 * @param o The object to be removed from the tree.
	 * @return Returns true if the element was successfully removed
	 * from the tree, false if otherwise.
	 * 
	 * @author Joanna Klukowska
	 */
	public boolean remove(Object o) {
		if (o == null)
			return false;
		if (!this.contains(o))
			return false;
		root = removeRec(root, (E) o);
		size--;
		return true;
	}
	private BSTNode<E> removeRec(BSTNode<E> n, E e){
		if (n == null)
			return null;
		else if ((n.data).compareTo(e) > 0) 
			n.left = removeRec(n.left, e);
		else if ((n.data).compareTo(e) < 0)
			n.right = removeRec(n.right, e);
		else {
			n = removeNode(n);
		}
		return n;
	}
	private BSTNode<E> removeNode(BSTNode<E> n){
		if (n.left == null)
			return n.right;
		if (n.right == null)
			return n.left;
		E data = getPredecessor(n);
		n.data = data;
		n.left = removeRec(n.left, data);
		return n;
	}
	private E getPredecessor(BSTNode<E> n) {
		if (n.left == null) {
			return null;
		} else {
			BSTNode<E> current = n.left;
			while (current.right != null) {
				current = current.right;
			}
			return current.data;
		}
	}

	/**
	 *  This method is unsupported for this implementation of binary search tree.
	 */
	@Override
	public boolean removeAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 *  This method is unsupported for this implementation of binary search tree.
	 */
	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return Returns the number of elements in the tree.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns a reference to the element in the tree equal to the 
	 * given argument if it exists in the tree
	 * @param value The value to be searched for in the tree.
	 * @return Returns a reference to the value in the tree.
	 */
	public E get(E value) {
		if (isEmpty())
			return null;
		//No null elements in the tree
		if (value == null)
			return null;
		//Only elements of type E in the tree
		if (value.getClass() != (root.data).getClass())
			return null;
		return getRec(root, value);
	}
	private E getRec(BSTNode<E> currentNode, E value) {
		//If the node is empty where o should be, then it is not in the tree
		if (currentNode == null)
			return null;
		//o is in the tree
		if ((currentNode.data).equals(value))
			return currentNode.data;
		if ((currentNode.data).compareTo(value) > 0)
			//o should be to the left of the currentNode
			return getRec(currentNode.left, value);
		//o should be to the right of the currentNode
		return getRec(currentNode.right, value);
	}
	
	/**
	 * Returns a string representation of the elements in the tree,
	 * Listed by their natural ordering.
	 * @return Returns a string representation of the tree.
	 */
	public String toString() {
		String result = "[";
		for (E e : this) {
			result = result + e.toString() + ", ";
		}
		return result + "]";
	}
	
	/**
	*Produces  tree  like  string  representation  of  this  BST.
	*@return  string  containing  tree - like  representation  of  this  BST.
	*
	*@author Joanna Klukowska
	*/
	public String toStringTreeFormat() {
		StringBuilder s = new StringBuilder();
		preOrderPrint(root, 0, s);
		return s.toString();
	}
	/*
	*Uses  pre - order  traversal  to  produce  a  tree - like  representation  of  this  BST .
	*@param  tree  the  root  of  the  current  subtree
	*@param  level  level  ( depth )  of  the  current  recursive  call  in  the  tree  to
	*determine  the  indentation  of  each  item
	*@param  output  the  string  that  accumulated  the  string  representation  of  this
	*BST
	*/
	private void preOrderPrint(BSTNode<E> tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.data);
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right , level + 1, output);
		}
		else {// print  the  null  children
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}
	
	/**
	 * @param e The given element to compare to
	 * @return Returns the least element in this set greater than or equal to the given element, 
	 * or null if there is no such element.
	 */
	public E ceiling(E e) {
		if (this.contains(e))
			return e;
		return higher(e);
	}
	
	/**
	 * Returns a shallow copy of the binary search tree.
	 * @return Returns a shallow copy of the tree.
	 */
	public Object clone() {
		if (root == null)
			return null;
		BST<E> result = new BST<>();
		Iterator<E> itr = preorderIterator();
		while(itr.hasNext()) {
			result.add(itr.next());
		}
		return result;
	}
	
	/**
	 * @return Returns the first (lowest) element currently in this set.
	 */
	public E first() {
		BSTNode<E> cursor = root;
		while (cursor.left != null) {
			cursor = cursor.left;
		}
		return cursor.data;
	}
	
	/**
	 * @param e The given element to compare to.
	 * @return Returns the greatest element in this set less than or equal to the given element, 
	 * or null if there is no such element
	 */
	public E floor (E e) {
		if (this.contains(e))
			return e;
		return lower(e);
	}
	
	/**
	 * @param e The given element to compare to.
	 * @return Returns the least element in this set strictly greater than the given element, 
	 * or null if there is no such element.
	 */
	public E higher (E e) {
		if (e == null)
			return null;
		return higherRec(root, e, new BSTNode<E>(null));
	}
	private E higherRec(BSTNode<E> n, E e, BSTNode<E> saved) {
		if (e.compareTo(n.data) < 0) {
			if (n.left == null) {
				try {
					return ((saved.data).compareTo(n.data) < 0) ? saved.data : n.data;
				} catch (NullPointerException ex) {
					return n.data;
				}
			}
			return higherRec(n.left, e, n);
		}
		else {
			if (n.right == null)
				return null;
			return higherRec(n.right, e, saved);
		}
	}
	
	/**
	 * @return Returns the last (highest) element currently in this set.
	 */
	public E last() {
		BSTNode<E> cursor = root;
		while (cursor.right != null) {
			cursor = cursor.right;
		}
		return cursor.data;
	}
	
	/**
	 * @param e The given element to compare to.
	 * @return Returns the greatest element in this set strictly less than the given element, 
	 * or null if there is no such element
	 */
	public E lower (E e) {
		if (e == null)
			return null;
		return lowerRec(root, e, new BSTNode<E>(null));
	}
	private E lowerRec(BSTNode<E> n, E e, BSTNode<E> saved) {
		if (e.compareTo(n.data) > 0) {
			if (n.right == null) {
				try {
					return ((saved.data).compareTo(n.data) < 0) ? saved.data : n.data;
				} catch (NullPointerException ex) {
					return n.data;
				}
			}
			return lowerRec(n.right, e, saved);
		}
		else {
			if (n.left == null) 
				return null;
			return lowerRec(n.left, e, n);
		}
	}
	
	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];
		int counter = 0;
		for (E e : this) {
			result[counter] = e;
			counter++;
		}
		return result;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		ArrayList<E> temp = new ArrayList<>();
		for (E e : this) {
			temp.add(e);
		}
		return temp.toArray(arg0);
	}
	
	/*
	 * Creates the nodes to be used as storage for elements in the tree.
	 */
	private class BSTNode<E extends Comparable<E>>
		implements Comparable<BSTNode<E>>{
		
		private E data;
		private BSTNode<E> left;
		private BSTNode<E> right;

		public BSTNode(E arg0) {
			this.data = arg0;
		}

		@Override
		public int compareTo(BSTNode<E> o) {
			return data.compareTo(o.data);	
		}

	}
	
	/*
	 * Creates three different iteraters, inorder, preorder, or postorder depending
	 * on the arguments of the constructor. Each constructor mode places elements into
	 * a stack in reverse order, such that, when hasNext()/next() is called, the elements
	 * are returned in their proper order.
	 */
	private class BSTIter implements Iterator<E>{
		
		private Stack<E> tree = new Stack<>();
		
		/*
		 * Populates the stack based on the given int mode, 0 denotes
		 * an inorder stack, 1 denotes a preorder stack, 2 denotes
		 * a postorder stack.
		 */
		public BSTIter(BSTNode<E> n, int mode) {
			switch(mode) {
			case 0: //In-order stack construction
				inOrderRec(n, tree);
				break;
			case 1: //Pre-order stack construction
				preOrderRec(n, tree);
				break;
			case 2: //Post-order stack construction
				postOrderRec(n, tree);
				break;
			}
		}
		/*
		 * Populates the stack in an inorder manner
		 */
		private void inOrderRec(BSTNode<E> n, Stack tree) {
			if (n == null) 
				return;
			inOrderRec(n.right, tree);
			tree.push(n.data);
			inOrderRec(n.left, tree);
			return;
		}
		/*
		 * Populates the stack in a preorder manner
		 */
		private void preOrderRec(BSTNode<E> n, Stack tree) {
			if (n == null)
				return;
			preOrderRec(n.right, tree);
			preOrderRec(n.left, tree);
			tree.push(n.data);
		}
		/*
		 * Populates the stack in a postorder manner
		 */
		private void postOrderRec(BSTNode<E> n, Stack tree) {
			if (n == null)
				return;
			tree.push(n.data);
			postOrderRec(n.right, tree);
			postOrderRec(n.left, tree);	
		}

		public boolean hasNext() {
			return (!tree.isEmpty());
		}

		public E next() {
			return tree.pop();
		}
		
	}

}
