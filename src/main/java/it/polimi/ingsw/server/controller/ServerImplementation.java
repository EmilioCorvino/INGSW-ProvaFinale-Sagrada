package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.common.network.Connection;
import it.polimi.ingsw.common.network.IFromClientToServer;
import it.polimi.ingsw.common.network.IFromServerToClient;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.common.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.common.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.common.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.server.controller.managers.EndGameManager;
import it.polimi.ingsw.server.controller.managers.GamePlayManager;
import it.polimi.ingsw.server.controller.managers.StartGameManager;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;


/**
 * This class manages server related operation that aren't strictly related to the game. Moreover, it handles the
 * messages arriving from the client, calling methods from {@link ControllerMaster}.
 * For methods that are not documented here:
 * @see IFromClientToServer
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
     * Method used to log in.
     * @param gameMode can be either single-player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     */
    @Override
    public void login(int gameMode, String playerName) {
        //useful for socket
    }

    /**
     * Registers the player to the starting match, adding him to the {@link WaitingRoom}.
     * @param gameMode can be either multi-player or single-player.
     * @param playerName name chosen by the player.
     * @param room waiting room where players waiting for the match to start are gathered.
     * @param connection established between client and server.
     * @throws UserNameAlreadyTakenException when the player tries to register with a username already taken.
     * @throws TooManyUsersException when the player tries to connect when the {@link WaitingRoom} is full.
     * @throws MatchAlreadyStartedException when the match is started when the player tries to register.
     */
    public void register(int gameMode, String playerName, WaitingRoom room, Connection connection) throws
            UserNameAlreadyTakenException, TooManyUsersException, MatchAlreadyStartedException {
        room.joinRoom(playerName, connection, gameMode);
        SagradaLogger.log(Level.INFO, "Connection accepted! Player " + playerName + " just joined.");
    }

    @Override
    public void windowPatternCardRequest(int idMap) {
        String username = connectionsQueue.remove().getUserName();
        StartGameManager startGameManager = controller.getStartGameManager();
        startGameManager.wpToSet(username, idMap);
    }

    @Override
    public void performDefaultMove(SetUpInformationUnit infoUnit) {
        String userName = connectionsQueue.remove().getUserName();
        GamePlayManager gamePlayManager = controller.getGamePlayManager();
        gamePlayManager.performDefaultMove(infoUnit, userName);
    }

    @Override
    public void performToolCardMove(int slotID, List<SetUpInformationUnit> infoUnits) {
        String userName = connectionsQueue.remove().getUserName();
        GamePlayManager gamePlayManager = controller.getGamePlayManager();
        gamePlayManager.performToolCardMove(slotID, infoUnits, userName);
    }

    @Override
    public void performRestrictedPlacement(SetUpInformationUnit infoUnit) {
        String userName = connectionsQueue.remove().getUserName();
        GamePlayManager gamePlayManager = controller.getGamePlayManager();
        gamePlayManager.performRestrictedPlacement(infoUnit, userName);
    }

    @Override
    public void moveToNextTurn() {
        String userName = connectionsQueue.remove().getUserName();
        GamePlayManager gamePlayManager = controller.getGamePlayManager();
        gamePlayManager.moveToNextTurn(userName);
    }

    @Override
    public void startNewGameRequest() {
        String userName = connectionsQueue.remove().getUserName();
        EndGameManager endGameManager = controller.getEndGameManager();
        endGameManager.newGame(userName);
    }

    /**
     * Lets the player log out from the game. This method has different effects depending on the game phase.
     */
    @Override
    public void exitGame() {
        String username = connectionsQueue.remove().getUserName();
        IFromServerToClient client = getController().getConnectedPlayers().get(username).getClient();

        //End Game.
        if (this.getController().getGameState().isMatchOver()) {
            EndGameManager endGameManager = this.controller.getEndGameManager();
            endGameManager.exitGame(username);
            return;
        }

        //Game Play.
        if (this.getController().getStartGameManager().isMatchRunning()) {
            this.controller.suspendPlayer(username, true);
            try {
                client.forceLogOut();
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.WARNING, username + "is unreachable while forcing him " +
                        "to log out");
            }
            return;
        }

        //Start Game
        StartGameManager startGameManager = this.controller.getStartGameManager();
        startGameManager.exitGame(username);
        try {
            client.forceLogOut();
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.WARNING, username + "is unreachable while forcing him " +
                    "to log out");
        }
    }

    @Override
    public void reconnect() {
        Connection connection = connectionsQueue.remove();
        this.controller.reconnectPlayer(connection.getUserName(), connection);
    }
}