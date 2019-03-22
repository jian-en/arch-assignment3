/******************************************************************************************************************
 * File: AuthenticateServicesAI.java
 * Course: 17655
 * Project: Assignment A3
 * Copyright: Copyright (c) 2019 Carnegie Mellon University
 * Versions:
 *	1.0 March 2019 - Assignment 3 (fj).
 *
 * Description: This class provides the abstract interface for the create micro services, AuthenticateServices.
 * The implementation of these abstract interfaces can be found in the AuthenticateServices.java class.
 * The micro services are partitioned as Create, Retrieve, Update, Delete (CRUD), and Authenticate service packages.
 * Each service is its own process (eg. executing in a separate JVM). It would be a good practice to follow this convention
 * when adding and modifying services. Note that services can be duplicated and differentiated by IP
 * and/or port# they are hosted on. For this assignment, create and retrieve services have been provided and are
 * services are hosted on the local host, on the default RMI port (1099).
 *
 * Parameters: None
 *
 * Internal Methods:
 *  String authenticateUser() - authenticate user with username and password
 *
 * External Dependencies: None
 ******************************************************************************************************************/

import java.rmi.*;

public interface AuthenticateServicesAI extends java.rmi.Remote
{
  /*******************************************************
   * Authenticates user with username and password
   * If the provided credential is valid, return true otherwise false
   *******************************************************/

  boolean authenticateUser(String username, String password) throws RemoteException;

}