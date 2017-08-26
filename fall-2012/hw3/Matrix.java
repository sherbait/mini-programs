import java.util.Scanner;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * This is a small linear algebra program that generates a random square matrix,
 * whose size is determined by the user. The program will have the following
 * options to the user:
 * 		1) view the matrix
 * 		2) view the determinant of the matrix
 * 		3) view the inverse of the matrix
 * 		4) view the product of the matrix and its inverse
 * 		5) terminate the program
 * 
 * @author Dinia Gepte, 107092681
 * Homework 3 for AMS326, Fall 2012
 *
 */
public class Matrix
{
	public static void main(String[] args)
	{
		// CLASS USED TO TAKE USER INPUT IN THE CONSOLE
		Scanner input = new Scanner(System.in);
		
		// ASK THE USER FOR THE SIZE OF THE N-BY-N MATRIX
		System.out.print("Enter n for an n-by-n matrix A: ");
		int n = input.nextInt();
		double[][] matrixA = generateSquareMatrix(n, -1, 1);
		
		// THIS IS A LOOP THAT WILL PROVIDE OPTIONS TO THE USER TO GET:
		// (M) PRINT THE MATRIX
		// (D) DETERMINANT OF MATRIX A
		// (I) INVERSE OF MATRIX A
		// (P) PRODUCT OF MATRIX A AND ITS INVERSE
		// (Q) EXIT THE PROGRAM
		String choice = "?";
		while (!choice.toUpperCase().equals("Q"))
		{
			System.out.println("\nEnter an option below:");
			System.out.println("(M) matrix A");
			System.out.println("(D) det(A)");
			System.out.println("(I) inverse(A)");
			System.out.println("(P) product of A and inverse(A)");
			System.out.println("(Q) quit");
			System.out.print("Choice: ");
			choice = input.next();
			
			// PRINT MATRIX A
			if (choice.toUpperCase().equals("M"))
				show(matrixA);
			// CALCULATE det(A)
			else if (choice.toUpperCase().equals("D"))
				det(matrixA);
			// PRINT THE INVERSE OF A
			else if (choice.toUpperCase().equals("I"))
				show(inv(matrixA));
			// CALCULATE THE PRODUCT OF A AND ITS INVERSE
			else if (choice.toUpperCase().equals("P"))
				show(prod(matrixA, inv(matrixA)));
		}
	}
	
