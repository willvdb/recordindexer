package shared.communicator;

import java.util.ArrayList;
import java.util.List;

import shared.model.Record;

/**
 * 
 * contains the results for the search method in the CC
 * @author Will
 *
 */
public class SearchResult
{	
	List<Find> output;
	boolean success;
	
	public SearchResult()
	{
		output = new ArrayList<Find>();
		success = true;
	}

	public SearchResult(boolean b)
	{
		success = b;
	}

	public List<Find> getOutput()
	{
		return output;
	}

	public void setOutput(List<Find> output)
	{
		this.output = output;
	}

	public void addAnswer(Record r, int fieldID, String imageURL)
	{
		Find f = new Find();
		f.setBatchID(r.batchID);
		f.setFieldID(fieldID);
		f.setImageURL(imageURL);
		f.setRecordNumber(r.getY() + 1);
		output.add(f);
	}

	public String getResult()
	{
		String result = "";
		if (success)
		{
			for (Find find : output)
			{
				result += find.getBatchID() + "\n";
				result += find.getImageURL() + "\n";
				result += find.getRecordNumber() + "\n";
				result += find.getFieldID() + "\n";
			}
		}
		else
		{
			result += "FAILED\n";
		}
		return result;
	}

	public String getResultWithURL(String host, String port)
	{
		String result = "";
		String url = "http://" + host + ":" + port + "/";
		if (success)
		{
			for (Find find : output)
			{
				result += find.getBatchID() + "\n";
				result += url + find.getImageURL() + "\n";
				result += find.getRecordNumber() + "\n";
				result += find.getFieldID() + "\n";
			}
		}
		else
		{
			result += "FAILED\n";
		}
		return result;
	}

	private class Find
	{
		int batchID;
		int fieldID;
		String imageURL;
		int recordNumber;
		public int getBatchID()
		{
			return batchID;
		}
		public void setBatchID(int batchID)
		{
			this.batchID = batchID;
		}
		public int getFieldID()
		{
			return fieldID;
		}
		public void setFieldID(int fieldID)
		{
			this.fieldID = fieldID;
		}

		public String getImageURL()
		{
			return imageURL;
		}

		public void setImageURL(String imageURL2)
		{
			this.imageURL = imageURL2;
		}
		public int getRecordNumber()
		{
			return recordNumber;
		}
		public void setRecordNumber(int recordNumber)
		{
			this.recordNumber = recordNumber;
		}
	}
}
