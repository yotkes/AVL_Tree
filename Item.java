

 /**
 *
 * Item
 *
 * Item object which based on a key (integer type) and 
 * info (String type)
 *
 * Yotam Hazan 205566870, username: yotamhazan
 * Kfir Grinberg  313434037, username: kfirgrinberg
 *
 */

public class Item{
	
	private int key;
	private String info;
	
	public Item (int key, String info){
		this.key = key;
		this.info = info;
	}
	public int getKey(){              // Time complexity : O(1)
		return key;
	}
	public String getInfo(){         // Time complexity : O(1)
		return info;
	}
}