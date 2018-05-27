package it.polimi.ingsw.controller;

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
     * Controller of the match.
     */
    private ControllerMaster controller;

    /**
     * Maximum number of players that can connect to the match.
     */
    static final int MAX_PLAYERS = 4;

    /**
     * Lets the player log in the match.
     * @param gameMode can be either single player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     * @throws UserNameAlreadyTakenException when a user with the same username is already logged in.
     * @throws TooManyUsersException when there already is the maximum number of players inside a game.
     */
    @Override
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

    /**
     * Lets the player log out from the game.
     * @param playerName player who wants to log out.
     * @throws BrokenConnectionException when the connection drops.
     */
    @Override
    public void exitGame(String playerName) throws BrokenConnectionException {

    }

    /**
     * This method is used by {@link it.polimi.ingsw.network.rmi.RmiServer} and SocketServer //todo add link
     * to ensure the login can be done. It establishes the connection updating the connected players map with the
     * player's name and a reference to his client.
     * @param client reference to the player's client.
     * @param gameMode can either be single-player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     * @throws UserNameAlreadyTakenException when a user with the same username is already logged in.
     * @throws TooManyUsersException when there already is the maximum number of players inside a game.
     */
    public void establishConnection(IFromServerToClient client, int gameMode, String playerName) throws
            UserNameAlreadyTakenException, TooManyUsersException {

        this.login(gameMode, playerName);

        //If login doesn't throw any exception, the player is added to the map and the other players get notified.
        this.controller.getConnectedPlayers().put(playerName, client);
        System.out.println("Player " + playerName + " just joined!");
        ((StartGameManager)this.controller.getStartGameManager()).notifyWaitingPlayers(gameMode);

        checkNumberOfPlayers();
    }

    /**
     * This method checks if the minimum player for a multiplayer match is reached, then starts the timer.
     */
    public void checkNumberOfPlayers() {

        if(this.controller.getConnectedPlayers().size() == 2) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("timer is expired");
                }
            }, 5 * 1000);
        }
    }
}