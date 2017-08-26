import java.text.DecimalFormat;

/**
 * This program calculates the integral of the function,
 * 		f(x) = (sin(x^3)/x^3 + exp(-sinx/x))^3
 * using two methods:
 * 
 * (a) Trapezoidal Method
 * (b) Monte Carlo Method
 * 
 * @author Dinia Gepte, 107092681
 * Homework 2 for AMS326, Fall 2012
 *
 */
public class Integral 
{
	// CONSTANTS FOR THIS FUNCTION
	public static final int N = 10000;	// ARBITRARILY CHOSEN
	public static final double A = 0;
	public static final double B = Math.PI / 4;
	public static final double DELTA = (B - A) / N;
	
	public static void main(String[] args)
	{		
		// Format class to display area up to 5 digits of accuracy
		DecimalFormat df = new DecimalFormat("#.#####");
		
		// PRINT THE AREA USING THE TRAPEZOIDAL METHOD
		System.out.println("The area by the Trapezoid method is " + 
				df.format(trapezoid()) + ".");
		
		// PRINT THE AREA USING THE MONTE CARLO METHOD
		System.out.println("The area by the Monte Carlo method is " + 
				df.format(monteCarlo()) + ".");
	}
	
	/**
	 * This method evaluates the function:
	 * 		f(x) = (sin(x^3)/x^3 + exp(-sinx/x))^3 
	 * at the given value x.
	 * 
	 * @param x
	 * @return the value of the function at x
	 */
	public static double function(double x)
	{
		// FOR THIS FUNCTION, WHEN X = 0 THERE IS A SPECIAL VALUE FOR IT.
		// IF THIS IS NOT CHECKED, THE FUNCTION WILL EVALUATE TO NaN,
		// A VALUE PRODUCED WHEN THE DENOMINATOR IS 0.
		if (x == 0)
			return (Math.pow(1 + Math.E, 3) / Math.pow(Math.E, 3));
		return Math.pow((Math.sin(Math.pow(x, 3))/Math.pow(x, 3)) + 
				(Math.exp(-(Math.sin(x)/x))), 3); 
	}
	
	/**
	 * Calculates the area of the function using the trapezoidal method where:
	 * 		A = Sum(0.5 * (f(x_i) + f(x_i+1)) * DELTA)
	 * 
	 * @return the area of the function from a to b
	 */
	public static double trapezoid()
	{
		double area = 0;
		for (int i = 0; i < N; i++)
			area += 0.5 * (function(getX(i)) + function(getX(i+1))) * DELTA;
		return area;
	}
	
	/**
	 * This method calculates the x-coordinates within the area of integration
	 * depending on step n.
	 * 
	 * @param n
	 * @return x-coordinates
	 */
	public static double getX(int n)
	{
		return A + n*DELTA;
	}
	
	/**
	 * Calculates an approximate area of the function using the Monte Carlo
	 * method by generating N random numbers between A and B, and plugging in
	 * those numbers to the function.
	 * 
	 * @return the area of the function
	 */
	public static double monteCarlo()
	{
		double area = 0;
		for (int i = 0; i < N; i++ )
			area += function(randomNumberBetweenAandB()) * DELTA;
		return area;
	}
	
	/**
	 * This method generates a random number from the constant A (inclusive)
	 * and B (exclusive).
	 * 
	 * @return a random number between [a,b)
	 */
	public static double randomNumberBetweenAandB()
	{
		return Math.random() * (B-A) + A;	// THIS WILL NEVER GENERATE B
	}
}
