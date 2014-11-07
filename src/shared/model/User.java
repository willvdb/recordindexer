package shared.model;

import org.w3c.dom.Element;

import server.Importer;
import shared.communicator.UserParams;

/**
 * 
 * Class modeling the user object in the indexer
 * 
 * @author Will
 * 
 */
public class User
{
	private int userID;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private int indexedRecords;

	private String currentBatch = "";

	public User(Element userElement, int ID)
	{
		userName = Importer.getChildren(userElement).get(0).getTextContent();
		password = Importer.getChildren(userElement).get(1).getTextContent();
		firstName = Importer.getChildren(userElement).get(2).getTextContent();
		lastName = Importer.getChildren(userElement).get(3).getTextContent();
		emailAddress = Importer.getChildren(userElement).get(4)
				.getTextContent();
		indexedRecords = Integer.parseInt(Importer.getChildren(userElement)
				.get(5).getTextContent());
		if (Importer.getChildren(userElement).size() > 6)
		{
			setCurrentBatch(Importer.getChildren(userElement).get(6)
					.getTextContent());
		}
		this.userID = ID;
	}

	public User(int userID, String userName, String password, String firstName,
			String lastName, String emailAddr, int iRecords)
	{
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddr;
		this.indexedRecords = iRecords;
	}

	public boolean matches(UserParams params)
	{
		boolean response = true;
		if (params.getUserName() != userName
				|| params.getPassword() != password)
		{
			response = false;
		}
		return response;
	}

	public User()
	{
		userName = null;
		password = null;
	}

	public int getUserID()
	{
		return userID;
	}

	public void setUserID(int key)
	{
		this.userID = key;
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

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public int getIndexedRecords()
	{
		return indexedRecords;
	}

	public void setIndexedRecords(int indexedRecords)
	{
		this.indexedRecords = indexedRecords;
	}

	public String getCurrentBatch()
	{
		return currentBatch;
	}

	public void setCurrentBatch(String currentBatch)
	{
		this.currentBatch = currentBatch;
	}
}
