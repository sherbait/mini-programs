/**
 * This program prints 260 stock-day values in different schemes. Each scheme
 * varies in mean and variance. The stock values are determined using a
 * normally distributed stock exchange rate.
 * 
 * @author Dinia Gepte
 * Homework 4, AMS 326, Fall 2012
 */
public class Problem4
{
	public static final int N = 260;	// NUMBER OF DAYS
	public static final int initStock = 20000;	// INITIAL STOCK
	
	public static void main(String[] args)
	{
		double[] scheme1 = scheme(-0.01, 0.0104);
		double[] scheme2 = scheme(-0.02, 0.0104);
		double[] scheme3 = scheme(0.01, 0.0104);
		double[] scheme4 = scheme(-0.01, 0.0114);
		double[] scheme5 = scheme(-0.02, 0.0114);
		double[] scheme6 = scheme(0.01, 0.0114);
		System.out.println("Day | Scheme 1 | Scheme 2 | Scheme 3 | Scheme 4 | Scheme 5 | Scheme 6");
		for (int i = 1; i < scheme1.length; i++)
			System.out.printf("%d   | %.2f | %.2f | %.2f | %.2f | %.2f | %.2f\n", i, 
					scheme1[i], scheme2[i], scheme3[i], 
					scheme4[i], scheme5[i], scheme6[i]);
	}
	
	/**
	 * Returns a listing of N stock values following a normal distribution 
	 * with the given mean and variance.
	 * @param mean - mean of the normal distribution
	 * @param var - variance of the normal distribution
	 * @return the listing of N stock values
	 */
	public static double[] scheme(double mean, double var)
	{
		// CREATE A Distribution CLASS WHICH CREATES 260 NORMALLY DISTRIBUTED VALUES
		Distribution d = new Distribution(mean, var, N);
		
		// RATE PER DAY
		double[] rate = new double[N+1];
		rate[0] = 1;	// INITIAL RATE
		for (int i = 1; i < rate.length; i++)
			rate[i] = rate[i-1] * (1 + d.getGaussRanVar());
		
		// STOCK VALUE PER DAY
		double[] stockValue = new double[N+1];
		stockValue[0] = initStock;	// INITIAL STOCK VALUE
		for (int i = 1; i < stockValue.length; i++)
			stockValue[i] = stockValue[i-1]*Math.pow(1+rate[i], 1.0/N);
		
		return stockValue;
	}
}
