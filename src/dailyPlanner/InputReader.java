package dailyPlanner;

import java.io.*;
import java.util.*;

public class InputReader implements Runnable{

	private BufferedReader reader;
	private ArrayList<String> value;
	public Hashtable<String, String[]> tableList;
	int columns;
	
	public InputReader(BufferedReader reader)
	{
		//gets the reader that is going to be used to read the input
		this.reader = reader;
		value = new ArrayList<String>();
		tableList = new Hashtable<String, String[]>();
	}
	
	@Override
	public void run()
	{
		String message;

		try
		{
			//checks if there is still data coming in
			while((message = reader.readLine()) != null)
			{
				//adds the values from the input and divide it by 
				//6 because total rows are a length of 6
				value.add(message);
				columns = value.size() / 6;
				
				//if columns becomes greater then 0,
				//then check if the reader is not ready, if so call tableColumns
				if(columns > 0)
					if(!reader.ready())
						tableColumns();
			}
				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void tableColumns()
	{
		System.out.println("Number of columns to be added to the table List: " + (columns));
		int multNumber;
		//start the columns by 1, then multi the number of iteration by 6
		//from there subtract by 6 to make sure everything gets placed in the tableList
		for(int i = 1; i < columns + 1; i++)
		{
			multNumber = 6 * i;
			tableList.put(Integer.toString(i), new String[]{value.get(multNumber - 6)});
		}

	}
	public Object getValues(int i)
	{
		//outputs the data to the planner GUI and removes it from the list
		Object returnval = value.get(i);
		value.remove(i);
		
		return returnval;

	}
	
	public int getRows()
	{
		return columns;
	}
	
	public void resetList()
	{
		columns = 0;
		value.clear();
	}
	
	public Hashtable<String, String[]> getData()
	{
		return tableList;
	}
}
