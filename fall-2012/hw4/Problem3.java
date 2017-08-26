import java.util.HashMap;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Generates 10^5 normally random numbers with given mean = 0 and variance = 9.
 * The values are then sorted into 12 groups (-infinity, -5] by counting them.
 * Q(x)=ln(P(x)) is then computed and fitted into Q(x)=c1+c2x^2. 
 * 
 * @author Dinia Gepte
 * Homework 4, AMS 326, Fall 2012
 */
public class Problem3
{
	public static final int N = 100000;	// GIVEN
	
	public static void main(String[] args)
	{	
		// CREATE A NEW DISTRIBUTION WITH mean = 0 AND variance = 9
		Distribution d = new Distribution(0, 9, N);
		double[] num = d.getValues();
	
		// USE A HASHTABLE TO COUNT THE NUMBER OF THE G_i NUMBERS IN EACH GROUP
		HashMap<Double, Integer> count = new HashMap<Double, Integer>(12);
		
		// SET THE KEYS OF THE HASHTABLE ALONG WITH A DEFAULT 0 COUNT
		for (double key = -5.5; key <= 5.5; key++)
			count.put(key, 0);
		
		// SORT THE G_i NUMBERS INTO 12 GROUPS
		for (int i = 0; i < num.length; i++)
		{
			if (num[i] <= -5)
				count.put(-5.5, count.get(-5.5)+1);
			else if (num[i] <= -4)
				count.put(-4.5, count.get(-4.5)+1);
			else if (num[i] <= -3)
				count.put(-3.5, count.get(-3.5)+1);
			else if (num[i] <= -2)
				count.put(-2.5, count.get(-2.5)+1);
			else if (num[i] <= -1)
				count.put(-1.5, count.get(-1.5)+1);
			else if (num[i] <= 0)
				count.put(-0.5, count.get(-0.5)+1);
			else if (num[i] <= 1)
				count.put(0.5, count.get(0.5)+1);
			else if (num[i] <= 2)
				count.put(1.5, count.get(1.5)+1);
			else if (num[i] <= 3)
				count.put(2.5, count.get(2.5)+1);
			else if (num[i] <= 4)
				count.put(3.5, count.get(3.5)+1);
			else if (num[i] <= 5)
				count.put(4.5, count.get(4.5)+1);
			else
				count.put(5.5, count.get(5.5)+1);
		}
		
		// CALCULATE Q(x) = lnP(x)
		double[] q = new double[12];
		q[0] = functionQ(count.get(-5.5));
		q[1] = functionQ(count.get(-4.5));
		q[2] = functionQ(count.get(-3.5));
		q[3] = functionQ(count.get(-2.5));
		q[4] = functionQ(count.get(-1.5));
		q[5] = functionQ(count.get(-0.5));
		q[6] = functionQ(count.get(0.5));
		q[7] = functionQ(count.get(1.5));
		q[8] = functionQ(count.get(2.5));
		q[9] = functionQ(count.get(3.5));
		q[10] = functionQ(count.get(4.5));
		q[11] = functionQ(count.get(5.5));
		
		// DISPLAY THE TABLE OF VALUES
		display(count, q);
		
		// INITIALIZE AND DECLARE THE VALUES OF x TO BE USED IN CURVE FITTING
		double[] x = {-5.5, -4.5, -3.5, -2.5, -1.5, -0.5, 0.5, 1.5, 2.5, 3.5, 4.5, 5.5};
		
		// FIT THE GRAPH INTO Q(x) = c1 + c2x^2
		fit(x, q);
	}
	
	/**
	 * Fits the points (x,y) into the equation Q(x) = c1 + c2x^2 using the least
	 * square method.
	 *  
	 * @param x - x-values
	 * @param y - y-values
	 */
	public static void fit(double[] x, double[] y)
	{
		// CALCULATE THE 3x3 MATRIX ON THE LHS
		double[][] a = new double[3][3];
		for (int i = 0; i < x.length; i++)
		{
			a[0][0] = x.length;
			a[0][1] += x[i];
			a[0][2] += x[i]*x[i];
			a[1][0] += x[i];
			a[1][1] += x[i]*x[i];
			a[1][2] += x[i]*x[i]*x[i];
			a[2][0] += x[i]*x[i];
			a[2][1] += x[i]*x[i]*x[i];
			a[2][2] += x[i]*x[i]*x[i]*x[i];
		}
		
		// CALCULATE THE RHS
		double[] b = new double[3];
		for (int i = 0; i < x.length; i++)
		{
			b[0] += y[i];
			b[1] += x[i]*y[i];
			b[2] += x[i]*x[i]*y[i];
		}
		
		// TRANSFORM THE SQUARE MATRIX INTO A RealMatrix OBJECT
		RealMatrix m = new Array2DRowRealMatrix(a);
		// INVERT m USING LU DECOMPOSITION
		RealMatrix mInverse = new LUDecomposition(m).getSolver().getInverse();
		// MULTIPLY THIS INVERSE WITH b
		double[] coef = mInverse.preMultiply(b);
		
		System.out.printf("The equation is Q(x) = %.4f + %.4fx^2.", coef[0], coef[2]);
	}
	
	/**
	 * Returns the value of the function Q(x) = ln(P(x)).
	 * 
	 * @param px - a double value to be evaluated into the function
	 * @return the value of the function Q(x) = ln(P(x)).
	 */
	public static double functionQ(double px)
	{
		return Math.log(px);
	}
	
	/**
	 * Displays a table with the values of x, P(x), Q(x) for [-5.5, 5.5]
	 * in 1 incremental.
	 * 
	 * @param map - contains the value of x and P(x)
	 * @param q - contains the value of Q(x)
	 */
	public static void display(HashMap<Double, Integer> map, double[] q)
	{
		System.out.println("  x  | P(x)     |\tQ(x)");
		System.out.println("-5.5 | " + map.get(-5.5) + "\t| " + q[0]);
		System.out.println("-4.5 | " + map.get(-4.5) + "\t| " + q[1]);
		System.out.println("-3.5 | " + map.get(-3.5) + "\t| " + q[2]);
		System.out.println("-2.5 | " + map.get(-2.5) + "\t| " + q[3]);
		System.out.println("-1.5 | " + map.get(-1.5) + "\t| " + q[4]);
		System.out.println("-0.5 | " + map.get(-0.5) + "\t| " + q[5]);
		System.out.println(" 0.5 | " + map.get(0.5) + "\t| " + q[6]);
		System.out.println(" 1.5 | " + map.get(1.5) + "\t| " + q[7]);
		System.out.println(" 2.5 | " + map.get(2.5) + "\t| " + q[8]);
		System.out.println(" 3.5 | " + map.get(3.5) + "\t| " + q[9]);
		System.out.println(" 4.5 | " + map.get(4.5) + "\t| " + q[10]);
		System.out.println(" 5.5 | " + map.get(5.5) + "\t| " + q[11]);
	}
}
