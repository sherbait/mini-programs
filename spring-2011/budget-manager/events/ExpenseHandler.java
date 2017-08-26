package budget_manager.events;

import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import budget_manager.BudgetViewer;

/**
 * This class listens for any changes made on the expense
 * radio button on the main window.
 * 
 * @author Dinia Gepte
 *
 */
public class ExpenseHandler implements ChangeListener
{
	public void stateChanged(ChangeEvent ce)
	{
		JRadioButton jrb = (JRadioButton)ce.getSource();
		if (jrb.isSelected())
			BudgetViewer.getBudgetViewer().inputVerifier();
	}
}
