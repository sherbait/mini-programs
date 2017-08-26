/**
 * A supplement <CODE>Exception</CODE> class for the <CODE>FriendList</CODE>
 * class that handles errors when the list is full.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #1 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA TBA
 *
 */
public class ListFullException extends Exception
{
	/**
	 * Constructor for this <CODE>Exception</CODE> that makes use of a
	 * friend name to be added to the message overriding.
	 * @param name
	 *   - the name of a friend
	 */
	public ListFullException(String name)
	{
		super("ERROR: Friend list is full. " + name + " cannot be added.");
	}
}
