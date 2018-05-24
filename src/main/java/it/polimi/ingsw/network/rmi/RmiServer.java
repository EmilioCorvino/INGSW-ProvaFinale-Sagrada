package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.exceptions.TooManyUsersException;
import it.polimi.ingsw.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.controller.ServerImplementation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer extends UnicastRemoteObject implements IRmiServer {

    private ServerImplementation server;

    public RmiServer(int port, ServerImplementation server) throws RemoteException {
        super(port);
        this.server = server;
    }

    @Override
    public void login(String gameMode, String playerName, IRmiClient callBack) throws UserNameAlreadyTakenException,
            TooManyUsersException, RemoteException {
        IFromServerToClient client = new RmiFromServerToClient(callBack);
        this.server.establishConnection(client, gameMode, playerName);
    }

    @Override
    public void exitGame(String playerName) throws RemoteException {

    }
}
