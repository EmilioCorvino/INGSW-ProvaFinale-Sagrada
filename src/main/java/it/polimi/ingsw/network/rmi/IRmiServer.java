package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface presents the same methods as {@link it.polimi.ingsw.network.IFromClientToServer}, but suited for a RMI
 * connection: in fact it extends remote and its methods throw {@link RemoteException}.
 */
public interface IRmiServer extends Remote {

    /**
     * Lets the player log in the match.
     * @param gameMode can be either single player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     * @param callBack reference to the client, to be stored in the server.
     * @param connection established between client and server.
     */
    void login(int gameMode, String playerName, IRmiClient callBack, Connection connection) throws
            UserNameAlreadyTakenException, TooManyUsersException, MatchAlreadyStartedException, RemoteException;

    void windowPatternCardRequest(int idMap, Connection connection) throws RemoteException;

    void performMove(SetUpInformationUnit info, Connection connection) throws RemoteException;

    void moveToNextTurn(Connection connection) throws RemoteException;

    /**
     * Lets the player log out from the game.
     * @param connection established between the client and the server.
     * @see Connection
     */
    void exitGame(Connection connection) throws RemoteException;

}
