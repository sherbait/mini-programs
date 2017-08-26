/**
 * A supplement <CODE>Exception</CODE> class for the <CODE>FriendList</CODE>
 * class that handles any duplicate name errors in the list.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #1 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA TBA
 *
 */
public class DuplicateNameException extends Exception
{	
	/**
	 * Constructor for this <CODE>Exception</CODE> that makes use of a
	 * friend name to be added to the message overriding.
	 * @param name
	 *   - the name of a friend already in the list
	 */
	public DuplicateNameException(String name)
	{
		super("ERROR: " + name + " is already in this list.");
	}
}
