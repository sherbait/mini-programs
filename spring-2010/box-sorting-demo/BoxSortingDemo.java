import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;

public class BoxSortingDemo extends JFrame
{
	// THIS CLASS IS A SINGLETON
	private static BoxSortingDemo singleton = null;
	
	// SORTING ALGORITHMS TO USE
	private static final String BUBBLE_SORT = "Bubble";
	private static final String INSERTION_SORT = "Insertion";
	private static final String RADIX_SORT = "Radix";

	// THIS OBJECT WILL DO ALL OF OUR SORTING FOR US
	// NOTE THAT BoxSorter IS AN INTERFACE, SO THIS
	// IS SIMPLY THE APPARENT TYPE
	private BoxSorter boxSorter;

	// GUI COMPONENTS
	private JPanel northPanel;
	private JButton resetButton;
	private JButton bubbleSortButton;
	
	private JPanel southPanel;
	private JLabel sortingSpeedLabel;
	private JPanel speedPanel;
	private JButton speedUpButton;
	private JButton slowDownButton;
	
	// EVENT HANDLERS
	private ResetHandler resetHandler;
	private BubbleSortHandler bubbleSortHandler;
	private SpeedUpHandler speedUpHandler;
	private SlowDownHandler slowDownHandler;
	
	// OUR DRAWING SURFACE
	private ColoredBoxPanel coloredBoxPanel;
	
	// HERE'S THE ARRAY WE'LL BE SORTING
	private ColoredBox[] coloredBoxes;
	
	// SWAPS PER SECOND FOR SHOWING SORTING
	private static int swapsPerSecond = 5;
	private static long sleepTime = 200;
	
	// FOR PRESENTING REAL NUMBERS NEATLY
	private NumberFormat numberFormatter;
	
