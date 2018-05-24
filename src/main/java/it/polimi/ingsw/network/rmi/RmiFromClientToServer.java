package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.ServerMain;
import it.polimi.ingsw.exceptions.BrokenConnectionException;
import it.polimi.ingsw.exceptions.TooManyUsersException;
import it.polimi.ingsw.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.view.ClientImplementation;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.view.AViewMaster;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiFromClientToServer implements IFromClientToServer {

    private IRmiServer rmiServer;

    private IRmiClient callBack;

    public RmiFromClientToServer(String ip, AViewMaster view) throws BrokenConnectionException {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, ServerMain.PORT);
            this.rmiServer = (IRmiServer) registry.lookup("RmiServer");
            this.callBack = new RmiClient(new ClientImplementation(view));
        } catch (RemoteException e) {
            System.err.println("Cannot connect to server during connection assignment: " + e.toString());
            throw new BrokenConnectionException();
        } catch (NotBoundException e) {
            System.err.println("There is no such interface in the registry: " + e.toString());
        }

    }

    @Override
    public void login(String gameMode, String playerName) throws UserNameAlreadyTakenException, TooManyUsersException,
            BrokenConnectionException {
        try {
            rmiServer.login(gameMode, playerName, this.callBack);
        } catch (RemoteException e) {
            System.err.println("Connection to server has been lost: " + e.toString());
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void exitGame(String playerName) throws BrokenConnectionException {

    }
}
