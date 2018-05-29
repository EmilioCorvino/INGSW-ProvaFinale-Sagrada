package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.GamePlayManager;
import it.polimi.ingsw.controller.IOController;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.network.PlayerColor;

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
     * @param currentPlayer
     * @param commonBoard
     * @param setUpInfoUnit
     */
    void executeMove(PlayerColor currentPlayer, GamePlayManager commonBoard, SetUpInformationUnit setUpInfoUnit);
}
