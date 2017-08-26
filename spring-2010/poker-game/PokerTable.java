/*
 * This PokerTable class is an object representing
 * the poker table. It has a 5x5 grid to place
 * the cards and an extra 5x1 row and 1x5 column
 * for scores.
 * 
 * @author Dinia Grace Gepte
 * CSE114 Spring 2010 Final Project
 * L03 TA Yifu Ren
 */
public class PokerTable 
{
	// Instance variables
	private String[][] table;
	private int[] rowScore;
	private int[] columnScore;
	private int totalScore;
	private PlayingCard[] hand = new PlayingCard[5];
	
	// Constructor method
	public PokerTable()
	{
		table = new String[6][6];
		rowScore = new int[5];
		columnScore = new int[5];
		resetTable();
	}
	
	/*
	 * If a row/column has 5 cards (full hand), this 
	 * method creates a 5-card PlayingCard array that 
	 * represents the cards in hand. It also sorts 
	 * the cards by rank.
	 */
	public void cardsAtHand(int row, int column)
	{
		if (row != -1)
		{
			for (int c = 0; c < 5; c++)
				hand[c] = stringCardToPlayingCard(table[row][c]);
			sortCards(hand);
		}
		if (column != -1)
		{
			for (int r = 0; r < 5; r++)
				hand[r] = stringCardToPlayingCard(table[r][column]);
			sortCards(hand);
		}
	}
	
	/*
	 * This method calls each of the winning hands
	 * to see if there is any, then it updates
	 * the total score of each row, column, and
	 * game table score.
	 */
	public void scoring(int row, int column)
	{
		if (straightFlush())
		{
			if (row != -1)
				rowScore[row] = 75;
			if (column != -1)
				columnScore[column] = 75;
		}
			
		else if (fourOfAKind())
		{
			if (row != -1)
				rowScore[row] = 50;
			if (column != -1)
				columnScore[column] = 50;
		}
		else if (fullHouse())
		{
			if (row != -1)
				rowScore[row] = 25;
			if (column != -1)
				columnScore[column] = 25;
		}
		else if (flush())
		{
			if (row != -1)
				rowScore[row] = 20;
			if (column != -1)
				columnScore[column] = 20;
		}
		else if (straight())
		{
			if (row != -1)
				rowScore[row] = 15;
			if (column != -1)
				columnScore[column] = 15;
		}
		else if (threeOfAKind())
		{
			if (row != -1)
				rowScore[row] = 10;
			if (column != -1)
				columnScore[column] = 10;
		}
		else if (twoPairs())
		{
			if (row != -1)
				rowScore[row] = 5;
			if (column != -1)
				columnScore[column] = 5;
		}
		else if (onePair())
		{
			if (row != -1)
				rowScore[row] = 2;
			if (column != -1)
				columnScore[column] = 2;
		}
		else
			totalScore = totalScore + 0;
		int totalRowScore = 0, totalColumnScore = 0;
		for (int i = 0; i < 5; i++)
		{
			totalRowScore = totalRowScore + rowScore[i];
			totalColumnScore = totalColumnScore + columnScore[i];
		}
			totalScore = totalRowScore + totalColumnScore;
	}
	
	// Checks to see if the cards in hand form a straight flush.
	public boolean straightFlush()
	{
		boolean flag = false;
		if ( (hand[1].getRank() == hand[0].getRank()+1 &&
			  hand[2].getRank() == hand[1].getRank()+1 &&
			  hand[3].getRank() == hand[2].getRank()+1 &&
			  hand[4].getRank() == hand[3].getRank()+1) &&
			 (hand[0].getSuit() == hand[1].getSuit() &&
			  hand[1].getSuit() == hand[2].getSuit() &&
			  hand[2].getSuit() == hand[3].getSuit() &&
			  hand[3].getSuit() == hand[4].getSuit()) )
			flag = true;
		return flag;
	}
	
	// Checks to see if the cards in hand form a four of a kind (same rank).
	public boolean fourOfAKind()
	{
		boolean flag = false;
		if ( (hand[0].getRank() == hand[1].getRank() &&
			  hand[1].getRank() == hand[2].getRank() &&
			  hand[2].getRank() == hand[3].getRank()) ||
			 (hand[1].getRank() == hand[2].getRank() &&
			  hand[2].getRank() == hand[3].getRank() &&
			  hand[3].getRank() == hand[4].getRank()) )
			flag = true;
		return flag;
	}
	
	// Checks to see if the cards in hand form a full house (3 same rank & 2 same rank).
	public boolean fullHouse()
	{
		boolean flag = false;
		if ( (hand[0].getRank() == hand[1].getRank() &&
			  hand[2].getRank() == hand[3].getRank() &&
			  hand[3].getRank() == hand[4].getRank()) ||
			 (hand[0].getRank() == hand[1].getRank() &&
			  hand[1].getRank() == hand[2].getRank() &&
			  hand[3].getRank() == hand[4].getRank()) )
			flag = true;
		return flag;	  
	}
	
