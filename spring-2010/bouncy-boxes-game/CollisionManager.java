import java.io.IOException;
import java.io.PrintWriter;

/**
 * This method performs all collision detection using
 * AABBs and a continuous collision detection algorithm.
 */
public class CollisionManager 
{
	/**
	 * Called each frame, this method performs all collision testing 
	 * for our game. It goes through the array of BouncyBoxes and 
	 * tests to see if each is colliding with the player.
	 */
	public static void performCollisionTests(BouncyBox player, BouncyBox[] bouncyBoxes, int worldWidth, int worldHeight)
	{
		boolean playerWasCollidingLastFrame = player.getColliding();
		boolean atLeastOneCollision = false;
		
		// GO THROUGH EACH BOX
		for (int i = 0; i < bouncyBoxes.length; i++)
		{
			BouncyBox testBox = bouncyBoxes[i];
			
			// WHEN WILL IT COLLIDE?
			double timeOfCollision = calculateTimeOfCollision(player, testBox);

			// WILL IT COLLIDE DURING THIS FRAME
			if ((testBox.getLength() > 0) && (timeOfCollision >= 0) && (timeOfCollision <= 1))
			{
				// MAKE SURE OUR PLAYER DOESN'T PIGGY BACK COLLISIONS, WE
				// ONLY WANT ONE COLLISION RESPONSE AT A TIME FOR THE 
				// SAKE OF GAMEPLAY
				if (!playerWasCollidingLastFrame)
				{
					// CALL EACH COLLISION EVENT HANDLER
					player.respondToCollision(testBox);
					testBox.respondToCollision(player);
					
					// AND MOVE THE testBox TO THE POSITION AT THE INSTANT
					// THE COLLISION IS TO HAPPEN
					moveBoxToTimeOfCollision(timeOfCollision, testBox, worldWidth, worldHeight);
				}
				atLeastOneCollision = true;
			}
			else
			{
				// NO COLLISION THIS FRAME FOR THE testBox
				testBox.respondToNoCollision();
				
				// SO UPDATE IT NORMALLY USING IT'S VELOCITY
				testBox.update(worldWidth, worldHeight);
			}
		}
		// NO COLLISION THIS FRAME FOR THE PLAYER
		if (!atLeastOneCollision)
			player.respondToNoCollision();
		
		// UPDATE THE PLAYER
		player.update(worldWidth, worldHeight);
		player.setColliding(atLeastOneCollision);
	}
	
	/**
	 * This method calculates and returns the time during a frame
	 * of a collision between two boxes.
	 */
	public static double calculateTimeOfCollision(BouncyBox player, BouncyBox testBox)
	{
		double x1 = player.getX();
		double vX1 = player.getXVelocity();
		double y1 = player.getY();
		double vY1 = player.getYVelocity();
		double length1 = player.getLength();

		double x2 = testBox.getX();
		double vX2 = testBox.getXVelocity();
		double y2 = testBox.getY();
		double vY2 = testBox.getYVelocity();
		double length2 = testBox.getLength();

		// WE NEED THE START AND END TIMES IN THE X AND Y AXES
		double xTimeStart 	= calculateStartTimeOfCollisionForAnyAxis(	x1, x2, vX1, vX2, length1, length2);
		double xTimeEnd 	= calculateEndTimeOfCollisionForAnyAxis(	x1, x2, vX1, vX2, length1, length2);
		double yTimeStart 	= calculateStartTimeOfCollisionForAnyAxis(	y1, y2, vY1, vY2, length1, length2);
		double yTimeEnd		= calculateEndTimeOfCollisionForAnyAxis(	y1, y2, vY1, vY2, length1, length2);
		
		// NO COLLISION EVER
		if ((xTimeEnd < yTimeStart)
			||
			(yTimeEnd < xTimeStart))
			return -1.0;
		// NO COLLISION THIS FRAME
		else if ((xTimeStart > 1.0) || (yTimeStart > 1.0))
			return 2.0;
		// COLLISION
		else
		{
			// RETURN THE LATER OF THE TWO TIMES
			if (xTimeStart < yTimeStart)
				return yTimeStart;
			else
				return xTimeStart;
		}
	}

