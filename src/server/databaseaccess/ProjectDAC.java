package server.databaseaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.ManagedConnection;
import shared.model.Project;

/**
 * 
 * Project Database Access Class
 * 
 * @author Will
 * 
 */

public class ProjectDAC
{
	ManagedConnection managedConnection;

	public ProjectDAC(ManagedConnection mcIn)
	{
		this.managedConnection = mcIn;
	}

	/**
	 * This method creates the table for the Projects data
	 */
	public void createTable()
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "CREATE TABLE Projects " + "(projectID INT NOT NULL,"
					+ " title CHAR(255) NOT NULL,"
					+ " recordsPerImage INT NOT NULL,"
					+ " fieldsPerImage INT NOT NULL,"
					+ " firstYCoord INT NOT NULL,"
					+ " recordHeight INT NOT NULL)";
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
	 * Method to drop current Projects table
	 */
	public void dropTable()
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "DROP TABLE Projects";

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
			sql += "FROM Projects";

			rs = statement.executeQuery(sql);
		}
		catch (SQLException e)
		{
			System.err.println("SQL error");
		}
		return rs;
	}
	
	/**
	 * Returns all the projects in the DB
	 * @return
	 */
	public ArrayList<Project> getAllProjects()
	{
		Statement statement = null;
		String sql = "";
		ArrayList<Project> result = new ArrayList<Project>();
		ResultSet rs = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Projects";

			rs = statement.executeQuery(sql);
			
			while (rs.next())
			{
				Project temp = new Project();
				temp.setProjectID(rs.getInt("projectID"));
				temp.setTitle(rs.getString("title"));
				temp.setFirstYCoord(rs.getInt("firstYCoord"));
				temp.setRecordHeight(rs.getInt("recordHeight"));
				temp.setRecordsPerImage(rs.getInt("recordsPerImage"));
				temp.setFieldsPerImage(rs.getInt("fieldsPerImage"));
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

	public Project getSpecificProject(int projectID)
	{
		Statement statement = null;
		String sql = "";
		Project result = null;
		ResultSet rs = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Projects WHERE projectID = " + projectID;

			rs = statement.executeQuery(sql);

			Project temp = new Project();
			temp.setProjectID(rs.getInt("projectID"));
			temp.setTitle(rs.getString("title"));
			temp.setFirstYCoord(rs.getInt("firstYCoord"));
			temp.setRecordHeight(rs.getInt("recordHeight"));
			temp.setFieldsPerImage(rs.getInt("fieldsPerImage"));
			temp.setRecordsPerImage(rs.getInt("recordsPerImage"));
			result = temp;
		}
		catch (SQLException e)
		{
			System.err.println("SQL error -- Get Specific Project");
			System.err.println(e.getMessage());
			e.printStackTrace();
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
	public void insert(Project input)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			String temp = "(" + input.getProjectID() + ",'" + input.getTitle()
					+ "'," + input.getRecordsPerImage() + ","
					+ input.getFieldsPerImage() + "," +
					+ input.getFirstYCoord() + "," + input.getRecordHeight()
					+ ");";
			sql = "INSERT INTO Projects (projectID,title,recordsPerImage,"
					+ "fieldsPerImage,firstYCoord,recordHeight) "
					+ "VALUES " + temp;

			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			System.err.println("SQL error");
		}
	}

	/**
	 * update data inside the Projects table
	 */
	public void update(ArrayList<String> setters, String condition)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();

			sql = "UPDATE Projects set ";
			for (int i = 0; i < setters.size(); i++)
			{
				if (i != setters.size() - 1)// last one doesn't need comma
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
			sql = "SELECT projectID FROM Projects";

			rs = statement.executeQuery(sql);
			while (rs.next())
			{
				results.add(rs.getInt("projectID"));
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
			System.err.println("SQL error -- getting last projectID");
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

	public int getFieldsPerImage(int projectID)
	{
		Statement statement = null;
		String sql = "";
		ResultSet rs = null;
		int finalResult = 0;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT fieldsPerImage FROM Projects WHERE projectID = "
					+ projectID;

			rs = statement.executeQuery(sql);
			finalResult = rs.getInt("fieldsPerImage");
		}
		catch (SQLException e)
		{
			System.err.println("SQL error -- getting Fields per image");
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

	public boolean isValidProjectID(int projectID)
	{
		Statement statement = null;
		String sql = "";
		ResultSet rs = null;
		ArrayList<Integer> ints = new ArrayList<Integer>();

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT projectID FROM Projects";

			rs = statement.executeQuery(sql);
			while (rs.next())
			{
				ints.add(rs.getInt("projectID"));
			}
		}
		catch (SQLException e)
		{
			System.err.println("SQL error -- validating project ID");
		}
		finally
		{
			try
			{
			if (statement != null) {statement.close();}
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
		return ints.contains(projectID);
	}
}
