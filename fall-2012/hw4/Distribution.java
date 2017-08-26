/**
 * This class describes a normal distribution with mean and variance.
 * It creates N normally distributed values.
 * 
 * @author Dinia Gepte
 * Homework 4, AMS 326, Fall 2012
 */
public class Distribution
{
	private double mean;
	private double variance;
	private double[] values;	// NORMAL DISTRIBUTION VALUES WITH mean AND variance (NDV)
	
	/**
	 * Creates <CODE>n</CODE> standard normally distributed numbers with 
	 * mean = 0 and variance = 1.
	 * 
	 * @param n - number of values to be generated
	 */
	public Distribution(int n)
	{
		mean = 0;
		variance = 1;
		generateValues(n);
	}
	
	/**
	 * Creates <CODE>n</CODE> normally distributed numbers with mean = <CODE>initMean</CODE> and
	 * variance = <CODE>initVariance</CODE>.
	 * 
	 * @param initMean - mean value to be used
	 * @param initVariance - variance value to be used
	 * @param n - number of values to be generated
	 */
	public Distribution(double initMean, double initVariance, int n)
	{
		mean = initMean;
		variance = initVariance;
		generateValues(n);
	}
	
	/***** ACCESSOR AND MUTATOR METHODS *****/
	
	public void setMean(double initMean)
	{
		mean = initMean;
		update();
	}
	public void setVar(double initVariance)
	{
		variance = initVariance;
		update();
	}
	public double getMean()		{	return mean;		}
	public double getVar()		{	return variance;	}
	public double[] getValues()	{	return values;	}
	
	/**
	 * Gets a random value from the array of <CODE>values</CODE>.
	 * 
	 * @return a random value from <CODE>values</CODE>
	 */
	public double getGaussRanVar()
	{
		int index = (int)(Math.random() * values.length);	// RANDOM INDEX IN values
		return values[index];
	}
	
	/**
	 * Updates the current table of values with the current mean and variance.
	 */
	private void update()
	{
		generateValues(values.length);
	}
	
	/**
	 * Generates n normally distributed values with the current mean and variance.
	 * 
	 * @param n - number of values to be generated
	 */
	private void generateValues(int n)
	{
		// INITIALIZE ARRAY THAT WILL HOLD OUR SNDV
		values = new double[n];
		
		// GENERATE STANDARD NORMAL DISTRIBUTION (g) BY BOX-MULLER TRANSFORM
		for (int i = 0; i < values.length; i+=2)
		{
			double x1 = random();
			double x2 = random();
			values[i] = Math.sqrt(-2*Math.log(x1)) * Math.cos(2*Math.PI*x2);
			values[i+1] = Math.sqrt(-2*Math.log(x1)) * Math.sin(2*Math.PI*x2);
		}
		
		// CONVERT TO THE NORMAL DISTRIBUTION (z) WITH mean AND variance
		for (int i = 0; i < values.length; i++)
			values[i] = mean + values[i] * Math.sqrt(variance);
	}
	
	/**
	 * Returns the value of the normal distribution formula at x using the current
	 * mean and variance of this object.
	 * 
	 * @param x - the value to be evaluated into the normal distribution formula.
	 * @return the value of the normal distribution at x
	 */
	public double normalDistributionFunction(double x)
	{
		return 1 / (Math.sqrt(2*Math.PI*variance*variance)) * 
			Math.exp(-Math.pow(x-mean, 2)/(2*variance*variance));
	}
	
	/**
	 * Generates a random number between 0 and 1, 0 exclusive.
	 * @return
	 */
	private double random()
	{
		double ran = Math.random();
		if (ran == 0)
			return 1;
		else
			return ran;
	}
}
