import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * This BouncyBoxesGame application has a main method for
 * starting the game and a window inside which the entire
 * game is played.
 * 
 * @author Richard McKenna
 */
public class BouncyBoxesGame extends JFrame
{
	// GAME CONTROLS
	private JPanel northPanel;
	private JButton startNewGameButton;
	private JButton exitProgramButton;
	
	// EVENT HANDLERS
	private StartNewGameHandler startNewGameHandler;
	private ExitProgramHandler exitProgramHandler;
	
	// DRAWING SURFACE
	private BouncyBoxesRenderingSurface canvas;
	
	// THREAD FOR CONTROLLING THE GAME'S FRAME RATE
	private Runner runner;
	
	/**
	 * Default constructor, it initializes everything our window needs
	 * to start the application.
	 */
	public BouncyBoxesGame()
	{
		// SET THE TITLE AND THE DEFAULT JFrame SETTINGS
		super("Bouncy Ball Game");
		
		// MAKE THE WINDOW FULL SCREEN
		setExtendedState(MAXIMIZED_BOTH);
		
		// NO MENU BAR OR TITLE BAR
		setUndecorated(true);
		
		// CONSTRUCT ALL INSTANCE VARIABLES
		northPanel = new JPanel();
		startNewGameButton = new JButton("Start New Game");
		exitProgramButton = new JButton("Exit Program");
		startNewGameHandler = new StartNewGameHandler();
		exitProgramHandler = new ExitProgramHandler();
		canvas = new BouncyBoxesRenderingSurface();
		runner = new Runner();

		// ARRANGE ALL OF THE COMPONENTS INSIDE THE WINDOW
		northPanel.add(startNewGameButton);
		northPanel.add(exitProgramButton);
		northPanel.setBackground(Color.DARK_GRAY);
		add(northPanel, BorderLayout.NORTH);
		add(canvas, BorderLayout.CENTER);

		// REGISTER THE EVENT HANDLERS
		startNewGameButton.addActionListener(startNewGameHandler);
		exitProgramButton.addActionListener(exitProgramHandler);
	}

	/**
	 * This method is called when a game is to be started. It
	 * resents the canvas' game state and then creates and starts
	 * a new thread for running the timed game loop.
	 */
	public void startNewGame()
	{
		// RESET THE GAME STATE
		canvas.resetGame();
		
		// START THE TIMED GAME LOOP
		runner = new Runner();
		runner.start();
	}

	/**
	 * This method should be called when the game is over
	 * to stop the rendering and kill the game loop thread.
	 */
	public void endPlaying()
	{
		// STOP THE RENDERING
		canvas.endGame();
		
		// AND KILL THE GAME LOOP THREAD
		runner.keepPlaying = false;
	}

	/**
	 * This method asks the rendering canvas if the game
	 * is still going on, if it is not, it will kill
	 * the game loop thread.
	 */
	public void checkIfGameIsOver()
	{
		if (!canvas.isGameStarted())
			runner.keepPlaying = false;
	}

	/**
	 * This class runs our game using a timed game loop. We need
	 * a separate thread because our main thread is concerned with
	 * rendering the frame and listening for user input.
	 */
	class Runner implements Runnable
	{
		// OUR PROXY THREAD
		Thread thread;
		
		// THIS CONTROLS OUR GAME LOOP, WHEN SET TO FALSE, THE
		// GAME LOOP ENDS EFFECTIVELY KILLING THE THREAD
		volatile boolean keepPlaying;

		/**
		 * Default Constructor, it simply initializes
		 * the proxy thread instance variable.
		 */
		public Runner()
		{
			thread = new Thread(this);
		}

		/**
		 * This start method starts the proxy thread instance variable.
		 */
		public void start()
		{
			thread.start();
		}
	
		/**
		 * This method controls the timed game loop.
		 */
		public void run()
		{
			// WHEN keepPlaying BECOMES false, THE GAME ENDS
			// AND THIS THREAD WILL DIE
			keepPlaying = true;
			while(keepPlaying)
			{
				try
				{
					// UPDATE ALL THE BOXES IN THE GAME
					canvas.updateBouncyBoxes();
					
					// REDRAW THE GAME
					canvas.repaint();
					
					// IS THE GAME OVER?
					checkIfGameIsOver();
					
					// MAKE SURE THE GAME RUNS AT A
					// CONSISTENT FRAME RATE
					Thread.sleep(20);
				}
				catch(InterruptedException ie)
				{
					ie.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This inner class handles the event generated when
	 * the user pressses the Start New Game button.
	 */
	class StartNewGameHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			BouncyBoxesGame.this.endPlaying();
			BouncyBoxesGame.this.startNewGame();
		}
	}

	/**
	 * This inner class handles the event generated when
	 * the user presses the Exit Program button.
	 */
	class ExitProgramHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			BouncyBoxesGame.this.endPlaying();
			System.exit(0);
		}
	}

	/**
	 * This main method is where the application starts.
	 */
	public static void main(String[] args) 
	{
		BouncyBoxesGame frame = new BouncyBoxesGame();
		frame.setVisible(true);
	}
}