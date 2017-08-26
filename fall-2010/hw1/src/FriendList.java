/**
 * An Abstract Data Type (ADT) that keeps track of a list of people.
 * It contains methods to manipulate the list and that certain
 * conditions must be met to successfully store names in the list.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #1 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA TBA
 *
 */
public class FriendList
{
	//Maximum number of elements (names) the list can store
	public static final int MAX_LIST_SIZE = 100;
	//Maximum number of characters an element (name) can have
	public static final int MAX_NAME_SIZE = 30;
	//Array of type String to store the elements (names)
	private String[] friendList;
	//Keeps track of the number of elements currently stored in the list
	private int friendListSize;
	
	/**
	 * Constructor for the <CODE>FriendList</CODE> class
	 * that constructs an instance of this class.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>FriendList</CODE> has 0 out of <CODE>MAX_LIST_SIZE</CODE>
	 *   people in it.
	 */
	public FriendList()
	{
		friendList = new String[MAX_LIST_SIZE];
		friendListSize = 0;
	}
	
	/**
	 * Adds a name to this <CODE>FriendList</CODE> given that:
	 * <br>(1) the name is not too short nor too long
	 * <br>(2) the name is not in this <CODE>FriendList</CODE>
	 * <br>(3) this <CODE>FriendList</CODE> is not full
	 * @param name
	 *   - the name of a friend to be added to this <CODE>FriendList</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>FriendList</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>FriendList</CODE> will contain a friend whose name
	 *   is given as a parameter.
	 * @throws IllegalArgumentException
	 *   Indicates the given name is too short (length = 0) or too long
	 *   (more than <CODE>MAX_NAME_SIZE</CODE> characters).
	 * @throws DuplicateNameException
	 *   Indicates the given name is already in this <CODE>FriendList</CODE>.
	 * @throws ListFullException
	 *   Indicates this <CODE>FriendList</CODE> is full and the
	 *   given name cannot be added.
	 */
	public void addFriend(String name) throws IllegalArgumentException,
	  DuplicateNameException, ListFullException
	{
		//Checks if the given name is too short or too long
		if ((name.length() == 0) || (name.length() > MAX_NAME_SIZE))
			throw new IllegalArgumentException(name);
		//for-loop creates a variable index i to determine the current position
		//of the element that is being compared to the given name
		for (int i = 0; i < friendListSize; i++)
			if (friendList[i].equalsIgnoreCase(name))
				throw new DuplicateNameException(name);
		//List is considered full if the friendListSize and the MAX_LIST_SIZE
		//have the same value
		if (friendListSize == MAX_LIST_SIZE)
			throw new ListFullException(name);
		//If none of the above exceptions are thrown, name is added to the list
		friendList[friendListSize] = name.substring(0);
		//friendListSize increments
		friendListSize++;
	}
	
	/**
	 * Removes a name from this <CODE>FriendList</CODE> given that
	 * the name is not too short nor too long.
	 * @param name
	 *   - the name of a friend to be removed form this <CODE>FriendList</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>FriendList</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>FriendList</CODE> will no longer contain a friend with the
	 *   given name.
	 * @return
	 *   a return value of true indicating that the given name has been removed
	 *   from this <CODE>FriendList</CODE>.<br>
	 *   a return value of false indicating that the given name was not
	 *   found in this <CODE>FriendList</CODE>.
	 * @throws IllegalArgumentException
	 *   Indicates the given name is too short (length = 0) or too long
	 *   (more than <CODE>MAX_NAME_SIZE</CODE> characters).
	 */
	public boolean removeFriend(String name) throws IllegalArgumentException
	{
		boolean flag = false;
		//Checks if the given name is too short or too long
		if ((name.length() == 0) || (name.length() > MAX_NAME_SIZE))
			throw new IllegalArgumentException(name);
		//for-loop creates a variable index i to determine the current position
		//of the element that is being compared to the given name
		for (int i = 0; i < friendListSize; i++)
			if (friendList[i].equalsIgnoreCase(name))
			{
				//The name at index i is replaced by the last name in the list
				friendList[i] = friendList[friendListSize-1];
				//friendListSize decrements
				friendListSize--;
				flag = true;
			}
		return flag;
	}
	
