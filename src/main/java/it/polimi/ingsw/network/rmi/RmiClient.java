package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.view.ClientImplementation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RmiClient extends UnicastRemoteObject implements IRmiClient {

    private ClientImplementation client;

    protected RmiClient(ClientImplementation client) throws RemoteException {
        this.client = client;
    }

    @Override
    public void showRoom(List<String> players) throws RemoteException {
        this.client.showRoom(players);
    }
}
