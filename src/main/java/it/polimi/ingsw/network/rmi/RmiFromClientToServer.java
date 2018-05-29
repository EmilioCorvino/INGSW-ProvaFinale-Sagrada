package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.ServerMain;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.ClientImplementation;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.view.AViewMaster;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

/**
 * This class represents the server on the client side. View can call controller's methods
 * through this class using RMI protocol.
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
     * This constructor retrieves the remote object published on RMI registry by {@link ServerMain} and creates a
     * {@link RmiClient} for callback.
     * @param ip address to get the registry from.
     * @param view instance of the view the server can use.
     * @throws BrokenConnectionException when the connection drops.
     * @see RmiClient
     */
    public RmiFromClientToServer(String ip, AViewMaster view) throws BrokenConnectionException {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, ServerMain.PORT);
            this.rmiServer = (IRmiServer) registry.lookup("RmiServer");
            this.callBack = new RmiClient(new ClientImplementation(view));
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Cannot connect to server during connection assignment", e);
            throw new BrokenConnectionException();
        } catch (NotBoundException e) {
            SagradaLogger.log(Level.SEVERE, "There is no such interface in the registry", e);
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
            BrokenConnectionException {
        try {
            this.rmiServer.login(gameMode, playerName, this.callBack);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Connection to server has been lost during login", e);
            throw new BrokenConnectionException();
        }
    }

    /**
     *
     * @param idMap
     * @throws BrokenConnectionException
     */
    @Override
    public void windowPatternCardRequest(int idMap) throws BrokenConnectionException {
        try {
            this.rmiServer.windowPatternCardRequest(idMap);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Connection to server has been lost during window pattern card request", e);
            throw new BrokenConnectionException();
        }
    }

    /**
     * Lets the player log out from the game.
     * @param playerName player who wants to log out.
     * @throws BrokenConnectionException when the connection drops.
     */
    @Override
    public void exitGame(String playerName) throws BrokenConnectionException {
        //todo implement this.
    }

}
