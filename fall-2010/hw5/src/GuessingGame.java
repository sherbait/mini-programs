import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Driver class that contains a main method that locates a file of an existing
 * <CODE>BTTree</CODE> object. If the file is found, it is loaded into the
 * program and begins the execution of the game. Otherwise, the user is asked
 * a series of questions to create a new <CODE>BTTree</CODE>. After the game,
 * the <CODE>BTTree</CODE> is saved for future games.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #5 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class GuessingGame
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to the Guessing Game!");
		
		try 
		{
			// Finds and loads an existing BTTree file.
			FileInputStream file = new FileInputStream("tree.obj");
			ObjectInputStream inStream = new ObjectInputStream(file);
			BTTree tree = (BTTree) inStream.readObject();
			// Actual execution of the game.
			executeBTTree(input, tree);
		}
		catch (FileNotFoundException fnfe)
		{	
			// Questions to gather information needed to create a new BTTree.
			System.out.println("\n\nNo previous knowledge file found!");
			System.out.println("\nInitializing a new game...");
			System.out.print("\nEnter a question about an object: ");
			String question = input.nextLine().trim();
			System.out.print("\nEnter a guess if the response is Yes: ");
			String yesGuess = input.nextLine().trim();
			System.out.print("\nEnter a guess if the response is No: ");
			String noGuess = input.nextLine().trim();
			
			BTTree tree = new BTTree(question, yesGuess, noGuess);
			System.out.println("\n\nGame Initialized!");
			// Game is executed.
			executeBTTree(input, tree);
		}
		catch (IOException ioe)		{	}
		catch (ClassNotFoundException cnfe)		{	}
	}
	
	/**
	 * Helper method that executes the game by calling the query method of
	 * the given <CODE>BTTree</CODE>.
	 * @param input
	 *   - <CODE>Scanner</CODE> object to receive user input
	 * @param tree
	 *   - tree to be used to execute the game
	 */
	private static void executeBTTree(Scanner input, BTTree tree)
	{
		String response;
		// Flag to stop the loop
		boolean flag = true;
		// Loop will continue as long as the user wishes to continue the game.
		do
		{
			System.out.println("\n\nThink of an object!");
			// Game is executed
			tree.query();
			// Used to determine if the user has entered "Y" or "N"
			boolean legalInput = false;
			// Loop will go on until the user has entered "Y" or "N"
			do
			{
				System.out.print("\n\nWould you like to play again? ");
				response = input.nextLine().trim();
				// Only a "Y" or "N" response would end this loop
				if (response.equalsIgnoreCase("Y") ||
				  response.equalsIgnoreCase("N"))
					legalInput = true;
			}
			while (!legalInput);
			if (response.equalsIgnoreCase("N"))
				flag = false;
		}
		while (flag);
		
		System.out.println("\n\nSaving my knowledge until next time..." +
		  " Goodbye!");
		try
		{
			// Saving the BTTree into a file for future reference
			FileOutputStream file = new FileOutputStream("tree.obj");
			ObjectOutputStream outStream = new ObjectOutputStream(file);
			outStream.writeObject(tree);
		}
		catch (FileNotFoundException fnfe)		{	}
		catch (IOException ioe)		{	}	
	}
}
