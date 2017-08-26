package budget_manager.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import budget_manager.BudgetViewer;

/**
 * This class is called whenever the user clicks on 
 * the save button in the main window.
 * 
 * @author Dinia Gepte
 *
 */
public class SaveHandler implements ActionListener 
{
	public void actionPerformed(ActionEvent ae)
	{
		BudgetViewer.getBudgetViewer().saveBudgetFile();
	}
}
