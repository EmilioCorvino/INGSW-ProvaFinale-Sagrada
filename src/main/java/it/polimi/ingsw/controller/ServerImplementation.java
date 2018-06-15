package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.managers.StartGameManager;
import it.polimi.ingsw.controller.simplified_view.InformationUnit;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * This class manages server related operation that aren't strictly related to the game. Moreover, it handles the
 * messages arriving from the client, calling methods from {@link ControllerMaster}.
 */
public class ServerImplementation implements IFromClientToServer {

    /**
     * Controller of the match.
     */
    private ControllerMaster controller;

    /**
     * Various connections related to clients requests arrive in this queue, and they get resolved with a FIFO policy.
     */
    private Queue<Connection> connectionsQueue;

    public ServerImplementation() {
        this.connectionsQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * This method tells the proper game manager of the controller to perform the move request.
     * @param sourceInfo the coordinates of the source container.
     */
    @Override
    public void performDefaultMove(SetUpInformationUnit sourceInfo) {
        String userName = connectionsQueue.poll().getUserName();
        GamePlayManager gamePlayManager = (GamePlayManager)controller.getGamePlayManager();
        gamePlayManager.performDefaultMove(sourceInfo, userName);
    }

    /**
     * This method tells the proper game manager of the controller to move to the next turn.
     */
    @Override
    public void moveToNextTurn() {
        String userName = connectionsQueue.poll().getUserName();
        GamePlayManager gamePlayManager = (GamePlayManager)controller.getGamePlayManager();
        gamePlayManager.moveToNextTurn(userName);
    }

    /**
     * //todo not needed anymore
     * @param toolIndex
     */
    public void toolCardMoveRequest(int toolIndex) {

    }

    /*
    public void register(int gameMode, String playerName) throws UserNameAlreadyTakenException, TooManyUsersException {
        if(((StartGameManager)this.controller.getStartGameManager()).isFull()) {
            System.out.println("The server reached the maximum number of players!");
            throw new TooManyUsersException();
        }
        if(!((StartGameManager)this.controller.getStartGameManager()).checkLogin(playerName)) {
            System.out.println("Username already taken");
            throw new UserNameAlreadyTakenException();
        }
    }
    */

    public IControllerMaster getController() {
        return controller;
    }

    /**
     *
     * @param gameMode can be either single-player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     */
    @Override
    public void login(int gameMode, String playerName) {
        //figure how to use this
    }

    /**
     * Registers the player to the starting match, adding him to the {@link WaitingRoom}.
     * @param gameMode can be either multi-player or single-player.
     * @param playerName name chosen by the player.
     * @param room waiting room where players waiting for the match to start are gathered.
     * @param connection established between client and server.
     * @throws UserNameAlreadyTakenException
     * @throws TooManyUsersException
     */
    public void register(int gameMode, String playerName, WaitingRoom room, Connection connection) throws
            UserNameAlreadyTakenException, TooManyUsersException {
        room.joinRoom(playerName, connection, gameMode);
        //this.login(gameMode, playerName);
    }

    /**
     *
     * @param idMap
     */
    @Override
    public void windowPatternCardRequest(int idMap) {
        String username = connectionsQueue.poll().getUserName();
        StartGameManager startGameManager = (StartGameManager)controller.getStartGameManager();
        startGameManager.wpToSet(username, idMap);
    }

    /**
     * Lets the player log out from the game.
     * @param playerName player who wants to log out.
     */
    @Override
    public void exitGame(String playerName) {

    }

    public void setController(ControllerMaster controller) {
        this.controller = controller;
    }

    public Queue<Connection> getConnectionsQueue() {
        return connectionsQueue;
    }
}