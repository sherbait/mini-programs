package budget_manager;

import java.awt.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import budget_manager.events.*;

/**
 * An application called Household Budget Manager that provides
 * functionality to keep track of income and expenses. It has a
 * table that neatly shows the budget table.
 * 
 * @author Dinia Gepte
 *
 */
public class BudgetViewer extends JFrame
{
	// PATH FOR BUTTON IMAGES
	public static String BUTTONS_DIRECTORY = "./setup/buttons/";
	
	// WE ONLY NEED 1 BudgetViewer INSTANCE FOR THE WHOLE APPLICATION
	private static BudgetViewer viewer;
	
	// HANDLES ALL DATA
	private BudgetManager manager;
	
	// WHERE FILE IS SAVED
	private File selectedFile;
	
	// GUI COMPONENTS
	private JPanel northPanel;
	private JButton newButton;
	private JButton openButton;
	private JButton saveButton;
	private JButton saveAsButton;
	private JPanel centerPanel;
	private JPanel firstPanelInCenterPanel;
	private JTextField editNameField;
	private JFormattedTextField editStartBalanceField;
	private JRadioButton expenseRadioButton;
	private JRadioButton incomeRadioButton;
	private ButtonGroup bg;
	private JTextField itemNameField;
	private JFormattedTextField amountField;
	private JButton addItemButton;
	private JFormattedTextField durationField;
	private JLabel balanceLabel;
	private JPanel secondPanelInCenterPanel;
	
	/**
	 * Constructor for this class.
	 */
	public BudgetViewer()
	{
		super("Household Budget Manager");
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		DefaultTableModel model = new DefaultTableModel();
		manager = new BudgetManager(model);
		layoutGUI();
		initHandlers();
		disableComponentsForUnloadedFile();
	}
	
	// ACCESSOR METHODS
	public static BudgetViewer getBudgetViewer()			{	return viewer;			}
	public BudgetManager getBudgetManager()					{	return manager;			}
	public JLabel getBalanceLabel()							{	return balanceLabel;	}
	public JPanel getSecondPanelInCenterPanel()				{	return secondPanelInCenterPanel;	}
	
	// MUTATOR METHODS
	public void setBalanceLabel(JLabel label)				{	balanceLabel = label;	}
	
