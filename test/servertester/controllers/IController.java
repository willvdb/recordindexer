package servertester.controllers;

import client.ClientException;

public interface IController {

	void initialize();
	
	void operationSelected();
	
	void executeOperation() throws ClientException;
}

