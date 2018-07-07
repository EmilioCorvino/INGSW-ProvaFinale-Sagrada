package it.polimi.ingsw.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

/**
 * This class loads and stores any configuration or property that has to be read from file at the start of the
 * application. This allows to easily change such properties without accessing to the code or decompressing the .jar
 * archive.
 * The properties available are: RMI port, timer of the {@link it.polimi.ingsw.server.controller.WaitingRoom} and timer
 * of the turn.
 * The class is implemented as a Singleton.
 */
public class PropertyLoader {

    /**
     * Reference to the instance of the class.
     */
    private static PropertyLoader instance = null;

    /**
     * Port used for RMI.
     */
    private static int rmiPort;

    /**
     * Timer used in the {@link it.polimi.ingsw.server.controller.WaitingRoom}.
     */
    private static long roomTimer;

    /**
     * Timer used during player turns.
     */
    private static long turnTimer;

    /**
     * Path of the file containing the properties.
     */
    private static final String PROPERTIES_PATH = "./src/main/conf/sagrada.properties";

    private PropertyLoader() {
        //Never directly instantiate this class!
    }

    /**
     * Method used to retrieve the only instance of this class: it also creates the class and loads the properties if
     * called for the first time, otherwise it just returns the instance of the class.
     * @return the instance of this class.
     */
    public static PropertyLoader getPropertyLoader() {
        if (instance == null) {
            instance = new PropertyLoader();

            Properties sagradaProperties = new Properties();
            try (FileInputStream file = new FileInputStream(PROPERTIES_PATH)) {
                sagradaProperties.load(file);
            } catch (IOException e) {
                SagradaLogger.log(Level.SEVERE, "Unable to load properties from file");
            }

            rmiPort = Integer.parseInt(sagradaProperties.getProperty("rmi.port"));
            roomTimer = Long.parseLong(sagradaProperties.getProperty("room.timer"));
            turnTimer = Long.parseLong(sagradaProperties.getProperty("turn.timer"));
        }

        return instance;
    }

    public  int getRmiPort() {
        return rmiPort;
    }

    public long getRoomTimer() {
        return roomTimer;
    }

    public long getTurnTimer() {
        return turnTimer;
    }
}
