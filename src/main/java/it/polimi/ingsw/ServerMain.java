package it.polimi.ingsw;

import it.polimi.ingsw.controller.ServerImplementation;
import it.polimi.ingsw.network.rmi.RmiServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {

    public static final int PORT = 54242;
    private final ServerImplementation server;

    private ServerMain(ServerImplementation server) {
        this.server = server;
    }

    public static void main(String[] args) {
        ServerMain serverMain = new ServerMain(new ServerImplementation());
        //Sets up Rmi server and client remote interfaces.
        try {
            Registry registry = LocateRegistry.createRegistry(PORT);
            RmiServer rmiServer = new RmiServer(PORT, serverMain.server);
            registry.bind("RmiServer", rmiServer);
        } catch (Exception e) {
            System.err.println("Rmi server exception: " + e.toString());
            e.printStackTrace();
        }
        System.err.println("Rmi Server ready, waiting for connections...");


        //todo setup socket server
    }
}