	/**
	 * Determines if this <CODE>FriendList</CODE> contains the given name,
	 * ignoring case considerations.
	 * @param name
	 *    - the name of a friend that may or may not be in this
	 *   <CODE>FriendList</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>FriendList</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>FriendList</CODE> will not be modified.
	 * @return
	 *   a return value of <CODE>true</CODE> indicates that this
	 *   <CODE>FriendList</CODE> contains the given name. <br>
	 *   a return value of <CODE>false</CODE> indicates that this
	 *   <CODE>FriendList</CODE> does not contain the given name.
	 * @throws IllegalArgumentException
	 *   Indicates the given name is too short (length = 0) or too long
	 *   (more than <CODE>MAX_NAME_SIZE</CODE> characters).
	 */
	public boolean contains(String name) throws IllegalArgumentException
	{
		boolean flag = false;
		//Checks if the given name is not too short nor too long
		if ((name.length() == 0) || (name.length() > MAX_NAME_SIZE))
			throw new IllegalArgumentException(name);
		//for-loop creates a variable index i to determine the current position
		//of the element that is being compared to the given name
		for (int i = 0; i < friendListSize; i++)
			if (friendList[i].equalsIgnoreCase(name))
				flag = true;
		return flag;
	}
	
	/**
	 * Returns an <CODE>int</CODE> representation of the number of friends
	 * in this <CODE>FriendList</CODE>.
	 * @return
	 *   a number of friends in this <CODE>FriendList</CODE>.
	 */
	public int numFriends()
	{
		return friendListSize;
	}
	
	/**
	 * Compares this <CODE>FriendList</CODE> to another object for equality.
	 * Two <CODE>FriendList</CODE> objects are considered equal if the names in
	 * one list are all contained in the second list and if they have the same
	 * number of friends.
	 * @param obj
	 *   - an object in which this <CODE>FriendList</CODE> is compared with
	 * @return
	 *   a return value of <CODE>true</CODE> indicating that <CODE>obj</CODE>
	 *   refers to a <CODE>FriendList</CODE> object with the same list of
	 *   people in it as this <CODE>FriendList</CODE>, otherwise the return
	 *   value is <CODE>false</CODE>.
	 */
	public boolean equals(Object obj)
	{
		//Type casting of obj to FriendList type
		FriendList comparedList = (FriendList)obj;
		boolean flag = false;
		//Counter incremented every time a friend name from one list
		//is seen in the other list
		int counter = 0;
		//Returns false if the lists' sizes are different, if the obj is null,
		//or if the obj is not a FriendList object
		if ((friendListSize != comparedList.friendListSize) ||
		  (obj == null) || !(obj instanceof FriendList))
			return flag;
		for (int indexList2 = 0; indexList2 < friendListSize; indexList2++)
		{
			try
			{
				if (this.contains(comparedList.friendList[indexList2]))
					counter++;
			}
			catch (IllegalArgumentException iae)	{ }
		}

		return (counter == friendListSize);
	}
	
	/**
	 * Returns a new <CODE>FriendList</CODE> object that contains all the names
	 * seen on either list, disregarding duplicates.
	 * @param list1
	 *   - an instantiated <CODE>FriendList</CODE> object
	 * @param list2
	 *   - an instantiated <CODE>FriendList</CODE> object
	 * <dt><b>Postconditions:</b><dd>
	 *   <CODE>list1</CODE> and <CODE>list2</CODE> are not modified.
	 * @return
	 *   a new <CODE>FriendList</CODE> that is the union of <CODE>list1</CODE>
	 *   and <CODE>list2</CODE>. That is, the returned list will contain all
	 *   names that appear in <i>either</i> list. Any modification to this new
	 *   <CODE>FriendList</CODE> will not affect <CODE>list1</CODE> or
	 *   <CODE>list2</CODE>.
	 * @throws IllegalArgumentException
	 *   Indicates one of the <CODE>FriendList</CODE> objects is
	 *   <CODE>null</CODE>.
	 */
	public static FriendList union(FriendList list1, FriendList list2)
	  throws IllegalArgumentException
	{
		if ((list1 == null) || (list2 == null))
			throw new IllegalArgumentException();
		//Gives all values of list1 to newList
		FriendList newList = (FriendList)(list1.clone());
		
		for (int indexList2 = 0; indexList2 < list2.friendListSize; 
		  indexList2++)
		{
			try
			{
				//Adds friend name from list2 with index indexList2 to
				//newList if the newList does NOT contain the name from
				//list2 with index indexList2
				if (!newList.contains(list2.friendList[indexList2]))
					newList.addFriend(list2.friendList[indexList2]);
			}
			catch (ListFullException lfe)	{ }
			catch (DuplicateNameException dne)	{ }
		}
		
		return newList;
	}
	
