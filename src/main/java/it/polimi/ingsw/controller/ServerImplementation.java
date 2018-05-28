package it.polimi.ingsw.controller;

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
public class ServerImplementation  {

    /**
     * Color identifying the player.
     */
    private PlayerColor playerColor;

    /**
     * Controller of the match.
     */
    private  IControllerMaster controller;

    /**
     * Maximum number of players that can connect to the match.
     */
    static final int MAX_PLAYERS = 4;




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

    public void setController(IControllerMaster controller) {
        this.controller = controller;
    }
}