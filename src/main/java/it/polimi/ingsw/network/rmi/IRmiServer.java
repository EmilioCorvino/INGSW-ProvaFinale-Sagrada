package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface presents the same methods as {@link it.polimi.ingsw.network.IFromClientToServer}, but suited for a RMI
 * connection: in fact it extends remote and its methods throw {@link RemoteException}.
 * Moreover, each of these methods takes also a Connection in input, to identify the player, for security reasons.
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

    void performDefaultMove(SetUpInformationUnit info, Connection connection) throws RemoteException;

    void performToolCardMove(int slotID, List<SetUpInformationUnit> infoUnits, Connection connection) throws RemoteException;

    void performRestrictedPlacement(SetUpInformationUnit infoUnit, Connection connection) throws RemoteException;

    void moveToNextTurn(Connection connection) throws RemoteException;

    void startNewGameRequest(Connection connection) throws RemoteException;

    void exitGame(Connection connection) throws RemoteException;

    void reconnect(IRmiClient callBack, Connection connection) throws RemoteException;
}
