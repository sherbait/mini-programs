import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * This class manages the boxes and performs all rendering.
 * 
 * @author Richard McKenna
 */
public class BouncyBoxesRenderingSurface extends JPanel
{
	// THERE ARE 30 BOXES IN ALL
	public static final int NUM_BOUNCY_BOXES = 30;
	
	// THERE ARE 20 GROWING BOXES
	public static final int NUM_GROWING_BOXES = 20;
	
	// THERE ARE 10 SHRINKING BOXES
	public static final int NUM_SHRINKING_BOXES = 10;
	
	// HERE IS THE PLAYER BOX
	private PlayerBouncyBox player;
	
	// AND HERE ARE THE GROWING AND SHRINKING BOXES
	private BouncyBox[] bouncyBoxes;
	
	// WE ONLY RENDER THE BOXIS IF gameStarted IS TRUE
	private volatile boolean gameStarted;
	
	// THIS WILL HANDLE KEYBOARD INPUT FOR MOVING THE PLAYER BOX
	private PlayerInputHandler playerInputHandler;

	/**
	 * This default constructor sets up this canvas.
	 */
	public BouncyBoxesRenderingSurface()
	{
		// THE GAME HAS NOT STARTED YET
		gameStarted = false;
		
		// SETUP THE KEY LISTENER
		setFocusable(true);
		playerInputHandler = new PlayerInputHandler();
		addKeyListener(playerInputHandler);
	}

	// ACCESSOR METHOD
	public boolean isGameStarted()	{ return gameStarted; 	}

	// MUTATOR METHOD
	public void endGame() 			{ gameStarted = false;	}

	/**
	 * This method resets the game, initializing all the
	 * game's bouncy boxes.
	 */
	public void resetGame()
	{
		// MAKE SURE WE CAN HEAR THE KEY PRESSES
		requestFocus();
		
		// CONSTRUCT ALL OF THE BOXES
		int xInc = getWidth()/10;
		int yInc = getHeight()/10;
		int length = 30;
		int v = 5;
		player = new PlayerBouncyBox(getWidth()/2, getHeight()/2, 0, 0, length);
		bouncyBoxes = new BouncyBox[NUM_BOUNCY_BOXES];
		int i;
		for (i = 0; i < 5; i++)
			bouncyBoxes[i] = new GrowingBouncyBox(((i%5) * xInc) + (2 * xInc), yInc, v, v, length);
		for (i = 5; i < 10; i++)
			bouncyBoxes[i] = new ShrinkingBouncyBox(xInc, ((i%5) * yInc) + yInc, v, v, length);
		for (i = 10; i < 15; i++)
			bouncyBoxes[i] = new GrowingBouncyBox(getWidth() - ((i%5) * xInc) - (2 * xInc), getHeight() - yInc, -v, -v, length);
		for (i = 15; i < 20; i++)
			bouncyBoxes[i] = new ShrinkingBouncyBox(getWidth() - xInc, getHeight() - ((i%5) * yInc) - yInc, -v, -v, length);
		for (i = 20; i < 25; i++)
			bouncyBoxes[i] = new GrowingBouncyBox(i * 40, 2, v, v, length);
		for (i = 25; i < 30; i++)
			bouncyBoxes[i] = new GrowingBouncyBox(getWidth() - (i*100), 2, -v, -v, length);

		// NOW RANDOMIZE THE INITIAL DIRECTION
		for (i = 0; i < NUM_BOUNCY_BOXES; i++)
		{
			BouncyBox box = bouncyBoxes[i];
			int randNum = (int)(Math.random() * 4);
			if (randNum == 0)
			{
				box.setXVelocity(v);
				box.setYVelocity(v);
			}
			else if (randNum == 1)
			{
				box.setXVelocity(v);
				box.setYVelocity(-v);
			}
			else if (randNum == 2)
			{
				box.setXVelocity(-v);
				box.setYVelocity(v);
			}
			else
			{
				box.setXVelocity(-v);
				box.setYVelocity(-v);
			}
		}
		
		// MAKE SURE THEY GET RENDERED
		gameStarted = true;
	}

