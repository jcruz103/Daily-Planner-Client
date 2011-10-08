package dailyPlanner;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatePlanner extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel dateLable = null;
	private JButton submitButton = null;
	private Date date;
	private JTextArea planTextField = null;
	private OutputWriter writer;

	public DatePlanner(final Date date, OutputWriter w) {
		super();
		initialize();
		this.date = date;
		writer = w;
		dateLable.setText("Set Current Plan For: " + date.toString());
		
		submitButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				writer.addPlan(planTextField.getText(), date);
			}
			
		});
	}

	private void initialize() {
		this.setSize(522, 395);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			dateLable = new JLabel();
			dateLable.setBounds(new Rectangle(6, 5, 498, 28));
			dateLable.setText("Set Current Plan For: ");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(dateLable, null);
			jContentPane.add(getSubmitButton(), null);
			jContentPane.add(getPlanTextField(), null);
		}
		return jContentPane;
	}

	private JButton getSubmitButton() {
		if (submitButton == null) {
			submitButton = new JButton();
			submitButton.setBounds(new Rectangle(9, 326, 81, 25));
			submitButton.setText("Submit");
		}
		return submitButton;
	}

	private JTextArea getPlanTextField() {
		if (planTextField == null) {
			planTextField = new JTextArea();
			planTextField.setBounds(new Rectangle(7, 37, 496, 280));
			planTextField.setWrapStyleWord(true);
			planTextField.setBackground(Color.white);
			planTextField.setLineWrap(true);
		}
		return planTextField;
	}

} 
