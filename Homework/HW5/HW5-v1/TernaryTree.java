import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.Stack;
import java.util.LinkedList;

public class TernaryTree<T> implements TernaryTreeInterface<T>{
	
	private TernaryNode<T> root;

	public TernaryTree(){
		root = null;
	}

	public TernaryTree(T data){
		root = new TernaryNode<>(data);
	}

	public TernaryTree(T data, TernaryTree<T> leftTree, TernaryTree<T> middleTree,
		 					TernaryTree<T> rightTree){
		privateSetTree(data, leftTree, middleTree, rightTree);
	}

	private void privateSetTree(T data, TernaryTree<T> leftTree, TernaryTree<T> middleTree,
		 					TernaryTree<T> rightTree){
		root = new TernaryNode<>(data);
		//root.setLeftChild(leftTree.root);
		if((leftTree!=null)){// && !leftTree.isEmpty()){
			root.setLeftChild(leftTree.root);
		}
		if((middleTree!=null)){// && !middleTree.isEmpty()){
			root.setMiddleChild(middleTree.root);
		}
		if((rightTree!=null)){// && !rightTree.isEmpty()){
			root.setRightChild(rightTree.root);
		}
	}

	////////////////////////////////////////////////////////////////////////////
	//************************************************************************//	
	//  TernaryTreeInterface
	//************************************************************************//	
	////////////////////////////////////////////////////////////////////////////
	
	public void setTree(T rootData){
		privateSetTree(rootData, null, null, null);
	}

	public void setTree(T rootData, TernaryTreeInterface<T> leftTree,
						TernaryTreeInterface<T> middleTree,
						TernaryTreeInterface<T> rightTree){
		privateSetTree(rootData, (TernaryTree<T>)leftTree,
						(TernaryTree<T>)middleTree,(TernaryTree<T>)rightTree);
	}

	////////////////////////////////////////////////////////////////////////////
	//************************************************************************//	
	//  TreeInterface
	//************************************************************************//	
	////////////////////////////////////////////////////////////////////////////	

	public void clear(){
		root = null; 
		return;
	}

	public boolean isEmpty(){
		return root == null;
	}

	public T getRootData(){
		if(root == null)
			throw new EmptyTreeException();
		return root.getData();
	}

	public int getHeight(){
		return root.getHeight();
	}

	public int getNumberOfNodes(){
		return root.getNumberOfNodes();
	}

	////////////////////////////////////////////////////////////////////////////
	//************************************************************************//	
	//  TreeIteratorInterface
	//************************************************************************//	
	////////////////////////////////////////////////////////////////////////////

	public Iterator<T> getPreorderIterator(){
		return new PreorderIterator();
	}

	public Iterator<T> getPostorderIterator(){
		return new PostorderIterator();
	}

	/** getInorderIterator is unsupported for multiple reasons.
	  * First, the behavior is unspecified in the ADT.  An inorder traversal
	  * is left-root-right, but since this is ternary, there are 2 possible
	  * outcomes: left-root-middle-right, and left-middle-root-right.  
	  * Since there is no logical hierarchy specified by this class, there is
	  * no logical ordering of data, so there is no reason to have an inorder
	  * traversal.
	  */
	public Iterator<T> getInorderIterator(){
		throw new UnsupportedOperationException();
	}

	public Iterator<T> getLevelOrderIterator(){
		return new LevelOrderIterator();
	}

	private class PreorderIterator implements Iterator<T>{
		private Stack<TernaryNode<T>> nodeStack;
		public PreorderIterator(){
			nodeStack = new Stack<>();
			if(root != null){
				nodeStack.push(root);
			}
		}
		public boolean hasNext(){
			return !nodeStack.isEmpty();
		}
		public T next(){
			TernaryNode<T> nextNode;
			if(hasNext()){
				nextNode = nodeStack.pop();
				TernaryNode<T> left = nextNode.getLeftChild();
				TernaryNode<T> middle = nextNode.getMiddleChild();
				TernaryNode<T> right = nextNode.getRightChild();
				if(right != null){
					nodeStack.push(right);
				}
				if(middle != null){
					nodeStack.push(middle);
				}
				if(left != null){
					nodeStack.push(left);
				}
			} else {
				throw new java.util.NoSuchElementException();
			}
			return nextNode.getData();
		}
		public void remove(){
			throw new java.lang.UnsupportedOperationException();
		}
	}

	private class PostorderIterator implements Iterator<T>{
		private Stack<TernaryNode<T>> nodeStack;
		private TernaryNode<T> currentNode;

		public PostorderIterator(){
			nodeStack = new Stack<>();
			currentNode = root;
		}

