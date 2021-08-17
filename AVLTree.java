

/**
 *
 * AVLTree
 *
 * An implementation of an AVL Tree with
 * distinct integer keys and info
 *
 * Yotam Hazan
 *
 */

public class AVLTree {

	private IAVLNode mockAVLNode = new AVLNode(-1, null);
	private IAVLNode root;
	private IAVLNode min;
	private IAVLNode max;

	  public AVLTree(){
	  	this.root = null;
	  	this.min = null;
	  	this.max = null;
	  	this.mockAVLNode.setHeight(-1);
	  	this.mockAVLNode.setSize(0);
	  }
		public AVLTree(IAVLNode root) {
			this.root = root;
			this.mockAVLNode.setHeight(-1);
			this.mockAVLNode.setSize(0);
			IAVLNode x = root;
			while (x.getLeft() != null && x.getLeft().isRealNode()) {
				x = x.getLeft();
			}
			this.min = x;
			x = root;
			while (x.getRight() != null && x.getRight().isRealNode()){
				x = x.getRight();
			}
			this.max = x;
		}

	
   /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
	public boolean empty() {  									// Time complexity : O(1)
		return this.root == null;
     }

   /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null.
   * 
   */
	public String search(int k){						// Time complexity : O(log n)
	if(this.empty()){
		return null;
	}
	
	IAVLNode x = searchRec(k, this.root);
	
	if(x == null || x.getKey() != k){
		return null;
	}
	return x.getValue();
   }

   /**
   * private IAVLNode searchRec (int k, IAVLNode x)
   *
   * returns a node with key k if it exists in the tree
   * 
   * otherwise, returns null.
   *
   */	
   private IAVLNode searchRec (int k, IAVLNode x) {				// Time complexity : O(log n)
	  if(this.empty()){
		  return null;
	  }
	if(x.getKey() == k){
		return x;
	}
	if(x.getKey() < k){
		if(x.getRight().getKey() == -1) {
			return x;
		}
		else{
			return searchRec(k, x.getRight());
		}
	}
	else{
		if(x.getLeft().getKey() == -1) {
			return x;
		}
		else{
			return searchRec(k, x.getLeft());
		}
	}
   }

  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i into the AVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   * 
   */
   public int insert(int k, String i) {											// Time complexity : O(log n)
	   IAVLNode newLeaf = new AVLNode(k, i);
	   newLeaf.setLeft(this.mockAVLNode);
	   newLeaf.setRight(this.mockAVLNode);
	   newLeaf.setHeight(0);
	   return insert(newLeaf);
   }
   
	/**
	* private int insert(IAVLNode newLeaf)
	*
	* inserts newLeaf into the AVL tree.
	* the tree must remain valid (keep its invariants).
	* returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
	* returns -1 if an item with key k already exists in the tree.
	* 
	*/
	private int insert(IAVLNode newLeaf) {											// Time complexity : O(log n)
		int k = newLeaf.getKey();
		if (this.root == null || this.getRoot().getKey() == -1){
			this.root = newLeaf;
			this.min = newLeaf;
			this.max = newLeaf;
			return 0;
		}
		IAVLNode place = searchRec(k, this.root);
		if (place.getKey() == k) {
			return -1;
		}
		if (k < this.min.getKey()){
			this.min = newLeaf;
		}
		if (k > this.max.getKey()){
			this.max = newLeaf;
		}
		IAVLNode y = place;
		while(y != null){
			y.setSize(y.getSize()+ newLeaf.getSize());
			y = y.getParent();
		}
		if (place.getKey() < k) {
			place.setRight(newLeaf);
		}
		if (place.getKey() > k) {
			place.setLeft(newLeaf);
		}
		newLeaf.setParent(place);
		place.setHeight(Math.max(place.getRight().getHeight(), place.getLeft().getHeight())+1);
		return rebalanceAfterInsertion(place);
	}
   