 	private void layoutGUI()
	{	
		centerPanel = new JPanel(new GridBagLayout());
		
		// "MENU BAR"
		northPanel = new JPanel();
		
		// MENU BAR BUTTONS
		Image image = loadImage(BUTTONS_DIRECTORY + "new.png");
		newButton = new JButton(new ImageIcon(image));
		newButton.setPreferredSize(new Dimension(36, 36));
		newButton.setToolTipText("New");
		
		image = loadImage(BUTTONS_DIRECTORY + "open.png");
		openButton = new JButton(new ImageIcon(image));
		openButton.setPreferredSize(new Dimension(36, 36));
		openButton.setToolTipText("Open");
		
		image = loadImage(BUTTONS_DIRECTORY + "save.png");
		saveButton = new JButton(new ImageIcon(image));
		saveButton.setPreferredSize(new Dimension(36, 36));
		saveButton.setToolTipText("Save");
		
		image = loadImage(BUTTONS_DIRECTORY + "save_as.png");
		saveAsButton = new JButton(new ImageIcon(image));
		saveAsButton.setPreferredSize(new Dimension(36, 36));
		saveAsButton.setToolTipText("Save As");
		
		// ADD BUTTONS TO MENU BAR
		northPanel.add(newButton);
		northPanel.add(openButton);
		northPanel.add(saveButton);
		northPanel.add(saveAsButton);
		
		// EDITING PANEL
		firstPanelInCenterPanel = new JPanel(new GridBagLayout());
		firstPanelInCenterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// BASIC INFORMATION
		addComponent(new JLabel("Name:"), firstPanelInCenterPanel, 0, 0, 1, 1);
		editNameField = new JTextField();
		editNameField.setBorder(BorderFactory.createLoweredBevelBorder());
		editNameField.setToolTipText("Hit 'TAB' to enter value");
		addComponent(editNameField, firstPanelInCenterPanel, 1, 0, 2, 1);
		addComponent(new JLabel("Starting balance:"), firstPanelInCenterPanel, 0, 1, 2, 1);
		editStartBalanceField = new JFormattedTextField(NumberFormat.getNumberInstance());
		editStartBalanceField.setBorder(BorderFactory.createLoweredBevelBorder());
		editStartBalanceField.setToolTipText("Hit 'TAB' to enter value");
		addComponent(editStartBalanceField, firstPanelInCenterPanel, 2, 1, 1, 1);
		
		// NEW ITEM
		JPanel newItemSelection = new JPanel();
		newItemSelection.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "New Item"));
		expenseRadioButton = new JRadioButton("Expense");
		incomeRadioButton = new JRadioButton("Income");
		newItemSelection.add(expenseRadioButton);
		newItemSelection.add(incomeRadioButton);
		bg = new ButtonGroup();
		bg.add(expenseRadioButton);
		bg.add(incomeRadioButton);
		addComponent(newItemSelection, firstPanelInCenterPanel, 0, 2, 3, 1);
		addComponent(new JLabel("Name: "), firstPanelInCenterPanel, 0, 3, 1, 1);
		itemNameField = new JTextField();
		itemNameField.setBorder(BorderFactory.createLoweredBevelBorder());
		itemNameField.setToolTipText("Hit 'TAB' to enter value");
		addComponent(itemNameField, firstPanelInCenterPanel, 1, 3, 2, 1);
		addComponent(new JLabel("Amount: "), firstPanelInCenterPanel, 0, 4, 1, 1);
		amountField = new JFormattedTextField(NumberFormat.getNumberInstance());
		amountField.setBorder(BorderFactory.createLoweredBevelBorder());
		amountField.setToolTipText("Hit 'TAB' to enter value");
		addComponent(amountField, firstPanelInCenterPanel, 1, 4, 2, 1);
		addItemButton = new JButton("Add Item");
		addItemButton.setFocusable(false);
		addComponent(addItemButton, firstPanelInCenterPanel, 2, 5, 1, 1);
		
		// NET BALANCE
		JPanel balancePanel = new JPanel();
		balancePanel.add(new JLabel("Balance in "));
		durationField = new JFormattedTextField(NumberFormat.getIntegerInstance());
		durationField.setBorder(BorderFactory.createLoweredBevelBorder());
		durationField.setToolTipText("Hit 'TAB' to enter value");
		durationField.setColumns(2);
		balancePanel.add(durationField);
		balancePanel.add(new JLabel(" month(s): "));
		addComponent(balancePanel, firstPanelInCenterPanel, 0, 6, 3, 1);
		balanceLabel = new JLabel("");
		balancePanel.add(balanceLabel);
		addComponent(balanceLabel, firstPanelInCenterPanel, 1, 7, 3, 1);
		
		// DISPLAY PANEL
		secondPanelInCenterPanel = new JPanel();
		JScrollPane jsp = new JScrollPane(manager);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jsp.setPreferredSize(new Dimension(300, 360));
		secondPanelInCenterPanel.add(jsp);
		
		// ADD PANELS IN centerPanel
		addComponent(northPanel, centerPanel, 0, 0, 1, 1);
		addComponent(firstPanelInCenterPanel, centerPanel, 0, 1, 1, 3);
		addComponent(secondPanelInCenterPanel, centerPanel, 1, 0, 1, 4);
		
		add(centerPanel, BorderLayout.CENTER);
		pack();
	}
	
	private void initHandlers()
	{
		NewHandler nh = new NewHandler();
		newButton.addActionListener(nh);
		
		OpenHandler oh = new OpenHandler();
		openButton.addActionListener(oh);
		
		SaveHandler sh = new SaveHandler();
		saveButton.addActionListener(sh);
		
		SaveAsHandler sah = new SaveAsHandler();
		saveAsButton.addActionListener(sah);
		
		EditNameHandler enh = new EditNameHandler();
		editNameField.addFocusListener(enh);
		
		EditStartBalanceHandler esbh = new EditStartBalanceHandler();
		editStartBalanceField.addFocusListener(esbh);
		
		ExpenseHandler eh = new ExpenseHandler();
		expenseRadioButton.addChangeListener(eh);
		
		IncomeHandler ih = new IncomeHandler();
		incomeRadioButton.addChangeListener(ih);
		
		ItemNameHandler inh = new ItemNameHandler();
		itemNameField.addFocusListener(inh);
		
		ItemAmountHandler iah = new ItemAmountHandler();
		amountField.addFocusListener(iah);
		
		AddItemHandler aih = new AddItemHandler();
		addItemButton.addActionListener(aih);
		
		DurationHandler dh = new DurationHandler();
		durationField.addFocusListener(dh);
		
		WindowHandler wh = new WindowHandler();
		this.addWindowListener(wh);
	}
	
	private void addComponent(JComponent jc, Container c, int x, int y, int w, int h)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		c.add(jc, gbc);
	}
	
	private void disableComponentsForUnloadedFile()
	{
		saveButton.setEnabled(false);
		saveAsButton.setEnabled(false);
		manager.setEnabled(false);
		editNameField.setEditable(false);
		editStartBalanceField.setEditable(false);
		expenseRadioButton.setEnabled(false);
		incomeRadioButton.setEnabled(false);
		itemNameField.setEditable(false);
		amountField.setEditable(false);
		addItemButton.setEnabled(false);
		durationField.setEditable(false);
		balanceLabel.setEnabled(false);
	}
	
	private void enableComponentsForLoadedFile()
	{
		saveButton.setEnabled(true);
		saveAsButton.setEnabled(true);
		manager.setEnabled(true);
		editNameField.setEditable(true);
		editStartBalanceField.setEditable(true);
		expenseRadioButton.setEnabled(true);
		incomeRadioButton.setEnabled(true);
		itemNameField.setEditable(true);
		amountField.setEditable(true);
		addItemButton.setEnabled(true);
		durationField.setEditable(true);
		balanceLabel.setEnabled(true);
	}
	
	private void disableComponentsForEmptyBasicInformation()
	{
		saveButton.setEnabled(false);
		saveAsButton.setEnabled(false);
		manager.setEnabled(false);
		expenseRadioButton.setEnabled(false);
		incomeRadioButton.setEnabled(false);
		itemNameField.setEditable(false);
		amountField.setEditable(false);
		addItemButton.setEnabled(false);
		durationField.setEditable(false);
		balanceLabel.setEnabled(false);
	}
	
	private void enableComponentsForFilledBasicInformation()
	{
		saveButton.setEnabled(true);
		saveAsButton.setEnabled(true);
		manager.setEnabled(true);
		expenseRadioButton.setEnabled(true);
		incomeRadioButton.setEnabled(true);
		itemNameField.setEditable(false);
		amountField.setEditable(false);
		addItemButton.setEnabled(false);
		durationField.setEditable(true);
		balanceLabel.setEnabled(true);
	}
	
	private void disableButtonsForSavedFile()
	{
		saveButton.setEnabled(false);
		manager.setEditStatus(false);
	}
	
	private void enableButtonsForUnsavedFile()
	{
		saveButton.setEnabled(true);
		manager.setEditStatus(true);
	}
	
	private void disableComponentsForNoAddItemRequest()
	{
		bg.clearSelection();
		expenseRadioButton.setEnabled(true);
		incomeRadioButton.setEnabled(true);
		itemNameField.setText(null);
		itemNameField.setEditable(false);
		amountField.setValue(null);
		amountField.setEditable(false);
		addItemButton.setEnabled(false);
	}
	
	private void enableComponentsForAddItemRequest()
	{
		itemNameField.setEditable(true);
		amountField.setEditable(true);
	}
	
	private void resetFields()
	{
		editNameField.setText(null);
		editStartBalanceField.setValue(null);
		expenseRadioButton.setSelected(false);
		incomeRadioButton.setSelected(false);
		itemNameField.setText(null);
		amountField.setValue(null);
		durationField.setValue(null);
		balanceLabel.setText(null);
		manager.setUserName("");
		manager.setStartBalance(0.0);
		manager.setIncome(new Vector<BudgetItem>());
		manager.setExpense(new Vector<BudgetItem>());
		manager.setDuration(0);
		manager.setEditStatus(false);
	}
	
	private int showSaveDialog()
	{
		String[] options = {"Yes", "No", "Cancel"};
		return JOptionPane.showOptionDialog(this, "Do you wish to save before continuing?",
				"Household Budget Manager", JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.WARNING_MESSAGE, null, options, options[2]);
	}
	
	private void transferData(BudgetManager bm)
	{
		manager.setUserName(bm.getUserName());
		manager.setDuration(bm.getDuration());
		manager.setStartBalance(bm.getStartBalance());
		manager.setExpense(bm.getExpense());
		manager.setIncome(bm.getIncome());
		manager.setBudgetItem(bm.getBudgetItem());
	}
	
	private int showOpenFileChooser()
	{
		JFileChooser jfc = new JFileChooser("setup/files");
		int result = jfc.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			selectedFile = jfc.getSelectedFile();
			if (selectedFile == null)
				JOptionPane.showMessageDialog(this, "ERROR - No selected file.", 
						"Household Budget Manager", JOptionPane.ERROR_MESSAGE);
			else
			{
				try
				{
					FileInputStream fis = new FileInputStream(selectedFile);
					ObjectInputStream ois = new ObjectInputStream(fis);
					transferData((BudgetManager)ois.readObject());
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(this, "Error loading file " + 
							selectedFile.toString() + ". Either it is corrupted or " +
							"a wrong file format.",	"Household Budget Manager", 
							JOptionPane.ERROR_MESSAGE);
					resetFields();
					disableComponentsForUnloadedFile();
					return -1;
				}
			}
		}
		return result;
	}
	
	private void loadDataIntoFields()
	{
		editNameField.setText(manager.getUserName());
		editStartBalanceField.setValue(manager.getStartBalance());
		durationField.setValue(manager.getDuration());
		manager.processNetBalanceRequest();
	}
	
	/**
	 * Responsible for GUI relationships. Depending on the data entered
	 * by the user parts of the GUI will be available or not.
	 */
	public void inputVerifier()
	{
		// USER HAS EDITED FIELDS
		if (manager.getEditStatus() == true)
			enableButtonsForUnsavedFile();
		
		// USER HAS ENTERED NAME AND STARTING BALANCE
		if (!manager.getUserName().equals("") && manager.getStartBalance() != 0.0)
			enableComponentsForFilledBasicInformation();
		
		// USER WANTS TO ADD ITEM
		if (expenseRadioButton.isSelected() || incomeRadioButton.isSelected())
		{
			enableComponentsForAddItemRequest();
			if (expenseRadioButton.isSelected())
				incomeRadioButton.setEnabled(false);
			else
				expenseRadioButton.setEnabled(false);
		}
		
		// USER HAS ENTERED VALUES IN ITEM
		if (!itemNameField.getText().equals("") && !amountField.getText().equals(""))
		{
			addItemButton.setEnabled(true);
			if (amountField.getValue() == null)
				addItemButton.setEnabled(false);
		}
		
		// USER CLICKED ADD ITEM
		if (manager.getBudgetItem().getName().equals("") && manager.getBudgetItem().getAmount() == 0.0
				&& addItemButton.isEnabled())
		{
			disableComponentsForNoAddItemRequest();
			durationField.setText(Integer.toString(manager.getDuration()));
		}
	}
	
	/**
	 * This method will be called when the user chooses to open a new file.
	 */
	public void newBudgetFile()
	{
		if (manager.getEditStatus() == true)
		{
			int selection = showSaveDialog();
			if (selection == JOptionPane.YES_OPTION)
				saveAsBudgetFile();
			else if (selection == JOptionPane.NO_OPTION)
				manager.setEditStatus(false);
			else
				return;
		}
		resetFields();
		enableComponentsForLoadedFile();
		disableComponentsForEmptyBasicInformation();
		selectedFile = null;
	}
	
	/**
	 * This method will be called when the user wants to open a previously saved file.
	 */
	public void openBudgetFile()
	{
		int result;
		if (manager.getEditStatus() == true)
		{
			int selection = showSaveDialog();
			if (selection == JOptionPane.YES_OPTION)
				saveAsBudgetFile();
			else if (selection == JOptionPane.NO_OPTION)
			{
				resetFields();
				manager.setEditStatus(false);
			}
			else
				return;
		}
		result = showOpenFileChooser();
		if (result == JFileChooser.APPROVE_OPTION)
		{
			loadDataIntoFields();
			disableComponentsForUnloadedFile();
			disableComponentsForEmptyBasicInformation();
			enableComponentsForLoadedFile();
			enableComponentsForFilledBasicInformation();
			disableButtonsForSavedFile();
			disableComponentsForNoAddItemRequest();
		}	
	}

	/**
	 * This method will be called when the user wishes to save current work.
	 */
	public void saveBudgetFile()
	{
		if (selectedFile == null)
			saveAsBudgetFile();
		else
		{
			try 
			{
				FileOutputStream fos = new FileOutputStream(selectedFile);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(manager);
				manager.setEditStatus(false);
				disableButtonsForSavedFile();
			} 
			catch (Exception e) 
			{
				JOptionPane.showMessageDialog(this, "Error saving file to " + 
						selectedFile.toString() + ". Either it is not being saved properly or " +
						"it is a wrong file format.", "Household Budget Manager", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * This method will be called when the user wishes to save current work
	 * but no file name yet.
	 */
	public void saveAsBudgetFile()
	{
		JFileChooser jfc = new JFileChooser("setup/files");
		jfc.setSelectedFile(selectedFile);
		int result = jfc.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			String fileNameTest = jfc.getSelectedFile().getName();
			
			// MAKES SURE THE USER-INPUT FILE NAME WILL BE A .obj FILE
			if (!fileNameTest.contains(".obj"))
				selectedFile = new File("setup/files/" + fileNameTest + ".obj");
			else
				selectedFile = jfc.getSelectedFile();
			saveBudgetFile();
		}
	}
	
	
	/**
	 * This method will be called when the user wishes to close the application
	 * and by clicking on the exit button on the top right hand side of the
	 * window.
	 */
	public void exitProgram()
	{
		if (manager.getEditStatus() == true)
		{
			int selection = showSaveDialog();
			if (selection == JOptionPane.YES_OPTION)
				saveAsBudgetFile();
			else if (selection == JOptionPane.CANCEL_OPTION)
				return;
		}
		System.exit(0);
	}
	
	
	/**
	 * Returns the state of the expense <CODE>JRadioButton</CODE>.
	 * @return
	 *   <CODE>true</CODE> if expense is selected, otherwise <CODE>false</CODE>.
	 */
	public boolean isExpenseSelected()			{	return expenseRadioButton.isSelected();	}
	
	/**
	 * Returns the state of the income <CODE>JRadioButton</CODE>.
	 * @return
	 *   <CODE>true</CODE> if expense is selected, otherwise <CODE>false</CODE>.
	 */
	public boolean isIncomeSelected()			{	return incomeRadioButton.isSelected();	}
	
	/**
	 * Loads an image from the directory to be used for the buttons.
	 * 
	 * @param path
	 * @return
	 */
	public Image loadImage(String path)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage(path);
		return img;
	}
	
	
	/**
	 * Main method of this application. It initializes this class.
	 * @param args
	 */
	public static void main(String[] args)
	{
		viewer = new BudgetViewer();
		viewer.setVisible(true);
	}
}
