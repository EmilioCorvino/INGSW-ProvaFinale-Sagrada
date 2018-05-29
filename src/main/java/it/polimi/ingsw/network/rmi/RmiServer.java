package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.WaitingRoom;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.controller.ServerImplementation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class is the implementation of the remote interface {@link IRmiServer} and it's published in the RMI registry by
 * {@link it.polimi.ingsw.ServerMain}.
 */
public class RmiServer extends UnicastRemoteObject implements IRmiServer {

    /**
     * Effective instance of the server. It can call methods from {@link it.polimi.ingsw.controller.ControllerMaster}.
     */
    private transient ServerImplementation serverImplementation;

    /**
     * Room where the players wait for the match to start.
     */
    private transient WaitingRoom room;

    /**
     * This constructor exports this class on the RMI registry on the given port, and sets {@link ServerImplementation}.
     * @param port on which the object is exported.
     * @param room it can call methods from the controller.
     * @throws RemoteException when RMI connection drops.
     */
    public RmiServer(int port, WaitingRoom room) throws RemoteException {
        super(port);
        this.room = room;
        this.serverImplementation = new ServerImplementation(room);
    }

    /**
     * This method brings client's registration to the server and allows future server's replies by giving it a
     * remote reference to the client in an RMI callback fashion.
     * @param gameMode can be either single player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     * @param callBack reference to the client, to be stored in the server.
     * @throws UserNameAlreadyTakenException when a user with the same username is already logged in.
     * @throws TooManyUsersException when there already is the maximum number of players inside a game.
     * @see RmiFromServerToClient
     */
    @Override
    public void login(int gameMode, String playerName, IRmiClient callBack) throws UserNameAlreadyTakenException,
            TooManyUsersException {
        IFromServerToClient client = new RmiFromServerToClient(callBack);
        this.room.login(playerName, new Connection(client, serverImplementation), gameMode);
    }

    @Override
    public void windowPatternCardRequest(int idMap) {
        this.serverImplementation.windowPatternCardRequest(idMap);
    }

    @Override
    public void defaultMoveRequest() {
        this.serverImplementation.defaultMoveRequest();
    }

    @Override
    public void performMove(SetUpInformationUnit info) {
        this.serverImplementation.performMove(info);
    }

    /**
     * Lets the player log out from the game.
     * @param playerName player who wants to log out.
     */
    @Override
    public void exitGame(String playerName) {

    }

}
