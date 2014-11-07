package server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.databaseaccess.FieldDAC;
import server.databaseaccess.ImageDAC;
import server.databaseaccess.ProjectDAC;
import server.databaseaccess.RecordDAC;
import server.databaseaccess.UserDAC;

/**
 * This is the class that manages the database connection
 * 
 * AKA, this class will handle the start and end transactions with the database.
 * Also the prepared
 * 
 * @author Will
 * 
 */

public class ManagedConnection
{
	private static final String DATABASE_DIRECTORY = "database";
	private static final String DATABASE_FILE = "recordIndexer.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:"
			+ DATABASE_DIRECTORY + File.separator + DATABASE_FILE;

	public static void initialize() throws ServerException
	{
		try
		{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch (ClassNotFoundException e)
		{
			throw new ServerException("Could not load database driver", e);
		}
	}



	private UserDAC usersAccess;
	private ProjectDAC projectAccess;
	private RecordDAC recordAccess;
	private ImageDAC imageAccess;
	private FieldDAC fieldAccess;
	private Connection conn;

	public ManagedConnection()
	{
		usersAccess = new UserDAC(this);
		projectAccess = new ProjectDAC(this);
		recordAccess = new RecordDAC(this);
		imageAccess = new ImageDAC(this);
		fieldAccess = new FieldDAC(this);
		conn = null;
	}

	public UserDAC getUsersAccess()
	{
		return usersAccess;
	}

	public ProjectDAC getProjectAccess()
	{
		return projectAccess;
	}

	public RecordDAC getRecordAccess()
	{
		return recordAccess;
	}

	public ImageDAC getImageAccess()
	{
		return imageAccess;
	}

	public FieldDAC getFieldAccess()
	{
		return fieldAccess;
	}

	public Connection getConnection()
	{
		return conn;
	}

	public void startTransaction() throws ServerException
	{
		System.out.println("Starting Transaction");
		try
		{
			assert (conn == null);

			conn = DriverManager.getConnection(DATABASE_URL);
			conn.setAutoCommit(false);
		}
		catch (SQLException e)
		{
			throw new ServerException(
					"Could not connect to database. Make sure " + DATABASE_FILE
							+ " is available in ./" + DATABASE_DIRECTORY, e);
		}
	}

	public boolean inTransaction()
	{
		return (conn != null);
	}

	public void endTransaction(boolean commit)
	{
		System.out.println("Ending Transaction");
		if (conn != null)
		{
			try
			{
				if (commit)
				{
					conn.commit();
				}
				else
				{
					conn.rollback();
				}
			}
			catch (SQLException e)
			{
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally
			{
				safeClose(conn);
				conn = null;
			}
		}
	}

	public static void safeClose(Connection conn)
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			}
			catch (SQLException e)
			{
				// ...
			}
		}
	}

	public static void safeClose(Statement stmt)
	{
		if (stmt != null)
		{
			try
			{
				stmt.close();
			}
			catch (SQLException e)
			{
				// ...
			}
		}
	}

	public static void safeClose(PreparedStatement stmt)
	{
		if (stmt != null)
		{
			try
			{
				stmt.close();
			}
			catch (SQLException e)
			{
				// ...
			}
		}
	}

	public static void safeClose(ResultSet rs)
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			}
			catch (SQLException e)
			{
				// ...
			}
		}
	}

}