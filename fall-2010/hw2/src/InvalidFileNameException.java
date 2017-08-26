/**
 * An <CODE>Exception</CODE> class for the <CODE>FileAllocationManager</CODE>
 * driver class that handles an invalid file name input.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #2 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class InvalidFileNameException extends Exception
{
	public InvalidFileNameException()
	{
		super(" ERROR: Invalid file name.");
	}
}
