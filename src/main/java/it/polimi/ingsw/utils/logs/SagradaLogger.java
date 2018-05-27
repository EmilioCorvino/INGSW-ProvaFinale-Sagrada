package it.polimi.ingsw.utils.logs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This class, implemented as a singleton, initialized the logger of the application.
 */
public class SagradaLogger {

    /**
     * Instance of the logger.
     */
    private static Logger logger;

    /**
     * File where the configuration is stored.
     */
    private static final String CONF_PATH = "./src/main/java/it/polimi/ingsw/utils/logs/logger.properties";

    /**
     * The constructor loads the configuration of the logger from file.
     */
    private SagradaLogger() {
        //Server logger setup.
        final LogManager logManager = LogManager.getLogManager();
        logger = Logger.getLogger("sagradaLogger");
        try {
            logManager.readConfiguration(new FileInputStream(
                    CONF_PATH));

        } catch (IOException e) {
                logger.log(Level.SEVERE, "Error in loading configuration", e);
        }
    }

    /**
     * Retrieves the logger and creates this class if it doesn't already exist.
     * @return the logger of the application.
     */
    private static Logger getLogger() {
        if(logger == null) {
            new SagradaLogger();
        }
        return logger;
    }

    /**
     * Used to log a {@code message} with a certain {@code level} of importance.
     * @param level importance of the log.
     * @param message explains what is the log about.
     */
    public static void log(Level level, String message) {
        try {
            getLogger().log(level, message);
        } catch (NullPointerException e) {
            Logger logger = Logger.getLogger(SagradaLogger.class.getName());
            logger.log(Level.SEVERE, "Couldn't retrieve SagradaLogger.");
        }
    }

    /**
     * Used to log a {@code message} with a certain {@code level} related to an {@code exception}.
     * @param level importance of the log.
     * @param message explains what is the log about.
     * @param exception to which the log is relative to.
     */
    public static void log(Level level, String message, Throwable exception) {
        try {
            getLogger().log(level, message, exception);
        } catch (NullPointerException e) {
            Logger logger = Logger.getLogger(SagradaLogger.class.getName());
            logger.log(Level.SEVERE, "Couldn't retrieve SagradaLogger.");
        }
    }

}
