package server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import server.databaseaccess.FieldDAC;
import shared.model.Field;

public class FieldDACTest
{
	private ManagedConnection managedConnection = new ManagedConnection();
	private FieldDAC fieldsAccessor = new FieldDAC(managedConnection);

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
		Field f = new Field();
		f.setFieldID(15);
		f.setFieldNum(0);
		f.setKnownData("TEST_FIELD");
		fieldsAccessor.insert(f);
		Field f2 = fieldsAccessor.getFieldByID(15);
		managedConnection.endTransaction(true);
		assertEquals(f.getFieldID(), f2.getFieldID());
		assertEquals(f.getFieldNum(), f2.getFieldNum());
		assertEquals(f.getKnownData(), f2.getKnownData());
	}
}
