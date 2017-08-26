package budget_manager.events;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

import budget_manager.BudgetViewer;

/**
 * This class listens for any for any changes made on the item
 * amount text box. 
 * 
 * @author Dinia Gepte
 *
 */
public class ItemAmountHandler implements FocusListener
{
	public void focusGained(FocusEvent fe) 	{	}

	public void focusLost(FocusEvent fe) 
	{	
		JFormattedTextField jtf = (JFormattedTextField)fe.getSource();
		try
		{
			double d = Double.parseDouble(jtf.getFormatter().stringToValue(jtf.getText()).toString());
			BudgetViewer.getBudgetViewer().getBudgetManager().getBudgetItem().setAmount(d);
			jtf.setValue(d);
		}
		catch (Exception e)
		{	
			if (!jtf.getText().equals(""))
			{	
				JOptionPane.showMessageDialog(BudgetViewer.getBudgetViewer(), "You must enter a number.");
				jtf.setValue(null);
			}
		}
		BudgetViewer.getBudgetViewer().inputVerifier();
	}
}
