/******************************************************************************************************************
 * File:LogToFile.java
 * Course: 17655
 * Project: Assignment A3
 * Copyright: Copyright (c) 2018 Carnegie Mellon University
 * Versions:
 *	1.0 March 2019 - Initial write of assignment 3.
 *
 * Description: This class is used to provide Logger object instance and the methods for other classes to access.
 *
 * Parameters: String - logfile name
 *
 * Internal Methods: None
 *  void logInfo() - log INFO level messages to the log file
 *  void logError() - log ERROR level messages to the log file
 *
 * External Dependencies: None
 ******************************************************************************************************************/

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogToFile {

    final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static FileHandler fileHandler;

    /*
     * Constructor that takes in string of the file name and create a file in .log format
     * @param name the name of the file to be created
     */
    public LogToFile(String name) {
        try {
            fileHandler = new FileHandler(name + ".log");
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            System.out.println("Create logging file failed: " + e);
        }
    }

    /*
     * log method to log given non-error message string to the log file created.
     * @param message the message string to be logged into the file
     */
    public void logInfo(String message)
    {
        LOGGER.info(message);
    }

    /*
     * log method to log given error message string to the log file created.
     * @param message the error message string to be logged into the file
     */
    public void logError(String message)
    {
        LOGGER.severe(message);
    }
}
