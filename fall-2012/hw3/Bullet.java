import java.util.ArrayList;
import java.util.Scanner;

/**
 * This program calculates the maximum distance traveled by a bullet of mass
 * m = 0.1kg to a medium whose resistance is -0.005(4+v^2) N * s^2/m^2.
 * 
 * The program asks the user for an initial velocity in m/s.
 * 
 * The program uses the Runge-Kutta method to solve the IVP:
 * 		m (dv/dt) = medium_resistance
 * 		v(t=0) = v0
 * 
 * @author Dinia Gepte, 107092681
 * Homework 3 for AMS326, Fall 2012
 *
 */
public class Bullet
{
	private static final double DELTA = 0.001;	// ARBITRARILY CHOSEN
	private static final double MASS = 0.1; // KG
	private static final double RESISTANCE = 0.005; // N * (s^2 / m^2)
	
	public static void main(String[] args)
	{
		// GET THE INITIAL VELOCITY FROM THE USER
		Scanner input = new Scanner(System.in);
		System.out.print("Enter an initial velocity (m/s): ");
		double initVelocity = input.nextDouble();
		
		// THESE ARRAYS WILL HOLD THE VALUES OF THE VELOCITY OF THE
		// BULLET AT ANY GIVEN TIME
		ArrayList<Double> velocity = new ArrayList<Double>();
		ArrayList<Double> time = new ArrayList<Double>();
		
		// ADD THE INITIAL VALUES TO THE ARRAY
		velocity.add(initVelocity);	// v0 = USER-ENTERED VALUE
		time.add(0.0);	// t0 = 0
		
		do
		{
			double lastV = velocity.get(velocity.size()-1);
			double lastT = time.get(time.size()-1);
			double nextV = lastV + DELTA / 6.0 * (k1(lastT, lastV) + 
					2*k2(lastT, lastV) + 2*k3(lastT, lastV) + k4(lastT, lastV));
			double nextT = lastT + DELTA;
			velocity.add(nextV);
			time.add(nextT);
			
		} while (velocity.get(velocity.size()-1) > 0);
		// LOOP WILL END WHEN VELOCITY REACHES 0 OR NEGATIVE,
		// THAT IS WHEN THE BULLET STOPS.
		
		// REMOVE THE LAST ELEMENTS SINCE VELOCITY IS NEGATIVE
		velocity.remove(velocity.size()-1);
		time.remove(time.size()-1);
		
		// OUTPUT THE DISTANCE TO THE USER
		System.out.printf("The maximum distance the bullet travels is %.4f m.", 
				distance(time, velocity));
	}
	
	/**
	 * Calculates the total distance traveled by the bullet through the medium
	 * by the Riemann sum method, i.e. summation of velocity * time.
	 * 
	 * @param time - a list of time values
	 * @param velocity  - a list of velocity as a function of time values
	 * @return the Reimann sum of the parameters.
	 */
	public static double distance(ArrayList<Double> time, ArrayList<Double> velocity)
	{
		double distance = 0;
		for (int i = 1; i < velocity.size(); i++)
			distance += velocity.get(i) * (time.get(i) - time.get(i-1));
		return distance;
	}
	
	/**
	 * Calculates the acceleration of the bullet as it goes through the medium.
	 * 
	 * @param t - time
	 * @param v - velocity
	 * @return the acceleration of the bullet at time <b>t</b>.
	 */
	public static double acceleration(double t, double v)
	{
		return -RESISTANCE * (4 + Math.pow(v, 2)) / MASS;
	}
	
	/**
	 * The slope at the beginning of the interval.
	 * 
	 * @param t - time
	 * @param v - velocity
	 * @return a slope.
	 */
	public static double k1(double t, double v)
	{
		return acceleration(t, v);
	}
	
	/**
	 * The slope at the midpoint of the interval, using slope k1 to determine the
	 * value of <i>v</i> at the point <i>tn + h / 2</i> using Euler's method.
	 * 
	 * @param t - time
	 * @param v - velocity
	 * @return a slope.
	 */
	public static double k2(double t, double v)
	{
		return acceleration(t + 0.5*DELTA, v + 0.5*DELTA*k1(t,v));
	}
	
	/**
	 * The slope at the midpoint, but now using the slope <i>k2</i> to determine
	 * the <i>v</i>-value.
	 * 
	 * @param t - time
	 * @param v - velocity
	 * @return a slope.
	 */
	public static double k3(double t, double v)
	{
		return acceleration(t + 0.5*DELTA, v + 0.5*DELTA*k2(t,v));
	}
	
	/**
	 * The slope at the end of the interval, with its <i>v</i>-value determined
	 * using <i>k3</i>.
	 * 
	 * @param t - time
	 * @param v - velocity
	 * @return a slope.
	 */
	public static double k4(double t, double v)
	{
		return acceleration(t + DELTA, v + DELTA*k3(t,v));
	}
}
