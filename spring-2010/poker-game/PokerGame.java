import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
/*
 * This is a 2D Poker Game allowing the user to:
 * manually lay the cards on a table; let the
 * program lay the cards; and view the high score
 * table.
 * 
 * @author Dinia Grace Gepte
 * CSE114 Spring 2010 Final Project
 * L03 TA Yifu Ren
 */
public class PokerGame 
{	
	// Global variable(s)
	public static HighScoreTable highScores = new HighScoreTable();
	
	// Main method that includes importing the high score table
	public static void main(String[] args) throws IOException
	{
		highScores.streamInHighScoreTable();
		Scanner input = new Scanner(System.in);
		String choice = "?";
		while (!choice.equals("Q"))
		{
			mainMenuHeader();
			choice = mainMenu(input, choice);
			if (choice.equals("P"))
				playGame(input);
			else if (choice.equals("A"))
				autoFill();
			else if (choice.equals("V"))
				highScores(input);
		}
		System.out.println("\nThank you for playing. Goodbye!");
	}
	
	public static void mainMenuHeader()
	{
		System.out.println("\n******************************************");
		System.out.println("**    Welcome to the 2D Poker Game!!!\t**");
		System.out.println("**\t\t\t\t\t**");
		System.out.println("**    Please select an option below.\t**");
		System.out.println("******************************************");
	}
	
	// Options for main menu
	public static String mainMenu(Scanner input, String choice)
	{
		System.out.println("P) Play a Game");
		System.out.println("A) Automatically Fill Board");
		System.out.println("V) View High Scores");
		System.out.println("Q) Quit");
		System.out.print("Selection: ");
		return choice = input.nextLine().toUpperCase();
	}
	
	/*
	 * This method is executed when user chooses "P" from
	 * the main menu. It allows manual laying of cards on
	 * the table.
	 */
	public static void playGame(Scanner input) throws IOException
	{
		PokerTable table = new PokerTable();
		PlayingDeck deck = new PlayingDeck();
		deck.shuffle();
		String choice = "?";
		// This loop will keep on going until player
		// chooses to stop his current game.
		while (!choice.equals("N"))
		{
			int row = -1, column = -1;
			boolean cardPlacement = false;
			System.out.println(table.toString());
			String drawnCard = deck.draw();
			System.out.println("\nYour drawn card is: " + drawnCard);
			// This loop will keep on going until the player
			// chooses a row/column on the table that's unoccupied.
			while (cardPlacement == false)
			{
				// loop for COLUMN input
				while (column < 0 || column > 4)
				{
					try
					{
						System.out.print("Column to place card (1-5): ");
						column = input.nextInt() - 1;
						if (column < 0 || column > 4)
							System.out.println("\tInput invalid. Please enter a column from 1 to 5.");
					}
					catch(InputMismatchException e)
					{
						System.out.println("\tInput invalid. Enter a valid number only.");
						input.nextLine();
					}
				}
				// loop for ROW input
				while (row < 0 || row > 4)
				{
					// try-catch block to check if the user-input is a number. 
					try
					{
						System.out.print("Row to place card (1-5): ");
						row = input.nextInt() - 1;
						if (row < 0 || row > 4)
							System.out.println("\tInput invalid. Please enter a row from 1 to 5.");
					}
					catch(InputMismatchException e)
					{
						System.out.println("\tInput invalid. Enter a valid number only.");
						input.nextLine();
					}
				}
				if (table.getTableValue(row, column).equals("_"))
					cardPlacement = true;
				else
				{
					System.out.println("\tOOPS! You can't replace a card on the table."
							+ "\n\tEnter another location.");
					row = -1;
					column = -1;
				}
			}
			String continueOrNot = "?";
			while (!continueOrNot.equals("Y"))
			{
				System.out.print("Do you wish to continue your game (Y/N)? ");
				choice = input.next().toUpperCase();
				input.nextLine();
				if (choice.equals("Y") || choice.equals("N"))
					continueOrNot = "Y";
			}
			table.setTableValue(row, column, drawnCard);
			testForFullHandRow(table, row);		// tests if there is a full row
			testForFullHandColumn(table, column);	// tests if there is a full column
			// This happens when the last spot on the table is filled.
			if (Integer.parseInt(deck.toString()) == 24)
			{
				table.cardsAtHand(row, -1);
				table.scoring(row, -1);
				table.cardsAtHand(-1, column);
				table.scoring(-1, column);
				System.out.println(table.toString());
				highScoreTest(table.toString(), table.getTotalScore());
				choice = "N";
			}	
		}
	}

