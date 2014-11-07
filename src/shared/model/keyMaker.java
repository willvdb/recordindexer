package shared.model;

import server.ManagedConnection;

public class keyMaker {
	public static int userKey = 1;
	public static int projectKey = 1;
	public static int fieldKey = 1;
	public static int imageKey = 1;
	public static int recordKey = 1;
	
	public static void reset()
	{
		userKey = 1;
		projectKey = 1;
		fieldKey = 1;
		imageKey = 1;
		recordKey = 1;
	}
	
	public static void updateKeys(ManagedConnection managedConnection)
	{
		userKey = managedConnection.getUsersAccess().getLastKey() + 1;
		projectKey = managedConnection.getProjectAccess().getLastKey() + 1;
		fieldKey = managedConnection.getFieldAccess().getLastKey() + 1;
		imageKey = managedConnection.getImageAccess().getLastKey() + 1;
		recordKey = managedConnection.getRecordAccess().getLastKey() + 1;
	}
}

