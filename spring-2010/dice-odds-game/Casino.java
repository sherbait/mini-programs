/*
 * This Casino class has the name of the
 * casino, its account balance, odds
 * for every roll number (2-12), the
 * casino factor of determining the odds,
 * the percent win or loss, and a brief
 * status of the casino.
 * 
 * @author Dinia Grace Gepte
 * 		   CSE114, HW4
 * 		   L03, TA Yifu Ren
 */
public class Casino 
{
	//Instance variables
	private String casinoName;
	private int accountBalance;
	private int initBalance;
	
	//This constructor accepts a String argument
	//which becomes the name of the casino,
	//and also sets the casino balance to be
	//$1,000,000.
	public Casino(String initCasinoName)
	{
		casinoName = initCasinoName;
		accountBalance = 1000000;
		initBalance = 1000000;
	}
	
	//This method accepts an int argument
	//which enables the user to add or
	//subtract money from the casino.
	public void setAccountBalance(int money)
	{
		accountBalance = accountBalance + money;
	}
	
	//This method returns the current
	//casino account balance.
	public int getAccountBalance()
	{
		return accountBalance;
	}
	
	//This method accepts a number of
	//rolls then returns the odds for
	//roll number 2 or 12.
	public int rolls2and12odds(int rolls)
	{
		int calculatedOdds = (int)(36 * Math.pow((1.0 + (3 * casinoFactor())), rolls));
		return calculatedOdds;
	}
	
	//This method accepts a number of
	//rolls then returns the odds for
	//roll number 3 or 11.
	public int rolls3and11odds(int rolls)
	{
		int calculatedOdds = (int)(36 * Math.pow((1.0 + (2 * casinoFactor())), rolls));
		return calculatedOdds;
	}
	
	//This method accepts a number of
	//rolls then returns the odds for
	//roll number 4 or 10
	public int rolls4and10odds(int rolls)
	{
		int calculatedOdds = (int)(36 * Math.pow((1.0 + casinoFactor()), rolls));
		return calculatedOdds;
	}
	
	//This method accepts a number of
	//rolls then returns the odds for
	//roll number 5 or 9.
	public int rolls5and9odds(int rolls)
	{
		int calculatedOdds = 9;
		return calculatedOdds;	
	}
	
	//This method accepts a number of
	//rolls then returns the odds for
	//roll number 6 or 8.
	public int rolls6and8odds(int rolls)
	{
		int calculatedOdds = (int)(7.2 * Math.pow((1.0 - casinoFactor()), rolls));
		return calculatedOdds;
	}

	//This method accepts a number of
	//rolls then returns the odds for
	//roll number 7.
	public int rolls7odds(int rolls)
	{
		int calculatedOdds = (int)(6.0 * Math.pow((1.0 - (2 * casinoFactor())), rolls));
		return calculatedOdds;
	}
	
	//This method determines which casino
	//factor to use to set the odds depending
	//on the casino account balance.
	public double casinoFactor()
	{
		double winningFactor = 0.005;
		double losingFactor = 0.01;
		if (accountBalance > 1000000)
			return winningFactor;
		else
			return losingFactor;
	}
	
	//This method determines the percentage
	//win or loss of the casino.
	public double percentage()
	{
		double number = (((double)accountBalance - (double)initBalance)/(double)initBalance) * 100;
		number = Math.abs(number);
		return number;
	}
	
	//This method returns a brief
	//"status report" of the casino.
	public String toString()
	{
		if (accountBalance > initBalance)
		{
			return "\nThe Bank's account is now at $" + accountBalance + "." +
				"\n\tOverall, the bank's balances are up " + percentage() + "%";
		}
		else if (accountBalance == initBalance)
		{
			return "\nThe Bank's account is now at $" + accountBalance + "." +
				"\n\tOverall, the bank's balances have not changed.";
		}
		else
		{
			return "\nThe bank's account is now at $" + accountBalance + "." +
				"\n\tOverall, the bank's balances are down " + percentage() + "%";
		}
	}
}
