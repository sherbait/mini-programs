import java.awt.Color;

/*
 * This abstract class contains the parent class 
 * BouncyBox which contains the properties position,
 * velocity, length and color of box, and a flag to 
 * see if the box has a collision.
 * 
 * @author Dinia Grace Gepte
 * CSE114 L03 TA Yifu Ren HW5
 */

public abstract class BouncyBox 
{
	// Instance variables
	protected double x;
	protected double y;
	protected double xVelocity;
	protected double yVelocity;
	protected int length;
	protected boolean colliding;
	protected Color color;
	protected double maxVelocity;
	 
	/* Constructor which accepts position and velocity
	 * in the x and y direction, and box length. It also
	 * initializes the default values for the flag, color,
	 * and max velocity.
	 */
	public BouncyBox(int initX,
					 int initY,
					 int initXVelocity,
					 int initYVelocity,
					 int initLength)		
	{
		x = initX;
		y = initY;
		xVelocity = initXVelocity;
		yVelocity = initYVelocity;
		length = initLength;
		colliding = false; 
		color = Color.black;
		maxVelocity = 0;
	}
	
	// Accessor method  for x position.
	public double getX()
	{
		return x;
	}
	
	// Accessor method for y position.
	public double getY()
	{
		return y;
	}
	
	// Accessor method for velocity in the x direction.
	public double getXVelocity()
	{
		return xVelocity;
	}
	
	// Accessor method for velocity in the y direction.
	public double getYVelocity()
	{
		return yVelocity;
	}
	
	// Accessor method for box length.
	public int getLength()
	{
		return length;
	}
	
	// Accessor method for collision flag.
	public boolean getColliding()
	{
		return colliding;
	}
	
	// Accessor method for box color.
	public Color getColor()
	{
		return color;
	}

	// Accessor method for maximum velocity.
	public double getMaxVelocity()
	{
		return maxVelocity;
	}
	
	// Mutator method for x position.
	public void setX(double initX)
	{
		x = initX;
	}
	
	// Mutator method for y position.
	public void setY(double initY)
	{
		y = initY;
	}
	
	// Mutator method for x velocity.
	public void setXVelocity(double initXVelocity)
	{
		xVelocity = initXVelocity;
	}
	
	// Mutator method for y velocity.
	public void setYVelocity(double initYVelocity)
	{
		yVelocity = initYVelocity;
	}
	
	// Mutator method for box length.
	public void setLength(int initLength)
	{
		length = initLength;
	}
	
	// Mutator method for collision flag.
	public void setColliding(boolean initColliding)
	{
		colliding = initColliding;
	}
	
	// Mutator method for box color.
	public void setColor(Color initColor)
	{
		color = initColor;
	}
	
	// Mutator method for max velocity.
	public void setMaxVelocity(double initMaxVelocity)
	{
		maxVelocity = initMaxVelocity;
	}
	
	/* This method accepts the width and height of the window
	 * then determines the movement of the boxes as they
	 * move around the window.
	 */
	public void update(int worldWidth, int worldHeight)
	{
		x = x + xVelocity;
		y = y + yVelocity;
		if ( (x < 0) || (x > worldWidth-length) )
		{
			if (x < 0)
				x = 0;
			if (x > worldWidth-length)
				x = worldWidth-length;
			xVelocity = -xVelocity;
		}	
		if ( (y < 0) || (y > worldHeight-length))
		{
			if (y < 0)
				y = 0;
			if (y > worldHeight-length)
				y = worldHeight-length;
			yVelocity = -yVelocity;
		}
	}
	
	/* This method corrects the location of a box
	 * if it is out of bounds.
	 */
	public void clampBounds(int worldWidth, int worldHeight)
	{
		if ( (x < 0) || (x > worldWidth-length) )
		{
			if (x < 0)
				x = 0;
			if (x > worldWidth-length)
				x = worldWidth-length;
		}
		if ( (y < 0) || (y > worldHeight-length))
		{
			if (y < 0)
				y = 0;
			if (y > worldHeight-length)
				y = worldHeight-length;
		}
	}
	
	/* Method to override by the subclass when there
	 * is a collision.
	 */
	public abstract void respondToCollision(BouncyBox otherBox);		
	
	/* Method to override by the subclass when there
	 * is no collision.
	 */
	public abstract void respondToNoCollision();
	
}
