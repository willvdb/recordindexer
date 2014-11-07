package client;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import server.Importer;
import server.Server;
import shared.communicator.DownloadBatchResult;
import shared.communicator.GetFieldsParams;
import shared.communicator.GetFieldsResult;
import shared.communicator.GetProjectsResult;
import shared.communicator.GetSampleImageResult;
import shared.communicator.SearchParams;
import shared.communicator.SearchResult;
import shared.communicator.SubmitBatchParams;
import shared.communicator.SubmitBatchResult;
import shared.communicator.UserParams;
import shared.communicator.UserProjectParams;
import shared.communicator.ValidateUserResult;

public class ClientCommunicatorTest
{
	private Server server;
	private ClientCommunicator cc = new ClientCommunicator("localhost", 5555);

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
	public void setup()
	{
		String[] args =
		{ "5555" };
		server.main(args);
	}

	@After
	public void cleanup()
	{}

	@Test
	public void validateUserTestOne() throws ClientException
	{
		UserParams params = new UserParams("test1", "test1");
		ValidateUserResult result = cc.validateUser(params);
		String answer = "TRUE\n" + "Test\n" + "One\n" + "0\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void validateUserTestTwo() throws ClientException
	{
		UserParams params = new UserParams("Bill", "Jackson");
		ValidateUserResult result = cc.validateUser(params);
		String answer = "FAILED\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void getProjectsTestOne() throws ClientException
	{
		UserParams params = new UserParams("test1", "test1");
		GetProjectsResult result = cc.getProjects(params);
		String answer = "1\n" + "1890 Census\n" + "2\n" + "1900 Census\n"
				+ "3\n" + "Draft Records\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void getProjectsTestTwo() throws ClientException
	{
		UserParams params = new UserParams("Alex", "theHomo");
		GetProjectsResult result = cc.getProjects(params);
		String answer = "FAILED\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void getSampleImageTestOne() throws ClientException
	{
		UserProjectParams params = new UserProjectParams("test1", "test1", 3);
		GetSampleImageResult result = cc.getSampleImage(params);
		String answer = "images/draft_image19.png\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void getSampleImageTestTwo() throws ClientException
	{
		UserProjectParams params = new UserProjectParams("Duke", "LeGray", 3);
		GetSampleImageResult result = cc.getSampleImage(params);
		String answer = "FAILED\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void downloadBatchTestOne() throws ClientException
	{
		UserProjectParams params = new UserProjectParams("test1", "test1", 3);
		DownloadBatchResult result = cc.downloadBatch(params);
		String answer = "41\n3\nimages/draft_image0.png\n195\n65\n7\n4\n10\n"
				+ "0\nLast Name\nfieldhelp/last_name.html\n75\n325\n"
				+ "knowndata/draft_last_names.txt\n11\n1\nFirst Name\n"
				+ "fieldhelp/first_name.html\n400\n325\nknowndata/draft_first_names.txt\n"
				+ "12\n2\nAge\nfieldhelp/age.html\n725\n120\n13\n3\nEthnicity\n"
				+ "fieldhelp/ethnicity.html\n845\n450\nknowndata/ethnicities.txt\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void downloadBatchTestTwo() throws ClientException
	{
		UserProjectParams params = new UserProjectParams("Fail", "Me", 3);
		DownloadBatchResult result = cc.downloadBatch(params);
		String answer = "FAILED\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void submitBatchTestOne() throws ClientException
	{
		String records = "";
		for (int i = 0; i < 10; i++)
		{
			records += "McGee,Zoozox,42,White";
			if (i != 9)
			{
				records += ";";
			}
		}
		SubmitBatchParams params = new SubmitBatchParams("test1", "test1", 3,
				records, 50);
		SubmitBatchResult result = cc.submitBatch(params);
		String answer = "TRUE\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void submitBatchTestTwo() throws ClientException
	{
		SubmitBatchParams params = new SubmitBatchParams("Fail", "Me", 3,
				"hoohaha", 1000);
		SubmitBatchResult result = cc.submitBatch(params);
		String answer = "FAILED\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void getFieldsTestOne() throws ClientException
	{
		GetFieldsParams params = new GetFieldsParams("test1", "test1", "3");
		GetFieldsResult result = cc.getFields(params);
		String answer = "3\n10\nLast Name\n3\n11\nFirst Name\n3\n12\nAge\n3\n13\nEthnicity\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void getFieldsTestTwo() throws ClientException
	{
		GetFieldsParams params = new GetFieldsParams("fail", "me", "");
		GetFieldsResult result = cc.getFields(params);
		String answer = "FAILED\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void searchTestOne() throws ClientException
	{
		ArrayList<Integer> ints = new ArrayList<Integer>();
		ArrayList<String> strings = new ArrayList<String>();
		ints.add(12);
		strings.add("28");
		SearchParams params = new SearchParams("test1", "test1", ints, strings);
		SearchResult result = cc.search(params);
		String answer = "41\nimages/draft_image0.png\n3\n12\n41\nimages/draft_image0.png\n"
				+ "5\n12\n51\nimages/draft_image10.png\n6\n12\n52\nimages/draft_image11.png\n"
				+ "4\n12\n53\nimages/draft_image12.png\n2\n12\n54\nimages/draft_image13.png\n"
				+ "4\n12\n56\nimages/draft_image15.png\n1\n12\n57\nimages/draft_image16.png\n"
				+ "5\n12\n58\nimages/draft_image17.png\n3\n12\n58\nimages/draft_image17.png\n"
				+ "4\n12\n60\nimages/draft_image19.png\n2\n12\n";
		assertEquals(answer, result.getResult());
	}

	@Test
	public void searchTestTwo() throws ClientException
	{
		ArrayList<Integer> ints = new ArrayList<Integer>();
		ArrayList<String> strings = new ArrayList<String>();
		ints.add(12);
		strings.add("28");
		SearchParams params = new SearchParams("Fail", "Me", ints, strings);
		SearchResult result = cc.search(params);
		String answer = "FAILED\n";
		assertEquals(answer, result.getResult());
	}


}
