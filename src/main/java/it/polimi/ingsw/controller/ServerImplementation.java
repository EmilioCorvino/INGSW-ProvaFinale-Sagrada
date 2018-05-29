package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.simplified_view.InformationUnit;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedDraftpool;
import it.polimi.ingsw.model.move.IMove;
import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.network.PlayerColor;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.network.IFromServerToClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class manages server related operation that aren't strictly related to the game. Moreover, it handles the
 * messages arriving from the client, calling methods from {@link ControllerMaster}.
 */
public class ServerImplementation implements IFromClientToServer {

    /**
     * Color identifying the player.
     */
    private PlayerColor playerColor;

    /**
     *
     */
    private String username;

    /**
     * Controller of the match.
     */
    private ControllerMaster controller;

    /**
     *
     */
    private final WaitingRoom waitingRoom;


    public ServerImplementation(WaitingRoom waitingRoom) {
        this.waitingRoom = waitingRoom;
    }

    /**
     * This method manages the request of the user to make a default move.
     */
    public void defaultMoveRequest() {
        GamePlayManager gamePlayManager = (GamePlayManager)controller.getGamePlayManager();
        if(gamePlayManager.checkCurrentPlayer(gamePlayManager.getPlayerColorList().get(gamePlayManager.getCurrentPlayer()))) {
            //tell the player that parameters are needed, the controller will send proper objects
            gamePlayManager.givePlayerObjectTofill();
        } else {
            // tell the user that it's not his/her turn.
        }

    }

    /**
     * This method tells the proper game manager of the controller to perform the move request.
     * @param sourceInfo the coordinates of the source container.
     */
    public void performMove(SetUpInformationUnit sourceInfo) {
        GamePlayManager gamePlayManager = (GamePlayManager)controller.getGamePlayManager();
        gamePlayManager.performMove(sourceInfo);

    }

    /**
     *
     * @param toolIndex
     */
    public void toolCardMoveRequest(int toolIndex) {

    }
    

    /**
     *
     * @param destination
     */
    public void placeDie(InformationUnit destination) {

    }


    /**
     *
     */
    public void endTurn() {

    }




    /*
    public void login(int gameMode, String playerName) throws UserNameAlreadyTakenException, TooManyUsersException {
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


    /*
     * Lets the player log out from the game.
     * @param playerName player who wants to log out.
     * @throws BrokenConnectionException when the connection drops.
     */
    /*
    @Override
    public void exitGame(String playerName) throws BrokenConnectionException {

    }
    */

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public IControllerMaster getController() {
        return controller;
    }

    public void setController(ControllerMaster controller) {
        this.controller = controller;
    }

    @Override
    public void login(int gameMode, String playerName) throws UserNameAlreadyTakenException, TooManyUsersException, BrokenConnectionException {
        Connection playerConn = this.waitingRoom.getPlayersRoom().get(playerName);
        this.waitingRoom.login(playerName, playerConn, gameMode);
    }

    @Override
    public void windowPatternCardRequest(int idMap) throws BrokenConnectionException {
        StartGameManager startGameManager = (StartGameManager)controller.getStartGameManager();
        startGameManager.wpToSet(this.playerColor, idMap);

    }




    @Override
    public void exitGame(String playerName) throws BrokenConnectionException {

    }
}