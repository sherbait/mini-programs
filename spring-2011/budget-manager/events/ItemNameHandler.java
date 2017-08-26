package budget_manager.events;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import budget_manager.BudgetViewer;

/**
 * This class listens for any changes made in the item name
 * text box in the main window. It sets the <CODE>BudgetItem</CODE>'s
 * name in the <CODE>BudgetManager</CODE> with the given value
 * in the text box.
 * 
 * @author Dinia Gepte
 *
 */
public class ItemNameHandler implements FocusListener
{
	public void focusGained(FocusEvent fe) 	{	}

	public void focusLost(FocusEvent fe)
	{
		JTextField jtf = (JTextField)fe.getSource();
		if (!jtf.getText().equals(""))
			BudgetViewer.getBudgetViewer().getBudgetManager().getBudgetItem().setName(jtf.getText());
		BudgetViewer.getBudgetViewer().inputVerifier();
	}
}
