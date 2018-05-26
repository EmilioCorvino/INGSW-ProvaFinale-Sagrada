package it.polimi.ingsw.network;

import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;

/**
 * This interface lists all the methods the client can require from the server. Methods in this interface are agnostic
 * towards the protocol used for networking. They can be used both for RMI and Socket.
 * @see it.polimi.ingsw.network.rmi.RmiFromClientToServer
 * @see it.polimi.ingsw.network.socket.SocketFromClientToServer
 */
public interface IFromClientToServer {

    /**
     * Lets the player log in the match.
     * @param gameMode can be either single-player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     * @throws UserNameAlreadyTakenException when a user with the same username is already logged in.
     * @throws TooManyUsersException when there already is the maximum number of players inside a game.
     * @throws BrokenConnectionException when the connection drops.
     */
    void login(String gameMode, String playerName) throws UserNameAlreadyTakenException,
            TooManyUsersException, BrokenConnectionException;

    /**
     * Lets the player log out from the game.
     * @param playerName player who wants to log out.
     * @throws BrokenConnectionException when the connection drops.
     */
    void exitGame(String playerName) throws BrokenConnectionException;
}
