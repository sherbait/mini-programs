/**
 * A supplement <CODE>Exception</CODE> class for the <CODE>FriendList</CODE>
 * class that handles any illegal input.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #1 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA TBA
 *
 */
public class IllegalArgumentException extends Exception
{
	private String message = "";
	
	/**
	 * Primary constructor for this <CODE>Exception</CODE>.
	 */
	public IllegalArgumentException()
	{
		super();
	}
	
	/**
	 * Constructor for this <CODE>Exception</CODE> that makes use of a
	 * friend name to be added to the message overriding.
	 * @param name
	 *   - the name of a friend either too long or too short in size
	 */
	public IllegalArgumentException(String name)
	{
		if (name.length() == 0)
			message = "ERROR: Entered name is too short.";
		else
			message = "ERROR: Entered name is too long.";
	}
	
	/**
	 * Returns the message created within the class.
	 */
	public String getMessage()
	{
		return message;
	}
}
