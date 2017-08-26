/*
 * PlayingDeck class that creates a 52-card regular
 * card deck.
 * 
 * @author Dinia Grace Gepte
 * CSE114 Spring 2010 Final Project
 * L03 TA Yifu Ren
 */
public class PlayingDeck
{
	// Instance variables
	private PlayingCard[] deck;
	private int numCards = 52;
	private int drawnCards = -1;
	
	// Constructor taken from lecture slides that
	// creates the actual un-shuffled deck.
	public PlayingDeck()
	{
		int cardNum = 0;
		int suitNum, rankNum;
		char suitChar = '?';
		deck = new PlayingCard[numCards];
		
		for (suitNum = 1; suitNum <= 4; suitNum++)
		{
			switch(suitNum)
			{
			case 1: suitChar = 'C'; 	break;
			case 2: suitChar = 'D'; 	break;
			case 3: suitChar = 'H'; 	break;
			case 4: suitChar = 'S'; 	break;
			}
			for (rankNum = 1; rankNum <= 13; rankNum++)
			{
				deck[cardNum] = new PlayingCard(suitChar, rankNum);
				cardNum++;
			}
		}
	}
	
	/*
	 * Method that shuffles the deck. It creates a
	 * temporary 52-card deck to place the randomly
	 * selected cards from the original deck created
	 * by the constructor method. It then gives the
	 * cards to the original deck.
	 */
	public void shuffle()
	{
		PlayingCard[] shuffledDeck = new PlayingCard[numCards];
		int shuffledDeckIndex = 0;
		while (shuffledDeckIndex != numCards)
		{
			int i = (int)(Math.random() * numCards);
			if (deck[i] != null)
			{
				shuffledDeck[shuffledDeckIndex] = 
					new PlayingCard(deck[i].getSuit(), deck[i].getRank());
				deck[i] = null;
				shuffledDeckIndex++;
			}
		}
		for (int j = 0; j < numCards; j++)
		{
			deck[j] = shuffledDeck[j];
		}
		drawnCards = -1;
	}
	
	/*
	 * Draws the card with index "drawnCards" which
	 * increments every time the method is called.
	 * Every time the deck is shuffled or a deck is
	 * created, the "drawnCards" variable resets to
	 * -1. If the drawnCards reaches the number of 
	 * deck card number, it is set to 0.
	 */
	public String draw()
	{
		drawnCards++;
		if (drawnCards == numCards)
			drawnCards = 0;
		return deck[drawnCards].toString();
	}
	
	// Represents the number of drawn cards so far.
	public String toString()
	{
		return "" + drawnCards;
	}
}
