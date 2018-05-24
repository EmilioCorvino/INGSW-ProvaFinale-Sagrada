package it.polimi.ingsw.network;

import it.polimi.ingsw.exceptions.BrokenConnectionException;
import it.polimi.ingsw.exceptions.TooManyUsersException;
import it.polimi.ingsw.exceptions.UserNameAlreadyTakenException;

/**
 * This interface lists all the methods the client can require from the server.
 */
public interface IFromClientToServer {

    /**
     * Lets the player log in the match.
     * @param gameMode can be either single player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     */
    void login(String gameMode, String playerName) throws UserNameAlreadyTakenException,
            TooManyUsersException, BrokenConnectionException;

    /**
     * Lets the player log out from the game.
     * @param playerName player who wants to log out.
     */
    void exitGame(String playerName) throws BrokenConnectionException;
}
