package budget_manager.events;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import budget_manager.BudgetViewer;

/**
 * This class listens for changes made in the start balance field
 * in the main window. It makes sure that the value entered is valid
 * before adding to the table.
 * 
 * @author Dinia Gepte
 *
 */
public class EditStartBalanceHandler implements FocusListener 
{
	public void focusGained(FocusEvent fe)		{	}

	public void focusLost(FocusEvent fe)
	{
		JFormattedTextField jtf = (JFormattedTextField)fe.getSource();
		try
		{
			BudgetViewer.getBudgetViewer().getBudgetManager().setStartBalance(
					Double.parseDouble(jtf.getFormatter().stringToValue(jtf.getText()).toString()));
		}
		catch (Exception e)
		{	
			if (!jtf.getText().equals(""))
				JOptionPane.showMessageDialog(BudgetViewer.getBudgetViewer(), "You must enter a number.");
		}
		BudgetViewer.getBudgetViewer().inputVerifier();
	}
}
