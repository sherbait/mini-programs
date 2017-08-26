/**
 * A supplement class for the <CODE>FileAllocationManager</CODE> class that
 * keeps track of the clusters on the disk. Each element of this class
 * corresponds to a cluster on the disk.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #2 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class Clusters
{
	//Maximum number of clusters the FileAllocationManager class can hold 
	public final int MAX_CLUSTERS = 150;
	//Array that keeps track of each cluster
	private boolean[] cluster;
	
	/**
	 * Constructor for the <CODE>Clusters</CODE> class that constructs an
	 * instance of this class.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>Clusters</CODE> class has boolean array of size
	 *   <CODE>MAX_CLUSTERS</CODE> that are all set to <CODE>false</CODE>.
	 */
	public Clusters()
	{
		cluster = new boolean[MAX_CLUSTERS];
	}
	
	/**
	 * Sets the <CODE>nextAvailableCluster</CODE> to <CODE>true</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Clusters</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   The <CODE>nextAvailableCluster</CODE> is set to <CODE>true</CODE>.
	 */
	public void addCluster()
	{
		cluster[nextAvailableCluster()] = true;
	}
	
	/**
	 * Sets the element in the <CODE>array</CODE> with matching
	 * <CODE>index</CODE> and cluster number to <CODE>false</CODE>.
	 * @param file
	 *   - the name of file to be accessed
	 * @param position
	 *   -  the position of the cluster in the file to be removed
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Clusters</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   The element with <CODE>index</CODE> matching that of
	 *   the determined cluster number is set to <CODE>false</CODE>.
	 */
	public void removeCluster(FileEntry file, int position)
	{
		//Loop will continue until it reaches the end of the array
		//or if the index i matches that of the cluster number
		for (int i = 0; i < MAX_CLUSTERS; i++)
		{
			try
			{
				//Sets the cluster at index i to false if it matches the
				//cluster number in the DiskClusterList at the given position
				if (((file.getCluster().getClusterNumber(position)) == i)
				  && (cluster[i]) == true)
					cluster[i] = false;
			}
			catch (InvalidPositionException ipe)	{	}
		}	
	}
	
	/**
	 * Returns the next available cluster which is an element having
	 * a <CODE>false</CODE> value.
	 * @return
	 *   the <CODE>int</CODE> number of the next available cluster,
	 *   -1 otherwise (the cluster is full).
	 */
	public int nextAvailableCluster()
	{
		for (int i = 0; i < MAX_CLUSTERS; i++)
			if (cluster[i] == false)
				return i;
		return -1;
	}
}
