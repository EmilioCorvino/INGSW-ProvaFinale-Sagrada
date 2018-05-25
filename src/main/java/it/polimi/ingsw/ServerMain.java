package it.polimi.ingsw;

import it.polimi.ingsw.controller.ServerImplementation;
import it.polimi.ingsw.network.rmi.RmiServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class is used to start the server of the application. It creates an instance of both {@link RmiServer} and
 * the thread for socket connections.
 */
public class ServerMain {

    /**
     * Port that the application will be using.
     */
    public static final int PORT = 54242;

    public static void main(String[] args) {
        //Sets up Rmi server and client remote interfaces.
        try {
            Registry registry = LocateRegistry.createRegistry(PORT);
            RmiServer rmiServer = new RmiServer(PORT, new ServerImplementation());
            registry.bind("RmiServer", rmiServer);
        } catch (Exception e) {
            System.err.println("Rmi server exception: " + e.toString());
            e.printStackTrace();
        }
        System.err.println("Rmi Server ready, waiting for connections...");


        //todo setup socket server
    }
}
