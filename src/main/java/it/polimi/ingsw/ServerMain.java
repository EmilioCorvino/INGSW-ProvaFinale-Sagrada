package it.polimi.ingsw;

import it.polimi.ingsw.controller.WaitingRoom;
import it.polimi.ingsw.network.rmi.RmiServer;
import it.polimi.ingsw.network.socket.ServerEncoderDecoder;
import it.polimi.ingsw.network.socket.SocketConnectionHandler;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
     * Path of the file containing RMI port.
     */
    private static final String RMI_PORT_PATH = "./src/main/java/it/polimi/ingsw/utils/config/rmiPort";

    /**
     * Path of the file containing Socket port.
     */
    private static final String SOCKET_PORT_PATH = "./src/main/java/it/polimi/ingsw/utils/config/socketPort";
    /**
     * Port that the application running on RMI will be using.
     */
    private static int rmiPort;

    /**
     * Port that the application running on Socket will be using.
     */
    private static int socketPort;

    private ServerMain(WaitingRoom room) {
        this.room = room;
    }

    public static int getRmiPort() {
        return rmiPort;
    }

    public static int getSocketPort() {
        return socketPort;
    }

    private void startRmiServer() {
        try {
            Registry registry = LocateRegistry.createRegistry(ServerMain.rmiPort);
            RmiServer rmiServer = new RmiServer(ServerMain.rmiPort, room); //to handle more matches, here a MatchHandler should be passed.
            registry.bind("RmiServer", rmiServer);
        } catch (Exception e) {
            SagradaLogger.log(Level.SEVERE, "Rmi server exception", e);
        }
        SagradaLogger.log(Level.INFO,"Rmi Server ready on port " + rmiPort + ", waiting for connections...");
    }

    private void startSocketServer() {
        SocketConnectionHandler handler = new SocketConnectionHandler();
        handler.start();
    }

    public static void main(String[] args) {
        ServerMain serverMain = new ServerMain(new WaitingRoom());

        //RMI port loading.
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(RMI_PORT_PATH)))) {
            ServerMain.rmiPort = Integer.parseInt(reader.readLine());
            SagradaLogger.log(Level.CONFIG, "RMI port successfully loaded");
        } catch (FileNotFoundException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to find RMI port's file", e);
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to find RMI port on file", e);
        }

        //Socket port loading.
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(SOCKET_PORT_PATH)))) {
            ServerMain.socketPort = Integer.parseInt(reader.readLine());
            SagradaLogger.log(Level.CONFIG, "Socket port successfully loaded");
        } catch (FileNotFoundException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to find Socket port's file", e);
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to find Socket port on file", e);
        }

        serverMain.startRmiServer();
        //serverMain.startSocketServer();
    }
}
