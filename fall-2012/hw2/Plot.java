import javax.swing.*;
import org.math.plot.*;
import static java.lang.Math.*;
import static org.math.array.DoubleArray.*;
import java.text.DecimalFormat;

/**
 * This program plots the function:
 * 		z(x,y) = (1-cos(PI*x))*((1.23456+cos(1.06512*y))^2)*e^(-x^2-y^2)
 * where x = [-1,1], y = [-2,2].
 * 
 * It also prints the two highest and lowest values of z within a 10x20 mesh point
 * and their corresponding x,y,z-coordinates and gradient on the console.
 * 
 * This program uses the centered finite difference method to calculate the
 * partial derivatives of the function to get the gradient.
 * 
 * 
 * @author Dinia Gepte, 107092681
 * Homework 2 for AMS326, Fall 2012
 *
 */
public class Plot
{
	// DELTA VARIABLE USED TO FIND THE PARTIAL DERIVATIVES
	private static final double DELTA = 0.0001;
	
	// VARIABLES THAT STORE THE x,y,z coordinates and corresponding gradient ACCDG TO THE z-value
	// 	double[0]: x-coord
	//	double[1]: y-coord
	//	double[2]: z-coord
	//	double[3]: grad(z)
	private static double[] highest = new double[4];
	private static double[] secondHighest = new double[4];
	private static double[] lowest = new double[4];
	private static double[] secondLowest = new double[4];
	
	public static void main(String[] args)
	{
		// PLOT DATA FOR X AND Y. DOMAIN IS A 10x20 GRID.
		double[] x = increment(-1.0, 0.2, 1.2); // INCREMENTS X = [-1.0, 1.2) BY 0.2
		double[] y = increment(-2.0, 0.2, 2.2); // INCREMENTS Y = [-2.0, 2.2) BY 0.2
		
		// THE increment METHOD OF THE DoubleArray CLASS GIVES INCREMENTED 
		// DOUBLE VALUES THAT AREN'T ENTIRELY ACCURATE DUE TO THE FORMULA USED.
		// THIS FIXES THAT FOR THIS PROGRAM WHERE WE ONLY NEED 1 DECIMAL PLACE.
		DecimalFormat df = new DecimalFormat("#.#");
		for (int i = 0; i < x.length; i++)
		{
			x[i] = Double.valueOf(df.format(x[i]));
			y[i] = Double.valueOf(df.format(y[i]));
		}
		
		// PLOT DATA FOR z(x,y)
		double[][] z = function(x, y);
		
 
		// PANEL THAT SHOWS THE PLOT WITH A LEGEND AT THE BOTTOM
		Plot3DPanel plot = new Plot3DPanel("SOUTH");
 
		// ADD THE VALUES OF X, Y, Z TO THE PANEL WITH LEGEND
		plot.addGridPlot("z(x,y) = (1-cos(PI*x))*((1.23456+cos(1.06512*y))^2)*e^(-x^2-y^2)", x, y, z);
 
		// PUT THE PANEL INTO A JFrame
		JFrame frame = new JFrame("A Plot");
		frame.setSize(600, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
		
		// FIND THE GRADIENTS
		highest[3] = gradient(partialX(highest[0], highest[1]), 
				partialY(highest[0], highest[1]));
		secondHighest[3] = gradient(partialX(secondHighest[0], secondHighest[0]), 
				partialY(secondHighest[0], secondHighest[0]));
		lowest[3] = gradient(partialX(lowest[0], lowest[1]), 
				partialY(lowest[0], lowest[1]));
		secondLowest[3] = gradient(partialX(secondLowest[0], secondLowest[1]), 
				partialY(secondLowest[0], secondLowest[1]));
		
		// PRINT A TABLE IN THE CONSOLE
		DecimalFormat out = new DecimalFormat("#.####");
		System.out.println("\t\tX\t|\tY\t|\tZ\t|\tGrad");
		System.out.println("Highest:\t" + highest[0] + "\t|\t" + highest[1] + 
				"\t|\t" + out.format(highest[2]) + "\t|\t" + out.format(highest[3]));
		System.out.println("2nd Highest:\t" + secondHighest[0] + "\t|\t" + 
				secondHighest[1] + "\t|\t" + out.format(secondHighest[2]) + 
				"\t|\t" + out.format(secondHighest[3]));
		System.out.println("Lowest: \t" + lowest[0] + "\t|\t" + lowest[1] + 
				"\t|\t" + out.format(lowest[2]) + "\t|\t" + out.format(lowest[3]));
		System.out.println("2nd Lowest:\t" + secondLowest[0] + "\t|\t" + 
				secondLowest[1] + "\t|\t" + out.format(secondLowest[2]) + 
				"\t|\t" + out.format(secondLowest[3]));
	}
	
	/*
	 * Function definition: z(x,y) = (1-cos(PI*x))*((1.23456+cos(1.06512*y))^2)*e^(-x^2-y^2)
	 */
	public static double function(double x, double y)
	{
		return (1-cos(PI * x)) * pow((1.23456 + cos(1.06512 * y)), 2) * 
				exp(-(pow(x, 2)) - (pow(y, 2)));
	}
	
	/*
	 * Grid of the function. This also determines the highest and lowest 2 values.
	 */
	public static double[][] function(double[] x, double[]y)
	{
		double[][] z = new double[y.length][x.length];
		for (int i = 0; i < x.length; i++)
			for (int j = 0; j < y.length; j++)
			{
				// EVALUATE THE FUNCTION
				z[j][i] = function(x[i], y[j]);
				
				// MAKE THE FIRST VALUE OF THE TABLE TO BE THE HIGHEST & LOWEST
				if (j == 0 && i == 0)
				{
					highest[0] = x[0];
					highest[1] = y[0];
					highest[2] = z[j][i];
					lowest[0] = x[0];
					lowest[1] = y[0];
					lowest[2] = z[j][i];
				}
				// THERE IS A NEW HIGHEST
				if (z[j][i] > highest[2])
				{
					// MAKE THE SECOND HIGHEST TAKE THE VALUES OF THE OLD HIGHEST
					secondHighest[0] = highest[0];
					secondHighest[1] = highest[1];
					secondHighest[2] = highest[2];
					
					// MAKE THE HIGHEST TAKE THE VALUES OF THE NEW HIGHEST
					highest[0] = x[i];
					highest[1] = y[j];
					highest[2] = z[j][i];
				}
				// THERE IS A NEW LOWEST
				if (z[j][i] < lowest[2])
				{
					secondLowest[0] = lowest[0];
					secondLowest[1] = lowest[1];
					secondLowest[2] = lowest[2];
					
					lowest[0] = x[i];
					lowest[1] = y[j];
					lowest[2] = z[j][i];
				}
			}
				
		return z;
	}
	
	public static double gradient(double partialX, double partialY)
	{
		return sqrt(pow(partialX, 2) + pow(partialY, 2));
	}
	
	public static double partialX(double x, double y)
	{
		return (function(x+DELTA, y) - function(x-DELTA, y)) / (2*DELTA);
	}
	
	public static double partialY(double x, double y)
	{
		return (function(x, y+DELTA) - function(x, y-DELTA)) / (2*DELTA);
	}
}
