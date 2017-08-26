import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Driver class that contains a main method that prompts the user for
 * simulation parameters (numServices, numAgents, arrivalProb,
 * maxProcessingTIme, simMinutes) and then simulates the waiting lines
 * and prints the average waiting time.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #4 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class Analyzer
{
	/**
	 * This main method instantiates and initializes the primary variables
	 * to be used by the program. It also controls the program based on user
	 * input and calls a method within this class to perform the chosen option.
	 */
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		String choice = "?";
		
		while (!choice.equalsIgnoreCase("Q"))
		{
			choice = menu(input);
			if (choice.equalsIgnoreCase("R"))
				runSimulation(input);
			else if (choice.equalsIgnoreCase("P"))
				printResults();
		}
		System.out.println("\nProgram terminated.");
	}
	
	/**
	 * Shows the user a main menu with options to run a simulation, print
	 * a sample table of results, or quit the program. It returns a choice
	 * of type <CODE>String</CODE> the user has made.
	 * @param in
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 * @return
	 *   a user input.
	 */
	public static String menu(Scanner in)
	{
		System.out.println("\nChoose an option from below:");
		System.out.println("R) Run a Simulation");
		System.out.println("P) Print Result Tables");
		System.out.println("Q) Quit");
		System.out.print(">>");
		return (in.nextLine().trim());
	}
	
	/**
	 * Takes in five simulation parameters from the user to be used to run
	 * the simulation. If user input is valid, it shows the average waiting
	 * time a customer in the simulation has. It also catches any exceptions
	 * thrown during runtime or thrown by the <CODE>Simulator</CODE> class. 
	 * @param in
	 *   - <CODE>Scanner</CODE> class variable used to take user input
	 */
	public static void runSimulation(Scanner in)
	{
		int numServices = 0, numAgents = 0, maxProcessingTime = 0, simMinutes = 0;
		double arrivalProb = 0.0;
				
		System.out.println("\nEnter FIVE values (separated by spaces)" +
		  " in this order:");
		System.out.println("number of services (1-10)");
		System.out.println("number of agents (1-20)");
		System.out.println("arrival probability of a customer (0.01-0.99)");
		System.out.println("maximum minutes to serve a customer (1-50)");
		System.out.println("number of simulation minutes (1-2400)");
		System.out.print(">>");
		StringTokenizer parameter = new StringTokenizer(in.nextLine());
		
		try
		{
			numServices = Integer.parseInt(parameter.nextToken());
			numAgents = Integer.parseInt(parameter.nextToken());
			arrivalProb = Double.parseDouble(parameter.nextToken());
			maxProcessingTime = Integer.parseInt(parameter.nextToken());
			simMinutes = Integer.parseInt(parameter.nextToken());
			// Checks if the user made more the 5 input values
			if (parameter.hasMoreTokens())
				throw new IllegalArgumentException();
			
			Simulator simulation = new Simulator();
			double avgWaitTime = simulation.run(numServices, numAgents,
			  arrivalProb, maxProcessingTime, simMinutes);
			
			// Used to format the avgWaitTime to have 1 decimal place
			DecimalFormat oneDecimal = new DecimalFormat("#.#");
			System.out.println("\nThe average waiting time is " +
			  oneDecimal.format(avgWaitTime) + " minutes.");
		}
		catch (NumberFormatException nfe)
		{
			System.out.println("\nInvalid format. Simulation cannot run.");
		}
		catch (IllegalArgumentException iae)
		{
			System.out.println();
			if (numServices < 1 || numServices > 10)
				System.out.println("Entered number of services is invalid.");
			if (numAgents < 1 || numAgents > 20)
				System.out.println("Entered number of agents is invalid.");
			if (arrivalProb < 0.01 || arrivalProb > 0.99)
				System.out.println("Entered probability is invalid.");
			if (maxProcessingTime < 1 || maxProcessingTime > 50)
				System.out.println("Entered processing time is invalid.");
			if (simMinutes < 1 || simMinutes > 2400)
				System.out.println("Entered simulation minutes is invalid.");
			if (parameter.hasMoreTokens())
				System.out.println("Entered parameters exceed five entries.");
			System.out.println("Simulation cannot run.");
		}
	}
	
	/**
	 * Prints 2 tables representing different agents, services, and
	 * simulation time quantities.
	 */
	public static void printResults()
	{
		int[] numAgents = {1, 2, 4, 8, 10, 12};
		int[] numServices = {2, 5, 10};
		
		Simulator simulation = new Simulator();
		double[][] results = new double[6][3];
		DecimalFormat oneDecimal = new DecimalFormat("#.#");
		
		// Runs the simulation 18 times and puts the resulting
		// avgWaitTimes into a matrix
		for (int i = 0; i < numAgents.length; i++)
			for (int j = 0; j < numServices.length; j++)
				results[i][j] = simulation.run(numServices[j],
				  numAgents[i], 0.71, 15, 1200);
		
		// Prints the table
		System.out.println("\n\t\tAverage waiting time");
		System.out.println("\t\t  (in 1200 minutes)");
		System.out.println("\nNumber of |\t\tNumber of Services");
		System.out.println(" Agents\t  |\t2\t\t5\t\t10");
		System.out.println("========================================================");
		for (int i = 0, j = 0; i < numAgents.length; i++)
		{
			System.out.println("   " + numAgents[i] + "\t  |\t" +
			  oneDecimal.format(results[i][j]) + "\t|\t" +
			  oneDecimal.format(results[i][j+1]) + "\t|\t" +
			  oneDecimal.format(results[i][j+2]));
		}
		
		// Same as above for-loop but with a simulation time of 2400
		// minutes instead of 1200
		for (int i = 0; i < numAgents.length; i++)
			for (int j = 0; j < numServices.length; j++)
				results[i][j] = simulation.run(numServices[j],
				  numAgents[i], 0.71, 15, 2400);
		
		System.out.println("\n\t\tAverage waiting time");
		System.out.println("\t\t  (in 2400 minutes)");
		System.out.println("\nNumber of |\t\tNumber of Services");
		System.out.println(" Agents\t  |\t2\t\t5\t\t10");
		System.out.println("========================================================");
		for (int i = 0, j = 0; i < numAgents.length; i++)
		{
			System.out.println("   " + numAgents[i] + "\t  |\t" +
			  oneDecimal.format(results[i][j]) + "\t|\t" +
			  oneDecimal.format(results[i][j+1]) + "\t|\t" +
			  oneDecimal.format(results[i][j+2]));
		}
	}
}
