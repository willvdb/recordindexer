package shared.model;

import java.util.ArrayList;

import org.w3c.dom.Element;

import server.Importer;
import server.ManagedConnection;

/**
 * 
 * Class modeling the image object in the indexer
 * 
 * @author Will
 * 
 */
public class Image
{

	String fileLocation;
	ArrayList<Record> records;
	int imageID;
	int projectID;
	int currentUser;
	int status; // 0 = . 1 = . 2 = .
	int fieldsNum;
	int recordsNum;

	public Image(ManagedConnection managedConnection, Element item,
			int Num_of_Fields, int Num_of_Records, int image_ID, int parent_ID)
	{
		records = new ArrayList<Record>();
		this.fieldsNum = Num_of_Fields;
		this.recordsNum = Num_of_Records;
		this.imageID = image_ID;
		this.projectID = parent_ID;
		fileLocation = Importer.getValue((Element) item.getElementsByTagName(
				"file").item(0));

		ArrayList<Element> ImageElements = Importer.getChildren(item);
		if (ImageElements.size() > 1)// there's values
		{
			ArrayList<Element> recordsRowElements = Importer
					.getChildren(ImageElements.get(1));// all the record rows
			int X = 0;
			int Y = 0;
			for (Element recordElement : recordsRowElements)// record
			{
				ArrayList<Element> valuesElement = Importer
						.getChildren(recordElement);// 1 values
				ArrayList<Element> single_values = Importer
						.getChildren(valuesElement.get(0));// all the value in 1
															// "values"
				for (int i = 0; i < single_values.size(); i++)
				{
					Record thisRecord = new Record(single_values.get(i), X, Y,
							keyMaker.recordKey, keyMaker.imageKey);
					records.add(thisRecord);
					keyMaker.recordKey++;
					X++;
					if (X == Num_of_Fields)
					{
						Y++;
						X = 0;
					}
				}
				// checking for array bounds
				if (Y > Num_of_Records || X != 0)
				{
					System.out.println("ERROR!!!! records have invalid XY");
					System.out.println("Num_of_Fields:" + Num_of_Fields
							+ " X = " + X);
					System.out.println("Num_of_Records:" + Num_of_Records
							+ " Y = " + Y);
				}
			}
		}
		// ***********INSERT ALL Records**************
		for (Record temp : records)
		{
			managedConnection.getRecordAccess().insert(temp);
		}
	}

	public Image()
	{
	}

	public String getFileLocation()
	{
		return fileLocation;
	}

	public void setFileLocation(String fileLocation)
	{
		this.fileLocation = fileLocation;
	}

	public int getID()
	{
		return imageID;
	}

	public void setID(int iD)
	{
		imageID = iD;
	}

	public int getProjectID()
	{
		return projectID;
	}

	public ArrayList<Record> getRecords()
	{
		return records;
	}

	public void setRecords(ArrayList<Record> records)
	{
		this.records = records;
	}

	public int getImageID()
	{
		return imageID;
	}

	public void setImageID(int imageID)
	{
		this.imageID = imageID;
	}

	public int getCurrentUser()
	{
		return currentUser;
	}

	public void setCurrentUser(int currentUser)
	{
		this.currentUser = currentUser;
	}

	public int getFieldsNum()
	{
		return fieldsNum;
	}

	public void setFieldsNum(int fieldsNum)
	{
		this.fieldsNum = fieldsNum;
	}

	public int getRecordsNum()
	{
		return recordsNum;
	}

	public void setRecordsNum(int recordsNum)
	{
		this.recordsNum = recordsNum;
	}

	public void setProjectID(int projectID)
	{
		this.projectID = projectID;
	}

	public void setStatus(int statusIn)
	{
		status = statusIn;
	}

	public int getStatus()
	{
		return status;
	}
}