	/**
	 * Returns the product of square matrices <b>a</b> and <b>b</b> of size n.
	 * 
	 * @param a - n square matrix
	 * @param b - n square matrix
	 * @return the product of a and b.
	 */
	private static double[][] prod(double[][] a, double[][] b)
	{
		double[][] c = new double[a.length][a[0].length];
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < b.length; j++)
				for(int k = 0; k < c.length; k++)
					c[i][j] += a[i][k] * b[k][j];
		return c;
	}
	
	/**
	 * Returns the inverse of a square matrix <b>a</b>. 
	 * The matrix inverse is calculated using a linear algebra package from
	 * <a href = "http://commons.apache.org/math/userguide/linear.html">Apache Commons</a>.
	 * 
	 * @param a - the matrix to be inverted
	 * @return a 2D {@code double} inverse matrix of a.
	 */
	private static double[][] inv(double[][] a)
	{
		// TRANSFORM THE SQUARE MATRIX INTO A RealMatrix OBJECT
		RealMatrix m = new Array2DRowRealMatrix(a);
		// INVERT m USING LU DECOMPOSITION
		RealMatrix mInverse = new LUDecomposition(m).getSolver().getInverse();
		// RETURN THE INVERSE DATA AS A DOUBLE[][]
		return mInverse.getData();
	}
	
	/**
	 * Prints (in a new line) the determinant of matrix <b>a</b>, accurate up to
	 * 5 decimal places, and adds a new line at the end.
	 * 
	 * @param a - an n-by-n matrix
	 */
	private static void det(double[][] a)
	{
		System.out.printf("det(a) = %.6f\n", laplaceDet(a));
	}
	
	/**
	 * Calculates the determinant of the matrix using Pierre-Simon Laplace's
	 * Laplace expansion. It is the the expansion of the determinant of a
	 * square matrix as a weighted sum of determinants of sub-matrices.
	 * <br><br>
	 * Source: <a href="http://en.wikipedia.org/wiki/Laplace_expansion"> Laplace Expansion</a>
	 * 
	 * @param m - an n-by-n matrix
	 * @return the determinant of matrix m.
	 */
	private static double laplaceDet(double[][] m)
	{
		double det = 0; 
		if (m.length == 2)	// A 2-by-2 MATRIX
			det = m[0][0] * m[1][1] - m[0][1] * m[1][0];
		else	// AN N-BY-N FOR N > 2
			for (int j = 1; j <= m.length; j++)
				det += m[0][j-1] * cofactor(m,1,j);
		return det;
	}
	
	/**
	 * Calculates the cofactor of matrix <b>m</b>, that is, the signed minor
	 * matrix of <b>m</b>. The minor matrix of <b>m</b> is the determinant of
	 * the (n-1)-by-(n-1) matrix that results from deleting the <b>r</b>-th row
	 * and the <b>c</b>-th column of <b>m</b>.
	 * 
	 * @param m - main matrix 
	 * @param r - row
	 * @param c - column
	 * @return the cofactor of matrix m.
	 */
	private static double cofactor(double[][] m, int r, int c)
	{
		return Math.pow(-1, r+c) * laplaceDet(delete(m,r,c));
	}
	
	/**
	 * This method deletes row <b>r</b> and column <b>c</b> in the matrix
	 * <b>m</b> for <b>r</b>, <b>c</b> = 1,2,...,n, and returns the
	 * resulting matrix. 
	 * 
	 * @param m - the matrix to be reduced
	 * @param r - the <i>rth</i> row to be deleted
	 * @param c - the <i>cth</i> column to be deleted
	 * @return the matrix that results from deleting the r-th row and the c-th
	 * 	column of m.
	 */
	private static double[][] delete(double[][] m, int r, int c)
	{
		// MAKE AN n-1-by-n-1 MATRIX
		double[][] n = new double[m.length-1][m[0].length-1];
		
		int x = 0;	// ROW INDEX OF MATRIX n
		for (int i = 1; i <= m.length; i++)
		{
			if (i == r)	continue;	// r-TH ROW IS TO BE DELETED SO SKIP IT
			int y = 0;	// COLUMN INDEX OF MATRIX n
			for (int j = 1; j <= m[i-1].length; j++)
			{
				if (j == c)	continue;	// c-TH COLUMN IS TO BE DELETED SO SKIP IT
				n[x][y] = m[i-1][j-1];	// ASSIGN THE VALUE TO THE NEW MATRIX
				y++;
			}
			x++;
		}
		
		// RETURN THE NEW MATRIX
		return n;
	}
	
	/**
	 * Prints the matrix <b>A</b> in the console, up to 6 decimal places.
	 * 
	 * @param a - the matrix to be displayed in the console
	 */
	private static void show(double[][] a)
	{
		for (int i = 0; i < a.length; i++)
		{
			for (int j = 0; j < a[i].length; j++)
				System.out.printf("%.6f \t", a[i][j]);
			System.out.println();
		}
	}
	
	/**
	 * Generates a random n-by-n matrix with values [a,b].
	 * 
	 * @param x - the size of the matrix
	 * @param a - the lower boundary of an element of the matrix
	 * @param b - the upper boundary of an element of the matrix
	 * @return a random n-by-n matrix.
	 */
	private static double[][] generateSquareMatrix(int n, double a, double b)
	{
		// DECLARE AN n-by-n MATRIX 
		double[][] matrix = new double[n][n];
		
		// INITIALIZE THE MATRIX WITH RANDOM NUMBERS [a,b]
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				matrix[i][j] = random(a, b);
		
		// RETURN THE MATRIX
		return matrix;
	}
	
	/**
	 * Generates a random number between x and y inclusive,
	 * using a poor-person's algorithm.
	 * 
	 * @param x - the lower boundary of the random number
	 * @param y - the upper boundary of the random number
	 * @return a random number [x,y].
	 */
	private static double random(double x, double y)
	{
		// INITITAL SEEDS
		double x0 = Math.random();
		double x1 = Math.random();
		
		// GENERATE A RANDOM NUMBER x2 = [0, 1]
		double x2 = (x0 + x1 < 1) ? (x0 + x1) : (x0 + x1 - 1.0);
		
		// GENERATE A RANDOM NUMBER [x, y]
		return (y - x) * x2 + (x);
	}
}
