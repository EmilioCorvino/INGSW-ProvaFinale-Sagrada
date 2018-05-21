package it.polimi.ingsw;

import it.polimi.ingsw.network.IServer;
import it.polimi.ingsw.network.rmi.RmiServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {

    public static final int PORT = 54242;

    public static void main(String[] args) {
        //Sets up Rmi server.
        try {
            RmiServer rmiServer = new RmiServer();
            IServer stub = (IServer) UnicastRemoteObject.exportObject(rmiServer, PORT);
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.bind("IServer", stub);
        } catch (Exception e) {
            System.err.println("Rmi server exception: " + e.toString());
            e.printStackTrace();
        }

        //todo setup socket server
    }
}
