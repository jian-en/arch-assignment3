/******************************************************************************************************************
* File: DeleteServices.java
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2018 Carnegie Mellon University
* Versions:
*	1.0 March 2019 - Initial write of assignment 3 (JTC).
 *	1.1 March 2019 - Updated logging function (BKW/IZ).
*
* Description: This class provides the concrete implementation of the delete micro services. These services run
* in their own process (JVM).
*
* Parameters: None
*
* Internal Methods:
*  String deleteOrder() - delete an order in the ms_orderinfo database from the supplied parameters.
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

public class DeleteServices extends UnicastRemoteObject implements DeleteServicesAI
{ 
    // Set up the JDBC driver name and database URL
    static final String JDBC_CONNECTOR = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/ms_orderinfo?autoReconnect=true&useSSL=false";

    // Set up the orderinfo database credentials
    static final String USER = "root";
    static final String PASS = "tmp"; //replace with your MySQL root password

    // Create new log file
    LogToFile logger = new LogToFile("./microservice_delete");

    // Do nothing constructor
    public DeleteServices() throws RemoteException {}

    // Main service loop
    public static void main(String args[]) 
    { 	
    	// What we do is bind to rmiregistry, in this case localhost, port 1099. This is the default
    	// RMI port. Note that I use rebind rather than bind. This is better as it lets you start
    	// and restart without having to shut down the rmiregistry. 

        try 
        { 
            DeleteServices obj = new DeleteServices();

            // Bind this object instance to the name DeleteServices in the rmiregistry
            Naming.rebind("//localhost:1099/DeleteServices", obj); 

        } catch (Exception e) {

            System.out.println("DeleteServices binding err: " + e.getMessage()); 
            e.printStackTrace();
        } 

    } // main

    // Authenticate with given credentials using authenticateServices
    private boolean authenticate(String username, String password) throws Exception {
        AuthenticateServicesAI obj = (AuthenticateServicesAI) Naming.lookup("rmi://localhost:1099/AuthenticateServices");
        return obj.authenticateUser(username, password);
    }


    // Implmentation of the abstract classes in DeleteServicesAI happens here.

    // This method add the entry into the ms_orderinfo database

    public String deleteOrder(String id, String username, String password) throws RemoteException
    {
        // Authenticate first
        try {
            if (!authenticate(username, password))
                return "Error: authentication failed!";
        } catch (Exception e) {
            System.out.println("Authentication failed:: " + e);
            return "Error: authentication service failed!";
        }

      	// Local declarations

        Connection conn = null;		                 // connection to the orderinfo database
        Statement stmt = null;		                 // A Statement object is an interface that represents a SQL statement.
        String ReturnString = " orders deleted";	 // Return string. If everything works you get an 'OK' message
        							                 // if not you get an error string
        int rows_affected = 0;                       // part of return string, which denotes how many rows are deleted.
        try
        {
            // Here we load and initialize the JDBC connector. Essentially a static class
            // that is used to provide access to the database from inside this class.

            Class.forName(JDBC_CONNECTOR);

            //Open the connection to the orderinfo database

            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // Here we create the queery Execute a query. Not that the Statement class is part
            // of the Java.rmi.* package that enables you to submit SQL queries to the database
            // that we are connected to (via JDBC in this case).

            stmt = conn.createStatement();
            
            String sql = "DELETE FROM orders WHERE order_id = " + id;

            // execute the update

            rows_affected = stmt.executeUpdate(sql);

            // Log info of order deleted
            if (rows_affected == 0) {
                logger.logInfo("Order "+id+" doesn't exist in database.");
            } else {
                logger.logInfo("Order "+id+" successfully deleted from the database.");
            }

            // clean up the environment

            stmt.close();
            conn.close();
            stmt.close(); 
            conn.close();

        } catch(Exception e) {
            ReturnString = e.toString();
            logger.logError("Order "+id+" cannot be deleted due to error: "+e.getMessage());
        } 
        
        return Integer.toString(rows_affected) + ReturnString;

    } //delete order

} // DeleteServices
