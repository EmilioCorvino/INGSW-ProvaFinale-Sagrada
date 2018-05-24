package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.exceptions.BrokenConnectionException;
import it.polimi.ingsw.exceptions.TooManyUsersException;
import it.polimi.ingsw.exceptions.UserNameAlreadyTakenException;

public class ServerImplementation implements IFromClientToServer{

    private ControllerMaster controller;

    public static final int MAX_PLAYERS = 4;

    public ServerImplementation() {
        this.controller = new ControllerMaster();
    }

    @Override
    public void login(String gameMode, String playerName) throws UserNameAlreadyTakenException, TooManyUsersException {
        if(this.controller.getStartGameState().isFull()) {
            System.out.println("The server reached the maximum number of players!");
            throw new TooManyUsersException();
        }
        if(!this.controller.getStartGameState().checkLogin(playerName)) {
            System.out.println("Username already taken");
            throw new UserNameAlreadyTakenException();
        }
    }

    @Override
    public void exitGame(String playerName) throws BrokenConnectionException {

    }

    public void establishConnection(IFromServerToClient client, String gameMode, String playerName) throws
            UserNameAlreadyTakenException, TooManyUsersException {
        this.login(gameMode, playerName);
        //If notifyWaitingPlayers doesn't throw any exception, the player is added to the map and the other players get notified.
        this.controller.getConnectedPlayers().put(playerName, client);
        System.out.println("Player " + playerName + " just joined!");
        this.controller.getStartGameState().notifyWaitingPlayers(gameMode);
    }
}
