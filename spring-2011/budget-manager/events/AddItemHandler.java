package budget_manager.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import budget_manager.BudgetViewer;

/**
 * This class is called when the user clicks on the Add item button.
 * 
 * @author Dinia Gepte
 *
 */
public class AddItemHandler implements ActionListener 
{
	public void actionPerformed(ActionEvent ae)
	{
		if (BudgetViewer.getBudgetViewer().isExpenseSelected())
			BudgetViewer.getBudgetViewer().getBudgetManager().processAddExpenseRequest();
		else if (BudgetViewer.getBudgetViewer().isIncomeSelected())
			BudgetViewer.getBudgetViewer().getBudgetManager().processAddIncomeRequest();
		BudgetViewer.getBudgetViewer().getBudgetManager().processNetBalanceRequest();
		BudgetViewer.getBudgetViewer().inputVerifier();
	}
}
