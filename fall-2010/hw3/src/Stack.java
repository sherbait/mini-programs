import java.util.EmptyStackException;

/**
 * This <CODE>Stack</CODE> ADT class is used as a last-in/first-out mechanism
 * for <CODE>Token</CODE> objects which makes it capable of storing integers
 * or characters. It uses a <CODE>Token</CODE> array which makes the first
 * element the bottom of the stack that goes to the top as the array index
 * increases.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #3 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class Stack
{
	// Maximum number of elements the stack can hold
	public final int MAX_SIZE = 100;
	// Token array that stores the tokens in the stack
	private Token[] tokenStack;
	// Counts the number of elements currently in the stack
	private int counter;
	
	/**
	 * Constructor for the <CODE>Stack</CODE> class that constructs
	 * an instance of this class.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>Stack</CODE> has a <CODE>Token</CODE> array of
	 *   <CODE>MAX_SIZE</CODE> and has no <CODE>Token</CODE> elements.
	 */
	public Stack()
	{
		tokenStack = new Token[MAX_SIZE];
		counter = -1;
	}
	
	/**
	 * Adds a <CODE>Token</CODE> object on top of this <CODE>Stack</CODE> or
	 * throws an exception if the stack is full.
	 * @param data
	 *   - the <CODE>Token</CODE> object to be placed in this <CODE>Stack</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Stack</CODE> is instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   The <CODE>Token</CODE> object parameter is added to this
	 *   <CODE>Stack</CODE> if it is not full.
	 * @throws FullStackException
	 *   Indicates that this <CODE>Stack</CODE> is full.
	 */
	public void push(Token data) throws FullStackException
	{
		if (this.isFull())
			throw new FullStackException();
		counter++;
		tokenStack[counter] = data;
	}
	
	/**
	 * Returns the <CODE>Token</CODE> object on top of this <CODE>Stack</CODE>.
	 * The said <CODE>Token</CODE> is removed from this <CODE>Stack</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Stack</CODE> is instantiated.
	 * @return
	 *   the <CODE>Token</CODE> object on top of this <CODE>Stack</CODE>.
	 */
	public Token pop()
	{
		if (this.isEmpty())
			throw new EmptyStackException();
		counter--;
		return tokenStack[counter+1];
	}
	
	/**
	 * Returns the <CODE>Token</CODE> object on top of this <CODE>Stack</CODE>
	 * but does not remove it.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Stack</CODE> is instantiated.
	 * @return
	 *   the <CODE>Token</CODE> object on top of this <CODE>Stack</CODE>.
	 */
	public Token peek()
	{
		return tokenStack[counter];
	}
	
	/**
	 * Returns a boolean value that indicates if this <CODE>Stack</CODE>
	 * is empty or not.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Stack</CODE> is instantiated.
	 * @return
	 *   <CODE>true</CODE> if this <CODE>Stack</CODE> is empty,
	 *   <CODE>false</CODE> otherwise.
	 */
	public boolean isEmpty()
	{
		return (counter == -1);
	}
	
	/**
	 * Returns a boolean value that indicates if this <CODE>Stack</CODE>
	 * is full or not. It is full if the number of elements reaches the
	 * <CODE>MAX_SIZE</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Stack</CODE> is instantiated.
	 * @return
	 *   <CODE>true</CODE> if this <CODE>Stack</CODE> is full,
	 *   <CODE>false</CODE> otherwise.
	 */
	public boolean isFull()
	{
		return (counter == MAX_SIZE-1);
	}
	
	/**
	 * Returns an <CODE>int</CODE> value that determines the number of
	 * elements in this <CODE>Stack</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Stack</CODE> is instantiated.
	 * @return
	 *   an integer value of the number of elements in this <CODE>Stack</CODE>.
	 */
	public int size()
	{
		return counter + 1;
	}
}
