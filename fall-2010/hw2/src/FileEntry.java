/**
 * A supplement class for the <CODE>FileAllocationManager</CODE> class
 * that contains a file name of <CODE>String</CODE> value and a reference
 * to a <CODE>DiskClusterList</CODE> object.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #2 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *   
 */
public class FileEntry
{
	private String fileName;
	private DiskClusterList clusterList;
	
	/**
	 * Constructor for the <CODE>FileEntry</CODE> class that
	 * constructs an instance of this class.
	 * <dt><b>Postconditions:</b><dd>
	 *   File name will contain an empty <CODE>String</CODE>.<dd>
	 *   A new <CODE>DiskClusterList</CODE> object will be created.
	 */
	public FileEntry()
	{
		fileName = "";
		clusterList = new DiskClusterList();
	}
	
	/**
	 * Sets the file name of this <CODE>FileEntry</CODE> object to the given
	 * <CODE>String</CODE> name.
	 * @param name
	 *   - the <CODE>String</CODE> name to be set to the <CODE>fileName</CODE>
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>FileEntry</CODE> is instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   File name will be set to the given value.
	 */
	public void setFileName(String name)
	{
		fileName = name;
	}
	
	/**
	 * Returns this <CODE>FileEntry</CODE>'s file name.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>FileEntry</CODE> is instantiated.
	 * @return
	 *   a <CODE>String</CODE> value of this <CODE>FileEntry</CODE>'s file name.
	 */
	public String getFileName()
	{
		return fileName;
	}
	
	/**
	 * Returns this <CODE>FileEntry</CODE>'s corresponding
	 * <CODE>DiskClusterList</CODE> object.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>FileEntry</CODE> is instantiated.
	 * @return
	 *   this <CODE>FileEntry</CODE>'s corresponding
	 *   <CODE>DiskClusterList</CODE> object.
	 */
	public DiskClusterList getCluster()
	{
		return clusterList;
	}
}
