package shared.model;

import java.util.ArrayList;

import org.w3c.dom.Element;

import server.Importer;
import server.ManagedConnection;

public class IndexerData
{
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Project> projects = new ArrayList<Project>();

	public IndexerData(ManagedConnection managedConnection, Element root)
	{
		ArrayList<Element> rootElements = Importer.getChildren(root);
		ArrayList<Element> userElements = Importer.getChildren(rootElements
				.get(0));
		for (Element userElement : userElements)
		{
			User thisUser = new User(userElement, keyMaker.userKey);
			users.add(thisUser);
			keyMaker.userKey++;
		}
		ArrayList<Element> projectElements = Importer.getChildren(rootElements
				.get(1));
		for (Element projectElement : projectElements)
		{
			Project thisProject = new Project(managedConnection,
					projectElement,
					keyMaker.projectKey);
			projects.add(thisProject);
			keyMaker.projectKey++;
		}
	}

	public ArrayList<User> getUsers()
	{
		return users;
	}

	public void setUsers(ArrayList<User> users)
	{
		this.users = users;
	}

	public ArrayList<Project> getProjects()
	{
		return projects;
	}

	public void setProjects(ArrayList<Project> projects)
	{
		this.projects = projects;
	}
}
