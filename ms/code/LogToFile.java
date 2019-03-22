//import jdk.internal.instrumentation.Logger;

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
     * log method to log given message string to the log file created.
     * We will use this for both info and error logging, as the java.util package does not have logger.error() method.
     * @param message the message string to be logged into the file
     */
    public void log(String message)
    {
        LOGGER.info(message);
    }
}