	/**
	 * private IAVLNode RotateRight(IAVLNode z)
	 *
	 * rotates a tree, with root z, to the right
	 * returns the new root of the tree.
	 * 
	 */
     private IAVLNode RotateRight(IAVLNode head){							// Time complexity : O(1)
 	  IAVLNode left = head.getLeft();
	  IAVLNode leftRight = head.getLeft().getRight();
	  if(leftRight == null){
		  return head;
	  }
	  left.setRight(head);
	  left.setParent(head.getParent());
	  if(head.getParent() != null){
	  	if(head.getParent().getRight() == head) {
			head.getParent().setRight(left);
		}
	  	else{
	  		head.getParent().setLeft(left);
		}
	  }
 		head.setParent(left);
 		head.setLeft(leftRight);
 		leftRight.setParent(head);
	  if(left.getParent() == null){
		  this.root = left;
	  }
	  head.setSize(head.getLeft().getSize()+head.getRight().getSize()+1);
	  left.setSize(left.getRight().getSize()+left.getLeft().getSize()+1);
 		return left;
	 }	
     
     /**
	 * private IAVLNode RotateLeft(IAVLNode x)
	 *
	 * rotates a tree, with root x, to the left
	 * returns the new root of the tree.
	 * 
	 */
	 private IAVLNode RotateLeft(IAVLNode h){									// Time complexity : O(1)
		IAVLNode r = h.getRight();
		IAVLNode rl = h.getRight().getLeft();
		if(rl == null){
			return h;
		}
		r.setLeft(h);
		r.setParent(h.getParent());
		if(h.getParent() != null) {
			if(h.getParent().getLeft() == h) {
				h.getParent().setLeft(r);
			}
			else{
				h.getParent().setRight(r);
			}
		}
		h.setParent(r);
		h.setRight(rl);
		rl.setParent(h);
		if(r.getParent() == null){
			this.root = r;
		}
		h.setSize(h.getRight().getSize() + h.getLeft().getSize() + 1);
		r.setSize(r.getLeft().getSize() + r.getRight().getSize() + 1);
		return r;
	}
	 
	/**
    * public int rebalanceAfterInsertion(IAVLNode place)
    *
    * rebalancing the tree after insertion using rotations
    * 
    * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
    * 
    */ 
	public int rebalanceAfterInsertion(IAVLNode place) {                          // Time complexity : O(log n)
		int rebalancingOps = 0;
		while (place != null) {
			int placeHeight = place.getHeight();
			int leftEdge = placeHeight - place.getLeft().getHeight();
			int rightEdge = placeHeight - place.getRight().getHeight();
			if ((leftEdge == 0 && rightEdge == 1) || (leftEdge == 1 && rightEdge == 0)) {             // Case 1
				place.setHeight(placeHeight + 1);
			}
			if (leftEdge == 0 && rightEdge == 2) {
				int sonsLeftEdge = place.getLeft().getHeight() - place.getLeft().getLeft().getHeight();
				if (sonsLeftEdge == 1) {                                          // Case 2
					place = RotateRight(place);                                     // Rotate the tree to the right
					place.getRight().setHeight(place.getRight().getHeight() - 1);    // Demote right son
					rebalancingOps += 1;
				}
				if (sonsLeftEdge == 2) {                                          // Case 3
					RotateLeft(place.getLeft());                                 // Rotate the left sub-tree to the left
					place = RotateRight(place);                                     // Rotate the tree to the right
					place.getRight().setHeight(place.getRight().getHeight() - 1);    // Demote right son
					place.getLeft().setHeight(place.getLeft().getHeight() - 1);  // Demote left son
					place.setHeight(place.getHeight() + 1);                        // Promote place
					rebalancingOps += 2;
				}
			}
			if (rightEdge == 0 && leftEdge == 2) {
				int sonsRightEdge = place.getRight().getHeight() - place.getRight().getRight().getHeight();
				if (sonsRightEdge == 1) {                                          // Case 2 Mirrored
					place = RotateLeft(place);                                     // Rotate the tree to the left
					place.getLeft().setHeight(place.getLeft().getHeight() - 1);    // Demote left son
					rebalancingOps += 1;
				}
				if (sonsRightEdge == 2) {                                          // Case 3 Mirrored
					RotateRight(place.getRight());                                 // Rotate the right sub-tree to the right
					place = RotateLeft(place);                                     // Rotate the tree to the left

					place.getLeft().setHeight(place.getLeft().getHeight() - 1);    // Demote left son
					place.getRight().setHeight(place.getRight().getHeight() - 1);  // Demote right son
					place.setHeight(place.getHeight() + 1);                        // Promote place
					rebalancingOps += 2;
				}
			}
			place = place.getParent();
		}
		return rebalancingOps;
	 }
	
	
	  /**
	   * public int delete(int k)
	   *
	   * deletes an item with key k from the binary tree, if it is there;
	   * the tree must remain valid (keep its invariants).
	   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
	   * returns -1 if an item with key k was not found in the tree.
	   * 
	   */
	   public int delete(int k) {															// Time complexity : O(log n)
		   IAVLNode item = searchRec(k, this.root);
		   
		   if (item == null || item.getKey() != k)
			   return -1;
		   
		   int ret = deleteNode(item);
		   updateMinMax(k);
		   
		   return ret;
	   }
	
