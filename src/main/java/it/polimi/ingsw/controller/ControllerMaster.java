package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.managers.EndGameManager;
import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.managers.StartGameManager;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.turn.GameState;
import it.polimi.ingsw.network.Connection;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.SagradaLogger;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;

import java.util.*;
import java.util.logging.Level;


/**
 * This class represents the part of the controller that manages all its other components and the possible flow of the game.
 */
public class ControllerMaster {

    /**
     * This is the reference to the common board of the game.
     */
    private final CommonBoard commonBoard;

    /**
     * This is the attribute that maps each username with the correspondent client reference.
     */
    private final Map<String, Connection> connectedPlayers;

    /**
     * This attribute represents the manager for the first state of the game.
     */
    private final StartGameManager startGameManager;

    /**
     * This attribute represents the manager for the second state of the game.
     */
    private final GamePlayManager gamePlayManager;

    /**
     * This attribute represents the manager for the last state of the game.
     */
    private final EndGameManager endGameManager;

    /**
     * This attribute represents a specific state of the match.
     */
    private GameState gameState;

    /**
     * List of the suspended players after prolonged inactivity. They could also be inserted here for connection
     * problems. In that case, they will be in {@link #disconnectedPlayers} too.
     */
    private List<String> suspendedPlayers;

    /**
     * List of the disconnected players.
     */
    private List<String> disconnectedPlayers;

    /**
     * Room where player wait for a match to start.
     */
    private WaitingRoom waitingRoom;


    ControllerMaster(Map<String, Connection> connectedPlayers, WaitingRoom waitingRoom) {
        this.waitingRoom = waitingRoom;
        this.commonBoard = new CommonBoard();
        this.commonBoard.initializeBoard();
        this.connectedPlayers = connectedPlayers;
        this.startGameManager = new StartGameManager(this);
        this.gamePlayManager = new GamePlayManager(this);
        this.endGameManager = new EndGameManager(this);
        this.gameState = new GameState();
        this.suspendedPlayers = new ArrayList<>();
        this.disconnectedPlayers = new ArrayList<>();
    }

    public CommonBoard getCommonBoard() {
        return commonBoard;
    }

    public Map<String, Connection> getConnectedPlayers() {
        return connectedPlayers;
    }

    public StartGameManager getStartGameManager() {
        return startGameManager;
    }

    public GamePlayManager getGamePlayManager() {
        return gamePlayManager;
    }

    public EndGameManager getEndGameManager() {
        return endGameManager;
    }

    public GameState getGameState() {
        return gameState;
    }

    public WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }

    public List<String> getSuspendedPlayers() {
        return suspendedPlayers;
    }

    public List<String> getDisconnectedPlayers() {
        return disconnectedPlayers;
    }

    /**
     * This method is used when a player disconnects or takes too much time to complete his turn. It adds the player to
     * the {@link ControllerMaster#suspendedPlayers} list and allows to skip his turns, considering him in the
     * final score anyway.
     * @param playerName player to suspend.
     * @param disconnected flag that signals if the suspension is due to a disconnection or not.
     */
    public synchronized void suspendPlayer(String playerName, boolean disconnected) {
        if (!this.suspendedPlayers.contains(playerName)) {
            this.suspendedPlayers.add(playerName);
            if (disconnected && !this.disconnectedPlayers.contains(playerName)) {
                this.disconnectedPlayers.add(playerName);
            }
            IFromServerToClient client = this.getConnectedPlayers().get(playerName).getClient();

            //Case with player just suspended.
            if (this.getStartGameManager().isMatchRunning() &&
                    this.getGameState().getCurrentPlayer().getPlayerName().equals(playerName) &&
                    !this.disconnectedPlayers.contains(playerName)) {
                this.getGamePlayManager().endTurn("\nIl tempo a tua disposizione è terminato, sei stato sospeso per inattività.");
                try {
                    client.showCommand(Arrays.asList(Commands.RECONNECT, Commands.LOGOUT));
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.INFO, "Impossible to send reconnection commands: " + playerName +
                    "'s connection is dropped.");
                    this.disconnectedPlayers.add(playerName);
                }
            }

            //Case with player disconnected
            else if (this.getStartGameManager().isMatchRunning() &&
                    this.getGameState().getCurrentPlayer().getPlayerName().equals(playerName) &&
                    this.disconnectedPlayers.contains(playerName)) {
                this.getGamePlayManager().endTurn(""); //The message is not important, since it cannot be sent.
            }

            this.gamePlayManager.broadcastNotification("\n" + playerName + " è stato sospeso.");
        } else {
            if (!this.disconnectedPlayers.contains(playerName)) {
                this.disconnectedPlayers.add(playerName);
            }
        }
    }

    /**
     * This method is used when a player, who previously logged out or disconnected, tries to get into the game again.
     * If the player didn't get the opportunity to choose the {@link it.polimi.ingsw.model.die.containers.WindowPatternCard},
     * this method shows him the Common Board, including the random Window Pattern Card assigned to him.
     * @param playerName name of the player who wants to reconnect.
     * @param connection connection established with the player. It is used only if the match is over when the attempt
     *                   at reconnection is done.
     * @see WaitingRoom
     */
    synchronized void reconnectPlayer(String playerName, Connection connection) {
        if (this.getStartGameManager().isMatchRunning()) {
            this.getConnectedPlayers().replace(playerName, connection);
            IFromServerToClient client = this.connectedPlayers.get(playerName).getClient();
            this.startGameManager.getPlayersDisconnectedBeforeCommonBoardSetting().remove(playerName);
            this.suspendedPlayers.remove(playerName);
            this.disconnectedPlayers.remove(playerName);
            this.getStartGameManager().setOneCommonBoard(playerName);
            try {
                client.showCommand(this.getGamePlayManager().getWaitingPlayersCommands());
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Connection lost with " + playerName + " while sending the new " +
                        "commands after reconnecting.");
                this.suspendPlayer(playerName, true);
            }

            this.gamePlayManager.broadcastNotification("\n" + playerName + " si è appena riconnesso!");
        } else {
            try {
                connection.getClient().showNotice("\nLa partita si è conclusa mentre eri sospeso, è impossibile riconnettersi.");
                connection.getClient().showCommand(Collections.singletonList(Commands.LOGOUT));
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Connection lost with " + playerName + " while sending the new " +
                        "commands after reconnecting.");
            }
        }
    }
}
