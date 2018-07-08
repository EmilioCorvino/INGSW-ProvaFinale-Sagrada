package it.polimi.ingsw.common.network.rmi;

import it.polimi.ingsw.common.network.Connection;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.common.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.common.utils.exceptions.UserNameAlreadyTakenException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface presents the same methods as {@link it.polimi.ingsw.common.network.IFromClientToServer}, but suited for a RMI
 * connection: in fact it extends remote and its methods throw {@link RemoteException}.
 * Moreover, each of these methods takes also a Connection in input, to identify the player, for security reasons.
 * @see it.polimi.ingsw.common.network.IFromClientToServer for documentation.
 */
public interface IRmiServer extends Remote {

    /**
     * Lets the player log in the match.
     * @param gameMode can be either single player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     * @param callBack reference to the client, to be stored in the server.
     * @param connection established between client and server.
     * @throws UserNameAlreadyTakenException when a user with the same username is already logged in.
     * @throws TooManyUsersException when there already is the maximum number of players inside a game.
     * @throws MatchAlreadyStartedException when the match is already started at the moment the request arrives.
     * @throws RemoteException when the connection drops.
     */
    void login(int gameMode, String playerName, IRmiClient callBack, Connection connection) throws
            UserNameAlreadyTakenException, TooManyUsersException, MatchAlreadyStartedException, RemoteException;

    void windowPatternCardRequest(int idMap, Connection connection) throws RemoteException;

    void performDefaultMove(SetUpInformationUnit info, Connection connection) throws RemoteException;

    void performToolCardMove(int slotID, List<SetUpInformationUnit> infoUnits, Connection connection) throws RemoteException;

    void performRestrictedPlacement(SetUpInformationUnit infoUnit, Connection connection) throws RemoteException;

    void moveToNextTurn(Connection connection) throws RemoteException;

    void startNewGameRequest(Connection connection) throws RemoteException;

    void exitGame(Connection connection) throws RemoteException;

    void reconnect(IRmiClient callBack, Connection connection) throws RemoteException;
}
