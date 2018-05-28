package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.network.PlayerColor;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;

import java.util.*;

/**
 * This class manages the waiting player before the match starts.
 */
public class WaitingRoom {

    /**
     * Maximum number of players that can connect to the match.
     */
    private static final int MAX_PLAYERS = 4;

    /**
     * Code for multi player match mode.
     */
    private static final int MULTIPLAYER_MODE = 1;

    /**
     * Code for single player match mode.
     */
    private static final int SINGLEPLAYER_MODE = 2;

    /**
     * The map for keeping in memory the player with its connection object.
     */
    private Map<String, Connection> playersRoom;

    /**
     *
     */
    boolean running;

    public WaitingRoom() {
        playersRoom = new HashMap<>();
        running = false;
    }

    /**
     * This method is responsible for the log in of a player.
     * @param username the player who wants to play.
     * @param connection the connection assigned to the player.
     * @param gameMode the type of match the players wants to play.
     * @throws UserNameAlreadyTakenException
     * @throws TooManyUsersException
     */
    public void login(String username, Connection connection, int gameMode) throws UserNameAlreadyTakenException, TooManyUsersException {

        if(gameMode == SINGLEPLAYER_MODE)
            startSingleMatch();
        else if(gameMode == MULTIPLAYER_MODE) {

            if (playersRoom.size() == MAX_PLAYERS)
                throw new TooManyUsersException();

            addPlayer(username, connection);
        }

        notifyWaitingPlayers();

        checkNumberOfPlayers();
    }

    /**
     * This method allows the connected clients of a multi player match to see other connecting players.
     */
    private void notifyWaitingPlayers() {

        List<String> listName = new ArrayList<>();

        playersRoom.entrySet().forEach( entry -> listName.add(entry.getKey()));

        playersRoom.entrySet().forEach(entry -> {
            try {
                entry.getValue().getClient().showRoom(listName);
            } catch (BrokenConnectionException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * This method checks if the minimum player for a multiplayer match is reached, then starts the timer.
     */
    private void checkNumberOfPlayers() {

        if(playersRoom.size() == 2) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("timer is expired");
                    startMultiPlayerMatch();
                }
            }, 5 * 1000);
        }
    }

    /**
     * This method adds a player to the waiting room.
     * @param username the name of the player to add.
     * @param connection the connection of the player to add.
     * @throws UserNameAlreadyTakenException
     */
    public synchronized void addPlayer(String username, Connection connection) throws UserNameAlreadyTakenException {

        for (String name : playersRoom.keySet())
            if (username.equals(name))
                throw new UserNameAlreadyTakenException();

        playersRoom.put(username, connection);

    }

    public Map<String, Connection> getPlayersRoom() {
        return playersRoom;
    }

    public void setPlayersRoom(Map<String, Connection> playersRoom) {
        this.playersRoom = playersRoom;
    }

    /**
     * This method starts a multi player match.
     */
    public void startMultiPlayerMatch() {

        running = true;

        IControllerMaster controllerMaster = new ControllerMaster();

        playersRoom.entrySet().forEach(entry -> {
            PlayerColor playerColor = PlayerColor.BLUE;
            entry.getValue().getServer().setPlayerColor(playerColor.selectRandomColor());
            entry.getValue().getServer().setController(controllerMaster);
        });



    }

    /**
     *
     */
    private void startSingleMatch() {

    }
}