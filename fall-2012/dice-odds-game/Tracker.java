/*
 * This Tracker class describes a rolled number.
 * It consists of the rolled number, occurrence,
 * and odds.
 * 
 * @author Dinia Grace Gepte
 * 		   CSE114, HW4
 * 		   L03, TA Yifu Ren
 */
public class Tracker 
{
	//Instance variables
	private int rollNumber;
	private int occurrence;
	private int odds;
	
	//This constructor accepts no arguments
	//and sets every variable to 0.
	public Tracker()
	{
		rollNumber = 0;
		occurrence = 0;
		odds = 0;
	}
	
	//This constructor accepts an int argument
	//and sets that to the roll number. The
	//rest of the variables are set to 0.
	public Tracker(int rollTotal)
	{
		rollNumber = rollTotal;
		occurrence = 0;
		odds = 0;
	}
	
	//This method increments the occurrence.
	public void setOccurrence()
	{
		occurrence++;
	}
	
	//This mutator method accepts 3 int arguments
	//which sets the values for all 3 variables.
	public void setAll(int count, int roll, int odd)
	{
		occurrence = count;
		rollNumber = roll;
		odds = odd;
	}
	
	//This mutator method accepts an int argument
	//which sets the value for the odds.
	public void setOdds(int odd)
	{
		odds = odd;
	}
	
	//This method returns the number of occurrence.
	public int getOccurrence()
	{
		return occurrence;
	}
	
	//This method returns the roll number.
	public int getRollNumber()
	{
		return rollNumber;
	}

	//This method returns the odds.
	public int getOdds()
	{
		return odds;
	}
	
	//This method resets all but the rollNumber
	//variable to 0.
	public void reset()
	{
		occurrence = 0;
		odds = 0;
	}
}
