package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;

/**
 * This class represents the client on the server side. Controller can call view's methods
 * through this class using RMI protocol.
 */
public class RmiFromServerToClient implements IFromServerToClient {

    /**
     * Client's remote interface. This object can call remotely {@link IRmiClient} methods.
     */
    private IRmiClient rmiClient;

    RmiFromServerToClient(IRmiClient rmiClient) {
        this.rmiClient = rmiClient;
    }

    /**
     * Shows the waiting room to the player owning the client.
     * @param players names of the players already connected (including the player itself).
     * @throws BrokenConnectionException when the connection drops.
     */
    @Override
    public void showRoom(List<String> players) throws BrokenConnectionException {
        try {
            this.rmiClient.showRoom(players);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the updated room", e);
            throw new BrokenConnectionException();
        }
    }

    /**
     *
     *
     * @param listWp
     * @throws BrokenConnectionException
     */
    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) throws
            BrokenConnectionException {
        try {
            this.rmiClient.showMapsToChoose(listWp);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the window pattern cards", e);
            throw new BrokenConnectionException();
        }
    }

}
