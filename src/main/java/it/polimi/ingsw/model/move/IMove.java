package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;

/**
 *
 */
public interface IMove {

    /**
     * @param manager
     * @param setUpInfoUnit
     */
    void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit);
}
