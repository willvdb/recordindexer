package server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import server.databaseaccess.RecordDAC;
import shared.model.Record;

public class RecordDACTest
{
	private ManagedConnection managedConnection = new ManagedConnection();
	private RecordDAC recordsAccessor = new RecordDAC(managedConnection);

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
		Record r = new Record(1, "hooohaha", 1);
		recordsAccessor.insert(r);
		ArrayList<Record> rArray = recordsAccessor.getAllRecordsByBatchID(1);
		managedConnection.endTransaction(true);
		Record r2 = null;
		for (Record record : rArray)
		{
			if (record.getContent().equalsIgnoreCase("hooohaha"))
			{
				r2 = record;
			}
		}
		assertEquals(r.batchID, r2.batchID);
		assertEquals(r.getContent(), r2.getContent());
		assertEquals(r.getX(), r2.getX());
	}
}