	/**
	 * This method counts and returns the number of 
	 * shrinking boxes that have disappeared.
	 */
	public int countShrunkBouncyBoxes()
	{
		int counter = 0;
		for (int i = 0; i < bouncyBoxes.length; i++)
		{
			if (bouncyBoxes[i].getLength() == 0)
				counter++;
		}
		return counter;
	}

	/**
	 * Called each frame, this method makes sure all the 
	 * boxes are updated each frame and then checks to see
	 * if the game is over, displaying a You Win or You Lose
	 * in such cases.
	 */
	public void updateBouncyBoxes()
	{
		if (gameStarted)
		{
			CollisionManager.performCollisionTests(player, bouncyBoxes, getWidth(), getHeight());
			if (player.getTransparency() == 0)
			{
				gameStarted = false;
				JOptionPane.showMessageDialog(null, "You Lose!");
			}
			if (this.countShrunkBouncyBoxes() == 10)
			{
				gameStarted = false;
				JOptionPane.showMessageDialog(null, "You Win!");
			}
		}
	}

	/**
	 * This method renders all of the bouncy boxes, but only
	 * if gameStarted is true.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (gameStarted)
		{
			for (int i = 0; i < bouncyBoxes.length; i++)
			{
				BouncyBox box = bouncyBoxes[i];
				renderBouncyBox(g, box);
			}
			renderBouncyBox(g, player);
		}
	}

	/**
	 * This method renders the BouncyBox argument
	 * inside this canvas via the Graphics argument.
	 * It will do so using a black outline and the color
	 * of the box.
	 */
	public void renderBouncyBox(Graphics g, BouncyBox box)
	{
		g.setColor(box.getColor());
		g.fillRect((int)Math.round(box.getX()), (int)Math.round(box.getY()), (int)Math.round(box.getLength()), (int)Math.round(box.getLength()));
		g.setColor(Color.black);
		g.drawRect((int)Math.round(box.getX()), (int)Math.round(box.getY()), (int)Math.round(box.getLength()), (int)Math.round(box.getLength()));		
	}

	/**
	 * This class handles user input from the keyboard so
	 * that the player may move the green box around the screen.
	 */
	class PlayerInputHandler implements KeyListener
	{
		/**
		 * We'll start moving the player upon the first press of a
		 * WASD or directional key.
		 */
		public void keyPressed(KeyEvent ke)
		{
			if (gameStarted)
			{
				int keyCode = ke.getKeyCode();
				if ((keyCode == KeyEvent.VK_W) || (keyCode == KeyEvent.VK_UP))
					player.moveUp();
				if ((keyCode == KeyEvent.VK_S) || (keyCode == KeyEvent.VK_DOWN))
					player.moveDown();
				if ((keyCode == KeyEvent.VK_A) || (keyCode == KeyEvent.VK_LEFT))
					player.moveLeft();
				if ((keyCode == KeyEvent.VK_D) || (keyCode == KeyEvent.VK_RIGHT))
					player.moveRight();
			}
		}

		/**
		 * Upon the release of a WASD or directional key we'll
		 * stop moving the player's box.
		 */
		public void keyReleased(KeyEvent ke)	
		{
			if (gameStarted)
			{
				int keyCode = ke.getKeyCode();
				if ((keyCode == KeyEvent.VK_W) || (keyCode == KeyEvent.VK_UP))
					player.stopInY();
				if ((keyCode == KeyEvent.VK_S) || (keyCode == KeyEvent.VK_DOWN))
					player.stopInY();
				if ((keyCode == KeyEvent.VK_A) || (keyCode == KeyEvent.VK_LEFT))
					player.stopInX();
				if ((keyCode == KeyEvent.VK_D) || (keyCode == KeyEvent.VK_RIGHT))
					player.stopInX();				
			}
		}

		// WE WILL NOT USE THIS METHOD
		public void keyTyped(KeyEvent ke) 		{}
	}
}