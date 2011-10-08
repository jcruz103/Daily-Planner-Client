package dailyPlanner;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Panel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JTextField;
import javax.swing.JButton;

public class addContactFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel nameLable = null;
	private Panel panel = null;
	private JTextField nameTextArea = null;
	private JLabel addressLabel = null;
	private JTextField addressTextArea = null;
	private JLabel phoneLabel = null;
	private JTextField phoneTextArea = null;
	private JLabel descriptionLabel = null;
	private JTextField descriptionTextArea = null;
	private JLabel imLabel = null;
	private JTextField imTextField = null;
	private JButton addButton = null;
	private DailyPlannerGUI parentFrame;
	private String ID;  //  @jve:decl-index=0:
	private int rowNumber;
	private Hashtable<String, String[]> tableList;
	private JButton deleteButton = null;
	private OutputWriter writer;
	/**
	 * This is the default constructor
	 */
	
	//Constructor to add a user
	public addContactFrame(final DailyPlannerGUI frame, OutputWriter w) 
	{
		super();
		initialize();
		
		this.parentFrame = frame;		
		writer = w;
		
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				//this gets all the text that the user writes and calls the OutputWriters addConent method
				writer.addContent(nameTextArea.getText(), addressTextArea.getText(), phoneTextArea.getText(), 
				descriptionTextArea.getText(), imTextField.getText());
				
				//calls method to update table and dispose of frame
				finalCommand(frame);
			}
		});
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	
	//Constructor for editing and deleting contact
	//passes table row with the information of the contact
	public addContactFrame(final DailyPlannerGUI frame, String name, String address, 
			String phone, String description, String im, Hashtable<String, String[]> table, 
			int rowN, OutputWriter w)
	{
		super();
		initialize();
		jContentPane.add(getDeleteButton(), null);
		
		this.parentFrame = frame;
		tableList = new Hashtable<String, String[]>();
		tableList = table;
		writer = w;
		
		//sets the information of the contact with the information passed from the constructor
		nameTextArea.setText(name);
		addressTextArea.setText(address);
		phoneTextArea.setText(phone);
		descriptionTextArea.setText(description);
		imTextField.setText(im);
		addButton.setText("Update");
		//row number of the contact
		rowNumber = rowN;
		
		this.setTitle("Edit Contact");
		
		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				//does a look up with the hashtable and the row number to find contacts ID
				findID(tableList, rowNumber);
				System.out.println(ID);
				
				//calls the OutputWWriter updateContact method to update the contacts
				writer.updateContact(ID, nameTextArea.getText(), addressTextArea.getText(), phoneTextArea.getText(), 
						descriptionTextArea.getText(), imTextField.getText());;
				
				//update table and destroy frame
				finalCommand(frame);
			}
		});
		
		deleteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				//does a look up with the hashtable and the row number to find contacts ID
				findID(tableList, rowNumber);
				//calls output writer method deleteContact to delete contact
				writer.deleteContact(ID);
				//update table and destroy frame
				finalCommand(frame);
			}
		});
	}

	private void initialize() {
		this.setSize(338, 229);
		this.setContentPane(getJContentPane());
		this.setTitle("Add Contact");
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			nameLable = new JLabel();
			nameLable.setText("Name: ");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getPanel(), null);
			jContentPane.add(getAddButton(), null);
		}
		return jContentPane;
	}

	private Panel getPanel() {
		if (panel == null) {
			imLabel = new JLabel();
			imLabel.setText("IM: ");
			descriptionLabel = new JLabel();
			descriptionLabel.setText("Description: ");
			phoneLabel = new JLabel();
			phoneLabel.setText("Phone: ");
			addressLabel = new JLabel();
			addressLabel.setText("Address: ");
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(5);
			gridLayout.setHgap(17);
			gridLayout.setColumns(2);
			gridLayout.setVgap(6);
			panel = new Panel();
			panel.setLayout(gridLayout);
			panel.setBounds(new Rectangle(0, -1, 319, 142));
			panel.add(nameLable, null);
			panel.add(getNameTextArea(), null);
			panel.add(addressLabel, null);
			panel.add(getAddressTextArea(), null);
			panel.add(phoneLabel, null);
			panel.add(getPhoneTextArea(), null);
			panel.add(descriptionLabel, null);
			panel.add(getDescriptionTextArea(), null);
			panel.add(imLabel, null);
			panel.add(getImTextField(), null);
		}
		return panel;
	}

	private JTextField getNameTextArea() {
		if (nameTextArea == null) {
			nameTextArea = new JTextField();
		}
		return nameTextArea;
	}

	private JTextField getAddressTextArea() {
		if (addressTextArea == null) {
			addressTextArea = new JTextField();
		}
		return addressTextArea;
	}

	private JTextField getPhoneTextArea() {
		if (phoneTextArea == null) {
			phoneTextArea = new JTextField();
		}
		return phoneTextArea;
	}

	private JTextField getDescriptionTextArea() {
		if (descriptionTextArea == null) {
			descriptionTextArea = new JTextField();
		}
		return descriptionTextArea;
	}

	private JTextField getImTextField() {
		if (imTextField == null) {
			imTextField = new JTextField();
		}
		return imTextField;
	}

	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setBounds(new Rectangle(0, 158, 81, 25));
			addButton.setText("Add");
		}
		return addButton;
	}
	
	private void findID(Hashtable<String, String[]> table, int rowNumber)
	{
		String[] compareVar;
		
		compareVar = table.get(Integer.toString(rowNumber+1));
		
		ID = compareVar[0];
		
	}

	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText("Delete");
			deleteButton.setBounds(new Rectangle(241, 158, 81, 25));
		}
		return deleteButton;
	}
	
	private void finalCommand(DailyPlannerGUI frame)
	{
		frame.updateContactTable();
		dispose();
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
