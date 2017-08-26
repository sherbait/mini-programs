import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;


public class ProblemF3 
{
	public static void main(String[] args) 
	{
		// CREATE THE 3x5 TORUS
		int[][] torus = createTorus(3,5);
		
		// CALCULATE THE DISTANCE BETWEEN EACH NODE
		double[][] distance = distanceTorus(torus);
		
		// USE APACHE'S LIBRARY TO CALCULATE THE EIGENVALUES OF distance
		RealMatrix m = new Array2DRowRealMatrix(distance);
		EigenDecomposition e = new EigenDecomposition(m, 0);
		
		// PRINT THE EIGENVALUES
		System.out.println("The eigenvalues are: ");
		display1(e.getRealEigenvalues());
	}
	
	/**
	 * Creates a matrix whose elements are the shortest distance between nodes
	 * of a torus (matrix) parameter.
	 * @param torus
	 * @return
	 */
	private static double[][] distanceTorus(int[][] torus)
	{
		int size = 15;
		double[][] d = new double[size][size];
		
		// ENTER THE VALUES
		// NODE 1
		d[0][1] = 1;
		d[0][2] = 2;
		d[0][3] = 2;
		d[0][4] = 1;
		
		d[0][5] = 1;
		d[0][6] = 2;
		d[0][7] = 3;
		d[0][8] = 3;
		d[0][9] = 2;
		
		d[0][10] = 1;
		d[0][11] = 2;
		d[0][12] = 3;
		d[0][13] = 3;
		d[0][14] = 2;
		
		// NODE 2
		d[1][2] = 1;
		d[1][3] = 2;
		d[1][4] = 2;
		
		d[1][5] = 2;
		d[1][6] = 1;
		d[1][7] = 2;
		d[1][8] = 3;
		d[1][9] = 3;
		
		d[1][10] = 2;
		d[1][11] = 1;
		d[1][12] = 2;
		d[1][13] = 3;
		d[1][14] = 3;
		
		// NODE 3
		d[2][3] = 1;
		d[2][4] = 2;
		
		d[2][5] = 3;
		d[2][6] = 2;
		d[2][7] = 1;
		d[2][8] = 2;
		d[2][9] = 3;
		
		d[2][10] = 3;
		d[2][11] = 2;
		d[2][12] = 1;
		d[2][13] = 2;
		d[2][14] = 3;
		
		// NODE 4
		d[3][4] = 1;
		
		d[3][5] = 3;
		d[3][6] = 3;
		d[3][7] = 2;
		d[3][8] = 1;
		d[3][9] = 2;
		
		d[3][10] = 3;
		d[3][11] = 3;
		d[3][12] = 2;
		d[3][13] = 1;
		d[3][14] = 2;
		
		// NODE 5
		d[4][5] = 2;
		d[4][6] = 3;
		d[4][7] = 3;
		d[4][8] = 2;
		d[4][9] = 1;
		
		d[4][10] = 2;
		d[4][11] = 3;
		d[4][12] = 3;
		d[4][13] = 2;
		d[4][14] = 1;
		
		// NODE 6
		d[5][6] = 1;
		d[5][7] = 2;
		d[5][8] = 2;
		d[5][9] = 1;
		
		d[5][10] = 1;
		d[5][11] = 2;
		d[5][12] = 3;
		d[5][13] = 3;
		d[5][14] = 2;
		
		// NODE 7
		d[6][7] = 1;
		d[6][8] = 2;
		d[6][9] = 2;
		
		d[6][10] = 2;
		d[6][11] = 1;
		d[6][12] = 2;
		d[6][13] = 3;
		d[6][14] = 3;
		
		// NODE 8
		d[7][8] = 1;
		d[7][9] = 2;
		
		d[7][10] = 3;
		d[7][11] = 2;
		d[7][12] = 1;
		d[7][13] = 2;
		d[7][14] = 3;
		
		// NODE 9
		d[8][9] = 1;
		
		d[8][10] = 3;
		d[8][11] = 3;
		d[8][12] = 2;
		d[8][13] = 1;
		d[8][14] = 2;
		
		// NODE 10
		d[9][10] = 2;
		d[9][11] = 3;
		d[9][12] = 3;
		d[9][13] = 2;
		d[9][14] = 1;
		
		// NODE 11
		d[10][11] = 1;
		d[10][12] = 2;
		d[10][13] = 2;
		d[10][14] = 1;
		
		// NODE 12
		d[11][12] = 1;
		d[11][13] = 2;
		d[11][14] = 2;
		
		// NODE 13
		d[12][13] = 1;
		d[12][14] = 2;
		
		// NODE 14
		d[13][14] = 1;
		
		for (int i = 0; i < d.length; i++)
		{
			for (int j = 0; j < d[i].length; j++)
			{
				if (i == j)
					d[i][j] = 0;	// THE DISTANCE BETWEEN A NODE AND ITSELF IS 0
				d[j][i] = d[i][j];	// THE MATRIX IS SYMMETRICAL SO MIRROR IT IN THE LOWER HALF OF THE  MATRIX
			}
		}
		
		display2(d);
		return d;
	}
	
	/**
	 * Creates a rxc matrix with initial values set to i=1,2,3,...,(rxc).
	 * @param r - rows
	 * @param c - columns
	 * @return
	 */
	private static int[][] createTorus(int r, int c)
	{
		int[][] array = new int[r][c];
		for (int i = 0, count = 1; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++, count++)
				array[i][j] = count;
		return array;
	}
	
	/**
	 * Displays an nxn matrix in the console.
	 * @param array
	 */
	private static void display2(double[][] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			for (int j = 0; j < array[i].length; j++)
				System.out.printf("%.1f \t", array[i][j]);
			System.out.println();
		}
	}
	
	/**
	 * Displays a 1xn matrix in the console.
	 * @param array
	 */
	private static void display1(double[] array)
	{
		for (int i = 0; i < array.length; i++)
			System.out.printf("%.2f \t", array[i]);
	}
}
