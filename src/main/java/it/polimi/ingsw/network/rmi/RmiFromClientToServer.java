package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.ClientMain;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.IServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiFromClientToServer implements IFromClientToServer {

    IServer rmiServer;

    public RmiFromClientToServer(String ip) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, ClientMain.PORT);
            rmiServer = (IServer) registry.lookup("IServer");
        } catch(NotBoundException e) {
            System.err.println("Rmi client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void login(String playerName, String ip, String gameMode) {
        try {
            rmiServer.login(playerName, ip, gameMode);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exitGame(String playerName) {
        try {
            rmiServer.exitGame(playerName);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }
}
