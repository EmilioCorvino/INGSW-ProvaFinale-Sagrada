package it.polimi.ingsw.network;

import it.polimi.ingsw.exceptions.TooManyUsersException;
import it.polimi.ingsw.exceptions.UserNameAlreadyTakenException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface lists all the methods the client can require from the server.
 * @see it.polimi.ingsw.network.rmi.RmiFromClientToServer
 * @see it.polimi.ingsw.network.socket.SocketFromClientToServer
 */
public interface IFromClientToServer extends Remote {

    /**
     * Lets the player log in the match.
     * @param gameMode can be either single player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     */
    public void login(String gameMode, String playerName) throws RemoteException, UserNameAlreadyTakenException,
            TooManyUsersException;

    /**
     * Lets the player log out from the game.
     * @param playerName player who wants to log out.
     */
    public void exitGame(String playerName) throws RemoteException;
}