	/**
	 * For any given axis, this method can determine the start
	 * time of collision using AABBs.
	 */
	public static double calculateStartTimeOfCollisionForAnyAxis(double pos1, double pos2, double v1, double v2, double length1, double length2)
	{
		double timeStart;
		if (pos1 == pos2)
			timeStart = 0.0;
		else if (pos1 < pos2)
			timeStart = calculateTimeOfFirstAxisCollision(pos1, pos2, v1, v2, length1, length2);
		else
			timeStart = calculateTimeOfFirstAxisCollision(pos2, pos1, v2, v1, length2, length1);
		return timeStart;
	}
	
	/**
	 * This method calculates the time of the first collision on
	 * a given axis assuming pos1 < pos2.
	 */
	public static double calculateTimeOfFirstAxisCollision(double pos1, double pos2, double v1, double v2, double length1, double length2)
	{
		double timeStart;
		double posDiff = pos2 - (pos1 + length1);

		// ARE THEY COLLIDING AT THE START?
		if (posDiff <= 0)
			timeStart = 0.0;
		else
		{
			double vDiff = v1 - v2;
			// MAYBE THEY'LL NEVER COLLIDE
			if (vDiff <= 0)
				timeStart = 2.0;
			else
				timeStart = posDiff/vDiff;
		}				
		return timeStart;
	}

	/**
	 * For any given axis, this method can determine the end
	 * time of collision using AABBs. This means the instant
	 * when two colliding boxes are about to be colliding 
	 * no more.
	 */
	public static double calculateEndTimeOfCollisionForAnyAxis(double pos1, double pos2, double v1, double v2, double length1, double length2)
	{
		double timeEnd;
		if (pos1 < pos2)
			timeEnd = calculateTimeOfFirstAxisCollision(pos1, pos2, v1, v2, length1, length2);
		else
			timeEnd = calculateTimeOfFirstAxisCollision(pos2, pos1, v2, v1, length2, length1);
		return timeEnd;
	}
	
	/**
	 * This method calculates the end time of collision on
	 * a given axis assuming pos1 < pos2.
	 */
	public static double calculateTimeOfLastAxisCollision(double pos1, double pos2, double v1, double v2, double length1, double length2)
	{
		double timeEnd;
		double vDiff = v1 - v2;

		// ARE THEY NOT MOVING RELATIVE TO ONE ANOTHER IN THIS AXIS?
		if (vDiff == 0)
			timeEnd = 2.0;
		// ARE THEY MOVING TOWARD EACH OTHER?
		else if (vDiff > 0.0)
		{
			double posDiff = (pos2 + length2) - pos1;
			timeEnd = posDiff/vDiff;
		}
		// THEY MUST BE MOVING AWAY FROM EACH OTHER
		else
		{
			double posDiff = (pos1 + length1) - pos2;
			if (posDiff < 0.0)
				timeEnd = 2.0;
			else
				timeEnd = posDiff/-vDiff;
		}
		return timeEnd;
	}

	/**
	 * This method moves the box argument ahead per its velocity to
	 * the moment in a frame represented by the time argument. We should
	 * also make sure not to move it outside the game world.
	 */
	public static void moveBoxToTimeOfCollision(double time, BouncyBox box, int worldWidth, int worldHeight)
	{
		double vX = box.getXVelocity();
		double vY = box.getYVelocity();
		double updatedX = Math.round((vX * time) + box.getX());
		double updatedY = Math.round((vY * time) + box.getY());
		box.setX(updatedX);
		box.setY(updatedY);
		box.clampBounds(worldWidth, worldHeight);
	}
}