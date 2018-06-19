package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.managers.StartGameManager;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.util.*;
import java.util.logging.Level;

/**
 * This class manages the waiting players before the match starts.
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
     * This attribute prevents other players to connect to a already started match if set to true.
     */
    private boolean matchAlreadyStarted;

    /**
     * This constructor is used in all new games starting without any player already in.
     */
    public WaitingRoom() {
        playersRoom = new HashMap<>();
        matchAlreadyStarted = false;
    }

    /**
     * This method is responsible for the register of a player.
     * @param username the player who wants to play.
     * @param connection the connection assigned to the player.
     * @param gameMode the type of match the players wants to play.
     * @throws UserNameAlreadyTakenException when an user with the same username is already in the room.
     * @throws TooManyUsersException when an user tries to connect to an already full {@link WaitingRoom}.
     * @throws MatchAlreadyStartedException when an user tries to connect to an already started match.
     */
    void joinRoom(String username, Connection connection, int gameMode) throws UserNameAlreadyTakenException,
            TooManyUsersException, MatchAlreadyStartedException {
        if(!isMatchAlreadyStarted()) {
            if(gameMode == SINGLEPLAYER_MODE)
                startSingleMatch();
            else if(gameMode == MULTIPLAYER_MODE) {
                if (playersRoom.size() == MAX_PLAYERS)
                    throw new TooManyUsersException();
                addPlayer(username, connection);
            }
            notifyWaitingPlayers();
            checkNumberOfPlayers();
        } else {
            throw new MatchAlreadyStartedException();
        }
    }

    /**
     * This method allows the connected clients of a multi player match to see other connecting players.
     */
    public void notifyWaitingPlayers() {
        List<String> listName = new ArrayList<>();
        playersRoom.forEach((key, value) -> listName.add(key));
        playersRoom.forEach((key, value) -> {
            try {
                value.getClient().showRoom(listName);
            } catch (BrokenConnectionException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * This method checks if the minimum player for a multi-player match is reached, then starts the timer.
     */
    private void checkNumberOfPlayers() {
        if(playersRoom.size() == 2) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    SagradaLogger.log(Level.WARNING, "timer is expired");
                    setMatchAlreadyStarted(true);
                    startMultiPlayerMatch();
                }
            }, 5 * (long)1000);
        }
    }

    /**
     * This method adds a player to the waiting room.
     * @param username the name of the player to addDie.
     * @param connection the connection of the player to addDie.
     * @throws UserNameAlreadyTakenException when a player with a same username is already present.
     */
    public synchronized void addPlayer(String username, Connection connection) throws UserNameAlreadyTakenException {
        for (String name : playersRoom.keySet())
            if (username.equals(name))
                throw new UserNameAlreadyTakenException();
        playersRoom.put(username, connection);
    }

    /**
     * This method starts a multi player match.
     * //TODO testing
     */
    private void startMultiPlayerMatch() {
        ControllerMaster controllerMaster = new ControllerMaster(this.playersRoom, this);

        for(Map.Entry<String, Connection> entry: controllerMaster.getConnectedPlayers().entrySet()) {
            controllerMaster.getCommonBoard().getPlayers().add(
                    new Player(entry.getKey(), controllerMaster.getCommonBoard()));
            entry.getValue().getServer().setController(controllerMaster);
        }

        ((StartGameManager)controllerMaster.getStartGameManager()).setUpPrivateObjectiveCardAndWp();
    }

    /**
     *
     */
    private void startSingleMatch() {
        //Implement this to handle single player.
    }

    public boolean isMatchAlreadyStarted() {
        return matchAlreadyStarted;
    }

    public void setMatchAlreadyStarted(boolean matchAlreadyStarted) {
        this.matchAlreadyStarted = matchAlreadyStarted;
    }

    public Map<String, Connection> getPlayersRoom() {
        return playersRoom;
    }

    public void setPlayersRoom(Map<String, Connection> playersRoom) {
        this.playersRoom = playersRoom;
    }
}