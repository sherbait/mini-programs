import java.io.Serializable;
import java.util.Scanner;

/**
 * This <CODE>BTTNode</CODE> class represents a node in a binary taxonomy tree.
 * It contains references to the left and right children as well as a
 * <CODE>String</CODE> variable that represents the computer's question
 * or guess.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #5 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class BTTNode implements Serializable
{
	// Instance Variables
	private String message;
	private BTTNode left;
	private BTTNode right;
	
	/**
	 * Constructor for the <CODE>BTTNode</CODE> class that constructs
	 * the instances of this class.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> will have left and right 
	 *   <CODE>BTTNode</CODE> references as well as a <CODE>String</CODE>
	 *   message representing this node.
	 */
	public BTTNode(String s)
	{
		message = s;
		left = null;
		right = null;
	}
	
	/**
	 * Sets the left <CODE>BTTNode</CODE> reference of this object.
	 * @param newLeft
	 *   - <CODE>BTTNode</CODE> to be set as the left node
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> will have a left node reference as
	 *   the parameter.
	 */
	public void setLeft(BTTNode newLeft)	{	left = newLeft;	}
	
	/**
	 * Sets the right <CODE>BTTNode</CODE> reference of this object.
	 * @param newRight
	 *   - <CODE>BTTNode</CODE> to be set as the right node
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> will have a right node reference as
	 *   the parameter.
	 */
	public void setRight(BTTNode newRight)	{	right = newRight;	}
	
	/** 
	 * Sets the message of this object.
	 * @param newMessage
	 *   - <CODE>String</CODE> value to be set as the message
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> will have a message as the parameter.
	 */
	public void setMessage(String newMessage)	{	message = newMessage;	}
	
	/**
	 * Returns the left <CODE>BTTNode</CODE> reference.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> will not be modified.
	 * @return
	 *   the left <CODE>BTTNode</CODE> reference.
	 */
	public BTTNode getLeft()	{	return left;	}
	
	/**
	 * Returns the right <CODE>BTTNode</CODE> reference.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> will not be modified.
	 * @return
	 *   the right <CODE>BTTNode</CODE> reference.
	 */
	public BTTNode getRight()	{	return right;	}
	
	/**
	 * Returns the message of this <CODE>BTTNode</CODE>.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> will not be modified.
	 * @return
	 *   the message of this <CODE>BTTNode</CODE>.
	 */
	public String getMessage()	{	return message;	}
	
	/**
	 * Determines whether or not this <CODE>BTTNode</CODE> is a leaf node.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> will not be modified.
	 * @return
	 *   <CODE>true</CODE> if this <CODE>BTTNode</CODE> is a leaf node,
	 *   <CODE>false</CODE> otherwise.
	 */
	public boolean isLeaf()		{	return (left == null);	}
	
	/**
	 * Handles all of the user interaction with the program during the course
	 * of the game. It performs different actions depending on whether or not
	 * this node is a leaf node. If this is not a leaf node, this node's
	 * message will be printed as a yes or no question. If the answer is yes,
	 * the query of the left reference will be called, otherwise the right
	 * reference will be. If this is a leaf node, a question will be asked.
	 * If the answer is yes, the computer has won the game, otherwise the
	 * tree will be updated so that the computer can now distinguish between
	 * its guess and what the user was thinking of.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>BTTNode</CODE> has been instantiated.
	 */
	public void query()
	{
		Scanner input = new Scanner(System.in);
		String response;
		
		if (!isLeaf())
		{
			// Used to determine if the user has entered "Y" or "N"
			boolean legalInput = false;
			// Loop will go on until the user has entered "Y" or "N"
			do
			{
				System.out.print("\n" + message + " ");
				response = input.nextLine().trim();
				
				if (response.equalsIgnoreCase("Y"))
				{
					left.query();
					legalInput = true; 
				}	
				else if (response.equalsIgnoreCase("N"))
				{
					right.query();
					legalInput = true;
				}	
			}
			while (!legalInput);
		}
		else
		{
			// Used to determine if the user has entered "Y" or "N"
			boolean legalInput = false;
			// Loop will go on until the user has entered "Y" or "N"
			do
			{
				System.out.print("\nAre you thinking of a(n) " + message + "? ");
				response = input.nextLine().trim();
				
				// Computer guessed right
				if (response.equalsIgnoreCase("Y"))
				{
					System.out.println("\nComputer wins!");
					legalInput = true;
				}
				// Computer was wrong
				else if (response.equalsIgnoreCase("N"))
				{
					System.out.print("\nYou win! What were you thinking of? ");
					String object = input.nextLine().trim();
					
					System.out.print("\nPlease enter a question to " +
					  "distinguish a(n) " + message + " from a(n) " +
					  object + ": ");
					String question = input.nextLine().trim();
					
					System.out.print("\nIf you were thinking of a(n) " +
					  object + ", what would the answer to that question be? ");
					String anotherResponse = input.nextLine().trim();
					
					// Used to determine if the user has entered "Y" or "N"
					boolean legalInput2 = false;
					// Loop will go on until the user has entered "Y" or "N"
					do
					{
						// The object that would have an answer of yes
						// will be the left child. This node's message
						// will be the right child.
						if (anotherResponse.equalsIgnoreCase("Y"))
						{
							left = new BTTNode(object);
							right = new BTTNode(message);
							legalInput2 = true;
						}
						// The object that would have an answer of no
						// will be the right child. This node's message
						// will be the left child.
						else if (anotherResponse.equalsIgnoreCase("N"))
						{
							left = new BTTNode(message);
							right = new BTTNode(object);
							legalInput2 = true;
						}
					}
					while (!legalInput2);
					
					// This node's message will be changed to a question
					// instead of an object.
					message = question;
					System.out.println("\nThank you! My knowledge has been" +
					  " increased.");
					// To get out of the loop.
					legalInput = true;
				}	
			}
			while (!legalInput);
		}
	}
}
