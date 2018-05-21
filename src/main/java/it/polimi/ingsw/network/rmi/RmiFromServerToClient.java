package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.IClient;
import it.polimi.ingsw.network.IFromServerToClient;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public class RmiFromServerToClient implements IFromServerToClient, Serializable {

    private IClient rmiClient;

    public RmiFromServerToClient(RmiClient rmiClient) {
        this.rmiClient = rmiClient;
    }

    @Override
    public void showRoom(List<String> players) {
        try {
            rmiClient.showRoom(players);
        } catch (RemoteException e) {
            System.err.println("Impossible to launch method showRoom " + e.toString());
            e.printStackTrace();
        }
    }

}
