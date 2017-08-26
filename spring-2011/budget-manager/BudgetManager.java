package budget_manager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * This class represents the table in the Household Budget Manager
 * application. It also stores table values and does the computation.
 * 
 * @author Dinia Gepte
 *
 */
public class BudgetManager extends JTable implements Serializable
{
	private String name;
	private double startBalance;
	private int duration;
	private Vector<BudgetItem> expense;
	private Vector<BudgetItem> income;
	private boolean isEdited;
	private BudgetItem item;
	
	public BudgetManager(DefaultTableModel model)
	{
		super(model);
		name = "";
		startBalance = 0.0;
		duration = 0;
		expense = new Vector<BudgetItem>();
		income = new Vector<BudgetItem>();
		isEdited = false;
		item = new BudgetItem("", 0.0);
	}
	
	// ACCESSOR METHODS
	public String getUserName()				{	return name;			}
	public double getStartBalance()			{	return startBalance;	}
	public int getDuration()				{	return duration;		}
	public Vector<BudgetItem> getExpense()	{	return expense;			}
	public Vector<BudgetItem> getIncome()	{	return income;			}
	public boolean getEditStatus()			{	return isEdited;		}
	public BudgetItem getBudgetItem()		{	return item;			}
	
	// MUTATOR METHODS
	public void setUserName(String initName)
	{	
		if (startBalance != 0.0)
			isEdited = true;
		name = initName;
	}
	
	public void setStartBalance(double balance)	
	{	
		if (!name.equals(""))
			isEdited = true;
		startBalance = balance;
	}
	
	public void setDuration(int time)
	{	
		duration = time;
		isEdited = true;
	}
	
	public void setExpense(Vector<BudgetItem> initExpense)
	{
		expense = initExpense;
		updateTable();
	}
	
	public void setIncome(Vector<BudgetItem> initIncome)
	{
		income = initIncome;
		updateTable();
	}
	
	public void setEditStatus(boolean flag)
	{
		isEdited = flag;
	}
	
	public void setBudgetItem(BudgetItem initItem)
	{
		item = initItem;
	}
	
	/**
	 * This method adds an expense item to the table
	 * and updates the table accordingly.
	 */
	public void processAddExpenseRequest()
	{
		expense.addElement(new BudgetItem(item.getName(), -item.getAmount()));
		resetItem();
		isEdited = true;
		updateTable();
	}
	
	/**
	 * This method adds an income item to the table
	 * and updates the table accordingly.
	 */
	public void processAddIncomeRequest()
	{
		income.addElement(new BudgetItem(item.getName(), item.getAmount()));
		resetItem();
		isEdited = true;
		updateTable();
	}
	
	/**
	 * This method handles the net balance after the given
	 * duration time. It updates the <CODE>JLabel</CODE>
	 * representing the net balance accordingly.
	 */
	public void processNetBalanceRequest()
	{
		double netBalance = startBalance + ((sum(expense) + sum(income)) * ((Integer)duration).doubleValue());
		if (netBalance < 0.0)
			BudgetViewer.getBudgetViewer().getBalanceLabel().setForeground(Color.RED);
		else if (netBalance > 0.0)
			BudgetViewer.getBudgetViewer().getBalanceLabel().setForeground(Color.GREEN);
		BudgetViewer.getBudgetViewer().getBalanceLabel().setText("$ " + 
				Double.toString(roundTo3Decimals(netBalance)));
		updateTable();
	}
	
	public boolean isCellEditable(int row, int column)
	{	
		return false;
	}
	
	/**
	 * Responsible for updating the table in the viewer.
	 */
	private void updateTable()
	{
		DefaultTableModel tableModel = new DefaultTableModel();
		setModel(tableModel);
		
		setFillsViewportHeight(true);
		setBackground(Color.WHITE);
		
		// HEADERS
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		getTableHeader().setBackground(Color.WHITE);
		
		// ADD THE COLUMNS
		tableModel.addColumn("");
		tableModel.addColumn("Item Name");
		tableModel.addColumn("Amount");
		
		// COLUMNS
		TableColumn column = this.getColumnModel().getColumn(0);
		column.setPreferredWidth(30);
		column = this.getColumnModel().getColumn(1);
		column.setPreferredWidth(40);
		column = this.getColumnModel().getColumn(2);
		column.setPreferredWidth(40);
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer()
		{
			public Component getTableCellRendererComponent(
                    JTable table, Object color,
                    boolean isSelected, boolean hasFocus,
                    int row, int column)
			{
				Component renderer = super.getTableCellRendererComponent(table, color, isSelected, hasFocus, row, column);
				if (column == 2)
				{
					BudgetManager bm = (BudgetManager)table;
					if (row >= 1 && row <= (1+bm.getIncome().size()))
						renderer.setForeground(Color.GREEN);
					if (row >= (4+bm.getIncome().size()) && row <= (4+bm.getIncome().size()+bm.getExpense().size()))
						renderer.setForeground(Color.RED);
					if (row == bm.getRowCount()-1)
					{
						String value = (String) bm.getValueAt(row, 2);
						value = value.substring(2);
						if (Double.parseDouble(value) < 0)
							renderer.setForeground(Color.RED);
						else if (Double.parseDouble(value) > 0)
							renderer.setForeground(Color.GREEN);
						else
							renderer.setForeground(Color.BLACK);
					}
				}
				return renderer;
			}
		};
		column.setCellRenderer(dtcr);
		
		// ADD THE ROWS
		tableModel.setRowCount(income.size()+expense.size()+7);
		
		int row = 0;
		// INCOME
		tableModel.setValueAt("Income", row, 0);
		row++;
		if (!income.isEmpty())
		{
			for (int i = 0; i < income.size(); row++, i++)
			{
				tableModel.setValueAt(income.get(i).getName(), row, 1);
				tableModel.setValueAt("$ " + roundTo3Decimals(income.get(i).getAmount()), row, 2);
			}
		}
		tableModel.setValueAt("Total: ", row, 0);
		tableModel.setValueAt("$ " + roundTo3Decimals(sum(income)), row, 2);
		row++;
		tableModel.setValueAt("", row, 0);
		row++;
		// EXPENSE
		tableModel.setValueAt("Expense", row, 0);
		row++;
		if(!expense.isEmpty())
		{
			for (int i = 0; i < expense.size(); row++, i++)
			{
				tableModel.setValueAt(expense.get(i).getName(), row, 1);
				tableModel.setValueAt("$ " + roundTo3Decimals(expense.get(i).getAmount()), row, 2);
			}
		}
		tableModel.setValueAt("Total: ", row, 0);
		tableModel.setValueAt("$ " + roundTo3Decimals(sum(expense)), row, 2);
		row++;
		tableModel.setValueAt("", row, 0);
		row++;
		// SUBTOTAL
		tableModel.setValueAt("SUBTOTAL: ", row, 0);
		tableModel.setValueAt("$ " + roundTo3Decimals((sum(income) + sum(expense))), row, 2);
	}
	
	private double sum(Vector<BudgetItem> items)
	{
		double sum = 0.0;
		for (int i = 0; i < items.size(); i++)
			sum = sum + items.get(i).getAmount();
		return sum;
	}
	
	private double roundTo3Decimals(double num)
	{
		return  Math.floor(num * 1000.0 + 0.5) / 1000.0;
	}
	
	private void resetItem()
	{
		item.setName("");
		item.setAmount(0.0);
	}
}
