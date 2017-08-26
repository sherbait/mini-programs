package budget_manager.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import budget_manager.BudgetViewer;

/**
 * This class is called whenever the user clicks on the 
 * save as button in the main window.
 * 
 * @author Dinia Gepte
 *
 */
public class SaveAsHandler implements ActionListener 
{
	public void actionPerformed(ActionEvent ae)
	{
		BudgetViewer.getBudgetViewer().saveAsBudgetFile();
	}
}
