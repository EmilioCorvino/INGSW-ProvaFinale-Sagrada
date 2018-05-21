package it.polimi.ingsw;

import it.polimi.ingsw.network.IFromServerToClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This remote interface lists all possible methods the server needs to launch upon a client request.
 * @see it.polimi.ingsw.network.rmi.RmiServer
 * @see it.polimi.ingsw.network.socket.SocketServer
 */
public interface IServer extends Remote {

    public void establishConnection(IFromServerToClient fromServerToClient) throws RemoteException;

    /**
     * Lets the player log in the match.
     * @param playerName name the player chooses for himself in the application.
     * @param gameMode can be either single player or multi-player.
     */
    public void login(String playerName, String gameMode) throws RemoteException;

    /**
     * Lets the player log out from the game.
     * @param playerName player who wants to log out.
     */
    public void exitGame(String playerName) throws RemoteException;
}
