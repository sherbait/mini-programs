package budget_manager.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import budget_manager.BudgetViewer;

/**
 * This class is called whenever the user presses the new
 * button in the main window.
 * 
 * @author Dinia Gepte
 *
 */
public class NewHandler implements ActionListener 
{
	public void actionPerformed(ActionEvent e)
	{
		BudgetViewer.getBudgetViewer().newBudgetFile();
	}
}