	/**
	 * Returns a new <CODE>FriendList</CODE> object that contains all the names
	 * seen on both lists.
	 * @param list1
	 *   - an instantiated <CODE>FriendList</CODE> object
	 * @param list2
	 *   - an instantiated <CODE>FriendList</CODE> object
	 * <dt><b>Postconditions:</b><dd>
	 *   <CODE>list1</CODE> and <CODE>list2</CODE> are not modified.
	 * @return
	 *   a new <CODE>FriendList</CODE> that is the intersection of
	 *   <CODE>list1</CODE> and <CODE>list2</CODE>. That is, the returned list
	 *   will contain all names that appear in <i>both</i> lists. Any
	 *   modification to this new <CODE>FriendList</CODE> will not affect 
	 *   <CODE>list1</CODE> or <CODE>list2</CODE>.
	 * @throws IllegalArgumentException
	 *   Indicates one of the <CODE>FriendList</CODE> objects is
	 *   <CODE>null</CODE>.
	 */
	public static FriendList intersect(FriendList list1, FriendList list2)
	  throws IllegalArgumentException
	{
		if ((list1 == null) || (list2 == null))
			throw new IllegalArgumentException();
		
		FriendList newList = new FriendList();
		
		//Tests every element in list2 if it's in list1
		for (int indexList2 = 0; indexList2 < list2.friendListSize;
		  indexList2++)
		{
			try
			{
				//Friend name will be added to newList if a name
				//is common to both lists
				if (list1.contains(list2.friendList[indexList2]))
					newList.addFriend(list2.friendList[indexList2]);
			}
			catch (IllegalArgumentException iae)	{ }
			catch (ListFullException lfe)	{ }
			catch (DuplicateNameException dne)	{ }
		}
		
		return newList;
	}
	
	/**
	 * Generates a copy of this <CODE>FriendList</CODE>, including all elements
	 * and the current <CODE>FriendList</CODE> size.
	 * @return
	 *   a copy of this <CODE>FriendList</CODE>. Subsequent changes to the copy
	 *   will not affect the original, and vice versa.
	 */
	public Object clone()
	{
		FriendList friendListCopy = new FriendList();
		//for-loop creates a variable index i to determine the current
		//position of the element to be copied.
		for (int i = 0; i < friendListSize; i++)
			friendListCopy.friendList[i] = friendList[i].substring(0);
		friendListCopy.friendListSize = friendListSize;
		return friendListCopy;
	}
	
	/**
	 * Returns a <CODE>String</CODE> representation of this <CODE>FriendList</CODE>
	 * that shows the name(s) of friends in the list.
	 * <dt><b>Preconditions:</b><dd>
	 *   This <CODE>FriendList</CODE> has been instantiated.
	 * @return
	 *   a <CODE>String</CODE> representation of this <CODE>FriendList</CODE>.
	 *   The names are separated by a comma and not in any particular order.
	 */
	public String toString()
	{
		String message = "{";
		//Exclusive for 1 number of friend
		if (friendListSize == 1)
			return message + friendList[0] + "}";
		//Executes if the number of friends is greater than 1
		for (int i = 0; i < friendListSize; i++)
		{
			//Adds the element in index i of the array to the String
			if (i < friendListSize-1)
				message = message + friendList[i] + ", ";
			//Executes when the array reaches the last element
			//to exclude the comma
			else
				message = message + friendList[i];
		}
		return message + "}";
	}
}
