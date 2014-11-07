package shared.communicator;

/**
 * 
 * contains the parameters for the getSamepleImage method in the CC
 * @author Will
 *
 */
public class UserProjectParams
{
	private String userName;
	private String password;
	private int projectID;
	
	public UserProjectParams(String nameIn, String passwordIn, int projectIDIn)
	{
		userName = nameIn;
		password = passwordIn;
		setProjectID(projectIDIn);
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
}
