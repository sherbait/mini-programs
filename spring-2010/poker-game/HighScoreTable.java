import java.io.*;
/*
 * This HighScoreTable object creates a 10x2 table
 * to show the top 10 scores of the Poker Game.
 * It also keeps a saved game table for every place/rank
 * on the high score list.
 * 
 * @author Dinia Grace Gepte
 * CSE114 Spring 2010 Final Project
 * L03 TA Yifu Ren
 */
public class HighScoreTable
{
	// Instance variable(s)
	private int[][] table;
	
	// Constructor that creates the high score list
	// and assigns the rank number to the first column.
	public HighScoreTable()
	{
		table = new int[10][2];
		for (int row = 0; row < 10; row++)
			table[row][0] = row + 1;
	}
	
	public void setHighScore(int row, int score)
	{	
		moveScores(row);
		table[row][1] = score;
	}
	
	/*
	 * If a high score is achieved, the scores below
	 * the new high score move down by 1 place.
	 */
	public void moveScores(int row)
	{
		for (int i = 9; i > row; i--)
			table[i][1] = table[i-1][1];
	}
	
	// Given a game score, this method checks if it is
	// higher than any of the current top scores.
	public int testForHighScore(int score)
	{
		int highScoreRank = -1;
		for (int row = 9; row >= 0; row--)
		{
			if (score > table[row][1])
				highScoreRank = row;
		}
		return highScoreRank;
	}

	/*
	 * This method streams out the table that achieved
	 * a high score and then updates the other files
	 * containing other high-score-tables by moving
	 * them 1 place below to make space for the new
	 * table.
	 */
	public void streamOutTable(int highScoreRow, String pokerTable) throws IOException
	{
		String fileName = "", tableCopy = "";
		DataOutputStream output;
		DataInputStream input;
		for (int i = 9; i >= highScoreRow + 1; i--)
		{
			try
			{
				fileName = "rank" + i + ".dat";
				input = new DataInputStream(new FileInputStream(fileName));
				tableCopy = input.readUTF();
				fileName = "rank" + (i + 1) + ".dat";
				output = new DataOutputStream(new FileOutputStream(fileName));
				output.writeUTF(tableCopy);
				output.close();
			}
			catch(IOException e)	{	}
		}
		fileName = "rank" + (highScoreRow + 1) + ".dat";
		output = new DataOutputStream(new FileOutputStream(fileName));
		output.writeUTF(pokerTable);
		output.close();
	}
	
	// This method prints out the table requested
	// from the high score list.
	public void streamInTable(int rankNumber)
	{
		try
		{
			String fileName = "rank" + rankNumber + ".dat";
			DataInputStream input = new DataInputStream(new FileInputStream(fileName));
			System.out.println(input.readUTF());
		}
		catch(IOException e)
		{
			if (rankNumber >= 1 && rankNumber <= 10)
				System.out.println("\tSorry, there's no current high score there.");
		}
	}
	
	// This method streams out the high scores.
	public void streamOutHighScoreTable() throws IOException
	{
		DataOutputStream output = new DataOutputStream(new FileOutputStream("highScoreTable.dat"));
		for (int rowIndex = 0; rowIndex < 10; rowIndex++)
			output.writeInt(table[rowIndex][1]);
		output.close();
	}
	
	// This method streams in the high scores.
	public void streamInHighScoreTable()
	{
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream("highScoreTable.dat"));
			for (int rowIndex = 0; rowIndex < 10; rowIndex++)
				table[rowIndex][1] = input.readInt();
		}
		catch(IOException e)	{	}
	}
	
	// String representation of the high score list.
	public String toString()
	{
		String top10List = "\nTop 10 Scores";
		for (int row = 0; row < 10; row++)
		{
			for (int column = 0; column < 2; column++)
			{
				if (column == 1)
					top10List = top10List + "\t" + table[row][column];
				else
					top10List = top10List + "\n(" + table[row][column] + ")";
			}
		}
		return top10List + "\n";
	}
}
