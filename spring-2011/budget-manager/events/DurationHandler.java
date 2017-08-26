package budget_manager.events;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

import budget_manager.BudgetViewer;

/**
 * This class is called when the duration text box loses its focus.
 * It also updates the duration variable in the BudgetManager class.
 * 
 * @author Dinia Gepte
 *
 */
public class DurationHandler implements FocusListener 
{
	public void focusGained(FocusEvent fe) {	}
	
	public void focusLost(FocusEvent fe)
	{
		JFormattedTextField jtf = (JFormattedTextField)fe.getSource();
		try
		{
			BudgetViewer.getBudgetViewer().getBudgetManager().setDuration(
					Integer.parseInt(jtf.getFormatter().stringToValue(jtf.getText()).toString()));
			BudgetViewer.getBudgetViewer().getBudgetManager().processNetBalanceRequest();
		}
		catch (Exception e)
		{	
			if (!jtf.getText().equals(""))
				JOptionPane.showMessageDialog(BudgetViewer.getBudgetViewer(), "You must enter a number.");
		}
		BudgetViewer.getBudgetViewer().inputVerifier();
	}
}