	   /**
	    * public void deleteNode(IAVLNode node)
	    *
	    * deletes an item from the binary tree;
	    * the tree must remain valid (keep its invariants).
	    * rebalancing the tree after the deletion.
	    * 
	    * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
	    * 
	    */
	 	public int deleteNode(IAVLNode item) {												// Time complexity : O(log n)
	 		  
	 		   if (this.getRoot() == item) {
	 				if (item.getRight() == mockAVLNode && item.getLeft() == mockAVLNode) {        // item has no sons
	 					this.root = null;
	 					this.max = null;
	 					this.min = null;
	 				}
	 				if (item.getRight() != mockAVLNode && item.getLeft() == mockAVLNode) {        // item only has a right son
	 					this.root = item.getRight();
	 					this.root.setParent(null);
	 					item.setRight(mockAVLNode);
	 					this.min=this.root;
	 				}
	 				if (item.getRight() == mockAVLNode && item.getLeft() != mockAVLNode) {        //item only has a left son
	 					this.root = item.getLeft();
	 					this.root.setParent(null);
	 					item.setLeft(mockAVLNode);
	 					this.max=this.root;
	 				}
	 				if (item.getLeft() != mockAVLNode && item.getRight() != mockAVLNode) {            // item is a binary vertex
	 					IAVLNode suc = successor(item);
	 					IAVLNode parent = suc.getParent();
	 					item.setKey(suc.getKey());
	 					item.setValue(suc.getValue());
	 					if (parent != null) {
	 						if (parent.getRight() == suc) {
	 							parent.setRight(suc.getRight());
	 						} else {
	 							parent.setLeft(suc.getRight());
	 						}
	 					}
	 					suc.getRight().setParent(parent);
	 					suc.setParent(null);
	 					downSize(parent);
	 					return this.rebalanceAfterDeletion(parent);
	 				}
	 			   return 0;                                    // If the root doesn't have two children, no need to rebalance
	 		   }
	 		   IAVLNode parent = item.getParent();
	 		   if (parent.getRight() == item) {                                                    // item is a right son
	 			   if (item.getRight() != mockAVLNode && item.getLeft() == mockAVLNode) {        // item only has a right son
	 				   parent.setRight(item.getRight());
	 				   item.getRight().setParent(parent);
	 				   downSize(parent);
	 			   }
	 			   if (item.getRight() == mockAVLNode && item.getLeft() != mockAVLNode) {            // item only has a left son
	 				   parent.setRight(item.getLeft());
	 				   item.getLeft().setParent(parent);
	 				   downSize(parent);
	 			   }
	 			   if (item.getRight() == mockAVLNode && item.getLeft() == mockAVLNode) {        // item is a leaf
	 				   parent.setRight(mockAVLNode);
	 				   downSize(parent);
	 			   }
	 			   if (item.getRight() != mockAVLNode && item.getLeft() != mockAVLNode) {        // item is a binary vertex
	 				   IAVLNode suc = successor(item);
	 				   parent = suc.getParent();
	 				   item.setKey(suc.getKey());                                        // Binary vertex always has a successor
	 				   item.setValue(suc.getValue());
	 				   if (parent != null) {
	 					   if (parent.getRight() == suc) {
	 						   parent.setRight(suc.getRight());
	 					   } else {
	 						   parent.setLeft(suc.getRight());
	 					   }
	 				   }
	 				   suc.getRight().setParent(parent);
	 				   suc.setParent(null);
	 				   downSize(parent);
	 			   }
	 		   } else {                                                                            //item is a left son
	 			   if (item.getRight() != mockAVLNode && item.getLeft() == mockAVLNode) {        // item only has a right son
	 				   parent.setLeft(item.getRight());
	 				   item.getRight().setParent(parent);
	 				   downSize(parent);
	 			   }
	 			   if (item.getRight() == mockAVLNode && item.getLeft() != mockAVLNode) {            // item only has a left son
	 				   parent.setLeft(item.getLeft());
	 				   item.getLeft().setParent(parent);
	 				   downSize(parent);
	 			   }
	 			   if (item.getRight() == mockAVLNode && item.getLeft() == mockAVLNode) {        // item is a leaf
	 				   parent.setLeft(mockAVLNode);
	 				   downSize(parent);
	 			   }
	 			   if (item.getRight() != mockAVLNode && item.getLeft() != mockAVLNode) {        // item is a binary vertex
	 				   IAVLNode suc = successor(item);
	 				   parent = suc.getParent();
	 				   item.setKey(suc.getKey());
	 				   item.setValue(suc.getValue());
	 				   if (parent != null) {
	 					   if (parent.getRight() == suc) {
	 						   parent.setRight(suc.getRight());
	 					   } else {
	 						   parent.setLeft(suc.getRight());
	 					   }
	 					   suc.getRight().setParent(parent);
	 					   suc.setParent(null);
	 					   downSize(parent);
	 				   }
	 			   }
	 		   }
	 		   int ret = rebalanceAfterDeletion(parent);
	 		   return ret;
	 	   }
	 	