		public boolean hasNext(){
			return !nodeStack.isEmpty() || (currentNode != null);
		}

		public T next(){
			boolean foundNext = false;
			TernaryNode<T> leftChild, middleChild, rightChild, nextNode = null;

			while(currentNode != null){
				nodeStack.push(currentNode);
				leftChild = currentNode.getLeftChild();
				middleChild = currentNode.getMiddleChild();
				/*if(leftChild == null){
					if(middleChild == null){
						currentNode = currentNode.getRightChild();
					} else {
						currentNode = middleChild;
					}
				} else {
					currentNode = leftChild;
				}*/
				if(leftChild != null){
					currentNode = leftChild;
				} else if(middleChild != null){
					currentNode = middleChild;
				} else {
					currentNode = currentNode.getRightChild();
				}
			}

			if(!nodeStack.isEmpty()){
				nextNode = nodeStack.pop();

				TernaryNode<T> parent = null;
				if(!nodeStack.isEmpty()){
					parent = nodeStack.peek();
					if(nextNode == parent.getLeftChild()){
						if(parent.getMiddleChild() == null)
							currentNode = parent.getRightChild();
						else
							currentNode = parent.getMiddleChild();
					} else if (nextNode == parent.getMiddleChild()){
						currentNode = parent.getRightChild();
					} else {
						currentNode = null;
					}
				} else {
					currentNode = null;
				}
			} else {
				throw new NoSuchElementException();
			}

			return nextNode.getData();
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}
	}

	private class LevelOrderIterator implements Iterator<T>{
		private LinkedList<TernaryNode<T>> nodeQueue;
		// nodeQueue.addLast() for enqueue
		// nodeQueue.peekFirst() for peek
		// nodeQueue.removeFirst() for dequeue

		public LevelOrderIterator(){
			nodeQueue = new LinkedList<>();
			if(root != null){
				nodeQueue.addLast(root);
			}
		}

		public boolean hasNext(){
			return nodeQueue.size()>0;
		}

		public T next(){
			TernaryNode<T> nextNode;

			if(hasNext()){
				nextNode = nodeQueue.removeFirst();
				TernaryNode<T> leftChild = nextNode.getLeftChild();
				TernaryNode<T> middleChild = nextNode.getMiddleChild();
				TernaryNode<T> rightChild = nextNode.getRightChild();

				if(leftChild != null){
					nodeQueue.addLast(leftChild);
				}

				if(middleChild != null){
					nodeQueue.addLast(middleChild);
				}

				if(rightChild != null){
					nodeQueue.addLast(rightChild);
				}
			} else {
				throw new NoSuchElementException();
			}

			return nextNode.getData();
			//return root.getData();
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}
	}

	////////////////////////////////////////////////////////////////////////////
	//************************************************************************//	
	//  TreeNode Class
	//************************************************************************//	
	////////////////////////////////////////////////////////////////////////////

	private class TernaryNode<T> {
		private T data;
		private TernaryNode<T> leftChild, middleChild, rightChild;

		public TernaryNode(){this(null);}

		public TernaryNode(T data){this(data,null,null,null);}

		public TernaryNode(T data, TernaryNode<T> left,TernaryNode<T> mid,TernaryNode<T> right){
			this.data = data;
			leftChild = left;
			middleChild = mid;
			rightChild = right;
		}

		public T getData(){
			return data;
		}

		public int getHeight(){
			return getHeight(this);
		}

		private int getHeight(TernaryNode<T> node){
			int h = 0;
			if(node != null){
				h = 1 + Math.max(Math.max(getHeight(node.leftChild),
								 getHeight(node.rightChild)),
								 getHeight(node.middleChild));
			}
			return h;
		}

		public void setData(T data){
			this.data = data;
		}

		public int getNumberOfNodes(){
			return getNumberOfNodes(this);
		}

		private int getNumberOfNodes(TernaryNode<T> node){
			int h = 0;
			if(node != null){
				h = 1 + getNumberOfNodes(node.getLeftChild()) + 
						getNumberOfNodes(node.getMiddleChild()) + 
						getNumberOfNodes(node.getRightChild());
			}
			return h;
		}

		public TernaryNode<T> getLeftChild(){
			return leftChild;
		}

		public TernaryNode<T> getMiddleChild(){
			return middleChild;
		}

		public TernaryNode<T> getRightChild(){
			return rightChild;
		}

		public void setLeftChild(TernaryNode<T> node){
			leftChild = node;
		}

		public void setMiddleChild(TernaryNode<T> node){
			middleChild = node;
		}

		public void setRightChild(TernaryNode<T> node){
			rightChild = node;
		}


	}

}























