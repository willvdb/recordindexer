package shared.communicator;

import java.util.ArrayList;
import java.util.List;

import shared.model.Field;

/**
 * 
 * contains the results for the getFields method in the CC
 * 
 * @author Will
 * 
 */
public class GetFieldsResult
{
	private List<Field> fields;
	boolean success;

	public GetFieldsResult(ArrayList<Field> fieldsIn)
	{
		fields = fieldsIn;
		if (fields.isEmpty())
		{
			success = false;
		}
		else
		{
			success = true;
		}
	}

	public List<Field> getFields()
	{
		return fields;
	}

	public void setFields(List<Field> fields)
	{
		this.fields = fields;
	}

	public boolean wasSuccessful()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	/**
	 * For all the fields print out the info contained therein, aka the project
	 * ID and the field ID and title
	 * 
	 * @return a String containing the output
	 */
	public String getResult()
	{
		String result = "";
		if (success)
		{
			for (Field field : fields)
			{
				result += field.getProjectID() + "\n";
				result += field.getFieldID() + "\n";
				result += field.getTitle() + "\n";
			}
		}
		else
		{
			result += "FAILED\n";
		}
		return result;
	}
}
