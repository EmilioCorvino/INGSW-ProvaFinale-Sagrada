package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.network.IFromServerToClient;

import java.util.HashMap;
import java.util.Map;


/**
 * This class
 */
public class ControllerMaster implements IControllerMaster {

    /**
     * This is the reference to the common board of the game.
     */
    private final CommonBoard commonBoard;

    /**
     * This is the attribute that maps each username with the correspondent client reference.
     */
    private final Map<String, IFromServerToClient> connectedPlayers;

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


    public ControllerMaster() {
        commonBoard = new CommonBoard();
        startGameManager = new StartGameManager(this);
        gamePlayManager = new GamePlayManager(this);
        endGameManager = new EndGameManager(this);
        connectedPlayers = new HashMap<>();
    }

    public CommonBoard getCommonBoard() {
        return commonBoard;
    }

    public Map<String, IFromServerToClient> getConnectedPlayers() {
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

    //todo handle reconnection
}