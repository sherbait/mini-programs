import java.io.Serializable;

/**
 * This <CODE>BTTree</CODE> class represents the binary taxonomy tree. It
 * contains a reference to a <CODE>BTTNode</CODE> representing the root
 * of the tree.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #5 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class BTTree implements Serializable
{
	// Instance variables
	private BTTNode root;
	private BTTNode left;
	private BTTNode right;
	
	/**
	 * Constructor for the <CODE>BTTree</CODE> class that constructs
	 * instances of this class.
	 * @param question
	 *   - message of the root <CODE>BTTNode</CODE>
	 * @param yesGuess
	 *   - message of the left reference of the root
	 * @param noGuess
	 *   - message of the right reference of the root
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>BTTree</CODE> contains a root with two leaf children.
	 */
	public BTTree(String question, String yesGuess, String noGuess)
	{
		root = new BTTNode(question);
		left = new BTTNode(yesGuess);
		right = new BTTNode(noGuess);
		root.setLeft(left);
		root.setRight(right);
	}
	
	/**
	 * Begins the execution process of the game.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>BTTree</CODE> has been instantiated.
	 */
	public void query()		{	root.query();	}
}
