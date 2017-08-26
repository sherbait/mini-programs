/*
 * PlayingCard class that creates an object
 * of playing card. It has the usual playing
 * card components of: suits (H)eart, (S)pade,
 * (D)iamond, and (C)lub; and ranks 1-13.
 * Values of the "letter" cards are as follows:
 * A = 1, J = 11, Q = 12, K = 13
 * 
 * @author Dinia Grace Gepte
 * CSE114 Spring 2010 Final Project
 * L03 TA Yifu Ren
 */
public class PlayingCard
{
	// Instance variables
	private char suit;
	private int rank;
	
	// Constructor taken from the lecture slides that
	// creates the card.
	public PlayingCard(char initSuit, int initRank)
	{
		if ( (initSuit == 'C' || initSuit == 'D' || initSuit == 'H' || initSuit == 'S')
				&& (initRank >= 1 && initRank <=13) )
		{
			suit = initSuit;
			rank = initRank;
		}
	}
	
	public char getSuit() {		return suit;	}
	public int getRank()  {		return rank;	}
	
	// Returns a String representation of the card.
	// e.g. AH = ace of hearts
	//		2H = 2 of hearts
	public String toString()
	{
		char cardChar = '?';
		if ( (rank > 10 && rank <= 13) || (rank == 1) )
		{
			switch(rank)
			{
			case 1: cardChar = 'A';		break;
			case 11: cardChar = 'J';	break;
			case 12: cardChar = 'Q';	break;
			case 13: cardChar = 'K';	break;
			}
			return "" + cardChar + suit;
		}
		else
			return "" + rank + suit;
	}
}
