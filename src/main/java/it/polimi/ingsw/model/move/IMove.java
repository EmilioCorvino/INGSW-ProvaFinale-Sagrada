package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;

/**
 *
 */
public interface IMove {

    static final String COMMANDS_HELP = " Digita 'comandi' per visualizzare i comandi ancora disponibili.";

    /**
     * @param manager
     * @param setUpInfoUnit
     */
    void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit);
}
