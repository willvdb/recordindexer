package server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import server.databaseaccess.ProjectDAC;
import shared.model.Project;

public class ProjectDACTest
{
	private ManagedConnection managedConnection = new ManagedConnection();
	private ProjectDAC projectsAccessor = new ProjectDAC(managedConnection);

	@BeforeClass
	public static void dbSetup() throws ParserConfigurationException,
			SAXException, IOException
	{
		// first import all the data
		String[] sArray =
		{ "Records.xml" };
		Importer.main(sArray);
	}

	@Before
	public void setup() throws ServerException
	{
		managedConnection.initialize();
	}

	@Test
	public void testInsert() throws ServerException
	{
		managedConnection.startTransaction();
		Project p = new Project();
		p.setProjectID(5);
		p.setRecordHeight(5);
		p.setTitle("THE_PROJECT");
		projectsAccessor.insert(p);
		Project p2 = projectsAccessor.getSpecificProject(5);
		managedConnection.endTransaction(true);
		assertEquals(p.getProjectID(), p2.getProjectID());
		assertEquals(p.getRecordsPerImage(), p2.getRecordHeight());
		assertEquals(p.getTitle(), p2.getTitle());
	}
}
