package shared.communicator;


/**
 * This class contains a username and password for a given user
 * this information will be used to retrieve the projects for the 
 * given user
 */
public class UserParams
{
	private String userName;
	private String password;
	
	public UserParams(String nameIn, String passwordIn)
	{
		userName = nameIn;
		password = passwordIn;
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
}
