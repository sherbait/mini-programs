import java.awt.Color;

/*
 * This subclass of BouncyBox 
 * is controlled by the
 * player.
 * 
 * @author Dinia Grace Gepte
 * CSE114 L03 TA Yifu Ren HW5
 */
public class PlayerBouncyBox extends BouncyBox 
{
	// Instance variable
	private int transparency;
	
	/* This constructor initializes the position,
	 * velocity, and box length. It also sets the
	 * box to color green with transparency 200,
	 * and with a maximum velocity of 20. 
	 */
	public PlayerBouncyBox(int initX,
			 			   int initY,
			 			   int initXVelocity,
			 			   int initYVelocity,
			 			   int initLength)
	{
		super(initX, initY, initXVelocity, initYVelocity, initLength); 
		color = Color.green;
		maxVelocity = 20;
		transparency = 200;
	}
	
	// Accessor method for box transparency.
	public int getTransparency()
	{
		return transparency;
	}
	
	// Mutator method for box transparency.
	public void setTransparency(int initTransparency)
	{
		transparency = initTransparency;
	}
	
	/* This method overrides a method of the parent
	 * class to determine the response of this object
	 * whenever it collides with something.
	 */
	public void respondToCollision(BouncyBox otherBox)
	{
		color = Color.magenta;
		if (otherBox instanceof GrowingBouncyBox)
		{
			maxVelocity--;
			transparency = transparency - 10;
		}
	}
	
	/* This method overrides a method of the parent
	 * class to determine the response of this object
	 * whenever it does not collide with anything.
	 */
	public void respondToNoCollision()
	{
		color = Color.green;
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), transparency);
	}
	
	// Method that affects the box velocity in the
	// x direction when player moves to the left.
	public void moveLeft()
	{
		xVelocity = -maxVelocity;
	}
	
	// Method that affects the box velocity in the 
	// x direction when player moves to the right.
	public void moveRight()
	{
		xVelocity = maxVelocity;
	}
	
	// Method that affects the box velocity in the
	// y direction when player moves up.
	public void moveUp()
	{
		yVelocity = -maxVelocity;
	}
	
	// Method that affects the box velocity in the
	// y direction when players moves down.
	public void moveDown()
	{
		yVelocity = maxVelocity;
	}
	
	// Method that sets the x velocity to be zero
	// when the player stops moving the box.
	public void stopInX()
	{
		xVelocity = 0;
	}
	
	// Method that sets the y velocity to be zero
	// when the player stops moving the box.
	public void stopInY()
	{
		yVelocity = 0;
	}
}
