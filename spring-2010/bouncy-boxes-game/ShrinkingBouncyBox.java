import java.awt.Color;

/*
 * This subclass of BouncyBox shrinks and
 * increases its velocity whenever it collides
 * with something.
 * 
 * @author Dinia Grace Gepte
 * CSE114 L03 TA Yifu Ren HW5
 */

public class ShrinkingBouncyBox extends BouncyBox
{
	/* This constructor initializes the position,
	 * velocity, and box length. It also sets the
	 * box to color blue. 
	 */
	public ShrinkingBouncyBox(int initX,
							  int initY,
							  int initXVelocity,
							  int initYVelocity,
							  int initLength)
	{
		super(initX, initY, initXVelocity, initYVelocity, initLength);
		color = Color.blue;
	}
	
	/* This method overrides a method of the parent
	 * class to determine the response of this object
	 * whenever it collides with something.
	 */
	public void respondToCollision(BouncyBox otherBox)
	{
		length = length - 10;
		color = Color.cyan;
		xVelocity = -(xVelocity * 2);
		yVelocity = -(yVelocity * 2);
	}
	
	/* This method overrides a method of the parent
	 * class to determine the response of this object
	 * whenever it does not collide with anything.
	 */
	public void respondToNoCollision()
	{
		color = Color.blue;
	}
}
