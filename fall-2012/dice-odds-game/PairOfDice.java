
public class PairOfDice 
{
	private Die die1 = new Die();
	private Die die2 = new Die();
	
	public PairOfDice(){}
	
	public int getTotal()
	{
		return die1.getUpValue() + die2.getUpValue();
	}
	
	public void rollDice()
	{
		die1.roll();
		die2.roll();
	}
}
