import java.io.PrintStream;
import java.util.Scanner;

/**
 * Driver class that contains the main method and other methods that will make
 * use of the <CODE>FriendList</CODE> class extensively. It contains a main
 * menu asking the user to add, remove, find, list, compare, combine, or
 * intersect friends in two <CODE>FriendList</CODE> objects.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #1 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA TBA
 *
 */
public class FriendDriver 
{
	/**
	 * This main method instantiates and initializes the primary variables
	 * to be used by the program. It also controls the program based on user
	 * input and, if necessary, calls on other methods within the class
	 * corresponding to the user input.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		//Scanner class is used to get user input
		Scanner input = new Scanner(System.in);
		PrintStream out = System.out;
		//2 FriendList objects instantiation
		FriendList list = new FriendList();
		FriendList list2 = new FriendList();
		//Initialization of friend name
		String name = "";
		//Initialization of menu option variable
		String choice = "?";
		
		//Loop will not stop until the user chooses to quit the program
		while (!choice.equalsIgnoreCase("Q"))
		{
			//Calls on the menu method to display the main menu options
			choice = menu(input, out, choice);
			//Calling of different methods based on user input
			if (choice.equalsIgnoreCase("A"))
				addFriend(input, out, list, name);
			else if (choice.equalsIgnoreCase("F"))
				findFriend(input, out, list, name);
			else if (choice.equalsIgnoreCase("L"))
				displayFriends(out, list);
			else if (choice.equalsIgnoreCase("R"))
				removeFriend(input, out, list, name);
			else if (choice.equalsIgnoreCase("A2"))
				//Same addFriend method is called but
				//different FriendList variable is used
				addFriend(input, out, list2, name);
			else if (choice.equalsIgnoreCase("F2"))
				findFriend(input, out, list2, name);
			else if (choice.equalsIgnoreCase("L2"))
				displayFriends(out, list2);
			else if (choice.equalsIgnoreCase("R2"))
				removeFriend(input, out, list2, name);
			else if (choice.equalsIgnoreCase("E"))
				equalityTestBetweenLists(out, list, list2);
			else if (choice.equalsIgnoreCase("I"))
				intersectionOfLists(out, list, list2);
			else if (choice.equalsIgnoreCase("U"))
				unionOfLists(out, list, list2);
		}
		out.println("Program closed.");
	}

	/**
	 * Shows the user a main menu with options to add, find, display, remove,
	 * compare, combine, or intersect two friend lists. It returns a choice
	 * of type <CODE>String</CODE> the user has made.
	 * @param in
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 * @param out
	 *   - <CODE>PrintStream</CODE> class variable used to display texts
	 *   to the user 
	 * @param choice
	 *   - user input
	 * @return
	 *   a user input
	 */
	public static String menu(Scanner in, PrintStream out, String choice)
	{
		out.println("\nA) Add a friend to the list");
		out.println("F) Find a friend in the list");
		out.println("L) Display all friends in the list");
		out.println("R) Remove a friend from the list");
		out.println("\nA2) Add a friend to List 2");
		out.println("F2) Find a friend in List 2");
		out.println("L2) Display all friends in List 2");
		out.println("R2) Remove a friend from List 2");
		out.println("\nE) Tests if List 1 and List 2 are Equal");
		out.println("I) Displayes the Intersection of List 1 and List 2");
		out.println("U) Displays the Union of List 1 and List 2");
		out.println("Q) Quit the program");
		out.print("\nEnter your choice: ");
		return choice = in.nextLine();
	}
	
	/**
	 * Prompts the user for a friend name and then calls the 
	 * <CODE>FriendList</CODE> addFriend method. It catches the exceptions
	 * may be thrown by <CODE>addFriend()</CODE>. It also displays a message
	 * if the name was successfully added or if there were any errors. 
	 * @param in
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 * @param out
	 *   - <CODE>PrintStream</CODE> class variable used to display texts
	 *   to the user
	 * @param list
	 *   - <CODE>FriendList</CODE> object
	 * @param name
	 *   - the name of a friend given by the user
	 */
	public static void addFriend(Scanner in, PrintStream out,
	  FriendList list, String name)
	{
		out.print("Enter a name: ");
		name = in.nextLine();
		try
		{
			list.addFriend(name);
			out.println(name + " is added to this list.\n");
		}
		catch (IllegalArgumentException iae)
		{
			out.println(iae.getMessage() + "\n");
		}
		catch (DuplicateNameException dne)
		{
			out.println(dne.getMessage() + "\n");
		}
		catch (ListFullException lfe)
		{
			out.println(lfe.getMessage() + "\n");
		}
	}
	
