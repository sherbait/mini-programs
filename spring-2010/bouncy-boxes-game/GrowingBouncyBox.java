import java.awt.Color;

/*
 * This subclass of BouncyBox grows whenever it
 * collides with something.
 * 
 * @author Dinia Grace Gepte
 * CSE114 L03 TA Yifu Ren HW5
 */
public class GrowingBouncyBox extends BouncyBox
{
	/* This constructor initializes the position,
	 * velocity, and box length. It also sets the
	 * box to color red with a maximum velocity
	 * of 30. 
	 */
	public GrowingBouncyBox(int initX,
			 				int initY,
			 				int initXVelocity,
			 				int initYVelocity,
			 				int initLength)
	{
		super(initX, initY, initXVelocity, initYVelocity, initLength);
		color = Color.red;
		maxVelocity = 30;
	}
	
	/* This method overrides a method of the parent
	 * class to determine the response of this object
	 * whenever it collides with something.
	 */
	public void respondToCollision(BouncyBox otherBox)
	{
			length = length + 20;
			color = Color.orange;
			if (Math.random() < 0.25)
			{
				xVelocity = (Math.random() * maxVelocity) + 1;
				yVelocity = (Math.random() * maxVelocity) + 1;
			}
			else if (Math.random() < 0.50)
			{
				xVelocity = (Math.random() * maxVelocity) + 1;
				yVelocity = -((Math.random() * maxVelocity) + 1);
			}
			else if (Math.random() < 0.75)
			{
				xVelocity = -((Math.random() * maxVelocity) + 1);
				yVelocity = (Math.random() * maxVelocity) + 1;
			}
			else
			{
				xVelocity = -((Math.random() * maxVelocity) + 1);
				yVelocity = -((Math.random() * maxVelocity) + 1);
			}
	}
	
	/* This method overrides a method of the parent
	 * class to determine the response of this object
	 * whenever it does not collide with anything.
	 */
	public void respondToNoCollision()
	{
		color = Color.red;
	}
}
