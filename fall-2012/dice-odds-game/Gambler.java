/*
 * This Gambler class contains the name of
 * the gambler, his account balance, the
 * percent win or loss, and a brief status
 * of the gambler.
 * 
 * @author Dinia Grace Gepte
 * 		   CSE114, HW4
 * 		   L03, TA Yifu Ren
 */
public class Gambler 
{
	//Instance variables
	private String gamblerName;
	private int accountBalance;
	private int initBalance;
	
	//This constructor accepts a String argument
	//to set the gambler's name and an int argument
	//to set the starting balance of the gambler.
	public Gambler(String initName, int initAccountBalance)
	{
		gamblerName = initName;
		if (initAccountBalance < 10)
		{
			initBalance = 10;
			accountBalance = 10;
		}
		else if (initAccountBalance > 100000)
		{
			initBalance = 100000;
			accountBalance = 100000;
		}
		else
		{
			initBalance = initAccountBalance;
			accountBalance = initAccountBalance;
		}
	}
	
	//This method accepts an int argument
	//to add or subtract money from the
	//gambler's account balance.
	public void setAccountBalance(int money)
	{
		accountBalance = accountBalance + money;
	}
	
	//This method returns the gambler's name.
	public String getGamblerName()
	{
		return gamblerName;
	}
	
	//This method returns the gambler's account balance.
	public int getAccountBalance()
	{
		return accountBalance;
	}
	
	//This method calculates the percent win or loss
	//of the gambler.
	public double percentage()
	{
		double number = (((double)accountBalance - (double)initBalance)/(double)initBalance) * 100;
		number = Math.abs(number);
		return number;
	}
	
	//This method returns a brief
	//"status report" of the gambler.
	public String toString()
	{
		if (accountBalance > initBalance)
		{
			return "\n" + gamblerName + ", you have a balance of $" + accountBalance + "." +
				"\n\tYou've increased your account by " + percentage() + "% since starting." +
				"\n\tSomeone's lucky today!";
		}
		else if (accountBalance == initBalance)
		{
			return "\n" + gamblerName + ", you have a balance of $" + accountBalance + "." +
				"\n\tYou haven't won or lost anything.";
		}
		else if (accountBalance >= 10)
		{
			return "\n" + gamblerName + ", you have a balance of $" + accountBalance + "." +
				"\n\tYou've lost " + percentage() + "% of your money." +
				"\n\tDon't worry, it happens. Try again!";
		}
		else
		{
			return "\n" + gamblerName + ", you have a balance of $" + accountBalance + "." +
			"\n\tYou've lost " + percentage() + "% of your money." +
			"\n\tYou don't have enough money to play!";
		}
	}
}
