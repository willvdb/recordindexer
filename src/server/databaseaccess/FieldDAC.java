package server.databaseaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import server.ManagedConnection;
import shared.model.Field;

/**
 * 
 * Field Database Access Class
 * 
 * @author Will
 * 
 */

public class FieldDAC
{

	ManagedConnection managedConnection;

	public FieldDAC(ManagedConnection mcIn)
	{
		this.managedConnection = mcIn;
	}

	/**
	 * This method creates the table for the Fields data
	 */
	public void createTable()
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "CREATE TABLE Fields " + "(fieldID INT NOT NULL,"
					+ " fieldNum INT NOT NULL,"
					+ " projectID INT NOT NULL," + " title CHAR(255) NOT NULL,"
					+ " xCoord INT NOT NULL," + " width INT NOT NULL,"
					+ " helpHTML CHAR(255) NOT NULL,"
					+ " knownData CHAR(255) NOT NULL)";
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
	 * Method to drop current Fields table
	 */
	public void dropTable()
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "DROP TABLE Fields";

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
			sql += "FROM Fields";
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
	 * Get's all the fields in the DB
	 */
	public ArrayList<Field> getAllFields()
	{
		Statement statement = null;
		String sql = "";
		ResultSet rs = null;
		ArrayList<Field> result = new ArrayList<Field>();

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Fields";
			rs = statement.executeQuery(sql);

			while (rs.next())
			{
				Field f = new Field();
				f.setFieldID(rs.getInt("fieldID"));
				f.setFieldNum(rs.getInt("fieldNum"));
				f.setTitle(rs.getString("title"));
				f.setProjectID(rs.getInt("projectID"));
				f.setXCoord(rs.getInt("xCoord"));
				f.setWidth(rs.getInt("width"));
				f.setHelpHTML(rs.getString("helpHTML"));
				f.setKnownData(rs.getString("knownData"));
				result.add(f);
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
	 * Get's all the fields associated with the given project ID
	 */
	public ArrayList<Field> getProjectFields(String projectID)
	{
		Statement statement = null;
		String sql = "";
		ResultSet rs = null;
		ArrayList<Field> result = new ArrayList<Field>();

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Fields WHERE projectID = " + projectID;
			rs = statement.executeQuery(sql);
			
			while (rs.next())
			{
				Field f = new Field();
				f.setFieldID(rs.getInt("fieldID"));
				f.setFieldNum(rs.getInt("fieldNum"));
				f.setTitle(rs.getString("title"));
				f.setProjectID(rs.getInt("projectID"));
				f.setXCoord(rs.getInt("xCoord"));
				f.setWidth(rs.getInt("width"));
				f.setHelpHTML(rs.getString("helpHTML"));
				f.setKnownData(rs.getString("knownData"));
				result.add(f);
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
	 * Get's a specific field by field ID
	 */
	public Field getFieldByID(int fieldID)
	{
		Statement statement = null;
		String sql = "";
		ResultSet rs = null;
		Field result = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Fields WHERE fieldID = " + fieldID;
			rs = statement.executeQuery(sql);

			result = new Field();
			result.setFieldID(rs.getInt("fieldID"));
			result.setFieldNum(rs.getInt("fieldNum"));
			result.setProjectID(rs.getInt("projectID"));
			result.setXCoord(rs.getInt("xCoord"));
			result.setHelpHTML(rs.getString("helpHTML"));
			result.setKnownData(rs.getString("knownData"));
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
	 * get all the fields from a list of fieldID's
	 * 
	 * @param fieldIDs
	 * @return
	 */
	public ArrayList<Field> getFieldsByIDs(List<Integer> fieldIDs)
	{
		ArrayList<Field> fields = new ArrayList<Field>();
		for (Integer fieldID : fieldIDs)
		{
			fields.add(getFieldByID(fieldID));
		}
		return fields;
	}

	/**
	 * Insert the current data into the DB
	 */
	public void insert(Field input)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			String temp = "(" + input.getFieldID() + "," + input.getFieldNum()
					+ "," + input.getProjectID()
					+ "," + "'" + input.getTitle() + "'," + input.getXCoord()
					+ "," + input.getWidth() + "," + "'" + input.getHelpHTML()
					+ "'," + // careful
					"'" + input.getKnownData() + "');";// careful
			sql = "INSERT INTO Fields (fieldID,fieldNum,projectID,title,xCoord,width,helpHTML,knownData) "
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

	/**
	 * update data inside the Fields table
	 */
	public void update(ArrayList<String> setters, String condition)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "UPDATE Fields set ";
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
			sql = "SELECT fieldID FROM Fields";

			rs = statement.executeQuery(sql);
			while (rs.next())
			{
				results.add(rs.getInt("fieldID"));
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
			System.err.println("SQL error -- getting last fieldID");
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

}
