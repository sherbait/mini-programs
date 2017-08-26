/**
 * This <CODE>Simulator</CODE> class contains a static method that will
 * simulate a local DMV office. The office provides different services that
 * are carried out by one or more DMV agents. A separated waiting line is
 * created for each service, and customers will be directed to the
 * corresponding line as they arrive in the office. 
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #4 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class Simulator
{
	/**
	 * Constructor for the <CODE>Simulator</CODE> class.
	 */
	public Simulator()	{	}
	
	/**
	 * Method that performs the simulation. It returns an average waiting
	 * time per customer depending on the values given as parameters.
	 * @param numServices
	 *   - the number of services provided in the simulation
	 * @param numAgents
	 *   - the number of agents
	 * @param arrivalProb
	 *   - the probability that a customer arrives during any given minute
	 * @param maxProcessingTime
	 *   - the maximum time in minutes needed to process a customer's request
	 * @param simMinutes
	 *   - number of minutes to be simulated
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Simulator</CODE> has been instantiated.
	 * @return
	 *   the average waiting time in minutes for each customer before
	 *   being served.
	 */
	public static double run(int numServices, int numAgents,
	  double arrivalProb, int maxProcessingTime, int simMinutes)
	{
		// Error checking of the input parameters.
		if ((numServices < 1 || numServices > 10)
		  || (numAgents < 1 || numAgents > 20)
		  || (arrivalProb < 0.01 || arrivalProb > 0.99)
		  || (maxProcessingTime < 1 || maxProcessingTime > 50)
		  || (simMinutes < 1 || simMinutes > 2400))
			throw new IllegalArgumentException();
		
		// Used to determine the line for each service.
		Queue[] lines = new Queue[numServices];
		// Assigning the lines for each service.
		for (int i = 0; i < numServices; i++)
		{
			Queue line = new Queue();
			lines[i] = line;
		}
		// Used to keep track of the minutes remaining before an
		// agent is available to accept the next customer in line.
		int[] agents = new int[numAgents];
		BooleanSource arrival = new BooleanSource(arrivalProb);
		// Other variables needed to perform the simulation.
		double avgWaitTime;
		int totalWaitTime = 0;
		int customersServed = 0;
		int currentTime;
		int waitingLine;
		int indexOfAvailableAgent;
		
		for (currentTime = 1; currentTime <= simMinutes; currentTime++)
		{
			// EVENT 1: Has a customer arrived?
			if (arrival.occurs())
			{
				Customer person = new Customer();
				person.setArrivalTime(currentTime);
				person.setProcessingTime(randomNumber(maxProcessingTime));
				// Determines which line the customer needs to go
				waitingLine = randomNumber(numServices);
				// Adds the customer to the queue
				lines[waitingLine-1].enqueue(person);
			}
			
			// EVENT 2: Can we take the next customer in line?
			indexOfAvailableAgent = nextAvailableAgent(agents);
			if (indexOfAvailableAgent != -1 && !linesEmpty(lines))
			{
				// Flag to avoid an infinite loop when there are available
				// agents but no one in line.
				boolean flag = false;
				while (!flag)
				{
					// The line an agent will accept the next customer.
					waitingLine = randomNumber(numServices);
					if (!lines[waitingLine-1].isEmpty())
					{
						try
						{
							Customer nextCustomer =
							  (Customer)(lines[waitingLine-1].dequeue());
							totalWaitTime = totalWaitTime +
							  (currentTime - nextCustomer.getArrivalTime());
							agents[indexOfAvailableAgent] = 
							  nextCustomer.getProcessingTime();
						} catch (EmptyQueueException e) 	{	}
						customersServed++;
						flag = true;
					}
				}
			}
				
			//EVENT 3: Process a customer's request for 1 minute
			for (int i = 0; i < agents.length; i++)
				if (agents[i] > 0)
					agents[i]--;
		}
		// Computation of the average waiting time of a customer
		avgWaitTime = (double)totalWaitTime/customersServed;
		return avgWaitTime;
	}
	
	/**
	 * Returns a random number between 1 and the given parameter.
	 * @param number
	 *   - the maximum number this method can generate
	 * @return
	 *   a random number between 1 and the given parameter.
	 */
	private static int randomNumber(int number)
	{
		return ((int)((Math.random() * number) + 1.0));
	}
	
	/**
	 * Finds the next available agent.
	 * @param agents
	 *   - array of agents
	 * @return
	 *   - the index of the next available agent in the array given as
	 *   parameter, otherwise -1 when there is no available agent.
	 */
	private static int nextAvailableAgent(int[] agents)
	{
		for (int i = 0; i < agents.length; i++)
			// 0 is the time left the agent has with a customer
			if (agents[i] == 0)
				return i;
		return -1;
	}
	
	/**
	 * Checks if all lines are empty.
	 * @param lines
	 *   - <CODE>Queue</CODE> array to be checked
	 * @return
	 *   - <CODE>true</CODE> if all lines are empty, otherwise <CODE>false</CODE>.
	 */
	private static boolean linesEmpty(Queue[] lines)
	{
		int numberOfEmptyLine = 0;
		for (int i = 0; i < lines.length; i++)
			if (lines[i].isEmpty())
				numberOfEmptyLine++;
		return (numberOfEmptyLine == lines.length);
	}
}
