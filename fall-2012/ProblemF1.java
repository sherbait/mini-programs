import java.util.ArrayList;
import java.util.Scanner;

/**
 * This program calculates the time a body has passed away after being in a medium.
 * In this program, the medium is snow with a temperature as a final variable.
 * It uses Newton's Law of cooling and 4th order Runge-Kutta method to calculate
 * the values.
 * 
 * @author Dinia Gepte, 107092681
 * AMS 326, Fall 2012
 *
 */
public class ProblemF1 
{
	private static final double DELTA = 0.001;	// ARBITRARILY CHOSEN
	public static final double K = 0.1;
	public static final double SNOW_TEMP = 31.8;
	public static final double LIVEBODY_TEMP = 99.1; 
	
	public static void main(String[] args) 
	{
		// GET THE RECENTLY DECEASED BODY TEMP
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a recently deceased body's temp (F): ");
		double finalTemp = input.nextDouble();
		if (finalTemp <= SNOW_TEMP)
		{
			finalTemp = SNOW_TEMP + 0.1;
			System.out.printf("The temperature entered cannot be less than or equal the " +
					"snow temperature. \nThe temperature is set at %.1f.\n", finalTemp);
		}

		// THESE ARRAYS WILL HOLD THE BODY'S TEMP AT ANY GIVEN TIME
		ArrayList<Double> temp = new ArrayList<Double>();
		ArrayList<Double> time = new ArrayList<Double>();
		
		// ADD THE INITIAL VALUES TO THE ARRAY
		temp.add(LIVEBODY_TEMP);	// t0 = intial temperature of body
		time.add(0.0);	// t0 = 0
		
		do
		{
			double lastTemp = temp.get(temp.size()-1);
			double lastTime = time.get(time.size()-1);
			double nextTemp = lastTemp + DELTA / 6.0 * (k1(lastTime, lastTemp) + 
					2*k2(lastTime, lastTemp) + 2*k3(lastTime, lastTemp) + k4(lastTime, lastTemp));
			double nextTime = lastTime + DELTA;
			temp.add(nextTemp);
			time.add(nextTime);
			
		} while (temp.get(temp.size()-1) > finalTemp);
		// LOOP WILL END WHEN TEMP REACHES THE USER-INPUT VALUE,
		// THAT IS WHEN THE BODY WAS FOUND.
		
		// REMOVE THE LAST ELEMENTS SINCE THE TEMP REACHED PAST THE USER-ENTERED VALUE
		temp.remove(temp.size()-1);
		time.remove(time.size()-1);
		
		// OUTPUT THE TIME TO THE USER
		System.out.printf("The victim passed away %.2f mins ago.", time.get(time.size()-1));
	}
	
	/**
	 * This is Newton's Law of Cooling. v (temp) must be greater than SNOW_TEMP.
	 * @param t
	 * @param v
	 * @return the temperature of of the body at time t.
	 */
	public static double function(double t, double v)
	{
		return -K * (v - SNOW_TEMP);
	}
	
	/**
	 * The slope at the beginning of the interval.
	 * 
	 * @param t - time
	 * @param v - temp
	 * @return a slope.
	 */
	public static double k1(double t, double v)
	{
		return function(t, v);
	}
	
	/**
	 * The slope at the midpoint of the interval, using slope k1 to determine the
	 * value of <i>v</i> at the point <i>tn + h / 2</i> using Euler's method.
	 * 
	 * @param t - time
	 * @param v -temp
	 * @return a slope.
	 */
	public static double k2(double t, double v)
	{
		return function(t + 0.5*DELTA, v + 0.5*DELTA*k1(t,v));
	}
	
	/**
	 * The slope at the midpoint, but now using the slope <i>k2</i> to determine
	 * the <i>v</i>-value.
	 * 
	 * @param t - time
	 * @param v - temp
	 * @return a slope.
	 */
	public static double k3(double t, double v)
	{
		return function(t + 0.5*DELTA, v + 0.5*DELTA*k2(t,v));
	}
	
	/**
	 * The slope at the end of the interval, with its <i>v</i>-value determined
	 * using <i>k3</i>.
	 * 
	 * @param t - time
	 * @param v - temp
	 * @return a slope.
	 */
	public static double k4(double t, double v)
	{
		return function(t + DELTA, v + DELTA*k3(t,v));
	}

}
