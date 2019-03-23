/******************************************************************************************************************
* File: MSClientAPI.java
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2018 Carnegie Mellon University
* Versions:
*	1.0 February 2018 - Initial write of assignment 3 (ajl).
*
* Description: This class provides access to the webservices via RMI. Users of this class need not worry about the
* details of RMI (provided the services are running and registered via rmiregistry).  
*
* Parameters: None
*
* Internal Methods:
*  String retrieveOrders() - gets and returns all the orders in the orderinfo database
*  String retrieveOrders(String id) - gets and returns the order associated with the order id
*  String newOrder(String Date, String FirstName, String LastName, String Address, String Phone) - creates a new 
*  order in the orderinfo database
*
*
* External Dependencies: None
******************************************************************************************************************/

import java.rmi.Naming;

public class MSClientAPI
{
	String response = null;
	String cachedUsername = "";
	String cachedPassword = "";
	boolean authFlag = false;


	/********************************************************************************
	 * Description: Authenticates provided user credentials.
	 *              Note that this method is serviced by the
	 *			   AuthenticateServices server process.
	 * Parameters: username and password, both String
	 * Returns: boolean true if authenticated otherwise false
	 ********************************************************************************/

	public boolean authenticateUser(String username, String password) throws Exception
	{
		AuthenticateServicesAI obj = (AuthenticateServicesAI) Naming.lookup("rmi://authenticate-server:1099/AuthenticateServices");
		authFlag = obj.authenticateUser(username, password);
		if (authFlag) {
			cachedUsername = username;
			cachedPassword = password;
		}
		return authFlag;
	}

	/********************************************************************************
	* Description: Retrieves all the orders in the orderinfo database. Note 
	*              that this method is serviced by the RetrieveServices server 
	*			   process.
	* Parameters: None
	* Returns: String of all the current orders in the orderinfo database
	********************************************************************************/

	public String retrieveOrders() throws Exception
	{
           RetrieveServicesAI obj = (RetrieveServicesAI) Naming.lookup("rmi://retrieve-server:1099/RetrieveServices");  
           response = obj.retrieveOrders(cachedUsername, cachedPassword);
           return(response);
	}
	
	/********************************************************************************
	* Description: Retrieves the order based on the id argument provided from the
	*              orderinfo database. Note that this method is serviced by the 
	*			   RetrieveServices server process.
	* Parameters: None
	* Returns: String of all the order corresponding to the order id argument 
	*          in the orderinfo database.
	********************************************************************************/

	public String retrieveOrders(String id) throws Exception
	{
           RetrieveServicesAI obj = (RetrieveServicesAI) Naming.lookup("rmi://retrieve-server:1099/RetrieveServices");  
           response = obj.retrieveOrders(id, cachedUsername, cachedPassword);
           return(response);	

	}

	/********************************************************************************
	* Description: Creates the new order to the orderinfo database
	* Parameters: None
	* Returns: String that contains the status of the create operatation
	********************************************************************************/

   	public String newOrder(String Date, String FirstName, String LastName, String Address, String Phone) throws Exception
	{
           CreateServicesAI obj = (CreateServicesAI) Naming.lookup("rmi://create-server:1099/CreateServices"); 
           response = obj.newOrder(Date, FirstName, LastName, Address, Phone, cachedUsername, cachedPassword);
           return(response);	
		
    }


    /********************************************************************************
	* Description: Delete an order with specific ID in the orderinfo database
	* Parameters: None
	* Returns: String that contains the status of the delete operatation
	********************************************************************************/

	public String deleteOrder(String id) throws Exception
	{
           DeleteServicesAI obj = (DeleteServicesAI) Naming.lookup("rmi://delete-server:1099/DeleteServices");
           response = obj.deleteOrder(id, cachedUsername, cachedPassword);
           return(response);
    }
}
