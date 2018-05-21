package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.network.IFromServerToClient;

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
    private IFromServerToClient fromServerToClient;

    public ControllerMaster() {
        commonBoard = new CommonBoard();
        currentGameState = new StartGameState();
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

    public IFromServerToClient getFromServerToClient() {
        return fromServerToClient;
    }

    public void setFromServerToClient(IFromServerToClient fromServerToClient) {
        this.fromServerToClient = fromServerToClient;
    }
}
