package budget_manager;

import java.io.Serializable;

/**
 * This class represents each item in the BalanceManager table.
 * 
 * @author Dinia Gepte
 *
 */
public class BudgetItem implements Serializable
{
	private String name;
	private double amount;
	
	/**
	 * Constructor of this class that initializes the variables
	 * with the parameters.
	 * 
	 * @param initName
	 *   name of the item
	 * @param initAmount
	 *   value associated with this item, presumably currency
	 */
	public BudgetItem(String initName, double initAmount)
	{
		name = initName;
		amount = initAmount;
	}
	
	// ACCESSOR METHODS
	public String getName()			{	return name;	}
	public double getAmount()		{	return amount;	}
	
	// MUTATOR METHODS
	public void setName(String initName)		{	name = initName;		}
	public void setAmount(double initAmount)	{	amount = initAmount;	}
}
