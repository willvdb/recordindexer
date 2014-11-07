package shared.communicator;

import java.net.URL;

/**
 * 
 * contains the results for the getSampleImage method in the CC
 * 
 * @author Will
 * 
 */
public class GetSampleImageResult
{
	String imageURL;

	public GetSampleImageResult(String urlIn)
	{
		imageURL = urlIn;
	}

	/**
	 * 
	 * @return the URL of the image
	 */
	public URL getURL()
	{
		return null;
	}

	public String getResult()
	{
		String result = "";
		if (imageURL == null)
		{
			result += "FAILED\n";
		}
		else if (imageURL == "")
		{
			result += "FAILED\n";
		}
		else
		{
			result += imageURL + "\n";
		}
		return result;
	}

	public String getResultwithURL(String host, String port)
	{
		String result = "";
		String url = "http://" + host + ":" + port + "/";
		if (imageURL == null)
		{
			result += "FAILED\n";
		}
		else if (imageURL == "")
		{
			result += "FAILED\n";
		}
		else
		{
			result += url + imageURL + "\n";
		}
		return result;
	}
}
