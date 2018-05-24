package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.exceptions.BrokenConnectionException;
import it.polimi.ingsw.network.IFromServerToClient;

import java.rmi.RemoteException;
import java.util.List;

public class RmiFromServerToClient implements IFromServerToClient {

    private IRmiClient rmiClient;

    public RmiFromServerToClient(IRmiClient rmiClient) {
        this.rmiClient = rmiClient;
    }

    @Override
    public void showRoom(List<String> players) throws BrokenConnectionException {
        try {
            this.rmiClient.showRoom(players);
        } catch (RemoteException e) {
            System.err.println("Impossible to show clients the updated room: " + e.toString());
            throw new BrokenConnectionException();
        }
    }
}
