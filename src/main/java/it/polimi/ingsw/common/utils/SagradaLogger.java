package it.polimi.ingsw.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This class, implemented as a singleton, initialized the logger of the application.
 * To call the logger, it's sufficient to call the methods {@link #log(Level, String)} or
 * {@link #log(Level, String, Throwable)} of this class. The first one is used just to print a message,
 * the second provides also the stack trace related to an exception.
 */
public class SagradaLogger {

    /**
     * Instance of the logger.
     */
    private static Logger logger;

    /**
     * File where the configuration is stored.
     */
    private static final String CONF_PATH = "./conf/logger.properties";

    /**
     * This class shouldn't be instantiated.
     */
    private SagradaLogger() {
        //Never directly try to instantiate this class!
        throw new IllegalStateException();
    }

    /**
     * Retrieves the logger and creates this class if it doesn't already exist.
     * In case the custom logger cannot be loaded, this methods returns the default one.
     * @return the logger of the application.
     */
    private static Logger getLogger() {
        if(logger == null) {
            final LogManager logManager = LogManager.getLogManager();
            try (FileInputStream file = new FileInputStream(CONF_PATH)) {
                logManager.readConfiguration(file);
                logger = Logger.getLogger("sagradaLogger");
            } catch (IOException e) {
                Logger stockLogger = Logger.getLogger(SagradaLogger.class.getName());
                stockLogger.log(Level.WARNING,
                        "Error in loading custom logger configuration, using the default one.", e);
                logger = stockLogger;
            }
        }
        return logger;
    }

    /**
     * Used to log a {@code message} with a certain {@code level} of importance.
     * @param level importance of the log.
     * @param message explains what is the log about.
     */
    public static void log(Level level, String message) {
        getLogger().log(level, message);
    }

    /**
     * Used to log a {@code message} with a certain {@code level} related to an {@code exception}.
     * @param level importance of the log.
     * @param message explains what is the log about.
     * @param exception to which the log is relative to.
     */
    public static void log(Level level, String message, Throwable exception) {
        getLogger().log(level, message, exception);
    }
}
