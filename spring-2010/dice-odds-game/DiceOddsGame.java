import java.util.Scanner;
/*
 * Dice Odds Game program allows
 * the user to setup an account
 * to play in a gambling game.
 * The game is about betting on
 * a roll of dice given the odds
 * beforehand.
 * 
 * @author Dinia Grace Gepte
 * 		   CSE114, HW4
 * 		   L03, TA Yifu Ren
 */
public class DiceOddsGame 
{
	//Global variables
	static Scanner input = new Scanner(System.in);
	static Gambler gambler1;
	static Casino casino1 = new Casino("palazzio");
	static int numberOfRolls = 0, betRoll = 0, bet = 0;
	static PairOfDice doubleDice = new PairOfDice();

	public static void main(String[] args)
	{
		String choice = "?";
		while (!choice.toLowerCase().equals("q"))
		{
			choice = promptUser();
			//This will be executed when either 1 or 2 is chosen.
			if (choice.equals("1"))
				newGamblingAccount();
			else if (choice.equals("2"))
			{
				if (gambler1 == null)
					System.out.println("\nYou must setup a Gambling Account before you start playing!\n");
				else if (gambler1.getAccountBalance() < 10)
					System.out.println("\nYou do not have enough money to play!");
				else
				{
					String playOrNot = "y";
					while (playOrNot.toLowerCase().equals("y"))
					{
						trackerSetReset();
						placeBet();
						histogram();
						highestNumberOfRolls();
						//Error checking variable
						String legalInput;
						legalInput = "?";
						//Checks to see whether player won or lost.
						//Body of "if" will execute when player loses the bet.
						if ((betRoll != largest.getRollNumber()) && !testForEquality())
						{
							System.out.println(gambler1.getGamblerName() +
									", YOU LOSE $" + bet);
							gambler1.setAccountBalance(-bet);
							casino1.setAccountBalance(bet);
							System.out.println(casino1.toString() + gambler1.toString());
							//Checks if player has enough money to play.
							if (gambler1.getAccountBalance() >= 10)
							{
								while (!legalInput.equals("y"))
								{
									System.out.print("\nWould you like to make another bet? (y/n): ");
									playOrNot = input.next();
									if (playOrNot.toLowerCase().equals("y") || 
											playOrNot.toLowerCase().equals("n"))
									{
										legalInput = "y";
									}
									input.nextLine();
								}
							}
							else if (gambler1.getAccountBalance() < 10)
							{
								playOrNot = "n";
								System.out.println("\nPRESS ENTER WHEN YOU ARE READY TO "
										+ "RETURN TO THE MAIN MENU");
								input.nextLine();
								input.nextLine();
							}
						}
						//This will execute when player wins the bet.
						else if ((betRoll == largest.getRollNumber()) && !testForEquality())
						{
							System.out.println(gambler1.getGamblerName() +
									", YOU WIN $" + (bet * largest.getOdds()));
							gambler1.setAccountBalance((bet * largest.getOdds()) - bet);
							casino1.setAccountBalance(-((bet * largest.getOdds()) - bet));
							System.out.println(casino1.toString() + gambler1.toString());
							//Checks to see if casino still has money.
							if (casino1.getAccountBalance() == 0)
							{
								System.out.println("THE PALAZZIO is bankrupt!" +
								"\n\nThank you for playing with us. GOODBYE!");
								System.exit(0);
							}
							while (!legalInput.equals("y"))
							{
								System.out.print("\nWould you like to make another bet? (y/n): ");
								playOrNot = input.next();
								if (playOrNot.toLowerCase().equals("y") || 
										playOrNot.toLowerCase().equals("n"))
								{
									legalInput = "y";
								}
								input.nextLine();
							}
						}
					}	
				}
			}
		}
		System.out.println("\nGOODBYE!");
	}

	//This method will prompt the user to either:
	//(1) Setup a new account
	//(2) Make a bet
	//(q) Quit
	public static String promptUser()
	{
		System.out.println("\n\n**********************************************************");
		System.out.println("**\t\tDice Odds Game Main Menu\t\t**");
		System.out.println("**\t\t\t\t\t\t\t**");
		System.out.println("** Please type a menu option and press enter.\t\t**");
		System.out.println("**********************************************************");
		System.out.println("\n1) Setup a new gambling account");
		System.out.println("2) Make a bet");
		System.out.println("q) Quit");
		System.out.print("\nSelection: ");
		return input.nextLine();
	}
	
