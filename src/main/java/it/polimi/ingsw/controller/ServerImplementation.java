package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.managers.EndGameManager;
import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.managers.StartGameManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;


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

    public ControllerMaster getController() {
        return controller;
    }

    public void setController(ControllerMaster controller) {
        this.controller = controller;
    }

    public Queue<Connection> getConnectionsQueue() {
        return connectionsQueue;
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
     * @throws UserNameAlreadyTakenException when the player tries to register with a username already taken.
     * @throws TooManyUsersException when the player tries to connect when the {@link WaitingRoom} is full.
     */
    public void register(int gameMode, String playerName, WaitingRoom room, Connection connection) throws
            UserNameAlreadyTakenException, TooManyUsersException, MatchAlreadyStartedException {
        room.joinRoom(playerName, connection, gameMode);
        SagradaLogger.log(Level.INFO, "Connection accepted! Player " + playerName + " just joined.");
    }

    /**
     *
     * @param idMap
     */
    @Override
    public void windowPatternCardRequest(int idMap) {
        String username = connectionsQueue.remove().getUserName();
        StartGameManager startGameManager = (StartGameManager)controller.getStartGameManager();
        startGameManager.wpToSet(username, idMap);
    }

    /**
     * This method tells the proper game manager of the controller to perform the move request.
     * @param infoUnit the coordinates of the source container.
     */
    @Override
    public void performDefaultMove(SetUpInformationUnit infoUnit) {
        String userName = connectionsQueue.remove().getUserName();
        GamePlayManager gamePlayManager = (GamePlayManager)controller.getGamePlayManager();
        gamePlayManager.performDefaultMove(infoUnit, userName);
    }

    @Override
    public void performToolCardMove(int slotID, List<SetUpInformationUnit> infoUnits) {
        String userName = connectionsQueue.remove().getUserName();
        GamePlayManager gamePlayManager = (GamePlayManager)controller.getGamePlayManager();
        gamePlayManager.performToolCardMove(slotID, infoUnits, userName);
    }

    @Override
    public void performRestrictedPlacement(SetUpInformationUnit infoUnit) {

    }

    /**
     * This method tells the proper game manager of the controller to move to the next turn.
     */
    @Override
    public void moveToNextTurn() {
        String userName = connectionsQueue.remove().getUserName();
        GamePlayManager gamePlayManager = (GamePlayManager)controller.getGamePlayManager();
        gamePlayManager.moveToNextTurn(userName);
    }

    @Override
    public void startNewGameRequest() {
        String userName = connectionsQueue.remove().getUserName();
        EndGameManager endGameManager = (EndGameManager)controller.getEndGameManager();
        endGameManager.newGame(userName);
    }

    /**
     * Lets the player log out from the game.
     */
    @Override
    public void exitGame() {
        String userName = connectionsQueue.remove().getUserName();
        if(this.getController().getGameState().isMatchOver()) {
            EndGameManager endGameManager = (EndGameManager)controller.getEndGameManager();
            endGameManager.exitGame(userName);
        } else {
            //todo suspend player
        }
    }



}