	 	/**
	 	* private void updateMinMax(int k)
	 	* 
	 	* updating the minimum / maximum values of the tree
	 	* based on the deletion of a node with key == k
	    * 
	 	*/	
	 	private void updateMinMax(int k)                            // Time complexity : O(log n)
	 	{
	 		
		   if (this.root == null) {
			   this.max = null;
			   this.min = null;
		   }
		   else {
			   IAVLNode runner = this.root;
			   if (this.min.getKey() == k) {
				   while (runner.getLeft() != mockAVLNode) {
					   runner = runner.getLeft();
				   }
				   this.min = runner;
			   }
			   if (this.max.getKey() == k) {
				   while (runner.getRight() != mockAVLNode) {
					   runner = runner.getRight();
				   }
				   this.max = runner;
			   }
		   }	
	 	}
   
	/**
	* private int rebalanceAfterDeletion(IAVLNode x)
	* 
	* rebalancing the tree after deletion using rotations
    *
    * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
	* 
	*/	 	
    private int rebalanceAfterDeletion(IAVLNode x){											// Time complexity : O(log n)
  		int rebalancingOps = 0;
  		while(x != null){
  			int xHeight = x.getHeight();
  			int leftHeight = x.getLeft().getHeight();
  			int rightHeight = x.getRight().getHeight();
  			if((xHeight - rightHeight) == 2 && (xHeight-leftHeight) == 2){					// Case 1
  				x.setHeight(x.getHeight()-1);
			}
  			if((xHeight-leftHeight) == 3 && (xHeight-rightHeight) == 1){
  				if((x.getRight().getHeight() - x.getRight().getRight().getHeight()) == 1) {
					x = RotateLeft(x);
					x.getLeft().setHeight(x.getLeft().getHeight() - 1);
					if (x.getLeft().getHeight() - x.getLeft().getRight().getHeight() == 1) {        // Case 2
						x.setHeight(x.getHeight() + 1);
					}
					if (x.getLeft().getHeight() - x.getLeft().getRight().getHeight() == 2) {    // Case 3
						x.getLeft().setHeight(x.getLeft().getHeight() - 1);
					}
					rebalancingOps += 1;
				}
  				else{																			// Case 4
					RotateRight(x.getRight());
					x = RotateLeft(x);
					x.setHeight(x.getHeight()+1);
					x.getRight().setHeight(x.getRight().getHeight()-1);
					x.getLeft().setHeight((x.getLeft().getHeight()-2));
					rebalancingOps += 2;
				}
			}
			if((xHeight-leftHeight) == 1 && (xHeight-rightHeight) == 3){
				if((x.getLeft().getHeight() - x.getLeft().getLeft().getHeight()) == 1) {
					x = RotateRight(x);
					x.getRight().setHeight(x.getRight().getHeight() - 1);
					if (x.getRight().getHeight() - x.getRight().getLeft().getHeight() == 1) {        // Case 2 mirrored
						x.setHeight(x.getHeight() + 1);
					}
					if (x.getRight().getHeight() - x.getRight().getLeft().getHeight() == 2) {    // Case 3 mirrored
						x.getRight().setHeight(x.getRight().getHeight() - 1);
					}
					rebalancingOps += 1;
				}
				else{																			// Case 4 mirrored
					RotateLeft(x.getLeft());
					x = RotateRight(x);
					x.setHeight(x.getHeight()+1);
					x.getLeft().setHeight(x.getLeft().getHeight()-1);
					x.getRight().setHeight((x.getRight().getHeight()-2));
					rebalancingOps += 2;
				}
			}
			x = x.getParent();
		}
  		return rebalancingOps;
     }

