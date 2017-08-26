
public class Die
{
	private int numFaces;
	private int upValue;
	
	public Die()
	{
		numFaces = 6;
		upValue = 1;
	}
	
	public void roll()
	{
		upValue = ((int)(Math.random() * numFaces)) + 1;
	}
	
	public int getUpValue()
	{
		return upValue;
	}
}
