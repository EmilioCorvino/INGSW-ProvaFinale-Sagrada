package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.ServerMain;
import it.polimi.ingsw.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.IServer;
import it.polimi.ingsw.view.AViewMaster;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiFromClientToServer extends UnicastRemoteObject implements IFromClientToServer {

    private IServer rmiServer;

    public RmiFromClientToServer(String ip, AViewMaster viewMaster) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, ServerMain.PORT);
            rmiServer = (IServer) registry.lookup("IServer");
        } catch(NotBoundException e) {
            System.err.println("Rmi client exception: " + e.toString());
            e.printStackTrace();
        }

        RmiClient rmiClient = new RmiClient(viewMaster);
        try {
            RmiFromServerToClient fromServerToClient = new RmiFromServerToClient(rmiClient);
            rmiServer.establishConnection(fromServerToClient);
        } catch (Exception e) {
            System.err.println("Rmi client exception: " + e.toString());
            e.printStackTrace();
        }

    }

    @Override
    public void login(String playerName, String gameMode) throws UserNameAlreadyTakenException {
        try {
            rmiServer.login(playerName, gameMode);
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
