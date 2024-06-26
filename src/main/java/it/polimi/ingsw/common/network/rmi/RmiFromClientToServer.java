package it.polimi.ingsw.common.network.rmi;

import it.polimi.ingsw.client.view.ClientImplementation;
import it.polimi.ingsw.client.view.IViewMaster;
import it.polimi.ingsw.common.network.Connection;
import it.polimi.ingsw.common.network.IFromClientToServer;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.utils.PropertyLoader;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.common.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.common.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.common.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.server.ServerMain;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;

/**
 * This class represents the server on the client side. View can call controller's methods
 * through this class using RMI protocol.
 * Methods in this class catch {@link RemoteException} by throwing a new {@link BrokenConnectionException}, so that
 * disconnection handling can be unified both for RMI and socket connections.
 * For methods documentation:
 * @see IFromClientToServer
 * @see it.polimi.ingsw.server.controller.ServerImplementation
 */
public class RmiFromClientToServer implements IFromClientToServer {

    /**
     * Server's remote interface. This object can call remotely {@link IRmiServer} methods.
     */
    private IRmiServer rmiServer;

    /**
     * Client's remote interface. This class uses it to register the client on the server, allowing RMI callback.
     */
    private IRmiClient callBack;

    /**
     * Connection established between client and server.
     */
    private Connection connection;

    /**
     * This constructor retrieves the remote object published on RMI registry by {@link ServerMain} and creates a
     * {@link RmiClient} for callback.
     * @param ip address to get the registry from.
     * @param view instance of the view the server can use.
     * @throws BrokenConnectionException when the connection drops.
     * @see RmiClient
     */
    public RmiFromClientToServer(String ip, IViewMaster view) throws BrokenConnectionException {
        int port = PropertyLoader.getPropertyLoader().getRmiPort();
        try {
            Registry registry = LocateRegistry.getRegistry(ip, port);
            this.rmiServer = (IRmiServer) registry.lookup("RmiServer");
            this.callBack = new RmiClient(new ClientImplementation(view));
        } catch (RemoteException e) {
            throw new BrokenConnectionException();
        } catch (NotBoundException e) {
            SagradaLogger.log(Level.SEVERE, "There is no such interface in the registry");
        }
    }

    /**
     * Lets the player log in the match.
     * @param gameMode can be either single player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     * @throws UserNameAlreadyTakenException when a user with the same username is already logged in.
     * @throws TooManyUsersException when there already is the maximum number of players inside a game.
     * @throws BrokenConnectionException when the connection drops.
     */
    @Override
    public void login(int gameMode, String playerName) throws UserNameAlreadyTakenException, TooManyUsersException,
            MatchAlreadyStartedException, BrokenConnectionException {
        this.connection = new Connection(playerName);
        try {
            this.rmiServer.login(gameMode, playerName, callBack, connection);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Connection to server has been lost during register");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void windowPatternCardRequest(int idMap) throws BrokenConnectionException {
        try {
            this.rmiServer.windowPatternCardRequest(idMap, connection);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Connection to server has been lost during window pattern card request");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void performDefaultMove(SetUpInformationUnit infoUnit) throws BrokenConnectionException {
        try {
            this.rmiServer.performDefaultMove(infoUnit, connection);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Connection to server has been lost during move execution");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void performToolCardMove(int slotID, List<SetUpInformationUnit> infoUnits) throws BrokenConnectionException {
        try {
            this.rmiServer.performToolCardMove(slotID, infoUnits, connection);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Connection to server has been lost during tool card move execution");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void performRestrictedPlacement(SetUpInformationUnit infoUnit) throws BrokenConnectionException {
        try {
            this.rmiServer.performRestrictedPlacement(infoUnit, connection);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Connection to the server has been lost during restricted placement execution");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void moveToNextTurn() throws BrokenConnectionException {
        try {
            this.rmiServer.moveToNextTurn(connection);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Connection to server has been lost while moving to the next round");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void startNewGameRequest() throws BrokenConnectionException {
        try {
            this.rmiServer.startNewGameRequest(connection);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Connection to the server has been lost while starting over a new" +
                    " match.");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void exitGame() throws BrokenConnectionException {
        try {
            this.rmiServer.exitGame(connection);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Connection to the server has been lost while logging out.");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void reconnect() throws BrokenConnectionException {
        try {
            this.rmiServer.reconnect(callBack, connection);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Connection to the server has been lost during reconnection.");
            throw new BrokenConnectionException();
        }
    }

}
