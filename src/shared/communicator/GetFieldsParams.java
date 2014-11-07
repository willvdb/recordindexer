package shared.communicator;

/**
 * This class contains a username and password for a given user
 * this information will be used to retrieve the Fields for the 
 * given user
 */
public class GetFieldsParams
{
	private String userName;
	private String password;
	private String projectID;
	
	public GetFieldsParams(String nameIn, String passwordIn, String projectIDIn)
	{
		userName = nameIn;
		password = passwordIn;
		projectID = projectIDIn;
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

	public String getProjectID()
	{
		return projectID;
	}

	public void setProjectID(String projectID)
	{
		this.projectID = projectID;
	}
}
