package server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import server.databaseaccess.UserDAC;
import shared.model.User;

public class UserDACTest
{
	private ManagedConnection managedConnection = new ManagedConnection();
	private UserDAC usersAccessor = new UserDAC(managedConnection);

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
		User u = new User(1, "bob", "dylan", "bobby", "dylanson",
				"bobby@bob.com", 9);
		usersAccessor.insert(u);
		User u2 = usersAccessor.getUser("bob", "dylan");
		managedConnection.endTransaction(true);
		assertEquals(u.getUserID(), u2.getUserID());
		assertEquals(u.getUserName(), u2.getUserName());
		assertEquals(u.getPassword(), u2.getPassword());
	}

}
