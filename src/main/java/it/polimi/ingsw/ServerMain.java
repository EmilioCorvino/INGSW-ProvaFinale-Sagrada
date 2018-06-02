package it.polimi.ingsw;

import it.polimi.ingsw.controller.WaitingRoom;
import it.polimi.ingsw.network.rmi.RmiServer;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * This class is used to start the server of the application. It creates an instance of both {@link RmiServer} and
 * the thread for socket connections.
 */
public class ServerMain {

    private WaitingRoom room; //Substitute this with a match handler to handle more matches.

    /**
     * Port that the application will be using.
     */
    public static final int PORT = 54242;

    private ServerMain(WaitingRoom room) {
        this.room = room;
    }

    private void startRmiServer() {
        try {
            Registry registry = LocateRegistry.createRegistry(PORT);
            RmiServer rmiServer = new RmiServer(PORT, room); //to handle more matches, here a MatchHandler should be passed.
            registry.bind("RmiServer", rmiServer);
        } catch (Exception e) {
            SagradaLogger.log(Level.SEVERE, "Rmi server exception", e);
        }
        SagradaLogger.log(Level.CONFIG,"Rmi Server ready, waiting for connections...");
    }

   /* private void startSocketServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Socket server exception", e);
        }
        boolean serverIsOn = true;
        SagradaLogger.log(Level.CONFIG, "Socket server ready, waiting for connections...");
        while (serverIsOn) {
            try {
                Socket clientSocket = serverSocket.accept();
                executor.submit(new )
            }
        }
    }*/

    public static void main(String[] args) {
        ServerMain serverMain = new ServerMain(new WaitingRoom());
        serverMain.startRmiServer();
        // todo serverMain.startSocketServer();
    }
}
