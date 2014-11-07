package server;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.ArrayList;

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
import shared.model.Field;
import shared.model.Image;
import shared.model.Project;
import shared.model.Record;
import shared.model.User;
import shared.model.keyMaker;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Server
{
	private static int PORT_NUMBER = 8080;
	private static final int MAX_WAITING_CONNECTIONS = 10;

	private HttpServer server;
	private XStream xmlStream = new XStream(new DomDriver());

	public static void main(String[] args)
	{
		if (args == null)
		{
			new Server().run();
		}
		if (args[0].equals(""))
		{
			new Server().run();
		}
		else
		{
			int x = 0;
			try
			{
				x = Integer.parseInt(args[0]);
			}
			catch (NumberFormatException e)
			{
				new Server().run();
			}
			PORT_NUMBER = Integer.parseInt(args[0]);
			new Server().run();
		}
	}

	private Server()
	{
		return;
	}

	private void run()
	{
		try
		{
			server = HttpServer.create(new InetSocketAddress(PORT_NUMBER),
					MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e)
		{
			/*
			 * System.err.println("Error creating the HTTP Server");
			 * System.err.println(e.getMessage()); e.printStackTrace();
			 */
			return;
		}

		server.setExecutor(null); // default executor

		server.createContext("/ValidateUser", validateUserHandler);
		server.createContext("/GetProjects", getProjectsHandler);
		server.createContext("/GetSampleImage", getSampleImageHandler);
		server.createContext("/DownloadBatch", downloadBatchHandler);
		server.createContext("/SubmitBatch", submitBatchHandler);
		server.createContext("/GetFields", getFieldsHandler);
		server.createContext("/Search", searchHandler);
		server.createContext("/", downloadHandler);
		server.start();
	}

	private void stop()
	{
		try
		{
			server = HttpServer.create(new InetSocketAddress(PORT_NUMBER),
					MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e)
		{
			System.err.println("Error creating the HTTP Server");
			e.printStackTrace();
			return;
		}

		server.stop(0);
	}

	private HttpHandler emptyHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			System.out.println("empty handler Yo!");
		}
	};

	private HttpHandler validateUserHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			ManagedConnection managedConnection = new ManagedConnection();
			UserParams params = (UserParams) xmlStream.fromXML(exchange
					.getRequestBody());
			ValidateUserResult result = null;

			try
			{
				managedConnection.initialize();
				managedConnection.startTransaction();
				User u = managedConnection.getUsersAccess().getUser(
						params.getUserName(), params.getPassword());
				result = new ValidateUserResult(u);
				managedConnection.endTransaction(true);
			}
			catch (ServerException e)
			{
				System.err.println("validateUser server exception thrown");
				e.printStackTrace();
				managedConnection.endTransaction(false);
				exchange.sendResponseHeaders(
						HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
				return;
			}

			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
	};

	private HttpHandler getProjectsHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			ManagedConnection managedConnection = new ManagedConnection();
			UserParams params = (UserParams) xmlStream.fromXML(exchange
					.getRequestBody());
			GetProjectsResult result = null;

			try
			{
				managedConnection.initialize();
				managedConnection.startTransaction();
				ValidateUserResult valid = new ValidateUserResult(
						managedConnection.getUsersAccess().getUser(
								params.getUserName(), params.getPassword()));
				if (valid.isValidUser())
				{
					result = new GetProjectsResult(managedConnection
							.getProjectAccess().getAllProjects());
				}
				else
				{
					result = new GetProjectsResult(false);
				}
				managedConnection.endTransaction(true);
			}
			catch (ServerException e)
			{
				System.err.println("getProjects server exception thrown");
				e.printStackTrace();
				managedConnection.endTransaction(false);
				exchange.sendResponseHeaders(
						HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
				return;
			}

			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
	};

	private HttpHandler getSampleImageHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			ManagedConnection managedConnection = new ManagedConnection();
			UserProjectParams params = (UserProjectParams) xmlStream
					.fromXML(exchange.getRequestBody());
			GetSampleImageResult result = null;

			try
			{
				managedConnection.initialize();
				managedConnection.startTransaction();
				ValidateUserResult valid = new ValidateUserResult(
						managedConnection.getUsersAccess().getUser(
								params.getUserName(), params.getPassword()));
				if (valid.isValidUser())
				{
					result = new GetSampleImageResult(managedConnection
							.getImageAccess().getSampleURL(
									params.getProjectID()));
				}
				else
				{
					result = new GetSampleImageResult(null);
				}
				managedConnection.endTransaction(true);
			}
			catch (ServerException e)
			{
				System.err.println("getSampleImage server exception thrown");
				e.printStackTrace();
				managedConnection.endTransaction(false);
				exchange.sendResponseHeaders(
						HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
				return;
			}

			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
	};

	private HttpHandler downloadBatchHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			ManagedConnection managedConnection = new ManagedConnection();
			UserProjectParams params = (UserProjectParams) xmlStream
					.fromXML(exchange.getRequestBody());
			DownloadBatchResult result = null;

			try
			{
				managedConnection.initialize();
				System.out.println("I'm Starting for the downloadBatchHandler");
				managedConnection.startTransaction();
				User u = managedConnection.getUsersAccess().getUser(
						params.getUserName(), params.getPassword());
				ValidateUserResult valid = new ValidateUserResult(u);
				if (u.getCurrentBatch() != null
						&& !u.getCurrentBatch().isEmpty()
						&& !u.getCurrentBatch().trim().isEmpty())
				{
					System.out
							.println("There's something in this user's batch");
					result = new DownloadBatchResult(false);
					managedConnection.endTransaction(false);
				}
				else
				{
					if (valid.isValidUser()
							&& managedConnection.getProjectAccess()
									.isValidProjectID(params.getProjectID()))
					{
						Project p = managedConnection.getProjectAccess()
								.getSpecificProject(params.getProjectID());
						Image i = managedConnection.getImageAccess()
								.getImageFromProjectID(params.getProjectID());
						i.setStatus(1);
						i.setCurrentUser(u.getUserID());
						u.setCurrentBatch(i.getFileLocation());
						managedConnection.getImageAccess().updateImage(i);
						managedConnection.getUsersAccess().updateUser(u);
						ArrayList<Field> f = managedConnection.getFieldAccess()
								.getProjectFields(
										String.valueOf(params.getProjectID()));
						result = new DownloadBatchResult(p, i, f);
						result.setSuccesfullyDownloaded(true);
					}
					else
					{
						result = new DownloadBatchResult(false);
					}
					System.out
							.println("I'm ending for the downloadBatchHandler");
					managedConnection.endTransaction(true);
				}
			}
			catch (ServerException e)
			{
				System.err.println("downloadBatch server exception thrown");
				e.printStackTrace();
				managedConnection.endTransaction(false);
				exchange.sendResponseHeaders(
						HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
				return;
			}

			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
	};

	private HttpHandler submitBatchHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			boolean fieldsNumberWasCorrect = true;
			ManagedConnection managedConnection = new ManagedConnection();
			SubmitBatchParams params = (SubmitBatchParams) xmlStream
					.fromXML(exchange.getRequestBody());
			SubmitBatchResult result = new SubmitBatchResult();

			try
			{
				managedConnection.initialize();
				managedConnection.startTransaction();
				User u = managedConnection.getUsersAccess().getUser(
						params.getUserName(), params.getPassword());
				ValidateUserResult valid = new ValidateUserResult(u);
				Image i = managedConnection.getImageAccess()
						.getImageFromBatchIDIgnoreStatus(params.getBatchID());
				if (i != null)
				{
					Project p = managedConnection.getProjectAccess()
							.getSpecificProject(i.getProjectID());
					if (valid.isValidUser()
							&& i.getFileLocation().equals(u.getCurrentBatch())
							&& params.wasValid(p.getFieldsPerImage(),
									p.getRecordsPerImage()))
					{

						keyMaker.updateKeys(managedConnection);
						int fieldsPerImage = p.getFieldsPerImage();
						ArrayList<Record> recs = formatRecords(params,
								fieldsPerImage);
						params.setRecords(recs);
						if (managedConnection.getRecordAccess()
								.insertArrayListofRecords(params.getRecords()))
						{
							result.wasASuccess();
							u.setCurrentBatch("");
							u.setIndexedRecords(u.getIndexedRecords()
									+ p.getRecordsPerImage());
							i.setCurrentUser(0);
							i.setStatus(2);
							managedConnection.getImageAccess().updateImage(i);
							managedConnection.getUsersAccess().updateUser(u);
							managedConnection.endTransaction(true);
						}
						else
						{
							result.wasNotASuccess();
							managedConnection.endTransaction(false);
						}
					}
					else
					{
						result.wasNotASuccess();
						managedConnection.endTransaction(false);
					}
				}
				else
				{
					result.wasNotASuccess();
					managedConnection.endTransaction(false);
				}
			}
			catch (ServerException e)
			{
				System.err.println("submitBatch server exception thrown");
				e.printStackTrace();
				managedConnection.endTransaction(false);
				exchange.sendResponseHeaders(
						HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
				return;
			}

			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}

		private ArrayList<Record> formatRecords(SubmitBatchParams params,
				int fieldsPerImage)
		{
			ArrayList<Record> records = params.getRecords();
			int yValue = 0;
			int yTimes = 0;
			for (Record record : records)
			{
				record.setRecordID(keyMaker.recordKey);
				keyMaker.recordKey++;
				record.setY(yValue);
				yTimes++;
				if (yTimes > fieldsPerImage - 1)
				{
					yValue++;
					yTimes = 0;
				}
			}
			return records;
		}
	};

	private HttpHandler getFieldsHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			ManagedConnection managedConnection = new ManagedConnection();
			GetFieldsParams params = (GetFieldsParams) xmlStream
					.fromXML(exchange.getRequestBody());
			GetFieldsResult result = null;

			try
			{
				managedConnection.initialize();
				managedConnection.startTransaction();
				ValidateUserResult valid = new ValidateUserResult(
						managedConnection.getUsersAccess().getUser(
								params.getUserName(), params.getPassword()));
				if (valid.isValidUser())
				{
					if (params.getProjectID().isEmpty())
					{
						result = new GetFieldsResult(managedConnection
								.getFieldAccess().getAllFields());
					}
					else
					{
						result = new GetFieldsResult(managedConnection
								.getFieldAccess().getProjectFields(
										params.getProjectID()));
					}
				}
				else
				{
					result = new GetFieldsResult(new ArrayList<Field>());
				}
				managedConnection.endTransaction(true);
			}
			catch (ServerException e)
			{
				System.err.println("getFieldsHandler server exception thrown");
				e.printStackTrace();
				managedConnection.endTransaction(false);
				exchange.sendResponseHeaders(
						HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
				return;
			}

			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
	};

	private HttpHandler searchHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			ManagedConnection managedConnection = new ManagedConnection();
			SearchParams params = (SearchParams) xmlStream.fromXML(exchange
					.getRequestBody());
			SearchResult result = null;

			try
			{
				managedConnection.initialize();
				managedConnection.startTransaction();
				ValidateUserResult valid = new ValidateUserResult(
						managedConnection.getUsersAccess().getUser(
								params.getUserName(), params.getPassword()));
				if (valid.isValidUser())
				{
					result = createSearchResult(managedConnection, params);
				}
				else
				{
					result = new SearchResult(false);
				}
				managedConnection.endTransaction(true);
			}
			catch (ServerException e)
			{
				System.err.println("searchHandler server exception thrown");
				e.printStackTrace();
				managedConnection.endTransaction(false);
				exchange.sendResponseHeaders(
						HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
				return;
			}

			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}

		private SearchResult createSearchResult(
				ManagedConnection managedConnection, SearchParams params)
		{
			SearchResult result = new SearchResult();
			ArrayList<Field> fields = managedConnection.getFieldAccess()
					.getFieldsByIDs(params.getFieldIDs());
			for (Field field : fields)
			{
				Project project = managedConnection.getProjectAccess()
						.getSpecificProject(field.getProjectID());
				ArrayList<Image> images = managedConnection.getImageAccess()
						.getAllImagesFromProjectID(project.getProjectID());
				for (Image image : images)
				{
					ArrayList<Record> records = managedConnection
							.getRecordAccess()
							.getAllRecordsByBatchIDAndFieldNum(
									image.getImageID(), field.getFieldNum());
					for (Record record : records)
					{
						for (String string : params.getValues())
						{
							if (record.getContent().equalsIgnoreCase(string))
							{
								result.addAnswer(record, field.getFieldID(),
										image.getFileLocation());
							}
						}
					}

				}
			}
			return result;
		}
	};

	private HttpHandler downloadHandler = new HttpHandler()
	{

		@Override
		public void handle(HttpExchange exchange) throws IOException
		{
			File file = new File("./Records" + File.separator
					+ exchange.getRequestURI().getPath());
			exchange.sendResponseHeaders(200, 0);
			Files.copy(file.toPath(), exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
	};
}