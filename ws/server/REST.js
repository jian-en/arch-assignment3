
/******************************************************************************************************************
* File:REST.js
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2019 Carnegie Mellon University
* Versions:
*   1.1 March 2019 - Added authentication, delete, and logging (Team Data61)
*   1.0 February 2018 - Initial write of assignment 3 for 2018 architectures course(ajl).
*
* Description: This module provides the restful webservices for the Server.js Node server. This module contains GET,
* and POST services.  
*
* Parameters: 
*   router - this is the URL from the client
*   connection - this is the connection to the database
*   md5 - This is the md5 hashing/parser... included by convention, but not really used 
*
* Internal Methods: 
*   router.get("/"... - returns the system version information
*   router.get("/orders"... - returns a listing of everything in the ws_orderinfo database
*   router.get("/orders/:order_id"... - returns the data associated with order_id
*   router.post("/order?"... - adds the new customer data into the ws_orderinfo database
*
* External Dependencies: mysql
*
******************************************************************************************************************/

var mysql   = require("mysql");     //Database
const logInstance = require("./logger"); //Logging instance
var userService = require('./UserService'); //User

function REST_ROUTER(router,connection) {
    var self = this;
    self.handleRoutes(router,connection);
}

// Here is where we define the routes. Essentially a route is a path taken through the code dependent upon the 
// contents of the URL

REST_ROUTER.prototype.handleRoutes= function(router,connection) {
    // POST with username and password to authenticate the user
    // req parameter is the request object
    // res parameter is the response object
    router.post("/authenticate",function (req,res) {
        var logging_rest = "POST /authenticate ";
        user = userService.authenticate(req.body);
        logInstance.write("INFO", logging_rest + " attempting to authenticate user: " + JSON.stringify(req.body.username));
        if (user) {
            logInstance.write("INFO", logging_rest + " successfully authenticated user: " + JSON.stringify(req.body.username));
            res.json(user);
        } else {
            logInstance.write("ERROR", logging_rest + " unable to authenticate user: " + JSON.stringify(req.body.username));
            res.status(400).json({ message: 'Username or password is incorrect!' });
        }

    });

    // GET with no specifier - returns system version information
    // req parameter is the request object
    // res parameter is the response object

    router.get("/",function(req,res){
        res.json({"Message":"Orders Webservices Server Version 1.0"});
        logInstance.write("INFO", "GET / with response message: Orders Webservices Server Version 1.0");
    });
    
    // GET for /orders specifier - returns all orders currently stored in the database
    // req paramdter is the request object
    // res parameter is the response object
  
    router.get("/orders",function(req,res){
        console.log("Getting all database entries..." );
        var logging_rest = "GET /orders ";
        logInstance.write("INFO", logging_rest + "Getting all database entries...");

        var query = "SELECT * FROM ??";
        var table = ["orders"];
        query = mysql.format(query,table);
        connection.query(query,function(err,rows){
            if(err) {
                logInstance.write("ERROR", logging_rest + "Error executing MySQL query");
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                logInstance.write("INFO", logging_rest + "Successfully got the following orders: " + JSON.stringify(rows));
                res.json({"Error" : false, "Message" : "Success", "Orders" : rows});
            }
        });
    });

    // GET for /orders/order id specifier - returns the order for the provided order ID
    // req parameter is the request object
    // res parameter is the response object
     
    router.get("/orders/:order_id",function(req,res){
        console.log("Getting order ID: ", req.params.order_id );
        var logging_rest = "GET /orders/" + req.params.order_id + " ";

        logInstance.write("INFO", logging_rest + "Getting order ID: " + req.params.order_id);
        var query = "SELECT * FROM ?? WHERE ??=?";
        var table = ["orders","order_id",req.params.order_id];
        query = mysql.format(query,table);
        connection.query(query,function(err,rows){
            if(err) {
                logInstance.write("ERROR", logging_rest + "Error executing MySQL query");
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                logInstance.write("INFO", logging_rest + "Successfully got the following order: " + JSON.stringify(rows));
                res.json({"Error" : false, "Message" : "Success", "Users" : rows});
            }
        });
    });

    // POST for /orders?order_date&first_name&last_name&address&phone - adds order
    // req paramdter is the request object - note to get parameters (eg. stuff afer the '?') you must use req.body.param
    // res parameter is the response object 
  
    router.post("/orders",function(req,res){
        //console.log("url:", req.url);
        //console.log("body:", req.body);
        console.log("Adding to orders table ", req.body.order_date,",",req.body.first_name,",",req.body.last_name,",",req.body.address,",",req.body.phone);
        var logging_rest = "POST /orders ";
        var order_details = "order_date: " + req.body.order_date + "\n"
                            + "first_name: " + req.body.first_name + "\n"
                            + "last_name: " + req.body.last_name + "\n"
                            + "address: " + req.body.address + "\n"
                            + "phone: " + req.body.phone;

        logInstance.write("INFO", logging_rest + "Adding the following order: \n" + order_details);

        var query = "INSERT INTO ??(??,??,??,??,??) VALUES (?,?,?,?,?)";
        var table = ["orders","order_date","first_name","last_name","address","phone",req.body.order_date,req.body.first_name,req.body.last_name,req.body.address,req.body.phone];
        query = mysql.format(query,table);
        connection.query(query,function(err,rows){
            if(err) {
                logInstance.write("ERROR", logging_rest + "Error executing MySQL query");
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                logInstance.write("INFO", logging_rest + "Successfully added the order!");
                res.json({"Error" : false, "Message" : "User Added !"});
            }
        });
    });


    // DELETE for /orders/order id specifier - remove an order with specific order ID
    // req parameter is the request object
    // res parameter is the response object

    router.delete("/orders/:order_id", function(req, res) {
        var order_id = req.params.order_id;
        console.log("Deleting order with order ID: ", order_id);
        var logging_rest = "DELETE /orders/" + order_id + " ";

        var query = "DELETE FROM ?? WHERE ??=?";
        var table = ["orders", "order_id", order_id];
        query = mysql.format(query, table);
        connection.query(query, function(err, rows){
            if(err) {
                logInstance.write("ERROR", logging_rest + "Error executing MySQL query");
                res.json({"Error" : true, "Message" : "Error executing MySQL query"});
            } else {
                logInstance.write("INFO", logging_rest + "Successfully deleted the order!");
                res.json({"Error" : false, "Message" : "Removed " + rows.affectedRows + " row(s) successfully."});
            }
        });
    });
}

// The next line just makes this module available... think of it as a kind package statement in Java

module.exports = REST_ROUTER;