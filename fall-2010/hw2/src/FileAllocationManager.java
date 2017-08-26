import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Driver class that contains the main method and other methods that will
 * extensively use other classes including: <CODE>DiskClusterList,
 * Clusters, FileEntry,</CODE> and <CODE>Exception</CODE> classes. It contains
 * a main menu asking the user various options, along with what to do if the
 * user selects that option.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #2 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class FileAllocationManager
{
	//Maximum number of files the program takes
	public static final int FILE_COUNT = 100;
	//Maximum data size the user can input
	public static final int MAX_DATA_SIZE = 70;
	//Maximum file name size the user can input
	public static final int MAX_FILENAME_SIZE = 30;
	//Counter for the current number of files
	private static int fileCount = 0;
	
	/**
	 * This main method instantiates and initializes the primary variables
	 * to be used by the program. It also controls the program based on user
	 * input and, if necessary, calls on other methods within the class
	 * corresponding to the user input.
	 */
	public static void main(String[] args)
	{
		//FileEntry array to keep track of files,
		//each containing a reference to a DiskClusterList object
		FileEntry[] file = new FileEntry[FILE_COUNT];
		//Clusters object to keep track of clusters created
		Clusters clusterList = new Clusters();
		//Scanner class is used to get user input
		Scanner input = new Scanner(System.in);
		//Initialization of menu option variable
		String choice = "?";
		
		//Instantiates all FileEntry objects in the FileEntry[]
		for (int i = 0; i < FILE_COUNT; i++)
			file[i] = new FileEntry();
		//Loop will not stop until the user chooses to quit the program
		while (!choice.equalsIgnoreCase("Q"))
		{
			//Calls on the menu method to display the main menu options
			choice = mainMenu(input);
			try
			{
				//Calling of different methods based on user input
				if (choice.equalsIgnoreCase("A"))
					appendClusterToTail(input, file, clusterList);
				else if (choice.equalsIgnoreCase("I"))
					insertCluster(input, file, clusterList);
				else if (choice.equalsIgnoreCase("R"))
					removeCluster(input, file, clusterList);
				else if (choice.equalsIgnoreCase("C"))
					containsCluster(input, file, clusterList);
				else if (choice.equalsIgnoreCase("P"))
					printAllClusters(input, file, clusterList);
				else if (choice.equalsIgnoreCase("D"))
					deleteAllClusters(input, file, clusterList);
			}
			catch (InvalidFileNameException ifne)
			{
				System.out.println(ifne.getMessage());
			}
			catch (InvalidDataException ide)
			{
				System.out.println(ide.getMessage());
			}
		}
		System.out.println("Program Terminated.");
	}

	/**
	 * Shows the user a main menu with options to append, insert, remove,
	 * or locate single clusters. Additionally, it has options to display or
	 * delete all clusters in the given file name. It returns a choice
	 * of type <CODE>String</CODE> the user has made.
	 * @param input
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 * @return
	 *   a user input.
	 */
	public static String mainMenu(Scanner input)
	{
		System.out.println("\nA) Append Cluster To Tail");
		System.out.println("I) Insert Cluster At Position");
		System.out.println("R) Remove Cluster");
		System.out.println("C) Contains Cluster");
		System.out.println("P) Print All Clusters");
		System.out.println("D) Delete All Clusters");
		System.out.println("Q) Quit\n");
		System.out.print("Select an Option: ");
		return input.nextLine();
	}
	
	/**
	 * Prompts the user for a file name and data and then calls the 
	 * <CODE>DiskClusterList</CODE> addClusterToTail method. It catches the
	 * exceptions may be thrown by this method and also displays a message
	 * if the cluster was successfully added or if there were any errors.
	 * @param input
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 * @param file
	 *   - <CODE>FileEntry</CODE> array
	 * @param clusterList
	 *   - <CODE>Clusters</CODE> object
	 * @throws InvalidFileNameException
	 *   Indicates the given file name is too short (length = 0)
	 *   or too long (length > <CODE>MAX_FILENAME_SIZE</CODE>).
	 * @throws InvalidDataException
	 *   Indicates the given data is too short (length = 0) or
	 *   too long (length > <CODE>MAX_DATA_SIZE</CODE>).
	 */
	public static void appendClusterToTail(Scanner input, FileEntry[] file,
	  Clusters clusterList) throws InvalidFileNameException, InvalidDataException
	{
		System.out.print("Enter File Name: ");
		String fileName = input.nextLine().trim();
		if (fileName.trim().isEmpty() || (fileName.length() > MAX_FILENAME_SIZE))
			throw new InvalidFileNameException();
		
		System.out.print("Enter Data: ");
		String data = input.nextLine();
		if (data.length() > MAX_DATA_SIZE || data.length() == 0)
			throw new InvalidDataException();
		
		//Checks if the given file name is already in the FileEntry array
		//or not. If it is, it finds the file name then calls the 
		//addClustertoTail method. Otherwise, a new file name is added to the
		//FileEntry array before calling the addClusterToTail method.
		if (!arrayContainsFileName(fileName, file))
		{
			//Global variable fileCount is used as index for the new file name
			file[fileCount].setFileName(fileName.trim());
			file[fileCount].getCluster().addClusterToTail(
			  clusterList.nextAvailableCluster(), data);
			//Updates the number of clusters in the program
			clusterList.addCluster();
			//fileCount increments
			fileCount++;
			System.out.println(" Insertion Successful!");
		}
		else if (arrayContainsFileName(fileName, file))
		{
			for (int i = 0; i < fileCount; i++)
				//Finds the given file name in the FileEntry array
				if (file[i].getFileName().equalsIgnoreCase(fileName))
				{
					file[i].getCluster().addClusterToTail(
					  clusterList.nextAvailableCluster(), data);
					clusterList.addCluster();
					System.out.println(" Insertion Successful!");
				}
		}
	}

	/**
	 * Prompts the user for a file name, data, and position and then calls the 
	 * <CODE>DiskClusterList</CODE> insertCluster method. It catches the
	 * exceptions may be thrown by this method and also displays a message
	 * if the cluster was successfully inserted or if there were any errors.
	 * @param input
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 * @param file
	 *   - <CODE>FileEntry</CODE> array
	 * @param clusterList
	 *   - <CODE>Clusters</CODE> object
	 * @throws InvalidFileNameException
	 * 	 Indicates the given file name is too short (length = 0)
	 *   or too long (length > <CODE>MAX_FILENAME_SIZE</CODE>).
	 * @throws InvalidDataException
	 *   Indicates the given data is too short (length = 0) or
	 *   too long (length > <CODE>MAX_DATA_SIZE</CODE>).
	 */
	public static void insertCluster(Scanner input, FileEntry[] file,
	  Clusters clusterList) throws InvalidFileNameException, InvalidDataException
	{
		System.out.print("Enter File Name: ");
		String fileName = input.nextLine().trim();
		if (fileName.trim().isEmpty() || (fileName.length() > MAX_FILENAME_SIZE))
			throw new InvalidFileNameException();
		
		System.out.print("Enter Data: ");
		String data = input.nextLine();
		if (data.length() > MAX_DATA_SIZE || data.length() == 0)
			throw new InvalidDataException();
		
		try
		{
			System.out.print("Enter Position: ");
			int position = input.nextInt();
			input.nextLine();
			
			//Checks if the given file name is already in the FileEntry array
			//or not. If it is, it finds the file name then calls the
			//insertCluster method. Otherwise, a new file name is added to
			//the FileEntry array before calling the insertCluster method.
			if (!arrayContainsFileName(fileName, file))
			{
				file[fileCount].setFileName(fileName.trim());
				file[fileCount].getCluster().insertCluster(
				  clusterList.nextAvailableCluster(), data, position);
				clusterList.addCluster();
				fileCount++;
				System.out.println(" Insertion Successful!");
			}
			else if (arrayContainsFileName(fileName, file))
			{
				for (int i = 0; i < fileCount; i++)
					if (file[i].getFileName().equalsIgnoreCase(fileName))
					{
						file[i].getCluster().insertCluster(
						  clusterList.nextAvailableCluster(), data, position);
						clusterList.addCluster();
						System.out.println(" Insertion Successful!");
					}
			}
		}
		//Executes when the given position is not a number
		catch (InputMismatchException ime)
		{
			System.out.println(" ERROR: Input invalid.");
			input.nextLine();
		}
		catch (InvalidPositionException ipe)
		{	
			System.out.println(ipe.getMessage());
		}
	}

	/**
	 * Prompts the user for a file name and position and then calls the 
	 * <CODE>DiskClusterList</CODE> removeCluster method. It catches the
	 * exception may be thrown by this method and also displays a message
	 * if the cluster was successfully removed or if there were any errors.
	 * @param input
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 * @param file
	 *   - <CODE>FileEntry</CODE> array
	 * @param clusterList
	 *   - <CODE>Clusters</CODE> object
	 * @throws InvalidFileNameException
	 *   Indicates the given file name is too short (length = 0)
	 *   or too long (length > <CODE>MAX_FILENAME_SIZE</CODE>).
	 */
	public static void removeCluster(Scanner input,
	  FileEntry[] file, Clusters clusterList) throws InvalidFileNameException
	{
		System.out.print("Enter File Name: ");
		String fileName = input.nextLine().trim();
		if (fileName.trim().isEmpty() || (fileName.length() > MAX_FILENAME_SIZE))
			throw new InvalidFileNameException();
		
		try
		{
			System.out.print("Enter Position: ");
			int position = input.nextInt();
			input.nextLine();
			
			//Checks for file name validity
			if (arrayContainsFileName(fileName, file))
			{
				//Finds the file name in the FileEntry array
				for (int i = 0; i < fileCount; i++)
					if (file[i].getFileName().equalsIgnoreCase(fileName))
					{
						//Removes the cluster in the Clusters class
						//to update the nextAvailableCluster
						clusterList.removeCluster(file[i], position);
						//Removes the cluster in the DiskClusterList
						file[i].getCluster().removeCluster(position);
						System.out.println(" Removal Successful!");
					}
			}
			else
				System.out.println(" ERROR: " + fileName
				  + " is NOT in the list of files.");
		}
		catch (InputMismatchException ime)
		{
			System.out.println(" ERROR: Invalid input.");
			input.nextLine();
		}
		catch (InvalidPositionException ipe)
		{
			System.out.println(ipe.getMessage());
		}
	}

	/**
	 * Prompts the user for a file name and cluster number and then calls the 
	 * <CODE>DiskClusterList</CODE> containsCluster method. It catches the
	 * exception may be thrown by this method and also displays a message
	 * if the cluster is in the file or not or if there were any errors.
	 * @param input
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 * @param file
	 *   - <CODE>FileEntry</CODE> array
	 * @param clusterList
	 *   - <CODE>Clusters</CODE> object
	 * @throws InvalidFileNameException
	 *   Indicates the given file name is too short (length = 0)
	 *   or too long (length > <CODE>MAX_FILENAME_SIZE</CODE>).
	 */
	public static void containsCluster(Scanner input,
	  FileEntry[] file, Clusters clusterList) throws InvalidFileNameException
	{
		System.out.print("Enter File Name: ");
		String fileName = input.nextLine().trim();
		if (fileName.trim().isEmpty() || (fileName.length() > MAX_FILENAME_SIZE))
			throw new InvalidFileNameException();
		
		try
		{
			System.out.print("Enter Cluster Number: ");
			int clusterNumber = input.nextInt();
			input.nextLine();
			
			//Checks for file name validity
			if (arrayContainsFileName(fileName, file))
			{
				//Finds the file name in the FileEntry array
				for (int i = 0; i < fileCount; i++)
					if (file[i].getFileName().equalsIgnoreCase(fileName))
					{
						if (file[i].getCluster().containsCluster(clusterNumber))
							System.out.println(fileName + " contains Cluster "
							  + clusterNumber + ".");
						else
							System.out.println(fileName + " does NOT contain "
							  + "Cluster " + clusterNumber + ".");
					}
			}
			else
				System.out.println(" ERROR: " + fileName
				  + " is NOT in the list of files.");
		}
		catch (InputMismatchException ime)
		{
			System.out.println(" ERROR: Invalid input.");
			input.nextLine();
		}
	}

	/**
	 * Prompts the user for a file name and then calls the 
	 * <CODE>DiskClusterList</CODE> printAllClusters method. It throws an
	 * exception if an input data is invalid and also displays a message
	 * if there were any errors.
	 * @param input
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 * @param file
	 *   - <CODE>FileEntry</CODE> array
	 * @param clusterList
	 *   - <CODE>Clusters</CODE> object
	 * @throws InvalidFileNameException
	 *   Indicates the given file name is too short (length = 0)
	 *   or too long (length > <CODE>MAX_FILENAME_SIZE</CODE>).
	 */
	public static void printAllClusters(Scanner input,
	  FileEntry[] file, Clusters clusterList) throws InvalidFileNameException
	{
		System.out.print("Enter File Name: ");
		String fileName = input.nextLine().trim();
		if (fileName.trim().isEmpty() || (fileName.length() > MAX_FILENAME_SIZE))
			throw new InvalidFileNameException();
		
		//Checks for file name validity
		if (arrayContainsFileName(fileName, file))
		{
			System.out.println(fileName + " contents:");
			for (int i = 0; i < fileCount; i++)
				if (file[i].getFileName().equalsIgnoreCase(fileName))
					file[i].getCluster().printAllClusters();
		}
		else
			System.out.println(" ERROR: " + fileName
			  + " is NOT in the list of files.");
	}

	/**
	 * Prompts the user for a file name and then calls the 
	 * <CODE>DiskClusterList</CODE> deleteAllClusters method. It throws an
	 * exception if an input data is invalid and also displays a message
	 * if there were any errors.
	 * @param input
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 * @param file
	 *   - <CODE>FileEntry</CODE> array
	 * @param clusterList
	 *   - <CODE>Clusters</CODE> object
	 * @throws InvalidFileNameException
	 *   Indicates the given file name is too short (length = 0)
	 *   or too long (length > <CODE>MAX_FILENAME_SIZE</CODE>).
	 */
	public static void deleteAllClusters(Scanner input,
	  FileEntry[] file, Clusters clusterList) throws InvalidFileNameException
	{
		System.out.print("Enter File Name: ");
		String fileName = input.nextLine().trim();
		if (fileName.trim().isEmpty() || (fileName.length() > MAX_FILENAME_SIZE))
			throw new InvalidFileNameException();
		
		//Checks if the file name is in the FileEntry array
		if (arrayContainsFileName(fileName, file))
		{
			//Finds the file name in the FileEntry array then accesses
			//the cluster list in that file
			for (int i = 0; i < fileCount; i++)
				if (file[i].getFileName().equalsIgnoreCase(fileName))
				{
					for (int j = 0; j <= file[i].getCluster().clusterLength(); j++)
						clusterList.removeCluster(file[i], j);
					file[i].getCluster().removeAllClusters();
					System.out.println(" Removal Successful!");
				}
		}
		else
			System.out.println(" ERROR: " + fileName
			  + " is NOT in the list of files.");
	}
	
	/**
	 * Checks if the given <CODE>FileEntry</CODE> array contains the file
	 * name parameter.
	 * @param name
	 *   - file name to be located in the <CODE>FileEntry</CODE> array
	 * @param file
	 *   - <CODE>FileEntry</CODE> array
	 * @return
	 *   a value of <CODE>true</CODE> if the given file name is in the
	 *   <CODE>FileEntry</CODE> array, <CODE>false</CODE> otherwise.
	 */
	public static boolean arrayContainsFileName(String name, FileEntry[] file)
	{
		for (int i = 0; i < fileCount; i++)
			if (file[i].getFileName().equalsIgnoreCase(name.trim()))
				return true;
		return false;
	}
}
