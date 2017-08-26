import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

public class ProblemF2 
{
	public static double N1 = 1000;
	public static double N2 = 10000;
	public static double N3 = 100000;
	public static double N4 = 1000000;
			
	public static void main(String[] args)
	{
		// HOLDS THE VALUES OF THE Y=M/N Ratio
		double[][] ratio = new double[4][2];
		
		// INITIALIZE N
		ratio[0][0] = N1;
		ratio[1][0] = N2;
		ratio[2][0] = N3;
		ratio[3][0] = N4;
		
		// CALCULATE M
		for (int i = 0; i < ratio.length; i++)
			ratio[i][1] = computeM(ratio[i][0], 1, 1);
		
		// DISPLAY THE TABLE
		System.out.println("\tN\t|\tY\n");
		for (int i = 0; i < ratio.length; i ++)
		{
			for (int j = 0; j < ratio[i].length; j++)
				System.out.printf("%.2f \t\t", ratio[i][j]);
			System.out.println();
		}
	}
	
	/**
	 * This method calculates the number of times in n experiments the needle
	 * crosses the parallel lines.
	 * 
	 * @param ratio
	 * @return
	 */
	private static double computeM(double n, double length, double distance)
	{
		return n * (2/Math.PI) * (length/distance);
	}
}
