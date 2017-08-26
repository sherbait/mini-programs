import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Driver class that contains a main method and a spam filter. It saves and
 * loads the spam filter as well as display the user interface.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #6 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class SpamDriver implements Serializable
{
	// Instance variables
	private static SpamFilter filter;
	private static final String FILENAME = "SpamFilter.obj";
	private static final float SPAM_RATIO = (float)0.05;
	
	/**
	 * Loads the spam filter on startup and saves it upon completion. It also
	 * contains a main menu that allows the user to check his/her email for
	 * spam mails, and add/remove/check for spam words in a
	 * <CODE>Hashtable</CODE>. 
	 */
	public static void main(String[] args)
	{
		loadFilter();
	
		Scanner input = new Scanner(System.in);
		String choice = "?";
		while (!choice.equalsIgnoreCase("Q"))
		{
			// Main menu options
			System.out.println("C) Check emails");
			System.out.println("I) Insert word");
			System.out.println("R) Remove word");
			System.out.println("S) Search for word");
			System.out.println("Q) Quit program");
			System.out.print("\nChoice> ");
			choice = input.nextLine();
			
			// OPTION C: User wants to check his/her emails for spam mails
			if (choice.equalsIgnoreCase("C"))
			{
				System.out.print("File that contains email info: ");
				String fileName = input.nextLine();
				
				try
				{
					FileReader file = new FileReader(fileName);
					BufferedReader inStream = new BufferedReader(file);
					// fileContents is like a StringBuilder
					String fileContents = "", readLine = "";
					// Reads every line in the user given file and appends it
					// to fileContents.
					while ((readLine = inStream.readLine()) != null)
						fileContents = fileContents + readLine + "\n";
					inStream.close();
					
					// Every line in fileContents is regarded as 1 email
					String[] emails = fileContents.trim().split("\n");
					// The email parts (e.g. recipient, author, etc.)
					// are determined using this variable.
					String[] perEmailData = null;
					// An asterisk(*) labels a mail as spam
					char[] spamEmailSymbol = new char[emails.length];
					for (int i = 0; i < emails.length; i++)
					{
						perEmailData = emails[i].split("\t");
						// EmailInfo instance for every mail
						EmailInfo perEmail = new EmailInfo();
						perEmail.setTo(perEmailData[0]);
						perEmail.setFrom(perEmailData[1]);
						perEmail.setDate(perEmailData[2]);
						perEmail.setSubject(perEmailData[3]);
						
						file = new FileReader(perEmailData[4]);
						inStream = new BufferedReader(file);
						// message is like a StringBuilder
						String message = "", perLine = "";
						// Reads a line one at a time from the file associated
						// with the current email, then appends it into message
						while ( (perLine = inStream.readLine()) != null)
							message = message + perLine + " ";
						inStream.close();
						
						perEmail.setMessage(message.trim());
						if (filter.checkEmail(perEmail.getMessage())
						  >= SPAM_RATIO)
							spamEmailSymbol[i] = '*';
						else
							spamEmailSymbol[i] = ' ';
					}
					// Tabulated output
					System.out.println("\nInbox - spam marked with a *");
					System.out.printf("  %-10s %-10s %-10s %-10s %-10s",
					  "To", "From", "Date", "Subject", "Filename");
					System.out.printf("\n  %s %s %s %s %s", "----------",
					  "----------", "----------", "----------", "----------");
					for (int i = 0; i < emails.length; i++)
					{
						perEmailData = emails[i].split("\t");
						System.out.printf("\n%c %-10.10s %-10.10s %-10.10s" +
						  " %-10.10s %-10.10s", spamEmailSymbol[i],
						  perEmailData[0], perEmailData[1], perEmailData[2],
						  perEmailData[3], perEmailData[4]);
					}
					System.out.println("\n");
				}
				catch (FileNotFoundException fnfe)
				{
					System.out.println("ERROR: File not found.\n");
				}
				catch (IOException ioe)		{	}
			}
			// OPTION I: User wants to insert a word into the hash table
			else if (choice.equalsIgnoreCase("I"))
			{
				System.out.print("Word to insert: ");
				String word = input.nextLine();
				filter.insert(word);
				System.out.println(word + " added to Hashtable!\n");
			}
			// OPTION R: User wants to remove a word from the hash table
			else if (choice.equalsIgnoreCase("R"))
			{
				System.out.print("Word to remove: ");
				String word = input.nextLine();
				try
				{
					filter.remove(word);
					System.out.println(word + " removed from Hashtable.\n");
				}
				catch (ElementNotFoundException e)
				{
					System.out.println(word + " not found in Hashtable.\n");
				}
			}
			// OPTION S: User wants to search for a word in the hash table
			else if (choice.equalsIgnoreCase("S"))
			{
				System.out.print("Word to search for: ");
				String word = input.nextLine();
				if (filter.isBadWord(word))
					System.out.println(word + " is in Hashtable.\n");
				else
					System.out.println(word + " is not in Hashtable.\n");
			}
			// OPTION Q: User wants to quit the program
			else if (choice.equalsIgnoreCase("Q"))		{	}
			// Error checking for the user input
			else	{	System.out.println("ERROR: Wrong input.\n");	}	
		}
		System.out.println("\nProgram terminated successfully.");
		
		saveFilter();
	}
	
	/**
	 * Loads the serialized object stored in <CODE>FILENAME</CODE> and stores
	 * in into <CODE>filter</CODE>. If no file named <CODE>FILENAME</CODE> is
	 * found, a new <CODE>SpamFilter</CODE> instance will be created. 
	 */
	private static void loadFilter()
	{
		try
		{
			FileInputStream file = new FileInputStream(FILENAME);
			ObjectInputStream inStream = new ObjectInputStream(file);
			filter = (SpamFilter)inStream.readObject();
			file.close();
		}
		catch (FileNotFoundException fnfe)
		{
			filter = new SpamFilter();
		}
		catch (IOException ioe)					{	}
		catch (ClassNotFoundException cnfe)		{	}
	}
	
	/**
	 * Saves filter into the file named <CODE>FILENAME</CODE>.
	 */
	private static void saveFilter()
	{
		try
		{
			FileOutputStream file = new FileOutputStream(FILENAME);
			ObjectOutputStream outStream = new ObjectOutputStream(file);
			outStream.writeObject(filter);
			outStream.close();
		}
		catch (FileNotFoundException fnfe)	{	}
		catch (IOException ioe)				{	}
	}
}
