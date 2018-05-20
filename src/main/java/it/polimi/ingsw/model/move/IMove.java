package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.IOController;
import it.polimi.ingsw.model.CommonBoard;

/**
 *
 */
public interface IMove {

    /**
     *
     * @return
     * @param commonBoard
     * @param ioController
     */
    public void executeMove(CommonBoard commonBoard, IOController ioController);
}
