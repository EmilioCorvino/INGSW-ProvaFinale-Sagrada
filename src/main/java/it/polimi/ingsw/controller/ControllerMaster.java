package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * This class represents the part of the controller that manages all its other components and the possible flow of the game.
 */
public class ControllerMaster implements IControllerMaster {

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
    private final AGameManager startGameManager;

    /**
     * This attribute represents the manager for the second state of the game.
     */
    private final AGameManager gamePlayManager;

    /**
     * This attribute represents the manager for the last state of the game.
     */
    private final AGameManager endGameManager;

    /**
     * This attribute represents a specific state of the match.
     */
    private GameState gameState;

    /**
     * List of the suspended player after a problem of connection.
     */
    private List<Player> suspendedPlayers;


    public ControllerMaster(Map<String, Connection> connectedPlayers) {
        commonBoard = new CommonBoard();
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

    public AGameManager getStartGameManager() {
        return startGameManager;
    }

    public AGameManager getGamePlayManager() {
        return gamePlayManager;
    }

    public AGameManager getEndGameManager() {
        return endGameManager;
    }



    public void startTurnPlayer(Player currPlayer) {
        ((GamePlayManager)this.gamePlayManager).startTurn(currPlayer);
    }

    public void checkMoveAvailability(String username) {
        if(((GamePlayManager)this.gamePlayManager).checkCurrentPlayer(username))
            ((GamePlayManager)gamePlayManager).givePlayerObjectTofill();

        else {
            //not valid
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    //todo handle reconnection
}