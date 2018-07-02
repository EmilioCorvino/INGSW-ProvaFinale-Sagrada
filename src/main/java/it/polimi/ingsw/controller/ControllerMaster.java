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
     * List of the suspended player after a problem of connection.
     */
    private List<String> suspendedPlayers;

    /**
     * Room where player wait for a match to start.
     */
    private WaitingRoom waitingRoom;


    ControllerMaster(Map<String, Connection> connectedPlayers, WaitingRoom waitingRoom) {
        this.waitingRoom = waitingRoom;
        this.commonBoard = new CommonBoard();
        this.commonBoard.initializeBoard();
        this.startGameManager = new StartGameManager(this);
        this.gamePlayManager = new GamePlayManager(this);
        this.endGameManager = new EndGameManager(this);
        this.connectedPlayers = connectedPlayers;
        this.gameState = new GameState();
        this.suspendedPlayers = new ArrayList<>();
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

    /**
     * This method is used when a player disconnects or takes too much time to complete his turn. It adds the player to
     * the {@link ControllerMaster#suspendedPlayers} list and allows to skip his turns, considering him in the
     * final score anyway.
     * @param playerName player to suspend.
     */
    public void suspendPlayer(String playerName) {
        if (!this.suspendedPlayers.contains(playerName)) {
            this.suspendedPlayers.add(playerName);
            IFromServerToClient client = this.getConnectedPlayers().get(playerName).getClient();
            if (this.getStartGameManager().isMatchRunning() && this.getGameState().getCurrentPlayer().getPlayerName().equals(playerName)) {
                this.getGamePlayManager().endTurn("\nIl tempo a tua disposizione è terminato, sei stato sospeso per inattività.");
                try {
                    client.showCommand(Arrays.asList(Commands.RECONNECT, Commands.LOGOUT));
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Impossible to send reconnection commands: " + playerName +
                    "'s connection is dropped.");
                }
            }
            this.gamePlayManager.broadcastNotification("\n" + playerName + " è stato sospeso.");
        }
    }

    /**
     * This method is used when a player, who previously logged out or disconnected, tries to get into the game again.
     * If the player didn't get the opportunity to choose the {@link it.polimi.ingsw.model.die.containers.WindowPatternCard},
     * this method shows him the Common Board, including the random Window Pattern Card assigned to him.
     * @param playerName name of the player who wants to reconnect.
     * @see WaitingRoom
     */
    void reconnectPlayer(String playerName) {
        IFromServerToClient client = this.connectedPlayers.get(playerName).getClient();

        //If the player logged out or disconnected before choosing a wp, gives him the opportunity to do so.
        //The removal from suspended player is done in {@link StartGameManager} to avoid
        if (this.startGameManager.getPlayersDisconnectedBeforeCommonBoardSetting().contains(playerName)) {
            this.getStartGameManager().setOneCommonBoard(playerName);
            this.startGameManager.getPlayersDisconnectedBeforeCommonBoardSetting().remove(playerName);
        }

        if (this.getStartGameManager().isMatchRunning()) {
            this.suspendedPlayers.remove(playerName);
            try {
                client.showCommand(this.getGamePlayManager().getWaitingPlayersCommands());
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Connection lost with " + playerName + " while sending the new " +
                        "commands after reconnecting.", e);
                this.suspendPlayer(playerName);
            }

            this.gamePlayManager.broadcastNotification("\n" + playerName + " si è appena riconnesso!");
        } else {
            try {
                client.showNotice("\nLa partita è finita mentre eri sospeso, è impossibile riconnettersi.");
                client.showCommand(Collections.singletonList(Commands.LOGOUT));
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Connection lost with " + playerName + " while sending the new " +
                        "commands after reconnecting.", e);
                this.suspendPlayer(playerName);
            }
        }
    }
}