	//This method will be executed whenever user
	//picks option (1) from the main menu.
	//It allows the user the setup a new gambling
	//account which consists of name and
	//initial betting money.
	public static void newGamblingAccount()
	{
		System.out.println("\n\n**********************************************************");
		System.out.println("**\t\tWelcome to THE PALLAZIO!\t\t**");
		System.out.println("**\t\t\t\t\t\t\t**");
		System.out.println("**\t--Where all your gambling needs are met--\t**");
		System.out.println("**\t\t\t\t\t\t\t**\n**\t\t\t\t\t\t\t**");
		System.out.println("** To setup your account,\t\t\t\t**" +
				"\n** please answer the following questions.\t\t**");
		System.out.println("**********************************************************");
		
		System.out.print("\nPlease Enter your Name: ");
		String initName = input.nextLine();
		int initAccount = 0;
		do
		{
			System.out.print("\nPlease Enter your Starting Balance ($10 Min, $100000 Max): $");
			initAccount = input.nextInt();
			if ((initAccount < 10) || (initAccount > 100000))
				System.out.println("\tYour entered amount is INVALID.");
		} while((initAccount < 10) || (initAccount > 100000));
		gambler1 = new Gambler(initName, initAccount);
		
		System.out.println("\nThank you, " + gambler1.getGamblerName() +
				", for opening an account with us.");
		System.out.print("\nPRESS ENTER WHEN YOU ARE READY TO CONTINUE\n\n");
		input.nextLine();
		input.nextLine();
	}

	//This method asks the user:
	//--the number of rolls of dice
	//--the number of roll
	//--the amount of bet
	//Also, the list of odds for every roll will
	//be printed here.
	public static void placeBet()
	{
		System.out.println("\n\n**********************************************************");
		System.out.println("**\t   You are playing DICE at THE PALAZZIO!\t**");
		System.out.println("**\t\t\t\t\t\t\t**");
		System.out.println("**\t\tWe hope you enjoy your stay.\t\t**");
		System.out.println("**\t\t\t\t\t\t\t**");
		System.out.println("**********************************************************");
		
		do
		{
			System.out.print("\n" + gambler1.getGamblerName() + 
					", enter the # of rolls you are betting on (1 - 50): ");
			numberOfRolls = input.nextInt();
			if (numberOfRolls < 1 || numberOfRolls > 50)
				System.out.println("\tYour entered # of rolls is INVALID.");
		} while(numberOfRolls < 1 || numberOfRolls > 50);
		setOdds();
		listOfOdds();
		do
		{
			System.out.print("\n" + gambler1.getGamblerName() +
					", what roll would you like to bet on? (2 - 12) ");
			betRoll = input.nextInt();
			if (betRoll < 2 || betRoll > 12)
				System.out.println("\tYour entered roll number is INVALID.");
		} while(betRoll < 2 || betRoll > 12);
		do
		{
			do
			{
				System.out.print("\n" + gambler1.getGamblerName() +
						", enter your bet (min $10, max $" + gambler1.getAccountBalance() + "): $");
				bet = input.nextInt();
				if (bet > gambler1.getAccountBalance() || bet < 10)
					System.out.println("\tYour entered bet is INVALID.");
			}while (bet < 10 || bet > gambler1.getAccountBalance());
			if (!betLegal())
			{
				System.out.println("We're sorry, the casino doesn't have enough"
						+ " money to bet against you." + "\n\tPlease choose another" +
						" bet with a maximum amount of $" + betMax() + ".");
			}
		} while (!betLegal());
	}
	
	//Creates global Tracker objects
	//that will store the roll number,
	//odds of each, and the number of
	//occurrences when the dice are played.
	static Tracker two = new Tracker(2);
	static Tracker three = new Tracker(3);
	static Tracker four = new Tracker(4);
	static Tracker five = new Tracker(5);
	static Tracker six = new Tracker(6);
	static Tracker seven = new Tracker(7);
	static Tracker eight = new Tracker(8);
	static Tracker nine = new Tracker(9);
	static Tracker ten = new Tracker(10);
	static Tracker eleven = new Tracker(11);
	static Tracker twelve = new Tracker(12);
	static Tracker largest = new Tracker();
	
	//This method assigns the odds to the
	//corresponding roll number depending
	//on the number of rolls of dice the
	//user picked.
	
	public static void setOdds()
	{
		two.setOdds(casino1.rolls2and12odds(numberOfRolls));
		three.setOdds(casino1.rolls3and11odds(numberOfRolls));
		four.setOdds(casino1.rolls4and10odds(numberOfRolls));
		five.setOdds(casino1.rolls5and9odds(numberOfRolls));
		six.setOdds(casino1.rolls6and8odds(numberOfRolls));
		seven.setOdds(casino1.rolls7odds(numberOfRolls));
		eight.setOdds(casino1.rolls6and8odds(numberOfRolls));
		nine.setOdds(casino1.rolls5and9odds(numberOfRolls));
		ten.setOdds(casino1.rolls4and10odds(numberOfRolls));
		eleven.setOdds(casino1.rolls3and11odds(numberOfRolls));
		twelve.setOdds(casino1.rolls2and12odds(numberOfRolls));
	}
	
