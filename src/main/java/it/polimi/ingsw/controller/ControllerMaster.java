package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.network.IFromServerToClient;

import java.util.HashMap;
import java.util.Map;


/**
 * This class
 */
public class ControllerMaster {

    /**
     *
     */
    private final CommonBoard commonBoard;

    /**
     *
     */
    private IGameState currentGameState;

    /**
     *
     */
    private final Map<String, IFromServerToClient> connectedPlayers;

    /**
     *
     */
    private StartGameState startGameState;

    public ControllerMaster() {
        commonBoard = new CommonBoard();
        startGameState = new StartGameState(this);
        connectedPlayers = new HashMap<>();
        currentGameState = startGameState;
    }

    public IGameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(IGameState currentGameState) {
        this.currentGameState = currentGameState;
    }

    public CommonBoard getCommonBoard() {
        return commonBoard;
    }

    public Map<String, IFromServerToClient> getConnectedPlayers() {
        return connectedPlayers;
    }

    public StartGameState getStartGameState() {
        return startGameState;
    }

    public void setStartGameState(StartGameState startGameState) {
        this.startGameState = startGameState;
    }
}