    /**
    * private IAVLNode successor(IAVLNode x)
    *
    * returns the successor of an AVLNode in the tree
    * or null if the tree is empty / last node in the tree (max) 
    * 
    */
    private IAVLNode successor(IAVLNode x) {							// Time complexity : O(log n)
    	   if(this.root == null)
    	   	  return null;
 	   
    	   int rank = treeRank(x);
    	   
    	   if(rank == this.root.getSize())
    		   return null;
    	   
    	   return treeSelect(this.root, rank+1);
    }

    /**
    * private void downSize (IAVLNode x)
    *
    * down-sizes the vertices from x upwards (size & height)
    * 
    */ 
    private void downSize (IAVLNode x) {					// Time complexity : O(log n)
	    while (x != null){
		   x.setSize(x.getRight().getSize()+x.getLeft().getSize()+1);
		   x.setHeight(Math.max(x.getRight().getHeight(), x.getLeft().getHeight())+1);
		   x = x.getParent();
	   }
    }
    

   /**
   * public String min()
   *
   * Returns the info of the item with the smallest key in the tree,
   * or null if the tree is empty.
   * 
   */
   public String min()										// Time complexity : O(1)
   {
	   if(this.min == null){
		   return null;
	   }
	   return this.min.getValue();
   }

   
   /**
   * public String max()
   *
   * Returns the info of the item with the largest key in the tree,
   * or null if the tree is empty.
   * 
   */
   public String max()											// Time complexity : O(1)
   {
   		if(this.max == null){
   			return null;
		}
	   return this.max.getValue();
   }
   
