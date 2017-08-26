/**
 * This <CODE>Token</CODE> class stores information about a given token
 * in the input <CODE>String</CODE> which can either be an operator or
 * an integer operand.<br><br>
 * Valid operators include:<br>
 * + (addition)<br>
 * - (subtraction)<br>
 * * (multiplication)<br>
 * / (division)<br>
 * ^ (exponent)
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #3 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class Token
{
	// Three member variables
	private int value;
	private char operator;
	private boolean isOperator;
	
	/**
	 * Constructor for the <CODE>Token</CODE> class that constructs an
	 * instance of this class.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>Token</CODE> class has boolean <CODE>isOperator</CODE> set
	 *   to <CODE>true</CODE> and char <CODE>operator</CODE> set to the given
	 *   <CODE>String</CODE> parameter if it is a legal operator.<dd>
	 *   This <CODE>Token</CODE> class has boolean <CODE>isOperator</CODE> set
	 *   to <CODE>false</CODE> and int <CODE>value</CODE> set to the given
	 *   <CODE>String</CODE> parameter if it is an integer.
	 */
	public Token(String s)
	{
		if (s.equals("+") || s.equals("-") || s.equals("*") ||
		  s.equals("/") || s.equals("^"))
		{
			isOperator = true;
			operator = s.charAt(0);
		}
		else
		{
			isOperator = false;
			value = Integer.parseInt(s);
		}
	}
	
	/**
	 * Returns a boolean value that indicates if this <CODE>Token</CODE> is an
	 * operator or not.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Token</CODE> is instantiated.
	 * @return
	 *   <CODE>true</CODE> if this <CODE>Token</CODE> is an operator,
	 *   <CODE>false</CODE> otherwise. 
	 */
	public boolean isOperator()
	{
		return isOperator;
	}
	
	/**
	 * Returns an <CODE>int</CODE> value of this <CODE>Token</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Token</CODE> is instantiated.
	 * @return
	 *   an <CODE>int</CODE> value of this <CODE>Token</CODE>.
	 * @throws EmptyVariableException
	 *   Indicates that the <CODE>int</CODE> value of this
	 *   <CODE>Token</CODE> is undefined.
	 */
	public int getValue() throws EmptyVariableException
	{
		if (isOperator == true)
			throw new EmptyVariableException();
		return value;
	}
	
	/**
	 * Returns a <CODE>char</CODE> operator of this <CODE>Token</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Token</CODE> is instantiated.
	 * @return
	 *   a <CODE>char</CODE> operator of this <CODE>Token</CODE>
	 * @throws EmptyVariableException
	 *   Indicates that the <CODE>char</CODE> operator of this
	 *   <CODE>Token</CODE> is undefined.
	 */
	public char getOperator() throws EmptyVariableException
	{
		if (isOperator == false)
			throw new EmptyVariableException();
		return operator;
	}
}
