package it.polimi.ingsw.network;

import java.util.List;

/**
 * This interface lists all possible methods the server can require from the client.
 * @see it.polimi.ingsw.network.rmi.RmiFromServerToClient
 * @see it.polimi.ingsw.network.socket.SocketFromServerToClient
 */
public interface IFromServerToClient {

    /**
     * Shows the waiting room to the player owning the client.
     * @param players names of the players already connected (including the player itself).
     */
    public void showRoom(List<String> players);

}
