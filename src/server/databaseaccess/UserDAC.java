package server.databaseaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.ManagedConnection;
import shared.model.User;

/**
 * 
 * User Database Access Class
 * 
 * @author Will
 * 
 */

public class UserDAC
{
	private ManagedConnection managedConnection;

	public UserDAC(ManagedConnection mcIn)
	{
		managedConnection = mcIn;
	}

	/**
	 * This method creates the table for the user data
	 */
	public void createTable()
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "CREATE TABLE Users " + "(userID INT NOT NULL,"
					+ " userName CHAR(255) NOT NULL,"
					+ " password CHAR(255) NOT NULL,"
					+ " firstName CHAR(255) NOT NULL,"
					+ " lastName CHAR(255) NOT NULL,"
					+ " email CHAR(255) NOT NULL,"
					+ " indexedRecords INT NOT NULL,"
					+ " currentBatch CHAR(255) NOT NULL" + ")";

			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			System.err.println("SQL error -- creating Users table");
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
	 * Method to drop current User table
	 */
	public void dropTable()
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "DROP TABLE Users";

			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			System.err.println("SQL error -- dropping Users table");
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
			sql += "FROM Users";

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
	 * Insert the current data into the DB
	 */
	public void insert(User input)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();

			String temp = "(" + input.getUserID() + "," + "'"
					+ input.getUserName() + "'," + "'" + input.getPassword()
					+ "'," + "'" + input.getFirstName() + "'," + "'"
					+ input.getLastName() + "'," + "'"
					+ input.getEmailAddress() + "',"
					+ input.getIndexedRecords() + "," + "'"
					+ input.getCurrentBatch() + "');";
			sql = "INSERT INTO Users (userID,userName,password,firstName,lastName,email,indexedRecords,currentBatch) "
					+ "VALUES " + temp;

			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			System.err.println("SQL error -- inserting into Users");
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
	
	public User getUser(String userName, String password)
	{
		Statement statement = null;
		String sql = "";
		ResultSet rs = null;
		User u = new User();

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Users WHERE userName = '" + userName + "' AND password = '" + password + "'";

			rs = statement.executeQuery(sql);
			u.setUserID(rs.getInt("userID"));
			u.setUserName(userName);
			u.setPassword(password);
			u.setFirstName(rs.getString("firstName"));
			u.setLastName(rs.getString("lastName"));
			u.setEmailAddress("emailAddress");
			u.setIndexedRecords(rs.getInt("indexedRecords"));
			u.setCurrentBatch(rs.getString("currentBatch"));

		}
		catch (SQLException e)
		{
			u.setFirstName("FALSE");
			u.setLastName("USER");
			// System.err.println("SQL error -- getting a User");
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
		return u;
	}

	/**
	 * update data inside the Users table
	 */
	public void update(ArrayList<String> setters, String condition)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			
				sql = "UPDATE Users set ";
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
			System.err.println("SQL error -- updating Users table");
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
			sql = "SELECT userID FROM Users";

			rs = statement.executeQuery(sql);
			while (rs.next())
			{
				results.add(rs.getInt("userID"));
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
			System.err.println("SQL error -- getting last userID");
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

	public void updateUser(User u)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();

			sql = "UPDATE Users set currentBatch = '" + u.getCurrentBatch()
					+ "', indexedRecords = " + u.getIndexedRecords()
					+ " WHERE userID = " + u.getUserID() + ";";

			statement.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			System.err.println("SQL error -- updating Users table");
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