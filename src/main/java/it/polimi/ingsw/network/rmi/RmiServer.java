package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.ServerImplementation;
import it.polimi.ingsw.controller.WaitingRoom;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * This class is the implementation of the remote interface {@link IRmiServer} and it's published in the RMI registry by
 * {@link it.polimi.ingsw.ServerMain}.
 */
public class RmiServer extends UnicastRemoteObject implements IRmiServer {

    /**
     * Room where the players wait for the match to start.
     */
    private transient WaitingRoom room;

    /**
     * Implementation of the server.
     */
    private transient ServerImplementation serverImplementation;

    /**
     * This constructor exports this class on the RMI registry on the given port, and sets {@link ServerImplementation}.
     * @param port on which the object is exported.
     * @param room it can call methods from the controller.
     * @throws RemoteException when RMI connection drops.
     */
    public RmiServer(int port, WaitingRoom room) throws RemoteException {
        super(port);
        this.room = room;
        this.serverImplementation = new ServerImplementation();
    }

    /**
     * This method brings client's registration to the server and allows future server's replies by giving it a
     * remote reference to the client in an RMI callback fashion.
     * @param gameMode can be either single player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     * @param callBack reference to the client, to be stored in the server.
     * @param connection established between server and client.
     * @throws UserNameAlreadyTakenException when a user with the same username is already logged in.
     * @throws TooManyUsersException when there already is the maximum number of players inside a game.
     * @see RmiFromServerToClient
     */
    @Override
    public void login(int gameMode, String playerName, IRmiClient callBack, Connection connection)
            throws UserNameAlreadyTakenException, TooManyUsersException, MatchAlreadyStartedException {
        IFromServerToClient client = new RmiFromServerToClient(callBack);
        connection.setClient(client);
        connection.setServer(this.serverImplementation);
        this.serverImplementation.register(gameMode, playerName, room, connection);
    }

    @Override
    public void windowPatternCardRequest(int idMap, Connection connection) {
        synchronized (this) {
            this.serverImplementation.getConnectionsQueue().add(connection);
            this.serverImplementation.windowPatternCardRequest(idMap);
        }
    }

    @Override
    public void performDefaultMove(SetUpInformationUnit info, Connection connection) {
        synchronized (this) {
            this.serverImplementation.getConnectionsQueue().add(connection);
            this.serverImplementation.performDefaultMove(info);
        }
    }

    @Override
    public void performToolCardMove(int slotID, List<SetUpInformationUnit> infoUnits, Connection connection) throws RemoteException {
        synchronized (this) {
            this.serverImplementation.getConnectionsQueue().add(connection);
            this.serverImplementation.performToolCardMove(slotID, infoUnits);
        }
    }

    @Override
    public void performRestrictedPlacement(SetUpInformationUnit infoUnit, Connection connection) throws RemoteException {
        synchronized (this) {
            this.serverImplementation.getConnectionsQueue().add(connection);
            this.serverImplementation.performRestrictedPlacement(infoUnit);
        }
    }

    @Override
    public void moveToNextTurn(Connection connection) {
        synchronized (this) {
            this.serverImplementation.getConnectionsQueue().add(connection);
            this.serverImplementation.moveToNextTurn();
        }
    }

    @Override
    public void startNewGameRequest(Connection connection) {
        synchronized (this) {
            this.serverImplementation.getConnectionsQueue().add(connection);
            this.serverImplementation.startNewGameRequest();
        }
    }

    @Override
    public void exitGame(Connection connection) {
        synchronized (this) {
            this.serverImplementation.getConnectionsQueue().add(connection);
            this.serverImplementation.exitGame();
        }
    }

    @Override
    public void reconnect(IRmiClient callBack, Connection connection) {
        IFromServerToClient client = new RmiFromServerToClient(callBack);
        connection.setClient(client);
        connection.setServer(this.serverImplementation);
        synchronized (this) {
            this.serverImplementation.getConnectionsQueue().add(connection);
            this.serverImplementation.reconnect();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
