package it.polimi.ingsw.network;

import it.polimi.ingsw.exceptions.BrokenConnectionException;

import java.util.List;

/**
 * This interface lists all possible methods the server can require from the client.
 */
public interface IFromServerToClient {

    /**
     * Shows the waiting room to the player owning the client.
     * @param players names of the players already connected (including the player itself).
     */
    void showRoom(List<String> players) throws BrokenConnectionException;

}
