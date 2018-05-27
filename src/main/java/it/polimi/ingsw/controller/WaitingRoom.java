package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;

import java.util.Map;

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

    /**
     * This method adds a player to the waiting room.
     * @param username the name of the player to add.
     * @param connection the connection of the player to add.
     * @throws UserNameAlreadyTakenException
     * @throws TooManyUsersException
     */
    public void addPlayer(String username, Connection connection, int gameMode) throws UserNameAlreadyTakenException, TooManyUsersException {
        
            if(gameMode == SINGLEPLAYER_MODE)
                startSingleMatch();
            else if(gameMode == MULTIPLAYER_MODE) {


                for (String name : playersRoom.keySet())
                    if (username.equals(name))
                        throw new UserNameAlreadyTakenException();

                if (playersRoom.size() == MAX_PLAYERS)
                    throw new TooManyUsersException();

                playersRoom.put(username, connection);

            }
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
    private void startSingleMatch() {

    }
}

