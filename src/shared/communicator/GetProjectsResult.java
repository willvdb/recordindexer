package shared.communicator;

import java.util.List;

import shared.model.Project;

/**
 * 
 * contains the results for the getProjects method in the CC
 * 
 * @author Will
 * 
 */
public class GetProjectsResult
{
	private List<Project> projects;

	public GetProjectsResult(List<Project> listIn)
	{
		projects = listIn;
	}

	public GetProjectsResult(boolean b)
	{
		projects = null;
	}

	public List<Project> getProjects()
	{
		return projects;
	}

	public void setProjects(List<Project> projects)
	{
		this.projects = projects;
	}

	/**
	 * For all the projects print out the info contained therein, aka the
	 * project ID and title
	 * 
	 * @return a String containing the output
	 */
	public String getResult()
	{
		String result = "";
		if (projects == null)
		{
			result += "FAILED\n";
		}
		else
		{
			for (Project project : projects)
			{
				result += project.getProjectID() + "\n";
				result += project.getTitle() + "\n";
			}
		}
		return result;
	}
}
