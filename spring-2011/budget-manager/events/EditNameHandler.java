package budget_manager.events;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import budget_manager.BudgetViewer;

/**
 * This class listens for any changes made on the user name field
 * in the main window.
 * 
 * @author Dinia Gepte
 *
 */
public class EditNameHandler implements FocusListener
{
	public void focusGained(FocusEvent arg0) {	}

	public void focusLost(FocusEvent fe) 
	{
		JTextField jtf = (JTextField)fe.getSource();
		if (!jtf.getText().equals(""))
			BudgetViewer.getBudgetViewer().getBudgetManager().setUserName(jtf.getText());
		
		BudgetViewer.getBudgetViewer().inputVerifier();
	}
}
