package dailyPlanner;

import java.io.*;
import java.util.Date;

import javax.swing.*;

public class OutputWriter 
{
	PrintWriter writer;
	DailyPlannerGUI planner;
	
	public OutputWriter(PrintWriter w, DailyPlannerGUI g)
	{
		writer = w;
		planner = g;
	}
	
	//updates the contact with : as the delimiter with the code 3 for update 
	public void updateContact(String id, String name, String address, String phone, String description, String im)
	{
		try
		{
			writer.println(3);
			writer.println(id + ":" + name + ":" + address + ":" + phone + ":" + description + ":" + im);
			writer.flush();
			planner.updateContactTable();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	//deletes the contact with the code 4 for delete
	public void deleteContact(String id)
	{
		try
		{
			writer.println(4);
			writer.println(id);
			writer.flush();
			planner.updateContactTable();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void addContent(String name, String address, String phone, String description, String im)
	{
		try
		{
			writer.println(2);
			writer.println(name + ":" + address + ":" + phone + ":" + description + ":" + im);
			writer.flush();
			planner.updateContactTable();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void addPlan(String planDescription, Date date)
	{
		try
		{
			writer.println(5);
			writer.println(planDescription + ":" + date);
			writer.flush();
			JDialog jd = new JDialog(planner);
			jd.setSize(200,100);
			jd.add(new JLabel("You plan has been added"));
			jd.setVisible(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
