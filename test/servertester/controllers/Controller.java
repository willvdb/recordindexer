package servertester.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import server.Server;
import servertester.views.IView;
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
import client.ClientCommunicator;
import client.ClientException;

public class Controller implements IController
{

	private IView _view;
	private ClientCommunicator cc = new ClientCommunicator("localhost", 8080);
	private Server server;

	public Controller()
	{
		return;
	}

	public IView getView()
	{
		return _view;
	}

	public void setView(IView value)
	{
		_view = value;
	}

	// IController methods
	//

	@Override
	public void initialize()
	{
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected()
	{
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");

		switch (getView().getOperation())
		{
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}

		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(
				paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation()
	{
		switch (getView().getOperation())
		{
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}

	private void validateUser()
	{
		cc.changeAddress(getView().getHost(),
				Integer.parseInt(getView().getPort()));
		String[] s = getView().getParameterValues();
		UserParams params = new UserParams(s[0], s[1]);
		ValidateUserResult result = new ValidateUserResult();
		try
		{
			result = cc.validateUser(params);
		}
		catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
		getView().setRequest(s[0] + "\n" + s[1] + "\n");
		getView().setResponse(result.getResult());
	}

	private void getProjects()
	{
		cc.changeAddress(getView().getHost(),
				Integer.parseInt(getView().getPort()));
		String[] s = getView().getParameterValues();
		UserParams params = new UserParams(s[0], s[1]);
		GetProjectsResult result = new GetProjectsResult(false);
		try
		{
			result = cc.getProjects(params);
		}
		catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
		getView().setRequest(s[0] + "\n" + s[1] + "\n");
		getView().setResponse(result.getResult());
	}

	private void getSampleImage()
	{
		cc.changeAddress(getView().getHost(),
				Integer.parseInt(getView().getPort()));
		String[] s = getView().getParameterValues();
		UserProjectParams params = new UserProjectParams(s[0], s[1],
				Integer.parseInt(s[2]));
		GetSampleImageResult result = new GetSampleImageResult("");
		try
		{
			result = cc.getSampleImage(params);
		}
		catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
		getView().setRequest(s[0] + "\n" + s[1] + "\n" + s[2] + "\n");
		getView().setResponse(
				result.getResultwithURL(getView().getHost(), getView()
						.getPort()));
	}

	private void downloadBatch()
	{
		cc.changeAddress(getView().getHost(),
				Integer.parseInt(getView().getPort()));
		String[] s = getView().getParameterValues();
		UserProjectParams params = new UserProjectParams(s[0], s[1],
				Integer.parseInt(s[2]));
		DownloadBatchResult result = new DownloadBatchResult(false);
		try
		{
			result = cc.downloadBatch(params);
		}
		catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
		getView().setRequest(s[0] + "\n" + s[1] + "\n" + s[2] + "\n");
		if (result == null)
		{
			getView().setResponse("FAILED\n");
		}
		else
		{
		getView().setResponse(
				result.getResultwithURL(getView().getHost(), getView()
						.getPort()));
		}
	}

	private void getFields()
	{
		cc.changeAddress(getView().getHost(),
				Integer.parseInt(getView().getPort()));
		String[] s = getView().getParameterValues();
		GetFieldsParams params = new GetFieldsParams(s[0], s[1], s[2]);
		GetFieldsResult result = new GetFieldsResult(new ArrayList<Field>());
		try
		{
			result = cc.getFields(params);
		}
		catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
		getView().setRequest(s[0] + "\n" + s[1] + "\n" + s[2] + "\n");
		getView().setResponse(result.getResult());
	}

	private void submitBatch()
	{
		cc.changeAddress(getView().getHost(),
				Integer.parseInt(getView().getPort()));
		String[] s = getView().getParameterValues();
		SubmitBatchParams params = new SubmitBatchParams(s[0], s[1],
				Integer.parseInt(s[2]), s[3]);
		SubmitBatchResult result = new SubmitBatchResult();
		try
		{
			result = cc.submitBatch(params);
		}
		catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
		getView().setRequest(s[0] + "\n" + s[1] + "\n" + s[2] + "\n");
		getView().setResponse(result.getResult());
	}

	private void search()
	{
		cc.changeAddress(getView().getHost(),
				Integer.parseInt(getView().getPort()));
		String[] s = getView().getParameterValues();
		String[] fields = s[2].split(",", -1);
		String[] values = s[3].split(",", -1);
		ArrayList<Integer> fs = new ArrayList<Integer>();
		for (String string : fields)
		{
			fs.add(Integer.parseInt(string));
		}
		SearchParams params = new SearchParams(s[0], s[1], fs,
				new ArrayList<String>(Arrays.asList(values)));
		SearchResult result = new SearchResult();
		try
		{
			result = cc.search(params);
		}
		catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
		getView().setRequest(
				s[0] + "\n" + s[1] + "\n" + s[2] + "\n" + s[3] + "\n");
		String response = result.getResultWithURL(getView().getHost(),
				getView().getPort());
		if (response.isEmpty())
		{
			response = "FAILED\n";
		}
		getView().setResponse(response);
	}


}
