/**
 * Thrown by methods in the <CODE>Stack</CODE> class that indicates the
 * stack is full.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #3 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class FullStackException extends Exception
{
	public FullStackException()
	{
		super("The stack is full.");
	}
}
