package it.polimi.ingsw;

import it.polimi.ingsw.controller.ServerImplementation;
import it.polimi.ingsw.network.rmi.RmiServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This class is used to start the server of the application. It creates an instance of both {@link RmiServer} and
 * the thread for socket connections.
 */
public class ServerMain {

    /**
     * Port that the application will be using.
     */
    public static final int PORT = 54242;

    //Server logger setup.
    private static final LogManager logManager = LogManager.getLogManager();
    private static final Logger LOGGER = Logger.getLogger("serverConfigurationLogger");
    static {
        try {
            logManager.readConfiguration(new FileInputStream(
                    "./src/main/java/it/polimi/ingsw/utils/logs/logger.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error in loading configuration", e);
        }
    }

    public static void main(String[] args) {
        //Sets up Rmi server and client remote interfaces.
        try {
            Registry registry = LocateRegistry.createRegistry(PORT);
            RmiServer rmiServer = new RmiServer(PORT, new ServerImplementation());
            registry.bind("RmiServer", rmiServer);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Rmi server exception", e);
        }
        LOGGER.config("Rmi Server ready, waiting for connections...");


        //todo setup socket server
    }
}
