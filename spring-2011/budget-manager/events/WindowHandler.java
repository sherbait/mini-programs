package budget_manager.events;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import budget_manager.BudgetViewer;

/**
 * This class is called whenever the user wishes to exit the program
 * by clicking on the exit button on the window.
 *
 * @author Dinia Gepte
 *
 */
public class WindowHandler implements WindowListener 
{
	public void windowActivated(WindowEvent arg0)		{	}
	public void windowClosed(WindowEvent arg0) 			{	}

	public void windowClosing(WindowEvent arg0)
	{
		BudgetViewer.getBudgetViewer().exitProgram();
	}

	public void windowDeactivated(WindowEvent arg0) 	{	}
	public void windowDeiconified(WindowEvent arg0) 	{	}
	public void windowIconified(WindowEvent arg0) 		{	}
	public void windowOpened(WindowEvent arg0) 			{	}
}
