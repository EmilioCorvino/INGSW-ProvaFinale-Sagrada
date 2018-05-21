package it.polimi.ingsw;

import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.network.rmi.RmiServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {

    public static final int PORT = 54242;
    //private final ControllerMaster controllerMaster;

    /*private ServerMain(ControllerMaster controllerMaster) {
        this.controllerMaster = controllerMaster;
    }*/

    public static void main(String[] args) {
        //ServerMain serverMain = new ServerMain(new ControllerMaster());
        //Sets up Rmi server and client remote interfaces.
        try {
            RmiServer rmiServer = new RmiServer(new ControllerMaster());
            IServer stub = (IServer) UnicastRemoteObject.exportObject(rmiServer, PORT);
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.bind("IServer", stub);
        } catch (Exception e) {
            System.err.println("Rmi server exception: " + e.toString());
            e.printStackTrace();
        }
        System.err.println("Rmi Server ready, waiting for connections...");

        //todo setup socket server
    }
}
