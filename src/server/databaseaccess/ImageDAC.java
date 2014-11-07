package server.databaseaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.ManagedConnection;
import shared.model.Image;

/**
 * 
 * Image Database Access Class
 * 
 * @author Will
 * 
 */

public class ImageDAC
{

	ManagedConnection managedConnection;

	public ImageDAC(ManagedConnection mcIn)
	{
		this.managedConnection = mcIn;
	}

	/**
	 * This method creates the table for the Images data
	 */
	public void createTable()
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "CREATE TABLE Images " + "(imageID INT NOT NULL,"
					+ " projectID INT NOT NULL,"
					+ " fileLocation CHAR(255) NOT NULL,"
					+ " status INT NOT NULL," + " currentUser INT NOT NULL)";
			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			System.err.println("SQL error");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
	}

	/**
	 * Method to drop current Images table
	 */
	public void dropTable()
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "DROP TABLE Images";

			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			System.err.println("SQL error");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
	}

	/**
	 * execute a query using the current data
	 */
	public ResultSet select(ArrayList<String> selectors)
	{
		Statement statement = null;
		String sql = "";
		ResultSet rs = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT ";
			for (int i = 0; i < selectors.size(); i++)
			{
				if (i != selectors.size() - 1)// no comma for last one
					sql += selectors.get(i) + ", ";
				else
					sql += selectors.get(i) + " ";
			}
			sql += "FROM Images";
			rs = statement.executeQuery(sql);
		}
		catch (SQLException e)
		{
			System.err.println("SQL error");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
		return rs;
	}
	
	/**
	 * Get's all the images
	 */
	public ArrayList<Image> getAllImages()
	{
		Statement statement = null;
		String sql = "";
		ArrayList<Image> result = new ArrayList<Image>();
		ResultSet rs = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Images";

			rs = statement.executeQuery(sql);

			while (rs.next())
			{
				Image temp = new Image();
				temp.setImageID(rs.getInt("imageID"));
				temp.setProjectID(rs.getInt("projectID"));
				temp.setFileLocation(rs.getString("fileLocation"));
				temp.setStatus(rs.getInt("status"));
				temp.setCurrentUser(rs.getInt("currentUser"));
				result.add(temp);
			}
		}
		catch (SQLException e)
		{
			System.err.println("SQL error");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
		return result;
	}

	/**
	 * Returns an image object that has not yet been processed and that also has
	 * the given projectID
	 * 
	 * @param projectID
	 * @return
	 */
	public Image getImageFromProjectID(int projectID)
	{
		Statement statement = null;
		String sql = "";
		ArrayList<Image> results = new ArrayList<Image>();
		Image result = null;
		ResultSet rs = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Images WHERE projectID = " + projectID
					+ " AND status = " + 0 + " AND currentUser = " + 0;

			rs = statement.executeQuery(sql);

			while (rs.next())
			{
				Image temp = new Image();
				temp.setImageID(rs.getInt("imageID"));
				temp.setProjectID(rs.getInt("projectID"));
				temp.setFileLocation(rs.getString("fileLocation"));
				temp.setStatus(rs.getInt("status"));
				temp.setCurrentUser(rs.getInt("currentUser"));
				results.add(temp);
			}
			if (!results.isEmpty())
			{
				result = results.get(0);
			}
		}
		catch (SQLException e)
		{
			System.err.println("SQL error");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
		return result;
	}

	/**
	 * Returns an image object that has not yet been processed and that also has
	 * the given projectID
	 * 
	 * @param projectID
	 * @return
	 */
	public ArrayList<Image> getAllImagesFromProjectID(int projectID)
	{
		Statement statement = null;
		String sql = "";
		ArrayList<Image> result = new ArrayList<Image>();
		ResultSet rs = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Images WHERE projectID = " + projectID;

			rs = statement.executeQuery(sql);

			while (rs.next())
			{
				Image temp = new Image();
				temp.setImageID(rs.getInt("imageID"));
				temp.setProjectID(rs.getInt("projectID"));
				temp.setFileLocation(rs.getString("fileLocation"));
				temp.setStatus(rs.getInt("status"));
				temp.setCurrentUser(rs.getInt("currentUser"));
				result.add(temp);
			}
		}
		catch (SQLException e)
		{
			System.err.println("SQL error");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
		return result;
	}

	/**
	 * Insert the current data into the DB
	 */
	public void insert(Image input)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			String temp = "(" + input.getID() + "," + input.getProjectID()
					+ "," + "'" + input.getFileLocation() + "',"
					+ input.getStatus() + "," + input.getCurrentUser() + ");";
			sql = "INSERT INTO Images (imageID,projectID,fileLocation,status,currentUser) "
					+ "VALUES " + temp;

			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			System.err.println("SQL error");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
	}

	public String getSampleURL(int projectID)
	{
		Statement statement = null;
		String sql = "";
		String resultURL = null;
		ResultSet rs = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT fileLocation FROM Images WHERE projectID = "
					+ projectID;

			rs = statement.executeQuery(sql);

			while (rs.next())
			{
				resultURL = rs.getString("fileLocation");
			}
		}
		catch (SQLException e)
		{
			System.err.println("SQL error in getSampleURL in the ImageDAC");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
		return resultURL;
	}

	/**
	 * update data inside the Images table
	 */
	public void update(ArrayList<String> setters, String condition)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "UPDATE Images set ";
			for (int i = 0; i < setters.size(); i++)
			{
				if (i != setters.size() - 1)
					sql += setters.get(i) + ", ";
				else
					sql += setters.get(i) + " ";
			}
			sql += "WHERE " + condition + ";";

			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			System.err.println("SQL error");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
	}

	public int getLastKey()
	{
		Statement statement = null;
		String sql = "";
		ResultSet rs = null;
		ArrayList<Integer> results = new ArrayList<Integer>();
		int finalResult = 0;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT imageID FROM Images";

			rs = statement.executeQuery(sql);
			while (rs.next())
			{
				results.add(rs.getInt("imageID"));
			}
			for (Integer integer : results)
			{
				if (integer > finalResult)
				{
					finalResult = integer;
				}
			}
		}
		catch (SQLException e)
		{
			System.err.println("SQL error -- getting last imageID");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
		return finalResult;
	}

	public Image getImageFromBatchID(int batchID)
	{
		Statement statement = null;
		String sql = "";
		ArrayList<Image> results = new ArrayList<Image>();
		Image result = null;
		ResultSet rs = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Images WHERE imageID = " + batchID
					+ " AND status = " + 0 + ";";

			rs = statement.executeQuery(sql);

			while (rs.next())
			{
				Image temp = new Image();
				temp.setImageID(rs.getInt("imageID"));
				temp.setProjectID(rs.getInt("projectID"));
				temp.setFileLocation(rs.getString("fileLocation"));
				temp.setStatus(rs.getInt("status"));
				temp.setCurrentUser(rs.getInt("currentUser"));
				results.add(temp);
			}
			if (!results.isEmpty())
			{
				result = results.get(0);
			}
		}
		catch (SQLException e)
		{
			System.err.println("SQL error");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
		return result;
	}

	public Image getImageFromBatchIDIgnoreStatus(int batchID)
	{
		Statement statement = null;
		String sql = "";
		ArrayList<Image> results = new ArrayList<Image>();
		Image result = null;
		ResultSet rs = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Images WHERE imageID = " + batchID + ";";

			rs = statement.executeQuery(sql);

			while (rs.next())
			{
				Image temp = new Image();
				temp.setImageID(rs.getInt("imageID"));
				temp.setProjectID(rs.getInt("projectID"));
				temp.setFileLocation(rs.getString("fileLocation"));
				temp.setStatus(rs.getInt("status"));
				temp.setCurrentUser(rs.getInt("currentUser"));
				results.add(temp);
			}
			if (!results.isEmpty())
			{
				result = results.get(0);
			}
		}
		catch (SQLException e)
		{
			System.err
					.println("SQL error -- Image from batch ID ignore status");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
				if (rs != null)
				{
					rs.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
		return result;
	}

	public void updateImage(Image i)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "UPDATE Images set status = " + i.getStatus()
					+ ", currentUser = " + i.getCurrentUser()
					+ " WHERE imageID = " + i.getImageID() + ";";

			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			System.err.println("SQL error -- updating images");
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
	}
}