	/*
	 * This constructor initializes our application.
	 */
	private BoxSortingDemo(String title, BoxSorter initBoxSorter)
	{
		super(title + " Sort");
		boxSorter = initBoxSorter;
		setSize(1200,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		layoutGUI();
		singleton = this;
	}
	
	/**
	 * Accessor method for our singleton.
	 */
	public static BoxSortingDemo getBubbleSortDemo()
	{
		return singleton;
	}
	
	/**
	 * This method lays out all GUI components and sets
	 * up all event handlers.
	 */
	public void layoutGUI()
	{
		// CONSTRUCT GUI COMPONENTS
		northPanel = new JPanel();
		resetButton = new JButton("Reset");
		bubbleSortButton = new JButton("Sort");
		
		southPanel = new JPanel();
		sortingSpeedLabel = new JLabel();
		numberFormatter = NumberFormat.getNumberInstance();
		numberFormatter.setMaximumFractionDigits(1);
		numberFormatter.setMinimumFractionDigits(0);
		updateSortingSpeedLabel();
		speedPanel = new JPanel();
		speedUpButton = new JButton("+");
		slowDownButton = new JButton("-");
		
		// MAKE OUR RENDERING SURFACE
		coloredBoxPanel = new ColoredBoxPanel();

		// CONSTRUCT OUR EVENT HANDLERS
		resetHandler = new ResetHandler();
		bubbleSortHandler = new BubbleSortHandler();
		speedUpHandler = new SpeedUpHandler();
		slowDownHandler = new SlowDownHandler();
		
		// ARRANGE THE COMPONENTS IN THE NORTH PANEL
		northPanel.add(resetButton);
		northPanel.add(bubbleSortButton);
		GridLayout gridLayout = new GridLayout(2,1);
		speedPanel.setLayout(gridLayout);
		speedPanel.add(speedUpButton, 0, 0);
		speedPanel.add(slowDownButton, 0, 1);
		southPanel.add(sortingSpeedLabel);
		southPanel.add(speedPanel);

		// ARRANGE THE PANELS IN THSI FRAME
		this.add(northPanel, BorderLayout.NORTH);
		this.add(coloredBoxPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		
		// REGISTER THE EVENT HANDLERS
		resetButton.addActionListener(resetHandler);
		bubbleSortButton.addActionListener(bubbleSortHandler);
		speedUpButton.addActionListener(speedUpHandler);
		slowDownButton.addActionListener(slowDownHandler);
	}
	
	class RenderThread implements Runnable
	{
		Thread proxyThread = null;
		public void start()
		{
			if (proxyThread == null)
				proxyThread = new Thread(this);
			proxyThread.start();
		}
		
		public void run()
		{
			//	Graphics g = singleton.coloredBoxPanel.getGraphics();
			//	singleton.coloredBoxPanel.paint(g);
			singleton.coloredBoxPanel.repaint();
		}
	}
	
	public void initNewRenderThread()
	{
		RenderThread rt = new RenderThread();
		rt.start();
	}
	
	public void updateSwapsPerSecond(int initSwapsPerSecond)
	{
		if ((initSwapsPerSecond > 0) && (initSwapsPerSecond <= 10))
		{
			swapsPerSecond = initSwapsPerSecond;
			sleepTime = (long)(1000.0/initSwapsPerSecond);
		}
	}
	
	public void incSortingSpeed()
	{
		updateSwapsPerSecond(swapsPerSecond + 1);
	}
	
	public void decSortingSpeed()
	{
		updateSwapsPerSecond(swapsPerSecond - 1);
	}

	public static void updateDisplay()
	{
		singleton.initNewRenderThread();
		try
		{
			System.out.println(sleepTime);
			Thread.sleep(sleepTime);
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
	}
	
	public void updateSortingSpeedLabel()
	{
		sortingSpeedLabel.setText("Current Swap Speed: " 
								+ swapsPerSecond 
								+ " per Second");		
	}
	
	public void resetData()
	{
		int randomSize = ((int)Math.random() * 11) + 30;
		coloredBoxes = new ColoredBox[randomSize];
		int randomRed;
		for (int i = 0; i < randomSize; i++)
		{
			randomRed = (int)(Math.random() * 256);
			Color c = new Color(randomRed, 0, 0);
			coloredBoxes[i] = new ColoredBox(randomRed, c);
		}
		updateDisplay();
	}
	
	class ColoredBoxPanel extends JPanel
	{
		static final int MARGIN = 25;
		static final int INDENT = 10;
		Font f = new Font("Serif", Font.BOLD, 16);
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			int panelWidth = getWidth();
			setFont(f);
			if ((panelWidth > 0) && (BoxSortingDemo.this.coloredBoxes != null))
			{
				int inc = (panelWidth - (MARGIN * 2))/BoxSortingDemo.this.coloredBoxes.length;
				int x = MARGIN;
				int y = MARGIN;
				for (int i = 0; i < BoxSortingDemo.this.coloredBoxes.length; i++)
				{
					g.setColor(BoxSortingDemo.this.coloredBoxes[i].getColor());
					g.fill3DRect(x, y, inc, inc, true);
					g.setColor(Color.WHITE);
					g.drawString("" + BoxSortingDemo.this.coloredBoxes[i].getNum(), x + INDENT, y + (inc/2));
					x += inc;
				}
			}
		}
	}
	
	class SortingThread implements Runnable
	{
		private Thread proxyThread;
		
		public SortingThread()
		{
			proxyThread = new Thread(this);
		}
		
		public void start()
		{
			proxyThread.start();
		}
		
		public void run()
		{
			boxSorter.sortBoxes(coloredBoxes);
		}
	}
	
	class ResetHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			BoxSortingDemo.this.resetData();
		}
	}
	
	class BubbleSortHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			SortingThread st = new SortingThread();
			st.start();
		}
	}
	
	class SpeedUpHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			BoxSortingDemo.this.incSortingSpeed();
			BoxSortingDemo.this.updateSortingSpeedLabel();
		}
	}
	
	class SlowDownHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			BoxSortingDemo.this.decSortingSpeed();
			BoxSortingDemo.this.updateSortingSpeedLabel();
		}
	}
	
	public static void main(String[] args)
	{
		BoxSorter sorter = null;
		if (args.length == 0)
		{
			JOptionPane.showMessageDialog(null, "Command Line Argument Missing - You Need to Specify the Sorting Algorithm");
			System.exit(-1);
		}
		else if (args[0].equals(BUBBLE_SORT))
		{
			sorter = new BubbleSortBoxSorter();
		}
		else if (args[0].equals(INSERTION_SORT))
		{
			sorter = new InsertionSortBoxSorter();
		}
		else if (args[0].equals(RADIX_SORT))
		{
			sorter = new RadixSortBoxSorter();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Illegal Command Line Argument - You Need to Specify the Sorting Algorithm");
			System.exit(-1);
			
		}
		BoxSortingDemo frame = new BoxSortingDemo(args[0], sorter);
		frame.setVisible(true);
	}
}