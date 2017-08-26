package budget_manager.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import budget_manager.BudgetViewer;

/**
 * This class is called whenever user clicks on the
 * open button in the main window.
 * 
 * @author Dinia Gepte
 *
 */
public class OpenHandler implements ActionListener 
{
	public void actionPerformed(ActionEvent ae)
	{
		BudgetViewer.getBudgetViewer().openBudgetFile();		
	}
}
