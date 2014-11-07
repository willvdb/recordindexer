package server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import shared.model.IndexerData;
import shared.model.Project;
import shared.model.User;
import shared.model.keyMaker;

/**
 * This class is used to parse xml and import
 * the content into the the database
 * @author Will
 *
 */

public class Importer
{
	public static ArrayList<Element> getChildren(Element userElement)
	{
		ArrayList<Element> result = new ArrayList<Element>();
		NodeList children = userElement.getChildNodes();
		for (int i = 0; i < children.getLength(); i++)
		{
			Node current = children.item(i);
			if (current.getNodeType() == Node.ELEMENT_NODE)
			{
				result.add((Element) current);
			}
		}
		return result;
	}
	
	public static String getValue(Element element)
	{
		return element.getFirstChild().getNodeValue();
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
	{
		
		// Get the xml parsed and ready to go
		File xml  = new File(args[0]);
		File dest = new File("Records");
		if (!xml.getParentFile().getCanonicalPath()
				.equals(dest.getCanonicalPath())) FileUtils
				.deleteDirectory(dest);

		// Copy the directories (recursively) from our source to our
		// destination.
		FileUtils.copyDirectory(xml.getParentFile(), dest);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbf.newDocumentBuilder();
		Document document = docBuilder.parse(xml);
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		ManagedConnection managedConnection = new ManagedConnection();
		
		/*prepare the SQL tables, drop and then recreate them, also prepare the key maker for creating
		 *the unique keys we will need*/
		keyMaker.reset();
		try
		{
			managedConnection.initialize();
		}
		catch (ServerException e)
		{
			e.printStackTrace();
		}
		initalizeTables(managedConnection);
		
		/*IndexData will be the root node holding all the info,
		 *  it will create all the Users and Projects we will insert.*/
		IndexerData indexData = createRoot(managedConnection, root);
		
		//First insert the users:
		insertUsers(managedConnection, indexData.getUsers());
		
		//Insert the projects:
		insertProjects(managedConnection, indexData.getProjects());
	}

	private static IndexerData createRoot(ManagedConnection managedConnection,
			Element root)
	{
		IndexerData indexData = null;
		try
		{
			managedConnection.startTransaction();
			indexData = new IndexerData(managedConnection, root);
			managedConnection.endTransaction(true);
		}
		catch (ServerException e)
		{
			managedConnection.endTransaction(false);
			e.printStackTrace();
		}
		return indexData;
	}

	private static void insertUsers(ManagedConnection managedConnection,
			ArrayList<User> users)
	{
		try
		{
			managedConnection.startTransaction();
			for (User u : users)
			{
				managedConnection.getUsersAccess().insert(u);
			}
			managedConnection.endTransaction(true);
		}
		catch (ServerException e)
		{
			managedConnection.endTransaction(false);
			e.printStackTrace();
		}
	}
	
	private static void insertProjects(ManagedConnection managedConnection,
			ArrayList<Project> projects)
	{
		try
		{
			managedConnection.startTransaction();
			for (Project u : projects)
			{
				managedConnection.getProjectAccess().insert(u);
			}
			managedConnection.endTransaction(true);
		}
		catch (ServerException e)
		{
			managedConnection.endTransaction(false);
			e.printStackTrace();
		}
	}

	private static void initalizeTables(ManagedConnection managedConnection)
	{
		try
		{
			managedConnection.startTransaction();
			managedConnection.getUsersAccess().dropTable();
			managedConnection.getProjectAccess().dropTable();
			managedConnection.getRecordAccess().dropTable();
			managedConnection.getImageAccess().dropTable();
			managedConnection.getFieldAccess().dropTable();
			
			managedConnection.getUsersAccess().createTable();
			managedConnection.getProjectAccess().createTable();
			managedConnection.getRecordAccess().createTable();
			managedConnection.getImageAccess().createTable();
			managedConnection.getFieldAccess().createTable();
			managedConnection.endTransaction(true);
		}
		catch (ServerException e)
		{
			managedConnection.endTransaction(false);
			e.printStackTrace();
		}
	}
}
