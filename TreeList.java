

/**
 *
 * Tree list
 *
 * An implementation of a Tree list with key and info
 *
 * Yotam Hazan 205566870, username: yotamhazan
 * Kfir Grinberg  313434037, username: kfirgrinberg
 * 
 */
 public class TreeList {
	private AVLTree tree;
	private AVLTree.IAVLNode mockAVLNode;

	public TreeList()
	{
		this.tree = new AVLTree();
		this.mockAVLNode = this.tree.getMock();	
	}
	
  /**
  * public Item retrieve(int i)
  *
  * returns the item in the ith position if it exists in the list.
  * otherwise, returns null.
  */
  public Item retrieve(int i)                                     // Time complexity : O(log n)
  {
	if(i < 0 || i > this.tree.size()-1 || this.tree == null)
		return null;
	
	AVLTree.IAVLNode new_node = this.tree.treeSelect(this.tree.getRoot(), i+1);

	
    return new Item(new_node.getKey(), new_node.getValue());
  }


   /**
   * public int insert(int i, int k, String s) 
   *
   * inserts an item to the ith position in list with key k and info s.
   * returns -1 if i < 0 or i > n otherwise, return 0.
   */
   public int insert(int i, int k, String s) {                         // Time complexity : O(log n)
	   if(i < 0 || i > this.tree.size())
			return -1;
	     
	   if(i == this.tree.size())     // Case 1 - i = n
	   {
		  insertLast(i, k, s); 
	   }
	   
	   
	   else                                   // Case 2 - 0 <= i < n
	   {   	
		   AVLTree.IAVLNode node = this.tree.treeSelect(this.tree.getRoot(), i+1);
		   AVLTree.IAVLNode new_node = this.tree.new AVLNode(k, s);
		   if(!node.getLeft().isRealNode())        // Case 2.1 - node in rank (i+1) has no left child
		   {
			   node.setLeft(new_node);
			   
		   }
		   
		   else                            // Case 2.2 - node in rank (i+1) has left child
		   {   
			   node = this.tree.treeSelect(this.tree.getRoot(), i);
			   node.setRight(new_node);
		   }
		   AVLTree.IAVLNode y = node;
		   while(y != null){
			   y.setSize(y.getSize()+new_node.getSize());
			   y = y.getParent();
		   }
		   new_node.setLeft(this.mockAVLNode);
		   new_node.setRight(this.mockAVLNode);
		   new_node.setHeight(0);
		   new_node.setParent(node);
		   this.tree.rebalanceAfterInsertion(new_node);
	   
	   }
	   return 0;
   }
   
   
   
   /**
   * private void insertLast (int i, int k, String s)
   *
   * inserts an item to the last position in list with key k and info s.
   * possible case: i == 0, which means the function also covers the case of the
   * first insert to the list.
   * 
   */
   private void insertLast (int i, int k, String s) {                   // Time complexity : O(log n)
	   
	   AVLTree.IAVLNode node = this.tree.getRoot();
	   AVLTree.IAVLNode new_node = this.tree.new AVLNode(k, s);
	   
	   if(node == null || !this.tree.getRoot().isRealNode())
	   {
		   this.tree.insert(k, s);
	   }
	   
	   else
	   { 
		    while(node.getRight().isRealNode())
				   node = node.getRight();
		    AVLTree.IAVLNode y = node;
			while(y != null){
				y.setSize(y.getSize()+new_node.getSize());
				y = y.getParent();
			}
		    node.setRight(new_node);
		    new_node.setLeft(this.mockAVLNode);
		    new_node.setRight(this.mockAVLNode);
		    new_node.setHeight(0);
		    new_node.setParent(node);
		    this.tree.rebalanceAfterInsertion(new_node);
	   }
	   
   }

   /**
   * public int delete(int i)
   *
   * deletes an item in the ith position from the list.
   * returns -1 if i<0 or i>n-1 otherwise returns 0.
   */
   public int delete(int i) {                      // Time complexity : O(log n)
	   
	   if(i < 0 || i > this.tree.size()-1)
			return -1;
	   
	   AVLTree.IAVLNode del = this.tree.treeSelect(this.tree.getRoot(), i+1);
	   this.tree.deleteNode(del);
	   
	   return 0;
   }
   
}
	  