	//This method prints the list of odds
	//which is based on the number of rolls
	//of dice the user picked.
	
	public static void listOfOdds()
	{
		System.out.println("\nFOR " + numberOfRolls + " roll(s), WE USE THE FOLLOWING ODDS:" +
				"\n2:\t" + two.getOdds() + " to 1" +
				"\n3:\t" + three.getOdds() + " to 1" +
				"\n4:\t" + four.getOdds() + " to 1" +
				"\n5:\t" + five.getOdds() + " to 1" +
				"\n6:\t" + six.getOdds() + " to 1" +
				"\n7:\t" + seven.getOdds() + " to 1" +
				"\n8:\t" + eight.getOdds() + " to 1" +
				"\n9:\t" + nine.getOdds() + " to 1" +
				"\n10:\t" + ten.getOdds() + " to 1" +
				"\n11:\t" + eleven.getOdds() + " to 1" +
				"\n12:\t" + twelve.getOdds() + " to 1");
	}
	
	//This method shows the number of occurrences
	//of a rolled number and prints a histogram
	//showing it.
	public static void histogram()
	{
		for (int i = 0; i < numberOfRolls; i++)
		{
			doubleDice.rollDice();
			int total = doubleDice.getTotal();
			if (total == 2)
				two.setOccurrence();
			if (total == 3)
				three.setOccurrence();
			if (total == 4)
				four.setOccurrence();
			if (total == 5)
				five.setOccurrence();
			if (total == 6)
				six.setOccurrence();
			if (total == 7)
				seven.setOccurrence();
			if (total == 8)
				eight.setOccurrence();
			if (total == 9)
				nine.setOccurrence();
			if (total == 10)
				ten.setOccurrence();
			if (total == 11)
				eleven.setOccurrence();
			if (total == 12)
				twelve.setOccurrence();
		}
		System.out.println("\nRESULTS OF " + numberOfRolls + " ROLL(S):");
		System.out.println("2:\t(" + two.getOccurrence() + ")" + lengthOfBars(two.getOccurrence())
				+ "\n3:\t(" + three.getOccurrence() + ")" + lengthOfBars(three.getOccurrence())
				+ "\n4:\t(" + four.getOccurrence() + ")" + lengthOfBars(four.getOccurrence())
				+ "\n5:\t(" + five.getOccurrence() + ")" + lengthOfBars(five.getOccurrence())
				+ "\n6:\t(" + six.getOccurrence() + ")" + lengthOfBars(six.getOccurrence())
				+ "\n7:\t(" + seven.getOccurrence() + ")" + lengthOfBars(seven.getOccurrence())
				+ "\n8:\t(" + eight.getOccurrence() + ")" + lengthOfBars(eight.getOccurrence())
				+ "\n9:\t(" + nine.getOccurrence() + ")" + lengthOfBars(nine.getOccurrence())
				+ "\n10:\t(" + ten.getOccurrence() + ")" + lengthOfBars(ten.getOccurrence())
				+ "\n11:\t(" + eleven.getOccurrence() + ")" + lengthOfBars(eleven.getOccurrence())
				+ "\n12:\t(" + twelve.getOccurrence() + ")" + lengthOfBars(twelve.getOccurrence()));
	}
	
	//This method specifies the number of
	//"X" that will be printed out to form
	//the bar lines in the histogram.
	public static String lengthOfBars(int count)
	{
		String s = "";
		for (int i = 0; i < count; i++)
			s = s + "X";
		return s;
	}
	
