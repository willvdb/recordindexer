package servertester;

import java.awt.EventQueue;

import server.Server;
import servertester.controllers.Controller;
import servertester.views.IndexerServerTesterFrame;
import client.ClientCommunicator;

public class GuiTester
{
	private Server server;
	private ClientCommunicator cc = new ClientCommunicator("localhost", 5555);

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				IndexerServerTesterFrame frame = new IndexerServerTesterFrame();
				Controller controller = new Controller();
				frame.setController(controller);
				controller.setView(frame);
				controller.initialize();
				frame.setVisible(true);
			}
		});

	}

}
