package it.polimi.ingsw.network;

import java.rmi.Remote;

/**
 * This remote interface lists all possible methods the client needs to launch upon a server request.
 * @see it.polimi.ingsw.network.rmi.RmiClient
 * @see it.polimi.ingsw.network.socket.SocketClient
 */
public interface IClient extends Remote {

    /**
     * Shows the waiting room to the player owning the client.
     * @param players names of the players already connected (including the player itself).
     */
    public void showRoom(String[] players);
}