	// Checks to see if the cards in hand form a flush (same suit).
	public boolean flush()
	{
		boolean flag = false;
		if (hand[0].getSuit() == hand[1].getSuit() &&
			hand[1].getSuit() == hand[2].getSuit() &&
			hand[2].getSuit() == hand[3].getSuit() &&
			hand[3].getSuit() == hand[4].getSuit())
			flag = true;
		return flag;
	}
	
	// Checks to see if the cards in hand form a straight.
	public boolean straight()
	{
		boolean flag = false;
		if (hand[1].getRank() == hand[0].getRank()+1 &&
		    hand[2].getRank() == hand[1].getRank()+1 &&
		    hand[3].getRank() == hand[2].getRank()+1 &&
		    hand[4].getRank() == hand[3].getRank()+1)
			flag = true;
		return flag;	
	}
	
	// Checks to see if the cards in hand form a three of a kind (same rank).
	public boolean threeOfAKind()
	{
		boolean flag = false;
		if ( (hand[0].getRank() == hand[1].getRank() &&
			  hand[1].getRank() == hand[2].getRank()) ||
			 (hand[1].getRank() == hand[2].getRank() &&
			  hand[2].getRank() == hand[3].getRank()) ||
			 (hand[2].getRank() == hand[3].getRank() &&
			  hand[3].getRank() == hand[4].getRank()) )
			flag = true;
		return flag;
	}
	
	// Checks to see if the cards in hand form two pairs (same rank).
	public boolean twoPairs()
	{
		boolean flag = false;
		if ( (hand[0].getRank() == hand[1].getRank() &&
			  hand[2].getRank() == hand[3].getRank()) ||
			 (hand[0].getRank() == hand[1].getRank() &&
			  hand[3].getRank() == hand[4].getRank()) ||
			 (hand[1].getRank() == hand[2].getRank() &&
			  hand[3].getRank() == hand[4].getRank()) )
			flag = true;
		return flag;
	}
	
	// Checks to see if the cards in hand form one pair (same rank).
	public boolean onePair()
	{
		boolean flag = false;
		if (hand[0].getRank() == hand[1].getRank() ||
			hand[1].getRank() == hand[2].getRank() ||
			hand[2].getRank() == hand[3].getRank() ||
			hand[3].getRank() == hand[4].getRank())
			flag = true;
		return flag;
	}
	
	// Sorts the cards in order by rank.
	public void sortCards(PlayingCard[] data)
	{
		int j, k , minIndex;
		for (j = 0; j <= data.length-2; j++)
		{
			minIndex = j;
			for (k = j+1; k <= data.length-1; k++)
				if (data[k].getRank() < data[minIndex].getRank())
					minIndex = k;
			PlayingCard temp = data[j];
			data[j] = data[minIndex];
			data[minIndex] = temp;
		}
	}
	
	public void setTableValue(int row, int column, String s)
	{
		table[row][column] = s;
	}
	
	public String getTableValue(int row, int column)
	{
		return table[row][column];
	}
	
	public int getTotalScore()	{	return totalScore;	}
	
	// Resets the poker table to have no cards in place
	// and 0 scores.
	public void resetTable()
	{
		for (int row = 0; row <= 4; row++)
		{
			for (int column = 0; column <= 5; column++)
			{
				if (column == 5)
					table[row][column] = "";
				else
					table[row][column] = "_";
			}
		}
		for (int row = 5; row == 5; row++)
		{
			for (int column = 0; column <= 4; column++)
				table[row][column] = "";
		}
	}
	
	// Converts the taken String representation of PlayingCard
	// into the a PlayingCard object.
	public PlayingCard stringCardToPlayingCard(String s)
	{
		PlayingCard card;
		char suit = s.charAt(s.length()-1);
		int rank = 0;
		String rankString = s.substring(0, s.length()-1);
		if (rankString.charAt(0) == 'A' || rankString.charAt(0) == 'J' ||
				rankString.charAt(0) == 'Q' || rankString.charAt(0) == 'K' )
		{
			switch(rankString.charAt(0))
			{
			case 'A': rank = 1;		break;
			case 'J': rank = 11;	break;
			case 'Q': rank = 12;	break;
			case 'K': rank = 13;	break;
			}
			return (card = new PlayingCard(suit, rank));
		}
		else
		{
			rank = Integer.parseInt(rankString);
			return (card = new PlayingCard(suit, rank));
		}
	}
	
	// String representation of the poker table showing
	// the total score and corresponding row/column scores.
	public String toString()
	{
		String totalScoreLine = "\nTotal Score: " + totalScore + "\n";
		String grid = "";
		
		for (int row = 0; row <= 4; row++)
		{
			for (int column = 0; column <= 5; column++)
			{
				if (column == 5)
					grid = grid + "|\t" + rowScore[row] + "\n";
				else
					grid = grid + "|\t" + table[row][column] + "\t";
			}
		}
		for (int row = 5; row == 5; row++)
		{
			for (int column = 0; column <= 4; column++)
				grid = grid + "\t" + columnScore[column] + "\t";
		}
		
		return totalScoreLine + grid;
	}
}
