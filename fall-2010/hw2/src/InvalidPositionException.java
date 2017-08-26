/**
 * An <CODE>Exception</CODE> class for the <CODE>DiskClusterList</CODE>
 * class that handles an invalid position input.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #2 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class InvalidPositionException extends Exception
{
	InvalidPositionException()
	{
		super(" ERROR: Invalid position number.");
	}
}
