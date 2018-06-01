package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;

import java.util.*;

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
    private boolean running;

    public WaitingRoom() {
        playersRoom = new HashMap<>();
        running = false;
    }

    /**
     * This method is responsible for the login of a player.
     * @param username the player who wants to play.
     * @param connection the connection assigned to the player.
     * @param gameMode the type of match the players wants to play.
     * @throws UserNameAlreadyTakenException
     * @throws TooManyUsersException
     */
    public void login(String username, Connection connection, int gameMode) throws UserNameAlreadyTakenException, TooManyUsersException {
        if(!isRunning()) {
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
            //Show message "match already started"
            System.out.println("match already started or maximum number of player reached");
        }
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
                    setRunning(true);
                    System.out.println("running");
                    startMultiPlayerMatch();
                    System.out.println("match started");
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

    /**
     * This method starts a multi player match.
     * //TODO testing
     */
    public void startMultiPlayerMatch() {
        ControllerMaster controllerMaster = new ControllerMaster();
        Map<String, IFromServerToClient> playerMap = controllerMaster.getConnectedPlayers();
        controllerMaster.getCommonBoard().getDraftPool().populateDiceDraftPool(playerMap.size());
        System.out.println("map created");

        playersRoom.entrySet().forEach(entry -> {
            entry.getValue().getServer().setController(controllerMaster);
            playerMap.put(entry.getKey(), entry.getValue().getClient());
            controllerMaster.getCommonBoard().getPlayers().add(new Player(entry.getKey(),controllerMaster.getCommonBoard()));
        });
        ((StartGameManager)controllerMaster.getStartGameManager()).chooseWindowPatternCard();
        System.out.println("maps????");
    }

    /**
     *
     */
    private void startSingleMatch() {

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Map<String, Connection> getPlayersRoom() {
        return playersRoom;
    }

    public void setPlayersRoom(Map<String, Connection> playersRoom) {
        this.playersRoom = playersRoom;
    }
}