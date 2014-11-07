package shared.communicator;

import shared.model.User;

/**
 * This class contains information about the user that was validated including
 * whether or not he was a valid user
 */
public class ValidateUserResult
{
	private boolean validity;
	private String firstName;
	private String lastName;
	private int numRecords;

	public ValidateUserResult(User u)
	{
		if (u != null)
		{
			if (u.getUserName() != null && u.getPassword() != null)
			{
				validity = true;
				firstName = u.getFirstName();
				lastName = u.getLastName();
				numRecords = u.getIndexedRecords();
			}
			else
			{
				validity = false;
				firstName = null;
				lastName = null;
				numRecords = 0;
			}
		}
		else
		{
			validity = false;
			firstName = null;
			lastName = null;
			numRecords = 0;
		}
		if (u.getFirstName() == "FALSE" && u.getLastName() == "USER")
		{
			validity = false;
			firstName = "falsy";
			lastName = null;
			numRecords = 0;
		}
	}

	public ValidateUserResult()
	{}

	public boolean isValidUser()
	{
		return validity;
	}

	public void setValidity(boolean validity)
	{
		this.validity = validity;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public int getNumRecords()
	{
		return numRecords;
	}

	public void setNumRecords(int numRecords)
	{
		this.numRecords = numRecords;
	}

	/**
	 * Check the validity and print format the result accordingly
	 * 
	 * @return a String containing the data. the content and format of the
	 *         string depends on the validity of the user
	 */
	public String getResult()
	{
		String result = "";
		if (validity)
		{
		result += String.valueOf(validity).toUpperCase() + "\n";
		result += firstName + "\n";
		result += lastName + "\n";
		result += String.valueOf(numRecords) + "\n";
		}
		else if (firstName == null)
		{
			result += "FAILED\n";
		}
		else if (firstName.equals("falsy"))
		{
			result += "FALSE\n";
		}
		else
		{
			result += "FAILED\n";
		}
		return result;
	}
}
