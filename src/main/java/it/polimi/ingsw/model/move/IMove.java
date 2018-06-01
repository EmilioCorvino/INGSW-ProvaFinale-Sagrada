package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.GamePlayManager;
import it.polimi.ingsw.controller.IOController;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
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
     void executeMove(CommonBoard commonBoard, IOController ioController);

    /**
     * @param commonBoard
     * @param setUpInfoUnit
     */
    void executeMove(GamePlayManager commonBoard, SetUpInformationUnit setUpInfoUnit);
}
