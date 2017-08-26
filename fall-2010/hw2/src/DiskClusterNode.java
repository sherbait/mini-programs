/**
 * <CODE>Node</CODE> that contains information such as the
 * <CODE>clusterNumber</CODE>, <CODE>clusterData</CODE>, and
 * a reference to the next <CODE>node</CODE>. It can be used to
 * build a <CODE>Linked List</CODE>.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #2 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *   
 */
public class DiskClusterNode
{
	private int clusterNumber;
	private String clusterData;
	private DiskClusterNode nextCluster;
	
	/**
	 * Constructor for the <CODE>DiskClusterNode</CODE> class that
	 * constructs an instance of this class.
	 * <dt><b>Postconditions:</b><dd>
	 *   <CODE>clusterNumber</CODE> is set to default value of -1.<dd>
	 *   <CODE>clusterData</CODE> is set to be an empty <CODE>String</CODE>.<dd>
	 *   References a next <CODE>null</CODE> node.
	 */
	public DiskClusterNode()
	{
		clusterNumber = -1;
		clusterData = "";
		nextCluster = null;
	}
	
	/**
	 * Sets the cluster number of this <CODE>DiskClusterNode</CODE>.
	 * @param cluster
	 *   - the <CODE>int</CODE> number to be set as the cluster number
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterNode</CODE> is instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>node</CODE>'s cluster number is set to the given number. 
	 */
	public void setClusterNumber(int cluster)
	{
		clusterNumber = cluster;
	}
	
	/**
	 * Returns an <CODE>int</CODE> representation of the cluster number
	 * of this <CODE>DiskClusterNode</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterNode</CODE> is instantiated.
	 * @return
	 *   the cluster number of this <CODE>DiskClusterNode<CODE>.
	 */
	public int getClusterNumber()
	{
		return clusterNumber;
	}
	
	/**
	 * Sets the cluster data of this <CODE>DiskClusterNode</CODE>.
	 * @param data
	 *   - the <CODE>String</CODE> value to be set as the cluster data
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterNode</CODE> is instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>node</CODE>'s cluster data is set to the given value. 
	 */
	public void setData(String data)
	{
		clusterData = data;
	}
	
	/**
	 * Returns a <CODE>String</CODE> representation of the cluster data
	 * of this <CODE>DiskClusterNode</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterNode</CODE> is instantiated.
	 * @return
	 *   this <CODE>node</CODE>'s cluster <CODE>String</CODE> data.
	 */
	public String getData()
	{
		return clusterData;
	}
	
	/**
	 * Sets the next <CODE>DiskClusterNode</CODE> this 
	 * <CODE>DiskClusterNode</CODE> will reference to.
	 * @param node
	 *   - the <CODE>DiskClusterNode</CODE> after this <CODE>node</CODE>
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterNode</CODE> is instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>node</CODE> will reference to another <CODE>node</CODE>. 
	 */
	public void setNextNode(DiskClusterNode node)
	{
		nextCluster = node;
	}
	
	/**
	 * Returns the <CODE>node</CODE> this <CODE>DiskClusterNode</CODE>
	 * references to.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>DiskClusterNode</CODE> is instantiated.
	 * @return
	 *   the <CODE>node</CODE> after this <CODE>DiskClusterNode</CODE>.
	 */
	public DiskClusterNode getNextNode()
	{
		return nextCluster;
	}
}
