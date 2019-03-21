/******************************************************************************************************************
 * File: RetrieveServices.java
 * Course: 17655
 * Project: Assignment A3
 * Copyright: Copyright (c) 2018 Carnegie Mellon University
 * Versions:
 *	1.0 February 2018 - Initial write of assignment 3 (ajl).
 *
 * Description: This class provides the concrete implementation of the retrieve micro services. These services run
 * in their own process (JVM).
 *
 * Parameters: None
 *
 * Internal Methods:
 *  String retrieveOrders() - gets and returns all the orders in the orderinfo database
 *  String retrieveOrders(String id) - gets and returns the order associated with the order id
 *
 * External Dependencies:
 *	- rmiregistry must be running to start this server
 *	= MySQL
 - orderinfo database
 ******************************************************************************************************************/
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class AuthenticateServices extends UnicastRemoteObject implements AuthenticateServicesAI
{

  // We store the user credentials
  // Hardcode the credential for simplicity
  static final String USERNAME = "test";
  static final String PASSWORD = "test";

  // Do nothing constructor
  public AuthenticateServices() throws RemoteException {}

  // Main service loop
  public static void main(String args[])
  {
    // What we do is bind to rmiregistry, in this case localhost, port 1099. This is the default
    // RMI port. Note that I use rebind rather than bind. This is better as it lets you start
    // and restart without having to shut down the rmiregistry.

    try
    {
      AuthenticateServices obj = new AuthenticateServices();

      // Bind this object instance to the name RetrieveServices in the rmiregistry
      Naming.rebind("//localhost:1099/AuthenticateServices", obj);

    } catch (Exception e) {

      System.out.println("AuthenticateServices binding err: " + e.getMessage());
      e.printStackTrace();
    }

  } // main

  // This method will return true if user enters valid credential otherwise it will
  // return false

  public boolean authenticateUser(String username, String password) throws RemoteException
  {
    if (username.equals(USERNAME) && password.equals(PASSWORD)) {
      return true;
    }
    else
      return false;
  } // end of the authenticateUser

} // AuthenticateServices