  /**
   * public IAVLNode getMock() 
   *
   * return the mock node (key = -1, value = null)
   * or null if the tree is empty.
   * 
   */
   public IAVLNode getMock()                                 // Time complexity : O(1)
   {
	   
	  return this.mockAVLNode; 
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   * 
   */
   public int[] keysToArray()								// Time complexity : O(nlog n)
   {
   	  if(this.empty()){
   	  	return new int[]{};
 	  }
   	  if(this.getRoot().getSize() == 1){
   	  	return new int[]{this.getRoot().getKey()};
 	  }
 	  int[] arr = new int[this.root.getSize()];
 	  IAVLNode x = treeSelect(this.root, 1);
 	  for (int i = 0; i < arr.length; i++) {
 		  arr[i] = x.getKey();
 		  x = successor(x);

 		  }
      return arr;
   }
   
	
  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   * 
   */
   public String[] infoToArray()											// Time complexity : O(nlog n)
   {
 	  if(this.empty()){
 		  return new String[]{};
 	  }
 	  String[] arr = new String[this.root.getSize()];
 	  IAVLNode x = this.min;
 	  for (int i = 0; i < arr.length; i++) {
 		  arr[i] = x.getValue();
 		  x = successor(x);
 		}
 	  return arr;
   }

  /**
   * public int size()
   *
   * Returns the number of nodes in the tree.
   *
   * precondition: none
   * postcondition: none
   * 
   */
   public int size()										// Time complexity : O(1)
   {
   		if(this.empty()) {
			return 0;
		}
   		return this.root.getSize();
   }
   
   /**
   * public int getRoot()
   *
   * Returns the root AVL node, or null if the tree is empty
   *
   * precondition: none
   * postcondition: none
   * 
   */
   public IAVLNode getRoot()								// Time complexity : O(1)
   {
	   return this.root;
   }
   
   /**
   * public IAVLNode treeSelect(IAVLNode node, int i)
   *
   * selects a node in the tree with rank i
   * returns the node in rank i
   * 
   */
   public IAVLNode treeSelect(IAVLNode node, int i) {              // Time complexity : O(log n)
		 
		boolean find = false;
		int j = node.getLeft().getSize()+1;

		if(j == i)
			return node;
		
		while (!find)
		{
		  if(j == i)
			  find = true;
		  
		  else if (i < j)
		  {
			  node = node.getLeft();
		  	  j = node.getLeft().getSize()+1;
		  }
			
		  else
		  {
			  node = node.getRight();
			  i = i-j;
			  j = node.getLeft().getSize()+1;
		  }
		  
		}	
		return node;
	}
   
   
   /**
   * public int treeRank(IAVLNode node)
   *
   * searches for a specific node's rank
   * returns the node's rank
   * 
   */
   public int treeRank(IAVLNode node) {                // Time complexity : O(log n)
	   int cnt = node.getLeft().getSize() +1;
	   
	   while (node.getParent() != null)
	   {
		 IAVLNode parent = node.getParent();
		
		 if(parent.getRight() == node)
			 cnt += parent.getLeft().getSize() +1;
		 
		 node = parent;
	   }
	   
	   return cnt;
	   
	  }
   


	/**
	   * public interface IAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IAVLNode{	
		public void setKey(int x); // Sets the node's key
		public int getKey(); // Returns node's key
		public void setValue(String x); // Sets the node's value
		public String getValue(); // Returns node's value [info]
		public void setLeft(IAVLNode node); // Sets left child
		public IAVLNode getLeft(); // Returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); // Sets right child
		public IAVLNode getRight(); // Returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); // Sets parent
		public IAVLNode getParent(); // Returns the parent (if there is no parent return null)
    	public void setHeight(int height); // Sets the height of the node
    	public int getHeight(); // Returns the height of the node
    	public void setSize(int x); // Sets the size of the sub-tree for which this node is the root
		public int getSize(); // Returns the size of the sub-tree for which this node is the root
		public boolean isRealNode(); // Returns True if this is not a virtual AVL node
	}

	
  /**
  * public class AVLNode
  *
  * If you wish to implement classes other than AVLTree
  * (for example AVLNode), do it in this file, not in 
  * another file.
  * This class can and must be modified.
  * (It must implement IAVLNode)
  */
  public class AVLNode implements IAVLNode{
	  
	    private int key;
		private String value;
		private IAVLNode leftSon;
		private IAVLNode rightSon;
		private IAVLNode parent;
		private int height;
		private int size;
		
		public AVLNode(int key, String value){
  			this.key = key;
  			this.value = value;
  			this.parent = null;
  			this.height = 0;
			this.size = 1;
		}
		public int getKey()
		{
			return this.key;
		}
	   public void setKey(int x)
	   {
		   this.key = x;
	   }
		public String getValue()
		{
			return this.value;
		}
	   public void setValue(String x)
	   {
		   this.value = x;
	   }
		public void setLeft(IAVLNode node)
		{
			this.leftSon = node;
		}
		public IAVLNode getLeft()
		{
			return this.leftSon;
		}
		public void setRight(IAVLNode node)
		{
			this.rightSon = node;
		}
		public IAVLNode getRight()
		{
			return this.rightSon;
		}
		public void setParent(IAVLNode node)
		{
			this.parent = node;
		}
		public IAVLNode getParent()
		{
			return this.parent;
		}
		public void setHeight(int height)
		{
		  this.height = height;
		}
		public int getHeight()
		{
		  return this.height;
		}
		public void setSize(int x){
  			this.size = x;
		}
		public int getSize(){
  			return this.size;
		}
		// Returns True if this is not a virtual AVLNode
		public boolean isRealNode()
		{
			return this.height != -1;
		}

  }

}




