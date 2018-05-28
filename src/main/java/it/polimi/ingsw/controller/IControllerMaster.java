package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.PlayerColor;

/**
 *
 */
public interface IControllerMaster {

    /**
     *
     * @param playerColor
     */
    void analyzeMoveRequest(PlayerColor playerColor);
}
