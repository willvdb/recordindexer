package shared.model;

import org.w3c.dom.Element;

/**
 * This class holds a single row of record values of an Image, it contains a
 * ArrayList of String which are the values in that row
 * 
 * @author Will
 * 
 */
public class Record
{
	private int recordID;
	private int X;
	private int Y;
	private String content;

	public int batchID = -1;



	public Record(Element item, int X, int Y, int recordID, int parentID)
	{
		this.recordID = recordID;
		this.batchID = parentID;
		this.X = X;
		this.Y = Y;
		content = item.getTextContent();
	}

	public Record(int recID, int x, int y, String content, int batchID)
	{
		this.recordID = recID;
		this.X = x;
		this.Y = y;
		this.content = content;
		this.batchID = batchID;
	}
	
	public Record(int x, String content, int batchID)
	{
		this.X = x;
		this.content = content;
		this.batchID = batchID;
	}

	public Record() {}
	
	public int getProjectID()
	{
		return batchID;
	}
	
	
	public void setProjectID(int projectID)
	{
		this.batchID = projectID;
	}

	public int getX()
	{
		return X;
	}

	public void setX(int x)
	{
		X = x;
	}

	public int getY()
	{
		return Y;
	}

	public void setY(int y)
	{
		Y = y;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String value)
	{
		this.content = value;
	}

	public int getRecordID()
	{
		return recordID;
	}

	public void setRecordID(int key)
	{
		this.recordID = key;
	}
}