	/**
	 * Prompts the user for a friend name and then calls the 
	 * <CODE>FriendList</CODE> contain method. It catches the exception
	 * may be thrown by <CODE>contains()</CODE>. It also displays a message
	 * if the name was found, if the name was not found, or if an error
	 * occurred.
	 * @param in
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 * @param out
	 *   - <CODE>PrintStream</CODE> class variable used to display texts
	 *   to the user
	 * @param list
	 *   - <CODE>FriendList</CODE> object
	 * @param name
	 *   - the name of a friend given by the user
	 */
	public static void findFriend(Scanner in, PrintStream out,
	  FriendList list, String name)
	{
		out.print("Enter a name: ");
		name = in.nextLine();
		try
		{
			if (list.contains(name))
				out.println(name + " is in this list.\n");
			else
				out.println(name + " is not in this list.\n");
		}
		catch (IllegalArgumentException iae)
		{
			out.println(iae.getMessage() + "\n");
		}
	}
	
	/**
	 * Displays the number of friends and the list of friends in the list.
	 * @param out
	 *   - <CODE>PrintStream</CODE> class variable used to display texts
	 *   to the user
	 * @param list
	 *   - <CODE>FriendList</CODE> object
	 */
	public static void displayFriends(PrintStream out, FriendList list)
	{
		out.println("This list has " + list.numFriends() + " friend(s) in it.");
		if (list.numFriends() > 0)
			out.println("List: " + list.toString() + "\n");
	}

	/**
	 * Prompts the user for a friend name and then calls the 
	 * <CODE>FriendList</CODE> removeFriend method. It catches the exception
	 * may be thrown by <CODE>removeFriend()</CODE>. It also displays a message
	 * if the name was successfully removed, if the name is not in the list,
	 * or if any error occurred.
	 * @param in
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 * @param out
	 *   - <CODE>PrintStream</CODE> class variable used to display texts
	 *   to the user
	 * @param list
	 *   - <CODE>FriendList</CODE> object
	 * @param name
	 *   - the name of a friend given by the user
	 */
	public static void removeFriend(Scanner in, PrintStream out,
	  FriendList list, String name)
	{
		out.print("Enter a name: ");
		name = in.nextLine();
		try
		{
			if (list.removeFriend(name))
				out.println(name + " is removed from this list.\n");
			else
				out.println("ERROR: " + name + " is not in this list.\n");
		}
		catch (IllegalArgumentException iae)
		{
			out.println(iae.getMessage() + "\n");
		}
	}
	
	/**
	 * Tests if the two <CODE>FriendList</CODE> objects are equal in terms
	 * of friend list size and friend list names. It displays a message
	 * indicating the equality of the lists.
	 * @param out
	 *   - <CODE>PrintStream</CODE> class variable used to display texts
	 *   to the user
	 * @param list1
	 *   - <CODE>FriendList</CODE> object
	 * @param list2
	 *   - <CODE>FriendList</CODE> object
	 */
	public static void equalityTestBetweenLists(PrintStream out, 
	  FriendList list1, FriendList list2)
	{
		if (list1.equals(list2))
			out.println("List 1 and List 2 are equal.");
		else
			out.println("List 1 and List 2 are NOT equal.");
	}
	
	/**
	 * Creates a new <CODE>FriendList</CODE> object and calls
	 * <CODE>intersect()</CODE> from the <CODE>FriendList</CODE> class
	 * to show the user the list of friends common to both lists.
	 * @param out
	 *   - <CODE>PrintStream</CODE> class variable used to display texts
	 *   to the user
	 * @param list1
	 *   - <CODE>FriendList</CODE> object
	 * @param list2
	 *   - <CODE>FriendList</CODE> object
	 */
	public static void intersectionOfLists(PrintStream out,
	  FriendList list1, FriendList list2)
	{
		try
		{
			FriendList newList = FriendList.intersect(list1, list2);
			out.println("The List of friend(s) common to both:");
			out.println(newList.toString() + "\n");
		}
		catch (IllegalArgumentException iae)	{ }
	}
	
	/**
	 * Creates a new <CODE>FriendList</CODE> object and calls
	 * <CODE>union()</CODE> from the <CODE>FriendList</CODE> class
	 * to show the user the list of friends combined.
	 * @param out
	 *   - <CODE>PrintStream</CODE> class variable used to display texts
	 *   to the user
	 * @param list1
	 *   - <CODE>FriendList</CODE> object
	 * @param list2
	 *   - <CODE>FriendList</CODE> object
	 */
	public static void unionOfLists(PrintStream out,
	  FriendList list1, FriendList list2)
	{
		try
		{
			FriendList newList = FriendList.union(list1, list2);
			out.println("The Union of the two List of Friends:");
			out.println(newList.toString() + "\n");
		}
		catch (IllegalArgumentException iae)	{ }
	}
}