package it.polimi.ingsw.server;

import it.polimi.ingsw.common.network.rmi.RmiServer;
import it.polimi.ingsw.common.utils.PropertyLoader;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.server.controller.WaitingRoom;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

/**
 * This class is used to start the server of the application. It creates an instance of both {@link RmiServer} and
 * the thread for socket connections.
 */
public class ServerMain {

    /**
     * Room in which players that connect wait for the match to start.
     */
    private WaitingRoom room; //Substitute this with a match handler to handle more matches.

    private ServerMain(WaitingRoom room) {
        this.room = room;
    }

    /**
     * Starts the RMI server.
     */
    private void startRmiServer() {
        int port = PropertyLoader.getPropertyLoader().getRmiPort();
        SagradaLogger.log(Level.CONFIG, "RMI port successfully loaded.");
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            RmiServer rmiServer = new RmiServer(port, room); //to handle more matches, here a MatchHandler should be passed.
            registry.bind("RmiServer", rmiServer);
        } catch (Exception e) {
            SagradaLogger.log(Level.SEVERE, "Rmi server exception");
        }
        SagradaLogger.log(Level.INFO,"Rmi Server ready on port " + port + ", waiting for connections...");
    }

    public static void main(String[] args) {
        ServerMain serverMain = new ServerMain(new WaitingRoom());
        serverMain.startRmiServer();
    }
}
