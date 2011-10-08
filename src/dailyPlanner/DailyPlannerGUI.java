package dailyPlanner;

import javax.swing.*;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Hashtable;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import com.toedter.calendar.JCalendar;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class DailyPlannerGUI extends JFrame {

	static final long serialVersionUID = 1L;
	JPanel jContentPane = null;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock; //  @jve:decl-index=0:
	private JScrollPane contactTable = null;
	private JTable ContactTable = null;
	DefaultTableModel model;
	InputReader IO;
	private JButton addButton = null;
	private final DailyPlannerGUI frame;
	private JLabel calanderLabel = null;
	private JLabel contactsLabel = null;
	private JCalendar calendar = null;
	private JButton addPlan = null;
	private OutputWriter print;
	private Timer timer;
	/**
	 * This is the default constructor
	 */
	public DailyPlannerGUI() {
		super();
		initialize();
		setNetwork(); //set up network
		IO = new InputReader(reader); //create new InputReader class
		frame = this;	
		print = new OutputWriter(writer, frame); // create new outputreader class
		timer = new Timer(150, new ActionListener() // New Timer for up to date time on calebdar
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				addPlan.setText(calendar.getDate().toString());
			}
			
		});
		timer.start();
		addListeners(); // add listeners to all buttons
		Thread t = new Thread(IO);// create threads for the output and input readers
		t.start();// start thread
		
		updateContactTable();//first update of table	
	}
	
	private void addListeners()
	{

		addButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				//creates new add contact frames
				addContactFrame addFrame = new addContactFrame(frame, print);
				addFrame.setVisible(true);
			}
		});
		
		ContactTable.addMouseListener(new MouseListener()
		{
			String[] tableInfo;// creates new String array to hold the contacts information
			int RowNumber; // holds what row number the contact is
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				tableInfo = new String[5];
				
				for(int i = 0; i < 5; i++)
				{
					//Retrieves the row of information and converts it into a string
					tableInfo[i] = (String)ContactTable.getValueAt(ContactTable.getSelectedRow(), i);
				}
				
				RowNumber = ContactTable.getSelectedRow();

				//creates frame to edit or delete contact
				addContactFrame addFrame = new addContactFrame(frame, tableInfo[0], 
						tableInfo[1], tableInfo[2], tableInfo[3], tableInfo[4], 
						IO.getData(), RowNumber, print);
				addFrame.setVisible(true);
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		addPlan.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				//add a plan from the calendar into the DBMS
				DatePlanner planner = new DatePlanner(calendar.getDate(), print);
				planner.setVisible(true);
			}
			
		});
	}

	private void initialize() {
		this.setSize(1042, 623);
		this.setContentPane(getJContentPane());
		this.setTitle("Daily Planner");
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			contactsLabel = new JLabel();
			contactsLabel.setBounds(new Rectangle(17, 1, 165, 22));
			contactsLabel.setText("Contacts");
			calanderLabel = new JLabel();
			calanderLabel.setBounds(new Rectangle(699, 0, 221, 17));
			calanderLabel.setText("Calander");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getContactTable(), null);
			jContentPane.add(getAddButton(), null);
			jContentPane.add(calanderLabel, null);
			jContentPane.add(contactsLabel, null);
			jContentPane.add(getCalendar(), null);
		}
		return jContentPane;
	}

	public void setNetwork()
	{
		try
		{
			//create new connection to set IP and new reader and writer
			sock = new Socket("192.168.1.65", 1234);
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("net done");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	private JScrollPane getContactTable() {
		if (contactTable == null) {
			contactTable = new JScrollPane();
			contactTable.setBounds(new Rectangle(14, 28, 678, 482));
			contactTable.setViewportView(getContactTable2());
		}
		return contactTable;
	}

	@SuppressWarnings("serial")
	private JTable getContactTable2() {
		if (ContactTable == null) {			
			model = new DefaultTableModel( new String[][] 
					{{"","","","","",""}}, new String[] 
							{ "ID","First Name", "Addres", "Phone", "Description", "IM"}){
				@Override
				public boolean isCellEditable(int row, int column)
				{
					return false;
				}
			};
			ContactTable = new JTable(model);
			//hides the ID column and removes the first empty row
			ContactTable.removeColumn(ContactTable.getColumnModel().getColumn(0));
			model.removeRow(0);
			
		}
		return ContactTable;
	}

	public void updateContactTable()
	{
		try
		{
			writer.println(1);
			writer.println(1);
			writer.println(false);
			writer.println(false);
			writer.flush();
			//clears the values that are stored from the previous input
			IO.resetList();
			
			//sleeps for half a second to wait for the input
			Thread.sleep(500);
			if(IO.getRows() > 0)
			{
				
				int rowCount = 0;
				//gets row count and increases by one to match row count
				rowCount =	model.getRowCount() + 1;
				
				//removes any rows 
				for(int j = 1; j < rowCount; j++)
					model.removeRow(0);
					
				
				System.out.println(ContactTable.getRowCount());
				
				for(int i = 0; i < IO.getRows(); i++)
				{
					//addes one contact at a time to the table from the INPUT reader class
					model.insertRow((ContactTable.getRowCount() < 0) ? 1 
							: ContactTable.getRowCount(), new Object[]
									{IO.getValues(0), IO.getValues(0), 
									IO.getValues(0), IO.getValues(0), 
									IO.getValues(0), IO.getValues(0)});
					
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setBounds(new Rectangle(15, 521, 107, 24));
			addButton.setText("Add Contact");
		}
		return addButton;
	}

	private JCalendar getCalendar() {
		if (calendar == null) {
			calendar = new JCalendar();
			calendar.setBounds(new Rectangle(705, 26, 315, 207));
			calendar.add(getAddPlan(), BorderLayout.SOUTH);
		}
		return calendar;
	}

	private JButton getAddPlan() {
		if (addPlan == null) {
			addPlan = new JButton();
			addPlan.setText("Add Plan ");
			addPlan.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		}
		return addPlan;
	}
	

}  //  @jve:decl-index=0:visual-constraint="10,10" 