	/*
	 * This method is executed when user chooses "A" from
	 * the main menu. The program automatically fills the
	 * table and checks to see if a high score is obtained.
	 */
	public static void autoFill() throws IOException
	{
		PokerTable table = new PokerTable();
		PlayingDeck deck = new PlayingDeck();
		deck.shuffle();
		for (int row = 0; row < 5; row++)
		{
			for (int column = 0; column < 5; column++)
			{
				String drawnCard = deck.draw();
				table.setTableValue(row, column, drawnCard);
				testForFullHandRow(table, row);
				testForFullHandColumn(table, column);
				if(Integer.parseInt(deck.toString()) == 24)
				{
					table.cardsAtHand(row, -1);
					table.scoring(row, -1);
					table.cardsAtHand(-1, column);
					table.scoring(-1, column);
				}
			}
		}
		System.out.println(table.toString());
		highScoreTest(table.toString(), table.getTotalScore());
	}
	
	/*
	 * This method is executed when user chooses "V" from
	 * the main menu. It displays the high score table and
	 * allows the user to choose a rank to show its table
	 * history.
	 */
	public static void highScores(Scanner input) throws IOException
	{
		System.out.println(highScores.toString());
		String option = "?";
		// This loop executes until player wishes to
		// return to the main menu.
		while (!option.toUpperCase().equals("R"))
		{
			// try-catch block to check if the input is other than
			// the numbers from 1-10 or "R".
			try
			{
				System.out.print("Enter a rank number to view its game history "
						+ "or R to return to main menu: ");
				option = input.nextLine();
				if (Integer.parseInt(option) >= 1 && Integer.parseInt(option) <= 10)
				{
					highScores.streamInTable(Integer.parseInt(option));
					System.out.println(highScores.toString());
				}
				if (Integer.parseInt(option) < 1 || Integer.parseInt(option) > 10)
					System.out.println("\tInput invalid. Enter a number from 1-10.");
			}
			catch(NumberFormatException nfe)
			{	
				if (!option.toUpperCase().equals("R"))
					System.out.println("\tInput invalid. Please enter a number from 1-10 or R.");
			}	
		}
	}

	/*
	 * Method that tests if the given ROW is filled. If so,
	 * it calls on a method to set the score on that hand.
	 */
	public static void testForFullHandRow(PokerTable table, int row)
	{
		int count = 0;
		for (int column = 0; column < 5; column++)
		{
			if (!table.getTableValue(row, column).equals("_"))
				count++;
		}
		if (count == 5)
		{
			table.cardsAtHand(row, -1);
			table.scoring(row, -1);
		}
	}
	
	/*
	 * Method that tests if the given COLUMN is filled.
	 * If so, it calls on a method to set the score on
	 * that hand.
	 */
	public static void testForFullHandColumn(PokerTable table, int column)
	{
		int count = 0;
		for (int row = 0; row < 5; row++)
		{
			if (!table.getTableValue(row, column).equals("_"))
				count++;
		}
		if (count == 5)
		{
			table.cardsAtHand(-1, column);
			table.scoring(-1, column);
		}
	}
	
	/*
	 * Method that tests if a HIGH SCORE is achieved after
	 * a full table. If so, it calls a method to update the
	 * high score table and then sends a copy of the "winning"
	 * table to a method to save it for future reference.
	 */
	public static void highScoreTest(String table, int totalScore) throws IOException
	{
		int highScoreRow = highScores.testForHighScore(totalScore);
		if (highScoreRow != -1)
		{
			highScores.setHighScore(highScoreRow, totalScore);
			System.out.println("\nCongratulations! Your score of " + totalScore +
					" made it to the High Score Board!");
			highScores.streamOutTable(highScoreRow, table);
			highScores.streamOutHighScoreTable();
		}
		else
			System.out.println("\nSorry, your score of " + totalScore +
					" didn't make it to the High Score Board.");
	}
}
