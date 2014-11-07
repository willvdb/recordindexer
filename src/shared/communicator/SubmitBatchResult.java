package shared.communicator;


/**
 * 
 * contains the results for the submitBatch method in the CC
 * 
 * @author Will
 * 
 */
public class SubmitBatchResult
{
	private boolean success;

	public void wasASuccess()
	{
		success = true;
	}

	public void wasNotASuccess()
	{
		success = false;
	}

	public boolean wasSuccessful()
	{
		return success;
	}

	public String getResult()
	{
		if (success)
		{
			return "TRUE\n";
		}
		else
		{
			return "FAILED\n";
		}
	}
}
