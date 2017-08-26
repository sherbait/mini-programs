import java.util.Vector;

/**
 * This <CODE>Queue</CODE> class is a subclass of <CODE>Vector</CODE>.
 * It holds items of type <CODE>Object</CODE>. 
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #4 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class Queue extends Vector 
{
	/**
	 * Constructor for the <CODE>Queue</CODE> class that constructs
	 * an instance of this class.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>Queue</CODE> inherits the instance variables of the
	 *   <CODE>Vector</CODE> class.
	 */
	public Queue()
	{
		super();
	}
	
	/**
	 * Overrides the <CODE>isEmpty()</CODE> method in the <CODE>Vector</CODE>
	 * parent class.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Queue</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>Queue</CODE> will not be modified.
	 * @return
	 *   <CODE>true</CODE> if this object is empty, otherwise <CODE>false</CODE>.
	 */
	public boolean isEmpty()
	{
		return super.isEmpty();
	}
	
	/**
	 * Adds an object at the end of this <CODE>Queue</CODE>.
	 * @param obj
	 *   - element to be added
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Queue</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>Queue</CODE> will have an object added to its rear.
	 */
	public void enqueue(Object obj)
	{
		super.addElement(obj);
	}
	
	/**
	 * Removes an object at the beginning of this <CODE>Queue</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Queue</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   The first element will be removed from this <CODE>Queue</CODE>.
	 * @throws EmptyQueueException
	 *   Indicates that this <CODE>Queue</CODE> is empty.
	 */
	public Object dequeue() throws EmptyQueueException
	{
		if (isEmpty())
			throw new EmptyQueueException();
		return super.remove(0);
	}
	
	/**
	 * Returns the number of elements in this <CODE>Queue</CODE>.
	 */
	public int size()
	{
		return super.size();
	}
}
