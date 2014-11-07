package shared.communicator;

import java.util.ArrayList;

import shared.model.Record;

/**
 * 
 * contains the parameters for the submitBatch method in the CC
 * @author Will
 *
 */
public class SubmitBatchParams
{
	String userName;
	String password;
	int projectID;
	int batchID;
	boolean validity;
	String translatable;

	public int getBatchID()
	{
		return batchID;
	}

	public void setBatchID(int batchID)
	{
		this.batchID = batchID;
	}

	ArrayList<Record> records;

	public SubmitBatchParams(String userNameIn, String passwordIn,
			int projectIDIn, String tranlateToRecords, int batchIDIn)
	{
		userName = userNameIn;
		password = passwordIn;
		projectID = projectIDIn;
		batchID = batchIDIn;
		translatable = tranlateToRecords;
		records = Translate(tranlateToRecords, batchIDIn);
	}

	public SubmitBatchParams(String userNameIn, String passwordIn,
			int batchIDIn, String tranlateToRecords)
	{
		userName = userNameIn;
		password = passwordIn;
		batchID = batchIDIn;
		translatable = tranlateToRecords;
		records = Translate(tranlateToRecords, batchID);
	}

	private ArrayList<Record> Translate(String input, int batchID)
	{
		ArrayList<Record> result = new ArrayList<Record>();
		String[] rows = input.split(";", -1);
		for (int i = 0; i < rows.length; i++)
		{
			String[] cells = rows[i].split(",", -1);
			for (int j = 0; j < cells.length; j++)
			{
				String cell = cells[j];
				result.add(new Record(j, cell, batchID));
			}
		}
		return result;
	}

	private ArrayList<Record> Translate(String input, int batchID,
			int numFields, int numRecords)
	{
		ArrayList<Record> result = new ArrayList<Record>();
		String[] rows = input.split(";", -1);
		for (int i = 0; i < rows.length; i++)
		{
			String[] cells = rows[i].split(",", -1);
			if (numFields != cells.length)
			{
				validity = false;
			}
			for (int j = 0; j < cells.length; j++)
			{
				String cell = cells[j];
				result.add(new Record(j, cell, batchID));
			}
		}
		if (numRecords * numFields != result.size())
		{
			validity = false;
		}
		return result;
	}

	public boolean wasValid(int numFields, int numRecords)
	{
		ArrayList<Record> result = new ArrayList<Record>();
		String[] rows = translatable.split(";", -1);
		if ((numRecords) != rows.length) { return false; }
		for (int i = 0; i < rows.length; i++)
		{
			String[] cells = rows[i].split(",", -1);
			if (numFields != cells.length)
			{
 return false;
			}
			for (int j = 0; j < cells.length; j++)
			{
				String cell = cells[j];
				result.add(new Record(j, cell, batchID));
			}
		}

		return true;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public int getProjectID()
	{
		return projectID;
	}

	public void setProjectID(int projectID)
	{
		this.projectID = projectID;
	}

	public ArrayList<Record> getRecords()
	{
		return records;
	}

	public void setRecords(ArrayList<Record> records)
	{
		this.records = records;
	}
}
