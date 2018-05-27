package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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

    public void login(String username, Connection connection, String gameMode) throws UserNameAlreadyTakenException, TooManyUsersException {

        if(Integer.parseInt(gameMode) == SINGLEPLAYER_MODE)
            startSingleMatch();
        else if(Integer.parseInt(gameMode) == MULTIPLAYER_MODE) {

            if (playersRoom.size() == MAX_PLAYERS)
                throw new TooManyUsersException();

            addPlayer(username, connection);
        }

        checkNumberOfPlayers();

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
    public void addPlayer(String username, Connection connection) throws UserNameAlreadyTakenException {

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
     *
     */
    public void startMultiPlayerMatch() {

    }

    /**
     *
     */
    private void startSingleMatch() {

    }
}

