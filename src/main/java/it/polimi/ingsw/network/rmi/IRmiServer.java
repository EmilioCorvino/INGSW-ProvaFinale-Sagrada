package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.exceptions.TooManyUsersException;
import it.polimi.ingsw.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.view.AViewMaster;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRmiServer extends Remote {

    /**
     * Lets the player log in the match.
     * @param gameMode can be either single player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     * @param callBack reference to the client, to be stored in the server.
     */
    void login(String gameMode, String playerName, IRmiClient callBack) throws UserNameAlreadyTakenException,
            TooManyUsersException, RemoteException;

    /**
     * Lets the player log out from the game.
     * @param playerName player who wants to log out.
     */
    void exitGame(String playerName) throws RemoteException;
}
