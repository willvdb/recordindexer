package shared.communicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * contains the parameters for the search method in the CC
 * @author Will
 *
 */
public class SearchParams
{
	private String userName;
	private String password;
	private List<Integer> fieldIDs;
	private List<String> values;
	
	public SearchParams()
	{}

	public SearchParams(String uName, String pWord, ArrayList<Integer> fIDs,
			ArrayList<String> vs)
	{
		userName = uName;
		password = pWord;
		fieldIDs = fIDs;
		values = vs;
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
	public List<Integer> getFieldIDs()
	{
		return fieldIDs;
	}
	public void setFieldIDs(List<Integer> fieldIDs)
	{
		this.fieldIDs = fieldIDs;
	}
	public List<String> getValues()
	{
		return values;
	}
	public void setValues(List<String> values)
	{
		this.values = values;
	}
}
