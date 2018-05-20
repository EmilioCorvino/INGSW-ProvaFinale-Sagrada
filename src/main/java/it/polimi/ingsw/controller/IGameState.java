package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.IFromServerToClient;

/**
 * This class collects all the useful methods to manage each state of the game.
 */
public interface IGameState {


    /**
     *
     * @param controllerMaster
     */
    public void changeGameState(ControllerMaster controllerMaster);
}
