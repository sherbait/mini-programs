/**
 * This <CODE>BooleanSource</CODE> class provides a random boolean value.
 * 
 * @author Dinia Gepte<br>SBU ID: 107092681
 * <dt><b>Assignment:</b><dd>
 *   Homework #4 in CSE 214
 * <dt><b>Section:</b><dd>
 *   R01, TA Supriya Garg
 *
 */
public class BooleanSource
{
	// Instance variable
	private double probability;
	
	/**
	 * Constructor for the <CODE>BooleanSource</CODE> class that constructs
	 * an instance of this class.
	 * @param prob
	 *   - the number to be set as the probability
	 * <dt><b>Postcondition:</b><dd>
	 *   This <CODE>BooleanSource</CODE> object will have a probability
	 *   based on the given parameter.
	 * @throws IllegalArgumentException
	 *   Indicates that the parameter given is less than 0.0 or greater than 1.0
	 */
	public BooleanSource(double prob) throws IllegalArgumentException
	{
		if (prob < 0.0 || prob > 1.0)
			throw new IllegalArgumentException();
		probability = prob;
	}
	
	public boolean occurs()
	{
		return (Math.random() < probability);
	}
}
