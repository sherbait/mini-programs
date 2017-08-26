package budget_manager.events;

import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import budget_manager.BudgetViewer;

/**
 * This class listens for changes in the income radio
 * button in the main window. If this radio button
 * is active, the expense radio button deactivates.
 * 
 * @author Dinia Gepte
 *
 */
public class IncomeHandler implements ChangeListener
{
	public void stateChanged(ChangeEvent ce)
	{
		JRadioButton jrb = (JRadioButton)ce.getSource();
		if (jrb.isSelected())
			BudgetViewer.getBudgetViewer().inputVerifier();
	}
}
