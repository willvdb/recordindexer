package shared.communicator;

import java.util.ArrayList;

import shared.model.Field;
import shared.model.Image;
import shared.model.Project;

/**
 * 
 * contains the result for the downloadBatch method in the CC
 * 
 * @author Will
 * 
 */
public class DownloadBatchResult
{
	Project project;
	Image image;
	ArrayList<Field> fields;
	boolean succesfullyDownloaded;

	public DownloadBatchResult(Project pIn, Image iIn, ArrayList<Field> fIn)
	{
		project = pIn;
		image = iIn;
		fields = fIn;
		succesfullyDownloaded = true;
	}

	public DownloadBatchResult(boolean b)
	{
		project = null;
		image = null;
		fields = null;
		this.succesfullyDownloaded = b;
	}

	public boolean wasSuccesful()
	{
		return succesfullyDownloaded;
	}

	public void setSuccesfullyDownloaded(boolean succesfullyDownloaded)
	{
		this.succesfullyDownloaded = succesfullyDownloaded;
	}

	public Project getProject()
	{
		return project;
	}

	public void setProject(Project project)
	{
		this.project = project;
	}

	public Image getImage()
	{
		return image;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}

	public ArrayList<Field> getFields()
	{
		return fields;
	}

	public void setFields(ArrayList<Field> fields)
	{
		this.fields = fields;
	}

	public String getResult()
	{
		String result = "";
		if (succesfullyDownloaded && result != null && image != null
				&& fields != null)
		{
			result += image.getImageID() + "\n";
			result += image.getProjectID() + "\n";
			result += image.getFileLocation() + "\n";
			result += project.getFirstYCoord() + "\n";
			result += project.getRecordHeight() + "\n";
			result += project.getRecordsPerImage() + "\n";
			result += project.getFieldsPerImage() + "\n";
			for (Field field : fields)
			{
				result += field.getFieldID() + "\n";
				result += field.getFieldNum() + "\n";
				result += field.getTitle() + "\n";
				result += field.getHelpHTML() + "\n";
				result += field.getXCoord() + "\n";
				result += field.getWidth() + "\n";
				if (field.getKnownData() != null)
				{
					if (!field.getKnownData().trim().toLowerCase()
							.equals("null"))
					{
						result += field.getKnownData() + "\n";
					}
				}
			}
		}
		else
		{
			result += "FAILED\n";
		}
		return result;
	}

	public String getResultwithURL(String host, String port)
	{
		String url = "http://" + host + ":" + port + "/";
		String result = "";
		if (succesfullyDownloaded && result != null && image != null
				&& fields != null)
		{
			result += image.getImageID() + "\n";
			result += image.getProjectID() + "\n";
			result += url + image.getFileLocation() + "\n";
			result += project.getFirstYCoord() + "\n";
			result += project.getRecordHeight() + "\n";
			result += project.getRecordsPerImage() + "\n";
			result += project.getFieldsPerImage() + "\n";
			for (Field field : fields)
			{
				result += field.getFieldID() + "\n";
				result += field.getFieldNum() + "\n";
				result += field.getTitle() + "\n";
				result += url + field.getHelpHTML() + "\n";
				result += field.getXCoord() + "\n";
				result += field.getWidth() + "\n";
				if (field.getKnownData() != null)
				{
					if (!field.getKnownData().trim().toLowerCase()
							.equals("null"))
					{
						result += url + field.getKnownData() + "\n";
					}
				}
			}
		}
		else
		{
			result += "FAILED\n";
		}
		return result;
	}
}
