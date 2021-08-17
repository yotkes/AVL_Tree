

/**
 *
 * Circular list
 *
 * An implementation of a circular list with key and info
 *
 * Yotam Hazan
 *
 */
 
 public class CircularList{
	 
	 private Item[] array;
	 private int maxLen;
	 private int start;
	 private int length;

	 
	public CircularList (int maxLen){
		this.array = new Item[maxLen];
		this.maxLen = maxLen;
		this.start = 0;
		this.length = 0;			
	}
	
  /**
  * public Item retrieve(int i)
  *
  * returns the item in the ith position if it exists in the list.
  * otherwise, returns null
  */
  public Item retrieve(int i)                                // Time complexity : O(1)
  {
	if (i < 0 || i >= this.length)
		return null;
	
	return this.array[(this.start+i) % this.maxLen];	
  }

  /**
   * public int insert(int i, int k, String s) 
   *
   * inserts an item to the ith position in list with key k and info s.
   * returns -1 if i < 0 or i > n or n = maxLen otherwise, return 0.
   */
   public int insert(int i, int k, String s) {                            // Time complexity : O(min⁡{𝑖+1, 𝑛−𝑖+1})

	  if(i < 0 || i > this.length || this.length == this.maxLen)
		  return -1;
	  
	  if (i == this.length){
		 insertLast(k, s);
		 return 0;
	  }  
	  if (i == 0){
		insertFirst(k, s);
		return 0;	  
	  }
	  
	  Item b = new Item(k, s); 
	  if (i < this.length - i) {
		for (int j = 0; j < i; j++)
		{
			this.array[(this.start-1+j) % this.maxLen] = this.array[(this.start+j) % this.maxLen];			
		}
		this.array[(this.start-1+i) % this.maxLen] = b;
		this.length ++;
		this.start = (this.start-1) % this.maxLen;
	  }
	  else
	  {
		for (int j = this.length-1; j >= i; j--)
		{
		 this.array[(this.start+j+1)% this.maxLen] = this.array[(this.start+j) % this.maxLen];		
			
		}
		this.array[(this.start+i) % this.maxLen] = b;
		this.length ++;
	  }
	  return 0;
   }
   
   /**
    * private void insertLast (int k, String s) 
    *
    * inserts an item to the last position in the list.
    * 
    */
   private void insertLast (int k, String s)                   // Time complexity : O(1)
   {
	 Item b = new Item(k, s);
	 
	 this.array[(this.start + this.length) % this.maxLen] = b;
	 this.length ++;	   
   }
   
   /**
    * private void insertFirst (int k, String s) 
    *
    * inserts an item to the first position in the list.
    * 
    */
   private void insertFirst (int k, String s)                  // Time complexity : O(1)
   {
	 Item b = new Item(k, s);
	 if(this.start == 0)
	 {
		 this.array[this.maxLen-1] = b;
		 this.start = this.maxLen-1;
	 }	
	 else {
	     this.array[this.start-1] = b;	
	     this.start = this.start-1;
	 }
	 
	 this.length ++;
   }

  /**
   * public int delete(int i)
   *
   * deletes an item in the ith position from the list.
   * returns -1 if i < 0 or i > n-1 otherwise, returns 0.
   */
   public int delete(int i)             // Time complexity : O(min⁡{𝑖+1, 𝑛−𝑖+1})
   {
	   if(i < 0 || i > this.length-1)
			  return -1;
		  
	   if (i == this.length-1)
		 {
			deleteLast();
			return 0;
		 }
		  
	   if (i == 0)
		 {
		   deleteFirst();
		   return 0;	  
		 }
		  
	   if (i < this.length - i)
		  {
		    for (int j = i; j > 0; j--)
			{
			   this.array[(this.start+j) % this.maxLen] = this.array[(this.start+j-1) % this.maxLen];			
			}
			this.array[this.start] = null;
			this.length --;
			this.start = (this.start+1) % this.maxLen;
		  }
	   else
		  {
		    for (int j = i; j < this.length; j--)
		    {
			 this.array[(this.start+j)% this.maxLen] = this.array[(this.start+j+1) % this.maxLen];			
			}
		    
			this.array[(this.start+this.length-1) % this.maxLen] = null;
			this.length --;
		  }
		  return 0;   
   }
   
   /**
   * private void deleteLast() 
   *
   * deletes an item in the last position in the array.
   * this function also handles the case of deleting a list with only one item.
   */
   private void deleteLast()                       // Time complexity : O(1)
   {
	 this.array[(this.start+this.length-1) % this.maxLen] = null;
	 this.length --;	   
   }
   
   /**
   * private void deleteFirst() 
   *
   * deletes an item in the first position in the array
   * precondition: n > 1
   * 
   */
   private void deleteFirst()                    // Time complexity : O(1)
   { 
	 this.array[this.start] = null;
	 this.length --;	
	 this.start = (this.start+1) % this.maxLen;
   }
   
 }
 
 
 