	//This method determines the highest number
	//of roll occurrence and prints the roll
	//and how many times it occurred.
	public static void highestNumberOfRolls()
	{
		if (two.getOccurrence() > three.getOccurrence())
			largest.setAll(two.getOccurrence(), two.getRollNumber(), two.getOdds());
		else
			largest.setAll(three.getOccurrence(), three.getRollNumber(), three.getOdds());
		if (largest.getOccurrence() < four.getOccurrence())
			largest.setAll(four.getOccurrence(), four.getRollNumber(), four.getOdds());
		if (largest.getOccurrence() < five.getOccurrence())
			largest.setAll(five.getOccurrence(), five.getRollNumber(), five.getOdds());
		if (largest.getOccurrence() < six.getOccurrence())
			largest.setAll(six.getOccurrence(), six.getRollNumber(), six.getOdds());
		if (largest.getOccurrence() < seven.getOccurrence())
			largest.setAll(seven.getOccurrence(), seven.getRollNumber(), seven.getOdds());
		if (largest.getOccurrence() < eight.getOccurrence())
			largest.setAll(eight.getOccurrence(), eight.getRollNumber(), eight.getOdds());
		if (largest.getOccurrence() < nine.getOccurrence())
			largest.setAll(nine.getOccurrence(), nine.getRollNumber(), nine.getOdds());
		if (largest.getOccurrence() < ten.getOccurrence())
			largest.setAll(ten.getOccurrence(), ten.getRollNumber(), ten.getOdds());
		if (largest.getOccurrence() < eleven.getOccurrence())
			largest.setAll(eleven.getOccurrence(), eleven.getRollNumber(), eleven.getOdds());
		if (largest.getOccurrence() < twelve.getOccurrence())
			largest.setAll(twelve.getOccurrence(), twelve.getRollNumber(), twelve.getOdds());
		if (testForEquality())
			System.out.println("\nNO single roll wins. ALL bets are off.");
		else
		{
			System.out.println("\nThe roll of " + largest.getRollNumber() + " wins!");
			System.out.println("It was rolled " + largest.getOccurrence() + " time(s)!");
		}
	}
	
	//This method will return "true" if at least 2
	//roll numbers occurred the same number
	//of times. Otherwise, it will return "false".
	public static boolean testForEquality()
	{
		int count = 0;
		if (largest.getOccurrence() == two.getOccurrence())
			count++;
		if (largest.getOccurrence() == three.getOccurrence())
			count++;
		if (largest.getOccurrence() == four.getOccurrence())
			count++;
		if (largest.getOccurrence() == five.getOccurrence())
			count++;
		if (largest.getOccurrence() == six.getOccurrence())
			count++;
		if (largest.getOccurrence() == seven.getOccurrence())
			count++;
		if (largest.getOccurrence() == eight.getOccurrence())
			count++;
		if (largest.getOccurrence() == nine.getOccurrence())
			count++;
		if (largest.getOccurrence() == ten.getOccurrence())
			count++;
		if (largest.getOccurrence() == eleven.getOccurrence())
			count++;
		if (largest.getOccurrence() == twelve.getOccurrence())
			count++;
		return (count > 1);
	}
	
	//This method returns "true" if the casino
	//is still capable of betting against the
	//user. "false" if otherwise.
	public static boolean betLegal()
	{
		if ((betRoll == two.getRollNumber()) || (betRoll == twelve.getRollNumber()))
			return ((bet * two.getOdds()) <= casino1.getAccountBalance());
		else if ((betRoll == three.getRollNumber()) || (betRoll == eleven.getRollNumber()))
			return ((bet * three.getOdds()) <= casino1.getAccountBalance());
		else if ((betRoll == four.getRollNumber()) || (betRoll == ten.getRollNumber()))
			return ((bet * four.getOdds()) <= casino1.getAccountBalance());
		else if ((betRoll == five.getRollNumber()) || (betRoll == nine.getRollNumber()))
			return ((bet * five.getOdds()) <= casino1.getAccountBalance());
		else if ((betRoll == six.getRollNumber()) || (betRoll == eight.getRollNumber()))
			return ((bet * six.getOdds()) <= casino1.getAccountBalance());
		else
			return ((bet * seven.getOdds()) <= casino1.getAccountBalance());
	}
	
	//This method gives the maximum amount
	//the casino is capable of betting against
	//the user.
	public static int betMax()
	{
		int max = 0;
		if (betRoll == 2 || betRoll == 12)
			max = casino1.getAccountBalance() / two.getOdds();
		if (betRoll == 3 || betRoll == 11)
			max = casino1.getAccountBalance() / three.getOdds();
		if (betRoll == 4 || betRoll == 10)
			max = casino1.getAccountBalance() / four.getOdds();
		if (betRoll == 5 || betRoll == 9)
			max = casino1.getAccountBalance() / five.getOdds();
		if (betRoll == 6 || betRoll == 8)
			max = casino1.getAccountBalance() / six.getOdds();
		if (betRoll == 7)
			max = casino1.getAccountBalance() / seven.getOdds();
		return max;
	}
	
	//This method resets the Tracker objects
	//to their initial values.
	public static void trackerSetReset()
	{
		two.reset();
		three.reset();
		four.reset();
		five.reset();
		six.reset();
		seven.reset();
		eight.reset();
		nine.reset();
		ten.reset();
		eleven.reset();
		twelve.reset();
	}

}
