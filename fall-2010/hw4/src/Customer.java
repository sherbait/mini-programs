/**
 * This <CODE>Customer</CODE> class contains two instance variables of type
 * <CODE>int</CODE>: "arrivalTime" (the time the customer arrives on a line)
 * and "processingTime" (the time to process a customer's request). Both of
 * these times are represented in minutes.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #4 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class Customer
{
	// Instance variables represented in minutes
	private int arrivalTime;
	private int processingTime;
	
	/**
	 * Constructor for the <CODE>Customer</CODE> class that constructs
	 * an instance of this class.
	 * <dt><b>Postconditions:</b><dd>
	 *   This <CODE>Customer</CODE> object has an arrivalTime of 0
	 *   and a processingTime of 0.
	 */
	public Customer()
	{
		arrivalTime = 0;
		processingTime = 0;
	}
	
	/**
	 * Sets the arrival time of the customer.
	 * @param time
	 *   - int value of when the customer arrived
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Customer</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>Customer</CODE> will have an arrivalTime value
	 *   given by the parameter.
	 */
	public void setArrivalTime(int time)
	{
		arrivalTime = time;
	}
	
	/**
	 * Sets the processing time the customer needs for his/her request.
	 * @param time
	 *   - int value of the amount of time the customer needs
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Customer</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>Customer</CODE> will have a processinTime value
	 *   given by the parameter.
	 */
	public void setProcessingTime(int time)
	{
		processingTime = time;
	}
	
	/**
	 * Returns the arrivalTime.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Customer</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>Customer</CODE> will not be modified.
	 * @return
	 *   the arrivalTime
	 */
	public int getArrivalTime()
	{
		return arrivalTime;
	}
	
	/**
	 * Returns the processingTime.
	 * <dt><b>Precondition:</b><dd>
	 *   This <CODE>Customer</CODE> has been instantiated.
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>Customer</CODE> will not be modified.
	 * @return
	 *   the processingTime
	 */
	public int getProcessingTime()
	{
		return processingTime;
	}
}
