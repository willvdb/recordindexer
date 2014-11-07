package server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import server.databaseaccess.ImageDAC;
import shared.model.Image;

public class ImageDACTest
{
	private ManagedConnection managedConnection = new ManagedConnection();
	private ImageDAC imagesAccessor = new ImageDAC(managedConnection);

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
		Image i = new Image();
		i.setCurrentUser(10);
		i.setID(10);
		i.setProjectID(10);
		imagesAccessor.insert(i);
		Image i2 = imagesAccessor.getImageFromProjectID(10);
		managedConnection.endTransaction(true);
		assertEquals(i.getCurrentUser(), i2.getCurrentUser());
		assertEquals(i.getID(), i2.getID());
		assertEquals(i.getProjectID(), i2.getProjectID());
	}
}
