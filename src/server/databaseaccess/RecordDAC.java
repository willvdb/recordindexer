package server.databaseaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.ManagedConnection;
import shared.model.Record;

/**
 * 
 * Record Database Access Class
 * 
 * @author Will
 * 
 */

public class RecordDAC
{

	ManagedConnection managedConnection;

	public RecordDAC(ManagedConnection mcIn)
	{
		this.managedConnection = mcIn;
	}

	/**
	 * This method creates the table for the Records data
	 */
	public void createTable()
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "CREATE TABLE Records " + "(recordID INT NOT NULL,"
					+ " batchID INT NOT NULL," + " X INT NOT NULL,"
					+ " Y INT NOT NULL," + " content CHAR(255) NOT NULL)";
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
	 * Method to drop current Records table
	 */
	public void dropTable()
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "DROP TABLE Records";

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
			sql += "FROM Records";

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
			}
			catch (SQLException e)
			{
				System.err.println("couldn't close the statment or result set");
			}
		}
		return rs;
	}

	/**
	 * get's all the records in the DB
	 * @return
	 */
	public ArrayList<Record> getAllRecords()
	{
		Statement statement = null;
		String sql = "";
		ArrayList<Record> result = new ArrayList<Record>();
		ResultSet rs = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Records";

			rs = statement.executeQuery(sql);

			while (rs.next())
			{
				Record temp = new Record();
				temp.setRecordID(rs.getInt("recordID"));
				temp.setProjectID(rs.getInt("batchID"));
				temp.setContent(rs.getString("content"));
				temp.setX(rs.getInt("X"));
				temp.setY(rs.getInt("Y"));
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
	 * get's all the records in the DB associated with the given batchID
	 * 
	 * @return
	 */
	public ArrayList<Record> getAllRecordsByBatchID(int batchID)
	{
		Statement statement = null;
		String sql = "";
		ArrayList<Record> result = new ArrayList<Record>();
		ResultSet rs = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Records WHERE batchID = " + batchID;

			rs = statement.executeQuery(sql);

			while (rs.next())
			{
				Record temp = new Record();
				temp.setRecordID(rs.getInt("recordID"));
				temp.setProjectID(rs.getInt("batchID"));
				temp.setContent(rs.getString("content"));
				temp.setX(rs.getInt("X"));
				temp.setY(rs.getInt("Y"));
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
	 * get's all the records in the DB associated with the given batchID and
	 * give fieldNum
	 * 
	 * @return
	 */

	public ArrayList<Record> getAllRecordsByBatchIDAndFieldNum(int batchID,
			int fieldNum)
	{
		Statement statement = null;
		String sql = "";
		ArrayList<Record> result = new ArrayList<Record>();
		ResultSet rs = null;

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "SELECT * FROM Records WHERE batchID = " + batchID
					+ " AND X= " + fieldNum;

			rs = statement.executeQuery(sql);

			while (rs.next())
			{
				Record temp = new Record();
				temp.setRecordID(rs.getInt("recordID"));
				temp.setProjectID(rs.getInt("batchID"));
				temp.setContent(rs.getString("content"));
				temp.setX(rs.getInt("X"));
				temp.setY(rs.getInt("Y"));
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
	public void insert(Record input)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			String temp = "(" + input.getRecordID() + ","
					+ input.getProjectID() + "," + input.getX() + ","
					+ input.getY() + "," + "'" + input.getContent() + "');";
			sql = "INSERT INTO Records (recordID,batchID,X,Y,content) "
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
	 * Insert the current data into the DB and return if it worked or not
	 */
	public boolean insertBoolean(Record input)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			String temp = "(" + input.getRecordID() + ","
					+ input.getProjectID() + "," + input.getX() + ","
					+ input.getY() + "," + "'" + input.getContent() + "');";
			sql = "INSERT INTO Records (recordID,batchID,X,Y,content) "
					+ "VALUES " + temp;

			statement.executeUpdate(sql);
			return true;
		}
		catch (SQLException e)
		{
			System.err.println("SQL  -- inserting a record");
			System.err.println("Record: " + input.getContent() + " Record ID: "
					+ input.getRecordID());
			return false;
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
	 * Insert a whole arrayList of records
	 */
	public boolean insertArrayListofRecords(ArrayList<Record> records)
	{
		boolean result = true;
		for (Record record : records)
		{
			boolean temp = insertBoolean(record);
			if (!temp)
			{
				result = false;
			}
		}
		return result;
	}

	/**
	 * update data inside the Records table
	 */
	public void update(ArrayList<String> setters, String condition)
	{
		Statement statement = null;
		String sql = "";

		try
		{
			statement = managedConnection.getConnection().createStatement();
			sql = "UPDATE Records set ";
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
			sql = "SELECT recordID FROM Records";

			rs = statement.executeQuery(sql);
			while (rs.next())
			{
				results.add(rs.getInt("recordID"));
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
			System.err.println("SQL error -- getting last recordID");
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
