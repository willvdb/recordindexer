package client;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

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
import sun.net.www.protocol.http.HttpURLConnection;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * Client communicator class, utilizes all the shared communicator classes to
 * interact with the client.
 * 
 * @author Will
 * 
 */
public class ClientCommunicator
{

	private String serverHost = "localhost";
	private int serverPort = 8080;
	private String stringURL = "http://" + serverHost
 + ":"
			+ String.valueOf(serverPort);

	private static XStream xmlStream;

	// Singleton Instance
	private static ClientCommunicator singleton = null;

	/**
	 * Used to implement the getSingleton method of the singleton pattern.
	 * 
	 * @return an ClientCommunicator
	 */
	public static ClientCommunicator getSingleton()
	{
		if (singleton == null)
		{
			singleton = new ClientCommunicator();
		}
		return singleton;
	}

	private void initializeXStream()
	{
		if (xmlStream != null)
		{
			return;
		}
		else
		{
			xmlStream = new XStream(new DomDriver());
		}
	}

	public ClientCommunicator()
	{}

	public ClientCommunicator(String host, int port)
	{
		serverHost = host;
		serverPort = port;
		stringURL = "http://" + serverHost + ":" + String.valueOf(serverPort);
	}

	/**
	 * method to change the host and port
	 */
	public void changeAddress(String host, int port)
	{
		serverHost = host;
		serverPort = port;
		stringURL = "http://" + serverHost + ":" + String.valueOf(serverPort);
	}

	/**
	 * This class creates the serializable objects that will be used by the
	 * server to perform the needed interactions with the database
	 */
	// 8 methods in the pdf

	/**
	 * validateUser returns a <code>ValidateUserResult</code> object containing
	 * the information on the given
	 * 
	 * @param params
	 *            contain the username and password of the user
	 * @throws ClientException
	 * @result
	 */
	public ValidateUserResult validateUser(UserParams params)
			throws ClientException
	{
		return (ValidateUserResult) doPost("/ValidateUser", params);
	}

	/**
	 * @param params
	 *            is a <code>UserParams</code> object
	 * @return Returns a <code>GetProjectsResult</code> object that contains a
	 *         list of projects
	 * @throws ClientException
	 */
	public GetProjectsResult getProjects(UserParams params)
			throws ClientException
	{
		return (GetProjectsResult) doPost("/GetProjects", params);
	}

	/**
	 * @param params
	 *            is a <code>GetFieldsParams</code> object the
	 * @return Returns a <code>GetFieldsResult</code> object that contains a
	 *         list of projects
	 * @throws ClientException
	 */
	public GetFieldsResult getFields(GetFieldsParams params)
			throws ClientException
	{
		return (GetFieldsResult) doPost("/GetFields", params);
	}

	/**
	 * @param params
	 *            is a <code>UserProjectParams</code> object the
	 * @return Returns a <code>GetSampleImageResult</code> object that contains
	 *         an image URL
	 * @throws ClientException
	 */
	public GetSampleImageResult getSampleImage(UserProjectParams params)
			throws ClientException
	{
		return (GetSampleImageResult) doPost("/GetSampleImage", params);
	}

	/**
	 * @param params
	 *            is a <code>UserProjectParams</code> object the
	 * @return Returns a <code>DownloadBatchResult</code> object that contains
	 *         an image URL
	 * @throws ClientException
	 */
	public DownloadBatchResult downloadBatch(UserProjectParams params)
			throws ClientException
	{
		return (DownloadBatchResult) doPost("/DownloadBatch", params);
	}

	/**
	 * @param params
	 *            is a <code>SubmitBatchParams</code> object
	 * @return Returns a <code>SubmitBatchResult</code> object
	 * @throws ClientException
	 */
	public SubmitBatchResult submitBatch(SubmitBatchParams params)
			throws ClientException
	{
		return (SubmitBatchResult) doPost("/SubmitBatch", params);
	}

	/**
	 * @param params
	 *            is a <code>SearchParams</code> object
	 * @return Returns a <code>SearchResult</code> object
	 * @throws ClientException
	 */
	public SearchResult search(SearchParams params) throws ClientException
	{
		return (SearchResult) doPost("/Search", params);
	}

	public File download(URL url) throws ClientException
	{
		return (File) doPost("/Download", url);
	}

	/**
	 * 
	 * @param urlPath
	 * @param obj
	 * @return
	 * @throws ClientException
	 */
	// post method
	public Object doPost(String urlPath, Object obj) throws ClientException
	{
		Object objectResult = null;
		try
		{
			URL url = new URL(stringURL + urlPath);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			OutputStream requestBody = connection.getOutputStream();

			initializeXStream();
			String xml = xmlStream.toXML(obj);
			requestBody.write(xml.getBytes());
			requestBody.close();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStream responseBody = connection.getInputStream();
				objectResult = xmlStream.fromXML(responseBody);
				responseBody.close();
				return objectResult;
			}
			else
			// connection error
			{
				throw new ClientException();
			}
		}
		catch (Exception e)
		{
			throw new ClientException();
		}
	}
}
