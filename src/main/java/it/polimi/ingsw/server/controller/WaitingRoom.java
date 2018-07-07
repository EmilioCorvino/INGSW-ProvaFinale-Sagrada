package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.common.network.Connection;
import it.polimi.ingsw.common.utils.PropertyLoader;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.common.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.common.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.common.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.server.controller.managers.AGameManager;
import it.polimi.ingsw.server.model.player.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
    private static final int MULTI_PLAYER_MODE = 1;

    /**
     * Code for single player match mode.
     */
    private static final int SINGLE_PLAYER_MODE = 2;

    /**
     * The map for keeping in memory the player with its connection object.
     */
    private Map<String, Connection> playersRoom;

    /**
     * This attribute prevents other players to connect to a already started match if set to true.
     */
    private boolean matchAlreadyStarted;

    /**
     * The main controller class.
     */
    private ControllerMaster controllerMaster;

    /**
     * This constructor is used in all new games starting without any player already in.
     */
    public WaitingRoom() {
        playersRoom = new ConcurrentHashMap<>();
        matchAlreadyStarted = false;
    }

    private boolean isMatchStillWaitingToStart() {
        return !matchAlreadyStarted;
    }

    public void setMatchAlreadyStarted(boolean matchAlreadyStarted) {
        this.matchAlreadyStarted = matchAlreadyStarted;
    }

    public Map<String, Connection> getPlayersRoom() {
        return playersRoom;
    }

    /**
     * This method is responsible for the registration of a player. It can also make a player reconnect, if the match
     * is started and the username is the same as one of the players that have disconnected.
     * @param username the player who wants to play.
     * @param connection the connection assigned to the player.
     * @param gameMode the type of match the players wants to play.
     * @throws UserNameAlreadyTakenException when an user with the same username is already in the room.
     * @throws TooManyUsersException when an user tries to connect to an already full {@link WaitingRoom}.
     * @throws MatchAlreadyStartedException when an user tries to connect to an already started match.
     */
    void joinRoom(String username, Connection connection, int gameMode) throws UserNameAlreadyTakenException,
            TooManyUsersException, MatchAlreadyStartedException {
        if(isMatchStillWaitingToStart()) {
            if(gameMode == SINGLE_PLAYER_MODE)
                startSingleMatch();
            else if(gameMode == MULTI_PLAYER_MODE) {
                if (playersRoom.size() == MAX_PLAYERS) {
                    throw new TooManyUsersException();
                }
                addPlayer(username, connection);
            }
            notifyWaitingPlayers();
            checkNumberOfPlayers();
        } else {
            if (this.playersRoom.containsKey(username) && this.controllerMaster.getDisconnectedPlayers().contains(username)) {
                this.playersRoom.replace(username, connection);
                this.controllerMaster.reconnectPlayer(username, connection);
            } else {
                throw new MatchAlreadyStartedException();
            }
        }
    }

    /**
     * This method allows the connected clients of a multi player match to see other connecting players.
     * If a player disconnects in this phase, he just gets removed from the room.
     */
    public void notifyWaitingPlayers() {
        List<String> listName = new ArrayList<>();
        playersRoom.forEach((playerName, connection) -> listName.add(playerName));
        playersRoom.forEach((playerName, connection) -> {
            try {
                connection.getClient().showRoom(listName);
            } catch (BrokenConnectionException e) {
                this.playersRoom.remove(playerName);
                notifyWaitingPlayers();
            }
        });
    }

    /**
     * This method checks if the minimum player for a multi-player match is reached, then starts the timer.
     */
    private void checkNumberOfPlayers() {
        if (playersRoom.size() == 2) {
            this.startTimer();
        } else if (playersRoom.size() == MAX_PLAYERS) {
            SagradaLogger.log(Level.WARNING, "Maximum number of players reached, starting match...");
            reportStartMatch();
            if(playersRoom.size() >= 2) {
                startMultiPlayerMatch();
            }
        }
    }

    /**
     * This method adds a player to the waiting room, in normal conditions. If the name of choice is already used and
     * the match still has to start, this method throws an exception. If the name is taken and the match is started, the
     * player will be able to reconnect.
     * @param username the name of the player to add.
     * @param connection the connection of the player to add.
     * @throws UserNameAlreadyTakenException when a player with a same username is already present.
     */
    private synchronized void addPlayer(String username, Connection connection) throws UserNameAlreadyTakenException {
        for (String name : playersRoom.keySet())
            if (username.equals(name) && this.isMatchStillWaitingToStart()) {
                throw new UserNameAlreadyTakenException();
            } else if (username.equals(name) && this.controllerMaster.getDisconnectedPlayers().contains(username)
                    && !this.isMatchStillWaitingToStart()) {
                this.playersRoom.replace(username, connection);
                this.controllerMaster.reconnectPlayer(username, connection);
            }
        this.playersRoom.put(username, connection);
    }

    /**
     * This method starts a multi player match, creating a new {@link ControllerMaster}.
     */
    private void startMultiPlayerMatch() {
        this.controllerMaster = new ControllerMaster(new HashMap<>(this.playersRoom), this);
        this.setMatchAlreadyStarted(true);

        for (Map.Entry<String, Connection> entry: controllerMaster.getConnectedPlayers().entrySet()) {
            this.controllerMaster.getCommonBoard().getPlayers()
                    .add(new Player(entry.getKey(), this.controllerMaster.getCommonBoard()));
            entry.getValue().getServer().setController(this.controllerMaster);
            this.controllerMaster.getGamePlayManager().getDynamicCommands()
                    .put(entry.getKey(), new ArrayList<>(this.controllerMaster.getGamePlayManager().getCurrentPlayerCommands()));
        }

        this.controllerMaster.getStartGameManager().setUpPrivateObjectiveCardAndWp();
    }

    /**
     * This method signals that the match is about to start to each player. If any of them has disconnected,
     * it will remove them from the room and notify the others.
     */
    private void reportStartMatch() {
        playersRoom.forEach((playerName, connection) -> {
            try {
                connection.getClient().showNotice("\nLa partita sta per iniziare.\n");
            } catch (BrokenConnectionException e) {
                playersRoom.remove(playerName);
                notifyWaitingPlayers();
            }
        });
    }

    /**
     * Starts the timer of the waiting room. When it expires, the match starts.
     */
    public void startTimer() {
        Timer timer = new Timer();

        //Back up value.
        long timeOut = AGameManager.BACK_UP_TIMER;

        //Value read from file. If the reading fails, the back up value is used.
        if (PropertyLoader.getPropertyLoader().getRoomTimer() != 0) {
            timeOut = PropertyLoader.getPropertyLoader().getRoomTimer();
            SagradaLogger.log(Level.CONFIG, "Waiting Room timer successfully loaded from file. " +
                    "Its value is: " + timeOut/1000 + "s");
        }
        SagradaLogger.log(Level.INFO, "Waiting Room timer is started.");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isMatchStillWaitingToStart()) {
                    SagradaLogger.log(Level.WARNING, "timer is expired");
                    reportStartMatch();
                    if (playersRoom.size()>= 2) {
                        startMultiPlayerMatch();
                    }
                }
            }
        }, timeOut);
    }

    /**
     * This method needs to be implemented in order to start a single player match.
     */
    private void startSingleMatch() {
        //Implement this to handle single player.
    }
}
