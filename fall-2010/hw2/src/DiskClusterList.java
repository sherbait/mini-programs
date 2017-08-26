/**
 * An Abstract Data Type (ADT) that represents the list of disk clusters
 * associated with a file. It contains methods to manipulate the nodes in
 * the list and that certain conditions must be met to successfully
 * create changes.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #2 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class DiskClusterList
{
	private DiskClusterNode head;
	private DiskClusterNode tail;
	
	/**
	 * Constructor for the <CODE>DiskClusterList</CODE> class
	 * that constructs an instance of this class.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> has a head and a tail node
	 *   that are <CODE>null</CODE>.
	 */
	public DiskClusterList()
	{
		head = null;
		tail = null;
	}
	
	/**
	 * Creates a new <CODE>DiskClusterNode</CODE> with the given cluster
	 * number and data and appends it to the tail of this
	 * <CODE>DiskClusterList</CODE>.
	 * @param cluster
	 *   - the cluster number of the node to be added to this
	 *   <CODE>DiskClusterList</CODE>
	 * @param data
	 *   - the <CODE>String</CODE> data of the node to be added to this
	 *   <CODE>DiskClusterList</CODE>
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> will contain a new
	 *   <CODE>DiskClusterNode</CODE>.
	 */
	public void addClusterToTail(int cluster, String data)
	{
		//Creation of the new node
		DiskClusterNode newNode = new DiskClusterNode();
		newNode.setClusterNumber(cluster);
		newNode.setData(data);
		//Executes if the list is empty
		if (head == null)
		{
			head = newNode;
			tail = newNode;
		}
		else
		{
			tail.setNextNode(newNode);
			tail = newNode;
		}
	}//Order of Complexity? O(1) - because the number of operations is 2-3
	
	/**
	 * Creates a new <CODE>DiskClusterNode</CODE> with the given cluster number
	 * and data and appends it after the node in the list at the specified
	 * position. If position is 0, the new node will be inserted at the head
	 * of the list. If position is less than 0 or greater than the number of
	 * nodes in the list, an exception will be thrown and insertion will
	 * not be performed.
	 * @param cluster
	 *   - the cluster number of the node to be added to this
	 *   <CODE>DiskClusterList</CODE>
	 * @param data
	 *   - the <CODE>String</CODE> data of the node to be added to this
	 *   <CODE>DiskClusterList</CODE>
	 * @param position
	 *   - the <CODE>int</CODE> location of the new node to be added to this
	 *   <CODE>DiskClusterList</CODE>
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> will contain a new
	 *   <CODE>DiskClusterNode</CODE>.
	 * @throws InvalidPositionException
	 *   Indicates the given position is less than 0 or greater than the
	 *   number of nodes in this <CODE>DiskClusterList</CODE>. 
	 */
	public void insertCluster(int cluster, String data, int position)
	  throws InvalidPositionException
	{
		//Creation of the new node
		DiskClusterNode newNode = new DiskClusterNode();
		newNode.setClusterNumber(cluster);
		newNode.setData(data);
		//Determines the position's validity
		if ((position < 0 || position > clusterLength()))
			throw new InvalidPositionException();
		//Executes if the new node is to be placed at the head of the list
		else if (position == 0)
		{
			//Executes if the list is empty
			if (head == null)
			{
				head = newNode;
				tail = newNode;
			}
			else
			{
				newNode.setNextNode(head);
				head = newNode;
			}
		}
		//Executes if the new node is to be added to the tail of the list
		else
		{
			//Creation of a dummy node to traverse the list
			DiskClusterNode cursor = head;
			//cursor will stop at the given position
			for (int i = 1; i < position; i++)
				cursor = cursor.getNextNode();
			//Executes if the cursor is at the tail of the list
			if (cursor.getNextNode() == null)
				addClusterToTail(cluster, data);
			else
			{
				newNode.setNextNode(cursor.getNextNode());
				cursor.setNextNode(newNode);
			}
		}
	}//Order of Complexity? O(N) - based on the given position, this method
	 //may execute just 1 operation or go through N
	
	/**
	 * Removes the <CODE>DiskClusterNode</CODE> currently located at the
	 * specified position. If position is less than or equal to 0 or if
	 * position is greater than the number of nodes in the list, an
	 * exception will be thrown and no removal will be performed.
	 * @param position
	 *   - the <CODE>int</CODE> location of the node to be removed from this
	 *   <CODE>DiskClusterList</CODE>
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> will no longer contain the
	 *   <CODE>DiskClusterNode</CODE> in the specified position.
	 * @throws InvalidPositionException
	 *   Indicates the given position is less than or equal to 0 or
	 *   greater than the number of nodes in this <CODE>DiskClusterList</CODE>.
	 */
	public void removeCluster(int position) throws InvalidPositionException
	{
		//Determines the position's validity
		if ((position <= 0) || (position > clusterLength()))
			throw new InvalidPositionException();
		else
		{
			//Creation of a dummy node to traverse the list
			DiskClusterNode cursor = head;
			//Makes the second node from the head of the list the new head
			if (position == 1)
				head = head.getNextNode();
			else 
			{
				//cursor will stop at a position before the given position
				for (int i = 1; i < position-1; i++)
					cursor = cursor.getNextNode();
				cursor.setNextNode(cursor.getNextNode().getNextNode());
				if (cursor.getNextNode() == null)
					//Executes if given position is the tail of the list
					tail = cursor;			
			}
		}
	}//Order of Complexity? O(N) - based on the given position, this method
	 //may execute just 1 operation or go through N
	
	/**
	 * Determines whether or  not this <CODE>DiskClusterList</CODE>
	 * has a <CODE>DiskClusterNode</CODE> containing the given cluster number.
	 * @param cluster
	 *   - the cluster number of the node to be located in this
	 *   <CODE>DiskClusterList</CODE>
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> will not be modified.
	 * @return
	 *   <CODE>true</CODE> if the <CODE>DiskClusterNode</CODE> with the given
	 *   cluster number exists in the list, or <CODE>false</CODE> otherwise.
	 */
	public boolean containsCluster(int cluster)
	{
		//Creation of a dummy node to traverse the list
		DiskClusterNode cursor = head;
		while (cursor != null)
		{
			if (cursor.getClusterNumber() == cluster)
				return true;
			cursor = cursor.getNextNode();
		}
		return false;
	}//Order of Complexity? O(N) - depending on the given cluster number.
	 //If the cluster is at the end of the list (worst-case)
	 //then O(N). If it's at the beginning (best-case) then O(1).
	
	/**
	 * Displays a list of all cluster numbers and data that appear in this
	 * <CODE>DiskClusterList</CODE>. For each cluster, the cluster number
	 * and data are printed on the same line and are separated by a space.
	 * Each cluster is printed on its own line.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> will not be modified.
	 */
	public void printAllClusters()
	{
		DiskClusterNode cursor = head;
		while (cursor != null)
		{
			System.out.println(cursor.getClusterNumber()
			  + " " + cursor.getData());
			cursor = cursor.getNextNode();
		}
	}//Order of Complexity? O(N) - to print all clusters would require
	 //the program to traverse the whole list
	
	/**
	 * All clusters that appear in this <CODE>DiskClusterList</CODE>
	 * are removed.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> will not contain any
	 *   <CODE>DiskClusterNode</CODE>.
	 */
	public void removeAllClusters()
	{
		try
		{
			//Removes a cluster one-by-one starting from the tail
			while (clusterLength() > 0)
				removeCluster(clusterLength());
		}
		catch (InvalidPositionException ipe)	{	}
	}//Order of Complexity? O(0) - no operations are made
	
	/**
	 * Returns an <CODE>int</CODE> representation of the number of nodes
	 * in this <CODE>DiskClusterList</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> will not be modified.
	 * @return
	 *   the number of nodes in this <CODE>DiskClusterList</CODE>.
	 */
	public int clusterLength()
	{
		DiskClusterNode cursor = head;
		int count = 0;
		while (cursor != null)
		{
			count++;
			cursor = cursor.getNextNode();
		}
		return count;
	}
	
	/**
	 * Returns an <CODE>int</CODE> representation of the cluster number
	 * of the <CODE>DiskClusterNode</CODE> at the given position in this
	 * <CODE>DiskClusterList</CODE>. If position is less than or equal to
	 * 0 or if position is greater than the number of nodes in the list, an
	 * exception will be thrown and no cluster number will be returned.
	 * @param position
	 *   - the <CODE>int</CODE> location of the node which contains the
	 *   cluster number to be returned
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>DiskClusterList</CODE> will not be modified.
	 * @return
	 *   the cluster number of the node at the given position in this
	 *   <CODE>DiskClusterList</CODE>.
	 * @throws InvalidPositionException
	 *   Indicates the given position is less than or equal to 0 or
	 *   greater than the number of nodes in this <CODE>DiskClusterList</CODE>.
	 */
	public int getClusterNumber(int position) throws InvalidPositionException
	{
		DiskClusterNode cursor = head;
		//Determines the position's validity
		if ((position <= 0) || (position > clusterLength()))
			throw new InvalidPositionException();
		else
		{
			//cursor stops at the given position
			for (int i = 1; i < position ; i++)
				cursor = cursor.getNextNode();
			return cursor.getClusterNumber();
		}	
	}
}
