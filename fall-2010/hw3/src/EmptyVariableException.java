/**
 * Thrown by methods in the <CODE>Token</CODE> class that indicates the
 * variable trying to be accessed is undefined.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #3 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class EmptyVariableException extends Exception
{
	public EmptyVariableException()
	{
		super("This element is empty.");
	}
}
