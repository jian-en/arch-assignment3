/******************************************************************************************************************
* File:logger.js
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2019 Carnegie Mellon University
* Versions:
*   1.0 March 2019 - Initial logger for server webservices. (BKW/IZ)
*
* Description: This module provides a logging instance that can be imported to other server files.
* This logger is dynamic enough to support any string log level and string message.
*
* Methods:
*   write(log_level, log_message) - writes log message (with timestamp) to log file path specified in log_path
*
* External Dependencies: fs
*
******************************************************************************************************************/


const fs = require('fs');
const log_path = "./webservices.log"; // log file location

 module.exports = {
     // write log_level and log_message to log file. 
     // String log_level should be INFO or ERROR
     // String log_message message to be logged
     // writes current timestamp with log_level and message to log_path
     write: function (log_level, log_message) {
        var current_time = new Date();
        var full_log_line = current_time.toDateString() 
                            + ' ' + current_time.toTimeString()
                            + ' [' + log_level + ']'
                            + ' ' + log_message + '\n';
        
        fs.appendFile(log_path, full_log_line, function(err) {
            if(err) {
                return console.log(err);
            }
        
            console.log("Log file: " + log_path + " created!");
        }); 
     }
 };